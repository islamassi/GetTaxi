package com.assi.islam.mytaxi.model;

import com.assi.islam.mytaxi.utility.ResourceUtil;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.annotations.SerializedName;
import com.google.maps.model.LatLng;

import java.util.Objects;

/**
 * Created by islam assi
 */
public class Vehicle {

    @SerializedName("id")
    private String id;

    @SerializedName("coordinate")
    private Coordinate coordinate;

    @SerializedName("fleetType")
    private FleetType fleetType;

    @SerializedName("heading")
    private double heading;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public FleetType getFleetType() {
        return fleetType;
    }

    public void setFleetType(FleetType fleetType) {
        this.fleetType = fleetType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return Double.compare(vehicle.heading, heading) == 0 &&
                Objects.equals(id, vehicle.id) &&
                Objects.equals(coordinate, vehicle.coordinate) &&
                Objects.equals(fleetType, vehicle.fleetType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coordinate, fleetType, heading);
    }

}
