package com.assi.islam.mytaxi.model;

/**
 * Created by islam assi
 */
public enum FleetType {

    TAXI("TAXI"), POOLING("POOLING") ;

    private String name;

    FleetType(String name) {
        this.name = name;
    }
}
