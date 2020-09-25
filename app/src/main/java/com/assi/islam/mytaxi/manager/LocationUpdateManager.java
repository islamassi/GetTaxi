package com.assi.islam.mytaxi.manager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;

import com.assi.islam.mytaxi.R;
import com.assi.islam.mytaxi.ui.activity.MainActivity;
import com.assi.islam.mytaxi.constants.Constants;
import com.assi.islam.mytaxi.interfaces.AbstractCallback;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.util.Observable;

import androidx.core.app.ActivityCompat;

import static com.assi.islam.mytaxi.utility.ResourceUtil.bindString;


/**
 *
 * This class manages location settings, permissions and register for location updates.
 *
 * Why? user location is required for showing full app features. It is needed to show details to the user.
 * The last location is not always exists and it could be incorrect.
 * By listening to location service, we ensure getting recent and accurate user location
 */

public class LocationUpdateManager extends Observable{
    private LocationRequest mLocationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    private MainActivity activity;
    private static LocationUpdateManager instance;

    private LocationUpdateManager() {
        this.locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult!= null && locationResult.getLastLocation() != null){
                    setChanged();
                    notifyObservers(locationResult.getLastLocation());
                }
            }
        };
        this.mLocationRequest = createLocationRequest();
    }

    public static LocationUpdateManager getInstance(){
        if (instance == null){
            instance = new LocationUpdateManager();
        }
        return instance;
    }

    public void init(MainActivity activity){
        this.activity = activity;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);

    }

    public boolean isPermissionGranted(){
        return ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestLocationUpdate() {
        if (isPermissionGranted()) {
            // granted
            mFusedLocationClient.requestLocationUpdates(
                    mLocationRequest,
                    locationCallback,
                    null
            );
        }
    }

    public void grantPermission(){
        if (!isPermissionGranted()) {
            ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.PERMISSIONS_REQUEST_LOCATION);
            return;
        }
        requestLocationUpdate();
    }


    private void settingsRequestFailed(Exception e){
        if (e instanceof ResolvableApiException) {
            try {
                ResolvableApiException resolvable = (ResolvableApiException) e;
                resolvable.startResolutionForResult(activity,
                        Constants.LOCATION_SETTINGS_REQUEST_CODE);
            }catch (Exception ignored){}
        }
    }

    /**
     * check settings that is required for getting user location to be changed
     */
    private void requestSettingsChangeAndPermission() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(activity);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(locationSettingsResponse -> grantPermission()).addOnFailureListener(this::settingsRequestFailed);
    }

    private LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(30000);
        mLocationRequest.setFastestInterval(30000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    /**
     * getting user's last location
     */
    public void getLastLocation(AbstractCallback<Location> callback) {
        if (isPermissionGranted()){
            // granted
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location!= null)
                                callback.onResult(true, location);
                            else
                                callback.onResult(false, null);
                        }
                    })
                    .addOnFailureListener(e -> callback.onResult(false, null))
                    .addOnCanceledListener(() -> callback.onResult(false, null));
        }else{
            callback.onResult(false, null);
        }
    }


    /**
     * start listining to location updates service
     */
    public void startLocationService() {
        requestSettingsChangeAndPermission();
    }


    /**
     * Stop listening to location updates service
     */
    public void stopLocationService() {
        if (mFusedLocationClient != null){
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    /**
     * shwos enable location SnackBar and prompts user to allow location permission
     */
    public void showEnableLocationSnackBar(){

        Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.container), bindString(R.string.location_permission_denied), Snackbar.LENGTH_LONG);
        snackbar.setAction(bindString(R.string.settings), v -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivity(intent);
        });

        snackbar.show();
    }
}
