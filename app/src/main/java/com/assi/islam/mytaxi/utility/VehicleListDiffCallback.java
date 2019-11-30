package com.assi.islam.mytaxi.utility;

import com.assi.islam.mytaxi.model.RideOption;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

/**
 * callback for calculating diff between old and new list
 */
public class VehicleListDiffCallback extends DiffUtil.Callback{

    List<RideOption> oldRideOptionList;
    List<RideOption> newRideOptionList;

    public VehicleListDiffCallback(List<RideOption> newRideOptionList, List<RideOption> oldRideOptionList) {
        this.newRideOptionList = newRideOptionList;
        this.oldRideOptionList = oldRideOptionList;
    }

    @Override
    public int getOldListSize() {
        return oldRideOptionList.size();
    }

    @Override
    public int getNewListSize() {
        return newRideOptionList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRideOptionList.get(oldItemPosition).getVehicle().getId().equals(newRideOptionList.get(newItemPosition).getVehicle().getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRideOptionList.get(oldItemPosition).deepEquals(newRideOptionList.get(newItemPosition));
    }

}