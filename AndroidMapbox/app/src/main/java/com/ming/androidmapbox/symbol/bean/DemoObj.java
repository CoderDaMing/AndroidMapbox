package com.ming.androidmapbox.symbol.bean;

public class DemoObj {
    private String id;
    private double lat;
    private double lng;

    public DemoObj(String id, double lat, double lng) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}


