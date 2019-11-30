package com.assi.islam.mytaxi.model;

/**
 * Created by islam assi
 *
 * Used for sorting {@link RideOption}
 */
public class SortModel {

    private SortType sortType;

    public SortModel(SortType sortType) {
        this.sortType = sortType;
    }

    public SortType getSortType() {
        return sortType;
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public enum  SortType{

        DURATION, DISTANCE, NO_SORT

    }
}

