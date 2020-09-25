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
 * Created by islam assi
 *
 * view model for {@link com.assi.islam.mytaxi.ui.activity.MainActivity}
 */
public class MainActivityViewModel extends ViewModel {
    private boolean isMapView = false;

    @Inject
    public MainActivityViewModel() { }
    public boolean isMapView() {
        return isMapView;
    }
    public void setMapView(boolean mapView) {
        isMapView = mapView;
    }
}
