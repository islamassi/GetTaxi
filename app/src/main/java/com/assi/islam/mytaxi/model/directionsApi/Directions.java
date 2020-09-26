package com.assi.islam.mytaxi.model.directionsApi;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by islam assi
 *
 * Directions Api response model
 */
public class Directions implements Serializable {

    private String status;

    private ArrayList<Route> routes;

    private List<LatLng> pathPoints;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public List<LatLng> getPathPoints() {
        return pathPoints;
    }

    public void setPathPoints(List<LatLng> pathPoints) {
        this.pathPoints = pathPoints;
    }

    public Route getFirstRoute(){

        if (routes != null && routes.size()>0)
            return routes.get(0);

        return null;
    }

    public boolean isEmpty(){
        return status.equals("ZERO_RESULTS");
    }
}
