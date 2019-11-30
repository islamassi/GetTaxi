package com.assi.islam.mytaxi.model;

import com.google.gson.annotations.SerializedName;
import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

/**
 * Created by islam assi
 */
public class Coordinate {

    @SerializedName(value = "latitude", alternate = "lat")
    public double lat;

    @SerializedName(value = "longitude", alternate = "lng")
    public double lng;

    public Coordinate() {
    }

    public Coordinate(LatLng latLng) {

        lat = latLng.latitude;

        lng = latLng.longitude;
    }

    public Coordinate(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public LatLng toLatLng(){

        return new LatLng(lat, lng);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate that = (Coordinate) o;
        return Double.compare(that.lat, lat) == 0 &&
                Double.compare(that.lng, lng) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lng);
    }
}
