package com.assi.islam.mytaxi.api.webService;

import com.assi.islam.mytaxi.model.VehiclesListResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by islam assi
 */
public interface Webservice {
    /**
     * web service for getting vehicles in a latLng bounds.
     */
    @GET("/")
    Observable<Response<VehiclesListResponse>> geyVehicles(
            @Query("p1Lat") double bound1Lat,
            @Query("p1Lon") double bound1Lon,
            @Query("p2Lat") double bound2Lat,
            @Query("p2Lon") double bound2Lon
    );
}