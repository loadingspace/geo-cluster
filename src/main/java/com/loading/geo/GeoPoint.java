package com.loading.geo;

/**
 * desc:
 *
 * @author Lo_ading
 * @version 1.0.0
 * @date 2020/3/9
 */
public class GeoPoint {

    private double lat;
    private double lon;

    public GeoPoint(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "{ \"lat\":" + lat + ", \"lon\":" + lon + "}";
    }
}