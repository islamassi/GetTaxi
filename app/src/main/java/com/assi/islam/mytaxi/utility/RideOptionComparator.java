package com.assi.islam.mytaxi.utility;

import com.assi.islam.mytaxi.model.RideOption;
import com.assi.islam.mytaxi.model.SortModel;

import java.util.Comparator;

/**
 * Created by islam assi
 *
 * Comparing between {@link RideOption} objects
 *
 * Used for sorting
 */
public class RideOptionComparator implements Comparator<RideOption> {

    private SortModel sortModel;
    public RideOptionComparator(SortModel sortModel) {
        this.sortModel = sortModel;
    }

    @Override
    public int compare(RideOption o1, RideOption o2) {
        if (sortModel.getSortType() == SortModel.SortType.DURATION) {
            if (o1.getRouteLeg() == null || o1.getRouteLeg().getDuration() == null)
                return (o2.getRouteLeg() == null || o2.getRouteLeg().getDuration() == null) ? 0 : 1;

            if (o2.getRouteLeg() == null || o2.getRouteLeg().getDuration() == null)
                return -1;

            return (int) (o1.getRouteLeg().getDuration().getValue() - o2.getRouteLeg().getDuration().getValue());
        }else if (sortModel.getSortType() == SortModel.SortType.DISTANCE) {
            if (o1.getRouteLeg() == null || o1.getRouteLeg().getDistance() == null)
                return (o2.getRouteLeg() == null || o2.getRouteLeg().getDistance() == null) ? 0 : 1;

            if (o2.getRouteLeg() == null || o2.getRouteLeg().getDistance() == null)
                return -1;

            return (int) (o1.getRouteLeg().getDistance().getValue() - o2.getRouteLeg().getDistance().getValue());
        }
        return 0;
    }
}
