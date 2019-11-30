package com.assi.islam.mytaxi.api.webService;

import com.assi.islam.mytaxi.model.directionsApi.Directions;
import com.assi.islam.mytaxi.model.distanceMatrixApi.DistanceResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by islam assi
 *
 * Webservices that are related to Google Apis
 */
public interface GoogleApiWebservice {

    /**
     * calculate driving distance and time between two points
     */
    @GET("/maps/api/distancematrix/json")
    Observable<Response<DistanceResponse>> getDistanceMatrix(
            @Query("origins") String origin,
            @Query("destinations") String destination,
            @Query("key") String apiKey
    );

    /**
     * calculate driving directions, distance and time between two points
     */
    @GET("/maps/api/directions/json")
    Observable<Response<Directions>> getDirections(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("key") String apiKey
    );
}
