/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.domain.model;

import android.location.Location;

public class UserLocation {
    private Location currentLocation;

    public UserLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void updateCurrentLocation(Location updatedLocation){
        this.currentLocation = updatedLocation;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }
}
