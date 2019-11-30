package com.assi.islam.mytaxi.model.directionsApi;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 *  copied from https://stackoverflow.com/questions/19290593/displaying-multiple-routes-using-directions-api-in-android/19329951
 */
public class Leg {
    private Distance distance;
    private Duration duration;
    @SerializedName("end_address")
    private String endAddress;
    @SerializedName("end_location")
    private LatLng endLocation;
    @SerializedName("start_address")
    private String startAddress;
    @SerializedName("start_location")
    private LatLng startLocation;
    private List<Step> steps;

    public Leg() {
        steps = new ArrayList<Step>();
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

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public void addStep(Step step) {
        this.steps.add(step);
    }

}