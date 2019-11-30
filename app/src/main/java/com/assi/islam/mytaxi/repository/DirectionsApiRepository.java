package com.assi.islam.mytaxi.repository;

import com.assi.islam.mytaxi.R;
import com.assi.islam.mytaxi.api.webService.GoogleApiWebservice;
import com.assi.islam.mytaxi.model.directionsApi.Directions;
import com.assi.islam.mytaxi.model.distanceMatrixApi.DistanceResponse;
import com.assi.islam.mytaxi.utility.ResourceUtil;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Created by islam assi
 *
 * Repository for managing directions api data
 *
 */

@Singleton
public class DirectionsApiRepository {

    private final GoogleApiWebservice googleApiWebservice;

    @Inject
    public DirectionsApiRepository(GoogleApiWebservice googleApiWebservice) {
        this.googleApiWebservice = googleApiWebservice;
    }

    public Observable<Response<DistanceResponse>> getDistanceMatrix(LatLng origin, LatLng destination){

        return googleApiWebservice.getDistanceMatrix(
                toUrlValue(origin),
                toUrlValue(destination),
                ResourceUtil.bindString(R.string.google_maps_key)
        );
    }

    public Observable<Response<Directions>> getDirections(LatLng origin, LatLng destination){

        return googleApiWebservice.getDirections(
                toUrlValue(origin),
                toUrlValue(destination),
                ResourceUtil.bindString(R.string.google_maps_key)
        );
    }

    public String toUrlValue(LatLng latLng) {
        // Enforce Locale to English for double to string conversion
        return String.format(Locale.ENGLISH, "%.8f,%.8f", latLng.latitude, latLng.longitude);
    }
}
