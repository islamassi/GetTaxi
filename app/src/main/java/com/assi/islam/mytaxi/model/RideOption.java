package com.assi.islam.mytaxi.model;

import com.assi.islam.mytaxi.model.directionsApi.Directions;
import com.assi.islam.mytaxi.model.directionsApi.Leg;

import java.util.Objects;

/**
 * Created by islam assi
 *
 * contains info related to a ride option that the user may select.
 * contains info about the vehicles and time, distance, directions between vehicle and user
 */
public class RideOption {

    private Vehicle vehicle;

    // coordinates of the rider/user
    private Coordinate riderCoordinate;

    // Google Directions Api response that contain info about duration, distance, and directions between rider and vehicle
    private Directions directions;

    public RideOption() {
    }

    public RideOption(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public RideOption(Vehicle vehicle, Coordinate riderCoordinate, Directions directions) {
        this.vehicle = vehicle;
        this.riderCoordinate = riderCoordinate;
        this.directions = directions;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Coordinate getRiderCoordinate() {
        return riderCoordinate;
    }

    public void setRiderCoordinate(Coordinate riderCoordinate) {
        this.riderCoordinate = riderCoordinate;
    }

    public Directions getDirections() {
        return directions;
    }

    public void setDirections(Directions directions) {
        this.directions = directions;
    }

    public Leg getRouteLeg(){

        if (directions != null &&
                directions.getRoutes()!= null &&
                directions.getRoutes().size() > 0 &&
                directions.getRoutes().get(0).getLegs()!= null &&
                directions.getRoutes().get(0).getLegs().size() > 0 &&
                directions.getRoutes().get(0).getLegs().get(0)!= null
        )
            return directions.getRoutes().get(0).getLegs().get(0);

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RideOption)) return false;
        RideOption that = (RideOption) o;
        return vehicle.getId().equals(that.vehicle.getId());
    }

    public boolean deepEquals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RideOption)) return false;
        RideOption that = (RideOption) o;
        return Objects.equals(vehicle, that.vehicle) &&
                Objects.equals(riderCoordinate, that.riderCoordinate) &&
                Objects.equals(directions, that.directions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle, riderCoordinate, directions);
    }
}
