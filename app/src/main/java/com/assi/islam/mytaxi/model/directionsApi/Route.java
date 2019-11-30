package com.assi.islam.mytaxi.model.directionsApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.android.gms.maps.model.Polyline;

/**
 *  copied from https://stackoverflow.com/questions/19290593/displaying-multiple-routes-using-directions-api-in-android/19329951
 */
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;
    private Bound bounds;
    private String copyrights;
    private List<Leg> legs;
    private Polyline overviewPolyLine;
    private String summary;

    public Route(Context context) {
        legs = new ArrayList<Leg>();
    }

    public Bound getBounds() {
        return bounds;
    }

    public void setBounds(Bound bounds) {
        this.bounds = bounds;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public Polyline getOverviewPolyLine() {
        return overviewPolyLine;
    }

    public void setOverviewPolyLine(Polyline overviewPolyLine) {
        this.overviewPolyLine = overviewPolyLine;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}