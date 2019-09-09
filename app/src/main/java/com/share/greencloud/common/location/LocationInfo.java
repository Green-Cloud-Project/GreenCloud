package com.share.greencloud.common.location;

import android.location.Address;

public interface LocationInfo {

    interface View {
        void onLocationUpdate(android.location.Location location);

        void onLocationSettingsUnsuccessful();

        void onAddressUpdate(Address address);
    }

    interface Presenter {
    }
}
