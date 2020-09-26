package com.assi.islam.mytaxi.repository;

import com.assi.islam.mytaxi.api.ApiResponse;
import com.assi.islam.mytaxi.api.webService.Webservice;
import com.assi.islam.mytaxi.manager.IdlingResourceManager;
import com.assi.islam.mytaxi.model.ApiError;
import com.assi.islam.mytaxi.model.Coordinate;
import com.assi.islam.mytaxi.model.Resource;
import com.assi.islam.mytaxi.model.RideOption;
import com.assi.islam.mytaxi.model.Vehicle;
import com.assi.islam.mytaxi.model.VehiclesListResponse;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.MutableLiveData;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by islam assi
 *
 * Repository for managing {@link Vehicle} and {@link RideOption}
 *
 * load vehicles and request more info from direction api for every vehicle
 */

@Singleton
public class VehiclesRepository {

    private final Webservice webservice;
    private final DirectionsApiRepository directionsApiRepository;
    private Disposable lastRideOptionMapperDisposal;
    private Disposable lastRideOptionDetailsDisposal;

    @Inject
    public VehiclesRepository(Webservice webservice, DirectionsApiRepository directionsApiRepository) {
        this.webservice = webservice;
        this.directionsApiRepository = directionsApiRepository;
    }

    /**
     * load {@link Vehicle} list in a latLng bounds
     */
    public void loadVehicles(LatLngBounds bounds, Coordinate riderCoordinate, Consumer<RideOption> onSuccess, Consumer<Throwable> onError, Action onComplete){
        lastRideOptionDetailsDisposal = webservice.geyVehicles(
                bounds.northeast.latitude, bounds.northeast.longitude,
                bounds.southwest.latitude, bounds.southwest.longitude)
                .subscribeOn(Schedulers.io())
                .flatMap(vehiclesResp -> {
                    if (vehiclesResp!= null && vehiclesResp.isSuccessful() && vehiclesResp.body()!= null)
                        return Observable.fromIterable(vehiclesResp.body().getVehicles());
                    return null;
                }).flatMap(vehicle -> {
                    if (riderCoordinate != null)
                        return requestRideOptionDetails(vehicle, riderCoordinate);
                    else
                        return Observable.just(new RideOption(vehicle));
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError, onComplete);
    }

    /**
     * request more details for a {@link Vehicle} and map it to {@link RideOption}
     * @param vehicle
     * @param riderCoordinates
     * @return
     */
    public ObservableSource<RideOption> requestRideOptionDetails(Vehicle vehicle, Coordinate riderCoordinates){
        return Observable.zip(
                Observable.just(vehicle),
                directionsApiRepository.getDirections(vehicle.getCoordinate().toLatLng(), riderCoordinates.toLatLng()),
                (vehicle1, directionsResponse) -> new RideOption(vehicle1, riderCoordinates, directionsResponse.body()));
    }

    /**
     * requesting {@link RideOption} list
     * 1- request {@link Vehicle} list
     * 2- getting more details for every {@link Vehicle} object from Google Directions Api
     * 3- map {@link Vehicle} list to {@link RideOption} list
     * @param rideOptionsResourceLiveData for notifying a subscribed lifecycle owner
     */
    public void loadRideOptions(LatLngBounds bounds, Coordinate riderCoordinates, MutableLiveData<Resource<List<RideOption>, ApiError>> rideOptionsResourceLiveData){
        IdlingResourceManager.getInstance().getServicesIdlingResource().increment();
        dispose(lastRideOptionMapperDisposal, lastRideOptionDetailsDisposal);
        List<RideOption> rideOptionList = new ArrayList<>();
        Consumer<RideOption> prepareRideOptinOnSuccess = new Consumer<RideOption>() {
            boolean isNewRequest = true;
            @Override
            public void accept(RideOption rideOption) throws Exception {
                rideOptionList.add(rideOption);
                rideOptionsResourceLiveData.setValue(Resource.loading(rideOptionList, isNewRequest));
                isNewRequest = false;
            }
        };
        Consumer<Throwable> prepareRideOptinOnError = throwable -> {
            throwable.toString();
        };
        Action rideOptionListComplete = () -> {
            rideOptionsResourceLiveData.setValue(Resource.success(rideOptionList));
            IdlingResourceManager.getInstance().getServicesIdlingResource().decrement();
        };
        loadVehicles(bounds, riderCoordinates, prepareRideOptinOnSuccess, prepareRideOptinOnError, rideOptionListComplete);
    }

    /**
     * dispose observers
     */
    private void dispose(Disposable... disposables){
        for (Disposable disposable: disposables) {
            if (disposable != null && !disposable.isDisposed()){
                disposable.dispose();
            }
        }
    }
}
