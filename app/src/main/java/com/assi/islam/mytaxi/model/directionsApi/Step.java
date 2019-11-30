package com.assi.islam.mytaxi.model.directionsApi;

import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.model.EncodedPolyline;

/**
 *  copied from https://stackoverflow.com/questions/19290593/displaying-multiple-routes-using-directions-api-in-android/19329951
 */
public class Step {
    private Distance distance;
    private Duration duration;
    private LatLng endLocation;
    private LatLng startLocation;
    private String htmlInstructions;
    private String travelMode;
    private List<LatLng> points;
    private EncodedPolyline polyline;

    public List<LatLng> getPoints() {
        return points;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public EncodedPolyline getPolyline() {
        return polyline;
    }

    public void setPolyline(EncodedPolyline polyline) {
        this.polyline = polyline;
    }
}