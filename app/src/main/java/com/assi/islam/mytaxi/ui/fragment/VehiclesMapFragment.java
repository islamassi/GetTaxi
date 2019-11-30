package com.assi.islam.mytaxi.ui.fragment;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assi.islam.mytaxi.databinding.VehiclesMapFragmentBinding;
import com.assi.islam.mytaxi.manager.LocationUpdateManager;
import com.assi.islam.mytaxi.model.ApiError;
import com.assi.islam.mytaxi.model.Resource;
import com.assi.islam.mytaxi.model.RideOption;
import com.assi.islam.mytaxi.model.Vehicle;
import com.assi.islam.mytaxi.model.directionsApi.Directions;
import com.assi.islam.mytaxi.viewModel.MainActivityViewModel;
import com.assi.islam.mytaxi.viewModel.RideOptionCardViewModel;
import com.assi.islam.mytaxi.viewModel.VehiclesSharedViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.assi.islam.mytaxi.R;
import com.assi.islam.mytaxi.dagger.component.DaggerAppComponent;
import com.assi.islam.mytaxi.viewModel.VehiclesMapViewModel;
import com.assi.islam.mytaxi.viewModel.ViewModelFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by islam assi
 *
 * Fragment with ride options available to the user shown on the map
 */
public class VehiclesMapFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMarkerClickListener,
        Observer<Resource<List<RideOption>, ApiError>>
{

    private static final String TAG = VehiclesMapFragment.class.getSimpleName();

    private VehiclesMapViewModel mViewModel;

    private VehiclesSharedViewModel mSharedViewModel;

    private GoogleMap mMap;

    @Inject
    ViewModelFactory mViewModelFactory;

    private VehiclesMapFragmentBinding mBinding;

    private int cameraMoveReason = -1;

    // keeps a reference to the added markers
    private ArrayList<Marker> rideOptionMarkeres = new ArrayList<>();

    private final int MAP_PADDING = 90;

    private boolean isMapBoundRequest = false;

    public static VehiclesMapFragment newInstance() {
        return new VehiclesMapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.vehicles_map_fragment, container, false);

        DaggerAppComponent.create().inject(this);

        mBinding.setLifecycleOwner(this);

        return mBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewModel();

        FragmentManager fm = getChildFragmentManager();

        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");

        if (mapFragment == null) {

            mapFragment = new SupportMapFragment();

            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.map_fragment_container, mapFragment, "mapFragment");
            ft.commit();
            fm.executePendingTransactions();
        }

        mapFragment.getMapAsync(this);

    }

    private void initViewModel(){

        // fragment scope
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(VehiclesMapViewModel.class);

        // activity scope
        mSharedViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(VehiclesSharedViewModel.class);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mSharedViewModel.getRideOptionListResourceLiveData().observe(this, this);

        mSharedViewModel.getSelectedRideOptionLiveData().observe(this, this::selectRideOption);

        mViewModel.getPolyPointsLiveData().observe(this, processedPolyPointsObserver);

        initMap();

        mBinding.setViewModel(mViewModel);

        initWithRideOption();
    }

    private void initMap() {

        if (LocationUpdateManager.getInstance().isPermissionGranted()) {

            mMap.setMyLocationEnabled(true);

            mMap.getUiSettings().setMyLocationButtonEnabled(true);

            mMap.getUiSettings().setAllGesturesEnabled(true);

        }else{

            // Trying one more time to get Location updates in case user denied in the previous screen
            LocationUpdateManager.getInstance().startLocationService();
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mSharedViewModel.getHamburgBounds(), 0));

        registerMapEventListeners();
    }

    private void initWithRideOption(){

        RideOption selectedRideOption = mSharedViewModel.getSelectedRideOptionLiveData().getValue();

        if (selectedRideOption != null) {

            if (selectedRideOption.getDirections() == null) {

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        selectedRideOption.getVehicle().getCoordinate().toLatLng(),
                        10)
                );

            } else {

                drawPolyLine(selectedRideOption.getDirections());
            }
        }
    }

    private void registerMapEventListeners() {

        mMap.setOnMyLocationButtonClickListener(this);

        mMap.setOnMyLocationClickListener(this);

        mMap.setOnCameraIdleListener(this);

        mMap.setOnCameraMoveStartedListener(this);

        mMap.setOnMarkerClickListener(this);
    }

    /**
     * adds taxi marker on the map
     * @param rideOption
     */
    private void addMarker(RideOption rideOption) {

        Vehicle vehicle = rideOption.getVehicle();

        Marker marker = mMap.addMarker(
                new MarkerOptions()
                        .position(vehicle
                                .getCoordinate()
                                .toLatLng())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_taxi_marker))
                        .flat(true)
                        .rotation((float) (vehicle.getHeading()*-1) +90)
        );

        marker.setTag(rideOption);

        rideOptionMarkeres.add(marker);
    }

    /**
     * clear markers
     */
    private void clearRideOptionMarkers() {

        for (Marker marker: rideOptionMarkeres) marker.remove();
    }

    private void addRideOptionMarkers(List<RideOption> rideOptionList) {

        for (RideOption rideOption: rideOptionList) addMarker(rideOption);
    }

    @Override
    public void onCameraMoveStarted(int reason) {

        // track the reason behind camera move
        cameraMoveReason = reason;
    }

    @Override
    public void onCameraIdle() {

        if (cameraMoveReason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            requestRideOptionList();
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

        requestRideOptionList();
    }


    @Override
    public boolean onMyLocationButtonClick() {

        requestRideOptionList();

        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (marker.getTag()!= null && marker.getTag() instanceof RideOption) {

            RideOption rideOption = (RideOption) marker.getTag();

            mSharedViewModel.getSelectedRideOptionLiveData().setValue(rideOption);

            if (rideOption.getDirections()!= null) {

                return true;
            }

        }
        return false;
    }

    /**
     * set selected RideOption object and bind RideOption card
     * @param rideOption
     */
    private void selectRideOption(RideOption rideOption) {

        mBinding.selectedRideOptionCard.setVisibility(View.VISIBLE);

        updateMapBottomPadding();

        mBinding.selectedRideOptionCard.setViewModel(new RideOptionCardViewModel(rideOption));

        drawPolyLine(rideOption.getDirections());
    }

    /**
     * request list of {@link RideOption} in camera {@link LatLngBounds}
     */
    private void requestRideOptionList(){

        isMapBoundRequest = true;

        LatLngBounds latLngBounds = mMap.getProjection().getVisibleRegion().latLngBounds;

        mSharedViewModel.requestRideOptionsList(latLngBounds);
    }

    /**
     * draw polyline between selected {@link RideOption} and user's current location
     *
     * @param directions directions result that were retrived from Google Directions Api
     */
    private void drawPolyLine(Directions directions){

        removeLastPolyLine();

        if (directions == null || directions.getRoutes() == null )
            return;

        if (directions.getPathPoints() != null && !directions.getPathPoints().isEmpty()) {

            // polyLine points was previously processed and they are ready to be drawn
            processedPolyPointsObserver.onChanged(directions);

        }else{

            // parse {@link directions} to get poly points to draw and notify observer
            mViewModel.getPolyPoints(directions);
        }
    }

    /**
     * removes last drawn polyLine if exists
     */
    private void removeLastPolyLine() {

        if (mViewModel.getLastDrawnPolyline() != null)
            mViewModel.getLastDrawnPolyline().remove();
    }

    /**
     * Observing resource status
     *
     * resource for requested {@link RideOption} list
     *
     *
     * SUCCESS status means the request finished loading successfully
     * LOADING status means ride options is still being processed. Every call contains the new processed objects
     * ERROR error occured
     *
     *  For more info, check {@link com.assi.islam.mytaxi.repository.VehiclesRepository:loadRideOptions}
     * @param resource
     */
    @Override
    public void onChanged(Resource<List<RideOption>, ApiError> resource) {

        if (resource.status == Resource.Status.SUCCESS || resource.status == Resource.Status.LOADING){

            handleNewList(resource.data);

            if (resource.isNewRequest) {

                clearRideOptionMarkers();

                removeLastPolyLine();

                mBinding.selectedRideOptionCard.setVisibility(View.GONE);

                mMap.setPadding(0,0,0,0);

                if (!isMapBoundRequest){

                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mSharedViewModel.getHamburgBounds(), 0));

                }

                isMapBoundRequest = false;
            }

        }else if(resource.status == Resource.Status.ERROR){

        }
    }

    /**
     * This method handles the new RideOption list
     *
     * @param newList the new RideOptionList that is loaded from service request
     *
     */
    private void handleNewList(List<RideOption> newList) {

        List<RideOption> oldList = mViewModel.getRideOptionList();

        for (RideOption rideOption: newList){

            int index = oldList.indexOf(rideOption);

            if (index == -1){

                addMarker(rideOption);
            }
        }

        mViewModel.setRideOptionList(new ArrayList<>(newList));

    }

    /**
     * get notified when finish processing polyLine points
     */
    private Observer<Directions> processedPolyPointsObserver = processedDirections -> {

        List<LatLng> path = processedDirections.getPathPoints();

        //Draw the polyline
        if (path.size() > 0) {

            removeLastPolyLine();

            PolylineOptions opts = new PolylineOptions().addAll(path).color(ContextCompat.getColor(getActivity(), R.color.orange)).width(7);

            mViewModel.setLastDrawnPolyline(mMap.addPolyline(opts));

            if (processedDirections.getRoutes().get(0).getBounds() != null && processedDirections.getRoutes().get(0).getBounds().toLatLngBounds() != null){

                updateMapBottomPadding();

                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(processedDirections.getRoutes().get(0).getBounds().toLatLngBounds(), MAP_PADDING));
            }
        }

    };

    private void updateMapBottomPadding() {

        // more bottom padding because of the shown card
        mMap.setPadding(MAP_PADDING, MAP_PADDING, MAP_PADDING,  mBinding.selectedRideOptionCard.getHeight());
    }
}
