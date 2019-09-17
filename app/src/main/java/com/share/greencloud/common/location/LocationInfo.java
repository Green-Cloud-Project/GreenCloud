package com.share.greencloud.common.location;

import android.location.Address;
import android.location.Location;

import com.google.android.gms.maps.OnMapReadyCallback;

public interface LocationInfo {

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
