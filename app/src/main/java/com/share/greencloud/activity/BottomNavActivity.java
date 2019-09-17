package com.share.greencloud.activity;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.share.greencloud.R;
import com.share.greencloud.common.BottomNavigationBehavior;
import com.share.greencloud.databinding.ActivityBottomNavBinding;
import com.share.greencloud.fragment.MapFragment;
import com.share.greencloud.fragment.NewsFragment;
import com.share.greencloud.fragment.WeatherFragment;
import com.share.greencloud.presenter.BottomNavPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import timber.log.Timber;

public class BottomNavActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        WeatherFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener,
        BottomNavPresenter.View {

    private ActionBar toolbar;
    private ActivityBottomNavBinding binding;

//    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
//    private boolean mLocationPermissionGranted;

    private SearchManager searchManager;
    private SearchView searchView;

    private Boolean hideSearchMenu;

    private BottomNavPresenter presenter;

    private final Fragment[] PAGES = new Fragment[]{
            new MapFragment(),
            new NewsFragment(),
            new WeatherFragment()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate is called");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_nav);

        toolbar = getSupportActionBar();
        toolbar.setDisplayShowHomeEnabled(true);
//        toolbar.setLogo(R.drawable.beach_access);
        toolbar.setDisplayUseLogoEnabled(true);
//        toolbar.setDisplayShowTitleEnabled(false);

        binding.bottomNavView.setOnNavigationItemSelectedListener(this);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) binding.bottomNavView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        hideSearchMenu = false;
        loadFragment(PAGES[0]);

        presenter = new BottomNavPresenter(this);

        if (!checkPermissions()) {
            getLocationPermission();
        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
//        transaction.addToBackStack(null);
        transaction.disallowAddToBackStack();
        transaction.commit();

        invalidateOptionsMenu(); // 메뉴 아이템 변경 시 호출해야함.
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void getLocationPermission() {
        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        reloadActivity();
                    } else {
                        Toast.makeText(this, "위치정보 사용에 대한 동의가 거부되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void reloadActivity() {
        Timber.d("reloadActivity is called");
        Intent intent = getIntent();
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Timber.d("onCreateOptionsMenu is called");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("대여소 검색...");

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        Timber.d("onPrepareOptionsMenu is called");

        if (hideSearchMenu) {
            updateMenuItemVisible(menu, false);
        } else {
            updateMenuItemVisible(menu, true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_places:
                hideSearchMenu = false;
                loadFragment(PAGES[0]);
                return true;

            case R.id.navigation_dashboard:
                hideSearchMenu = true;
                loadFragment(PAGES[1]);
                return true;

            case R.id.navigation_mygreen:
                hideSearchMenu = true;
                loadFragment(PAGES[2]);
                return true;
        }
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFragment(PAGES[0]);
        Timber.d("onResume is called");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchManager = null;
        searchView = null;
    }

    @Override
    public void updateMenuItemVisible(Menu menu, Boolean status) {
        menu.findItem(R.id.menu_search).setVisible(status);
    }
}
