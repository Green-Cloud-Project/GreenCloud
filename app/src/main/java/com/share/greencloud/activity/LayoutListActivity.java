package com.share.greencloud.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.share.greencloud.R;
import com.share.greencloud.fragment.CameraFragment;
import com.share.greencloud.fragment.DrawerFragment;
import com.share.greencloud.fragment.GetUmbrellaCompleteFragment;
import com.share.greencloud.fragment.GreenCloudInfoFragment;
import com.share.greencloud.fragment.InputCodeFragment;
import com.share.greencloud.fragment.LayoutListFragment;
import com.share.greencloud.fragment.LoginFragment;
import com.share.greencloud.fragment.MapFragment;
import com.share.greencloud.fragment.MyGreenFragment;
import com.share.greencloud.googlelogin.GoogleLoginActivity;
import com.share.greencloud.kakaologin.KakaoLoginActiviy;

import timber.log.Timber;

public class LayoutListActivity extends AppCompatActivity implements LayoutListFragment.CommunicateListener,
        GreenCloudInfoFragment.OnFragmentInteractionListener,
        MyGreenFragment.OnFragmentInteractionListener,
        CameraFragment.OnFragmentInteractionListener,
        DrawerFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener,
        GetUmbrellaCompleteFragment.OnFragmentInteractionListener,
        InputCodeFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener {

    ViewPager vp;

    LoginFragment loginFragment;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_list);

        vp = findViewById(R.id.vp);
        vp.setAdapter(new VpAdt(getSupportFragmentManager()));

        if (!checkPermissions()) {
            getLocationPermission();
        }

    }

    @Override
    public void clickItem(LayoutListFragment.FragmentList fragmentListType) {
        switch (fragmentListType) {
            case LOGIN:
                vp.setCurrentItem(1);
                break;
            case MYGREEN:
                vp.setCurrentItem(2);
                break;
            case GREEN_CLOUD_INFO:
                vp.setCurrentItem(3);
                break;
            case CAMERA:
                vp.setCurrentItem(4);
                break;
            case DRAWER:
                vp.setCurrentItem(5);
                break;
            case COMPLETE:
                vp.setCurrentItem(6);
                break;
            case CODE:
                vp.setCurrentItem(6);
                break;
            case KAKAO_LOGIN:
                Intent intent = new Intent(LayoutListActivity.this, KakaoLoginActiviy.class);
                startActivity(intent);
                break;
            case GOOGLE_LOGIN:
                Intent g_intent = new Intent(LayoutListActivity.this, GoogleLoginActivity.class);
                startActivity(g_intent);
                break;
            case MAP:
                vp.setCurrentItem(7);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class VpAdt extends FragmentPagerAdapter {

        public VpAdt(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new LayoutListFragment();
                case 1:
                    loginFragment = LoginFragment.newInstance();
                    return loginFragment;
                case 2:
                    return MyGreenFragment.newInstance("", "");
                case 3:
                    return GreenCloudInfoFragment.newInstance("", "");
                case 4:
                    return CameraFragment.newInstance("", "");
                case 5:
                    return DrawerFragment.newInstance("", "");
                case 6:
                    return GetUmbrellaCompleteFragment.newInstance("", "");
                case 7:
                    return MapFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 8;
        }
    }

    @Override
    public void onBackPressed() {
        vp.setCurrentItem(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (loginFragment != null)
            loginFragment.onActivityResult(requestCode, resultCode, data);
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        Timber.d("onRequestPermissionsResult() is called");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
                else {
                    Toast.makeText(this, "위치정보 사용에 대한 동의가 거부되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
