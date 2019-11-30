package com.assi.islam.mytaxi.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.assi.islam.mytaxi.R;
import com.assi.islam.mytaxi.constants.Constants;
import com.assi.islam.mytaxi.dagger.component.DaggerAppComponent;
import com.assi.islam.mytaxi.ui.fragment.VehicleListFragment;
import com.assi.islam.mytaxi.ui.fragment.VehiclesMapFragment;
import com.assi.islam.mytaxi.manager.LocationUpdateManager;
import com.assi.islam.mytaxi.model.RideOption;
import com.assi.islam.mytaxi.viewModel.MainActivityViewModel;
import com.assi.islam.mytaxi.viewModel.ViewModelFactory;
import com.polyak.iconswitch.IconSwitch;

import javax.inject.Inject;

/**
 * Created by islam assi
 *
 * Main app activity and the entry point to the app
 */
public class MainActivity extends AppCompatActivity implements IconSwitch.CheckedChangeListener {

    private LocationUpdateManager locationUpdateManager;

    private final String REQUESTING_LOCATION_SETTINGS_KEY = "REQUESTING_LOCATION_SETTINGS_KEY";

    private final String REQUESTING_LOCATION_PERMISSION_KEY = "REQUESTING_LOCATION_PERMISSION_KEY";

    private boolean canRequestLocationSettings = true;

    private boolean canRequestLocationPermission = true;

    @Inject
    ViewModelFactory mViewModelFactory;

    private MainActivityViewModel mViewModel;

    private IconSwitch iconSwitch;

    private VehicleListFragment vehicleListFragment;

    private VehiclesMapFragment vehiclesMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        DaggerAppComponent.create().inject(this);

        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainActivityViewModel.class);

        locationUpdateManager = LocationUpdateManager.getInstance();

        locationUpdateManager.init(this);

        updateValuesFromBundle(savedInstanceState);

        if (savedInstanceState == null) {

            if (!mViewModel.isMapView()) {
                showListFragment();
            }else{
                showMapFragment();
            }
        }

        iconSwitch = findViewById(R.id.mapListSwitch);

        iconSwitch.setCheckedChangeListener(this);
    }

    public void showVehiclesMapFragment(){

        iconSwitch.toggle();
    }

    private void showMapFragment() {

        mViewModel.setMapView(true);

        if (vehiclesMapFragment == null) {
            vehiclesMapFragment = VehiclesMapFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, vehiclesMapFragment)
                    .commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .show(vehiclesMapFragment)
                    .commit();
        }

        if (vehicleListFragment != null){

            getSupportFragmentManager().beginTransaction()
                    .hide(vehicleListFragment)
                    .commit();
        }
    }

    private void showListFragment(){

        mViewModel.setMapView(false);

        if (vehicleListFragment == null) {

            vehicleListFragment = VehicleListFragment.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, vehicleListFragment)
                    .commit();
        }else{

            getSupportFragmentManager().beginTransaction()
                    .show( vehicleListFragment)
                    .commit();
        }

        if (vehiclesMapFragment != null){

            getSupportFragmentManager().beginTransaction()
                    .hide( vehiclesMapFragment)
                    .commit();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (canRequestLocationSettings && canRequestLocationPermission) {
            locationUpdateManager.startLocationService();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationUpdateManager.stopLocationService();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(REQUESTING_LOCATION_SETTINGS_KEY,
                canRequestLocationSettings);

        outState.putBoolean(REQUESTING_LOCATION_PERMISSION_KEY,
                canRequestLocationPermission);

        super.onSaveInstanceState(outState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }

        // Update the value of canRequestLocationSettings from the Bundle.
        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_SETTINGS_KEY)) {
            canRequestLocationSettings = savedInstanceState.getBoolean(
                    REQUESTING_LOCATION_SETTINGS_KEY);
        }else if(savedInstanceState.keySet().contains(REQUESTING_LOCATION_PERMISSION_KEY)) {

            canRequestLocationPermission = savedInstanceState.getBoolean(
                    REQUESTING_LOCATION_PERMISSION_KEY);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.LOCATION_SETTINGS_REQUEST_CODE){

            if (resultCode == RESULT_OK) {

                canRequestLocationSettings = true;

                locationUpdateManager.grantPermission();

            }else {

                canRequestLocationSettings = false;
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case Constants.PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    canRequestLocationPermission = true;

                    locationUpdateManager.requestLocationUpdate();

                }else{

                    canRequestLocationPermission = false;

                    locationUpdateManager.showEnableLocationSnackBar();
                }
            }
        }
    }

    @Override
    public void onCheckChanged(IconSwitch.Checked current) {

        if (mViewModel.isMapView()){ // change map view to list view

            showListFragment();

        } else{ // change list view to map view

            showMapFragment();
        }
    }

    @Override
    public void onBackPressed() {

        if (mViewModel.isMapView())
            iconSwitch.toggle();
        else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
