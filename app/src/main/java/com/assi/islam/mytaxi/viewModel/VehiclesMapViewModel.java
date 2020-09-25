package com.assi.islam.mytaxi.viewModel;

import android.util.Log;

import com.assi.islam.mytaxi.model.RideOption;
import com.assi.islam.mytaxi.model.directionsApi.Directions;
import com.assi.islam.mytaxi.model.directionsApi.Leg;
import com.assi.islam.mytaxi.model.directionsApi.Route;
import com.assi.islam.mytaxi.model.directionsApi.Step;
import com.assi.islam.mytaxi.repository.VehiclesRepository;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.maps.model.EncodedPolyline;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ViewModel for {@link com.assi.islam.mytaxi.ui.fragment.VehiclesMapFragment}
 *
 */

public class VehiclesMapViewModel extends ViewModel {

    private static final String TAG = VehiclesMapViewModel.class.getSimpleName();
    private Disposable lastPolyPointsDisposal;
    private MutableLiveData<Directions> polyPointsLiveData = new MutableLiveData<>();
    private List<RideOption> rideOptionList = new ArrayList<>();
    private Polyline lastDrawnPolyline;

    @Inject
    public VehiclesMapViewModel(VehiclesRepository vehiclesRepository) {

    }

    /**
     *
     * process Google Api Directions to generate list of points represent the path between current location
     * and selected vehicle
     *
     * data is processed in a computation thread
     * @param directions
     */
    public void getPolyPoints(Directions directions){
        if (lastPolyPointsDisposal != null && !lastPolyPointsDisposal.isDisposed())
            lastPolyPointsDisposal.dispose();
        lastPolyPointsDisposal = Single.just(directions)
                .map(directionsResponse -> processPolyPoints(directions))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(polyPointsLiveData::setValue);
    }


    /**
     * process Google Api Directions to generate list of points represent the path between current location
     * and selected vehicle
     *
     * data is processed in a computation thread
     * @param directions directions needs to be processed
     * @return directions after processing (containing path points)
     */
    @WorkerThread
    private Directions processPolyPoints(Directions directions){
        List<Route> routes = directions.getRoutes();
        List<LatLng> path = new ArrayList();
        try {
            if (routes != null && routes.size()>0) {
                Route route = routes.get(0);

                if (route.getLegs() !=null) {
                    for(Leg leg : route.getLegs()) {

                        if (leg.getSteps() != null) {
                            for (Step step: leg.getSteps()){

                                if (step.getPoints() != null && step.getPoints().size() >0) {

                                    for (LatLng coord : step.getPoints()) {
                                        path.add(coord);
                                    }

                                } else {
                                    EncodedPolyline polyline = step.getPolyline();
                                    if (polyline != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords1 = polyline.decodePath();
                                        for (com.google.maps.model.LatLng coord1 : coords1) {
                                            path.add(new LatLng(coord1.lat, coord1.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch(Exception ex) {
            Log.e(TAG, ex.getLocalizedMessage());
        }

        directions.setPathPoints(path);

        return directions;
    }

    public MutableLiveData<Directions> getPolyPointsLiveData() {
        return polyPointsLiveData;
    }

    public void setPolyPointsLiveData(MutableLiveData<Directions> polyPointsLiveData) {
        this.polyPointsLiveData = polyPointsLiveData;
    }

    public Polyline getLastDrawnPolyline() {
        return lastDrawnPolyline;
    }

    public void setLastDrawnPolyline(Polyline lastDrawnPolyline) {
        this.lastDrawnPolyline = lastDrawnPolyline;
    }

    public List<RideOption> getRideOptionList() {
        return rideOptionList;
    }

    public void setRideOptionList(List<RideOption> rideOptionList) {
        this.rideOptionList = rideOptionList;
    }
}
