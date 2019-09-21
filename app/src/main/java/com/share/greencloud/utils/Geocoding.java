package com.share.greencloud.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Geocoding {

    public static String getAddress(Context mContext, double lat, double lng) {

        String localAddress ="현재 위치를 확인 할 수 없습니다.";
        String error = null;

        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {

                address = geocoder.getFromLocation(lat, lng, 1);

                if (address != null && address.size() > 0) {
                     localAddress = address.get(0).getAddressLine(0).toString();
                }
            }
        } catch (IOException e) {
            error = "주소를 가져올 수 없습니다.";
        }

        if (error == null)  {

            //split: 대한민국 대구광역시 동구 지정동 321-232 ->  대구광역시 동구 지정동
            int  firstIndex  = localAddress.indexOf(" ");
            int  secondIndex = localAddress.lastIndexOf(" ");
            return  localAddress.substring(firstIndex+1,secondIndex);

        }

        return error;
    }

}
