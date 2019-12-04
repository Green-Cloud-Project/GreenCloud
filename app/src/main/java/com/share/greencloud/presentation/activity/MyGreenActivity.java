package com.share.greencloud.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.share.greencloud.R;
import com.share.greencloud.presentation.fragment.MapFragment;
import com.share.greencloud.presentation.fragment.NewsFragment;
import com.share.greencloud.presentation.fragment.WeatherFragment;

import timber.log.Timber;

public class MyGreenActivity extends AppCompatActivity implements
        WeatherFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener {

    private final String[] PAGE_TITLES = new String[]{
            "MyGreen",
            "위치찾기",
            "환경뉴스"
    };

    private final Fragment[] PAGES = new Fragment[]{
            new WeatherFragment(),
            new MapFragment(),
            new NewsFragment()

    };

    ViewPager mViewPager;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygreen);

        mViewPager = findViewById(R.id.viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ((TabLayout) findViewById(R.id.tab_layout)).setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        if (!checkPermissions()) {
            getLocationPermission();
        }

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
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    reloadActivity(); // 액티비티를 다시 로딩해줘야 새로운 위치 정보가 반영된다.
                } else {
                    Toast.makeText(this, "위치정보 사용에 대한 동의가 거부되었습니다.", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void reloadActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
