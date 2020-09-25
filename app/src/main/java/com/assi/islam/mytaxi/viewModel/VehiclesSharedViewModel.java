package com.assi.islam.mytaxi.viewModel;

import android.location.Location;

import com.assi.islam.mytaxi.manager.LocationUpdateManager;
import com.assi.islam.mytaxi.model.ApiError;
import com.assi.islam.mytaxi.model.Coordinate;
import com.assi.islam.mytaxi.model.Resource;
import com.assi.islam.mytaxi.model.RideOption;
import com.assi.islam.mytaxi.repository.VehiclesRepository;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.assi.islam.mytaxi.constants.Constants.HAMBURG_EAST;
import static com.assi.islam.mytaxi.constants.Constants.HAMBURG_NORTH;
import static com.assi.islam.mytaxi.constants.Constants.HAMBURG_SOUTH;
import static com.assi.islam.mytaxi.constants.Constants.HAMBURG_WEST;

/**
 * Created by islam
 *
 * Shared view model between
 * {@link com.assi.islam.mytaxi.ui.fragment.VehiclesMapFragment}
 * and
 * {@link com.assi.islam.mytaxi.ui.fragment.VehicleListFragment}
 */
public class VehiclesSharedViewModel extends ViewModel {

    private MutableLiveData<RideOption> selectedRideOptionLiveData = new MutableLiveData<>();
    private VehiclesRepository vehiclesRepository;
    private MutableLiveData<Resource<List<RideOption>, ApiError>> rideOptionListResourceLiveData = new MutableLiveData<>();
    private LatLngBounds hamburgBounds = new LatLngBounds(new LatLng(HAMBURG_SOUTH, HAMBURG_WEST), new LatLng(HAMBURG_NORTH, HAMBURG_EAST));
    private static final String TAG = MainActivityViewModel.class.getSimpleName();

    @Inject
    public VehiclesSharedViewModel(VehiclesRepository vehiclesRepository) {
        this.vehiclesRepository = vehiclesRepository;
    }

    /**
     * request {@link RideOption} list with last user location
     * @param bounds
     */
    public void requestRideOptionsList(LatLngBounds bounds){
        LocationUpdateManager.getInstance().getLastLocation((isSuccess, result) -> {
            if (isSuccess) {
                // successfully get user location
                vehiclesRepository.loadRideOptions(bounds, new Coordinate(result.getLatitude(), result.getLongitude()), rideOptionListResourceLiveData);
            }else{
                // register observer that listen to location updates for one time and remove it self
                LocationUpdateManager.getInstance().addObserver(new Observer() {
                    @Override
                    public void update(Observable o, Object arg) {
                        vehiclesRepository.loadRideOptions(bounds, new Coordinate(((Location)arg).getLatitude(), ((Location)arg).getLongitude()), rideOptionListResourceLiveData);
                        LocationUpdateManager.getInstance().deleteObserver(this);
                    }
                });
                vehiclesRepository.loadRideOptions(bounds, null, rideOptionListResourceLiveData);
            }
        });
    }

    public void requestHamburgRideOptions(){
        requestRideOptionsList(hamburgBounds);
    }

    public MutableLiveData<Resource<List<RideOption>, ApiError>> getRideOptionListResourceLiveData() {
        return rideOptionListResourceLiveData;
    }


    public LatLngBounds getHamburgBounds() {
        return hamburgBounds;
    }

    public void setHamburgBounds(LatLngBounds hamburgBounds) {
        this.hamburgBounds = hamburgBounds;
    }

    public MutableLiveData<RideOption> getSelectedRideOptionLiveData() {
        return selectedRideOptionLiveData;
    }

    public void setSelectedRideOptionLiveData(MutableLiveData<RideOption> selectedRideOptionLiveData) {
        this.selectedRideOptionLiveData = selectedRideOptionLiveData;
    }
}
