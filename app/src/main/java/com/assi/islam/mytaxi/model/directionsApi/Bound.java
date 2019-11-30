package com.assi.islam.mytaxi.model.directionsApi;

import com.assi.islam.mytaxi.model.Coordinate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

/**
 *  copied from https://stackoverflow.com/questions/19290593/displaying-multiple-routes-using-directions-api-in-android/19329951
 */
public class Bound {
    private Coordinate northeast;
    private Coordinate southwest;

    public Coordinate getNortheast() {
        return northeast;
    }

    public void setNortheast(Coordinate northeast) {
        this.northeast = northeast;
    }

    public Coordinate getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Coordinate southwest) {
        this.southwest = southwest;
    }

    public LatLngBounds toLatLngBounds(){

        if (southwest != null && northeast != null)
            return new LatLngBounds(southwest.toLatLng(), northeast.toLatLng());

        return null;
    }
}