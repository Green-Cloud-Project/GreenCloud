/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.domain.interator;

import android.location.Address;
import android.location.Location;

import com.google.android.gms.maps.OnMapReadyCallback;

public interface LocationInfoMVP {

    interface View {
        void onLocationUpdate(android.location.Location location);

        void onLocationSettingsUnsuccessful();

        void onAddressUpdate(Address address);

        void onMapUpdate(OnMapReadyCallback callback);
    }

    interface Presenter {
        Location getUserLocation();

        void updateUserLocation(Location updatedLocation, OnMapReadyCallback callback);
    }
}
