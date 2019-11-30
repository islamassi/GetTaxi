package com.assi.islam.mytaxi.model.distanceMatrixApi;

import java.util.List;

public class DistanceResponse {

    private List<String> destinationAddresses = null;
    private List<String> originAddresses = null;
    private List<Row> rows = null;
    private String status;

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    public List<Row> getRows() {
        return rows;
    }

    public String getStatus() {
        return status;
    }

}