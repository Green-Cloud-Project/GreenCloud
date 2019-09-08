package com.share.greencloud.common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import timber.log.Timber;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.share.greencloud.common.Constants.LATITUE_SEOUL;
import static com.share.greencloud.common.Constants.LONGITUTE_SEOUL;

public class LastLocationInfo {
    private Context mContext;
    private LocationManager locationManager;
    private Location userLocation;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            userLocation = location;  // 위치가 변할 때마다 현재 위치 가져옴
            Timber.i("현재 위도: %s", userLocation.getLatitude());
            Timber.i("현재 경도: %s", userLocation.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            //todo 위치정보 갱신을 해제하는 로직 적용해야함
            //locationManager.removeUpdates(locationListener); // GPS, Network Provider으로 사용이 불가할때 위치 갱신 해제
            //Toast.makeText(mContext, "위치정보 갱신을 중지합니다.", Toast.LENGTH_SHORT).show();

        }
    };

    public LastLocationInfo(Context mContext) {
        this.mContext = mContext;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        userLocation = new Location("");
        userLocation.setLatitude(LATITUE_SEOUL);
        userLocation.setLongitude(LONGITUTE_SEOUL);

    }

    public Location getLastLocation() {
//        Criteria criteria = new Criteria();
////        provider = locationManager.getBestProvider(criteria, true);
        final Location location = new Location("");
////        if (checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            // 기본 표시 지역은 서울으로 지정
//            location.setLatitude(LATITUE_SEOUL);
//            location.setLongitude(LONGITUTE_SEOUL);
//            return location;
////        }
////        return locationManager.getLastKnownLocation(provider);
        if (checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            location.setLatitude(LATITUE_SEOUL); // 기본 표시 지역은 서울으로 지정
            location.setLongitude(LONGITUTE_SEOUL);
            Toast.makeText(mContext, "기본 설정 위치정보를 사용합니다.", Toast.LENGTH_SHORT).show();
            return location;
        }
        //todo  mintim, minDistance  변경 후 테스트 부탁드립니다.
        // Default Setting: 시간 1초/ 거리 1m 위치 갱신
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
        Toast.makeText(mContext, "GPS, Network Provider를 활용하여, 위치정보를 주기적으로 갱신합니다.", Toast.LENGTH_SHORT).show();
        return userLocation;
    }
}
