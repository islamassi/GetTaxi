package com.assi.islam.mytaxi.viewModel;

import android.view.View;

import com.assi.islam.mytaxi.model.FleetType;
import com.assi.islam.mytaxi.model.RideOption;
import com.assi.islam.mytaxi.model.directionsApi.Leg;
import com.assi.islam.mytaxi.utility.ResourceUtil;

/**
 * Created by islam on 05,April,2019
 *
 * view model for {@link com.assi.islam.mytaxi.ui.view.RideOptionCardView}
 */
public class RideOptionCardViewModel {

    private RideOption rideOption;

    public RideOptionCardViewModel(RideOption rideOption) {
        this.rideOption = rideOption;
    }
    public RideOption getRideOption() {
        return rideOption;
    }
    public void setRideOption(RideOption rideOption) {
        this.rideOption = rideOption;
    }
    public String getHeadingText(){
        return ResourceUtil.getHeadingHumanReadableText(rideOption.getVehicle().getHeading());
    }

    public String getDurationText(){
        Leg leg = rideOption.getRouteLeg();
        if (rideOption.getRouteLeg() == null || leg.getDuration() == null)
            return "";
        return leg.getDuration().getText();
    }

    public String getDistanceText(){
        Leg leg = rideOption.getRouteLeg();
        if (rideOption.getRouteLeg() == null || leg.getDistance() == null)
            return "";
        return leg.getDistance().getText();
    }

    public String getAddressText(){
        if (rideOption.getRouteLeg() == null || rideOption.getRouteLeg().getStartAddress() == null)
            return "";
        return rideOption.getRouteLeg().getStartAddress();
    }

    public int getDurationVisibility(){
        if (!getDurationText().isEmpty())
            return View.VISIBLE;
        return View.GONE;
    }

    public int getDistanceVisibility(){
        if (!getDistanceText().isEmpty())
            return View.VISIBLE;
        return View.GONE;
    }

    public String getCopyRightText(){
        if (rideOption.getDirections() == null || rideOption.getDirections().getFirstRoute() == null)
            return "";
        return rideOption.getDirections().getFirstRoute().getCopyrights();
    }

    public int getCopyRightVisibility(){
        if (!getCopyRightText().isEmpty())
            return View.VISIBLE;
        return View.GONE;
    }

    public int getAddressVisibility(){
        if ( !getAddressText().isEmpty())
            return View.VISIBLE;
        return View.GONE;
    }

    public int getPoolVisibility(){
        if ( getRideOption().getVehicle().getFleetType() == FleetType.POOLING)
            return View.VISIBLE;
        return View.INVISIBLE;
    }
}
