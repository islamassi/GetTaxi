package com.assi.islam.mytaxi.model;

import com.google.gson.annotations.SerializedName;
import com.google.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by islam assi
 */
public class VehiclesListResponse {

    @SerializedName("poiList")
    private ArrayList<Vehicle> vehicles;

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
