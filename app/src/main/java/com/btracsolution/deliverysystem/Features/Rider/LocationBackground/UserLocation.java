package com.btracsolution.deliverysystem.Features.Rider.LocationBackground;

/**
 * Created by mahmudul.hasan on 10/10/2017.
 */

public class UserLocation {
    public String latitude;
    public String longitude;
    public String name;
    public String dateTime;
    public String phoneNumber;

    public UserLocation(String latitude, String longitude, String name, String dateTime, String phoneNumber) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.dateTime = dateTime;
        this.phoneNumber = phoneNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getDateTime() {
        return dateTime;
    }
}
