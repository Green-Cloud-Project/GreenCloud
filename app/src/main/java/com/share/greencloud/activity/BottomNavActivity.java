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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.share.greencloud.R;
import com.share.greencloud.databinding.ActivityBottomNavBinding;
import com.share.greencloud.fragment.AlarmFragment;
import com.share.greencloud.fragment.CameraFragment;
import com.share.greencloud.fragment.MapFragment;
import com.share.greencloud.fragment.NewsFragment;
import com.share.greencloud.fragment.WeatherFragment;
import com.share.greencloud.viewmodel.BottomNavVIewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import timber.log.Timber;

public class BottomNavActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        WeatherFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener,
        AlarmFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private ActivityBottomNavBinding binding;

    private SearchManager searchManager;
    private SearchView searchView;

    private BottomNavVIewModel viewModel;

    private BottomSheetBehavior bottomSheetBehavior;

    private final Fragment[] childFragment = new Fragment[]{
            new MapFragment(),
            new NewsFragment(),
            new WeatherFragment(),
            new AlarmFragment(),
            new CameraFragment()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate is called");

        setupintialView();

        if (!checkPermissions()) {
            getLocationPermission();
        }
    }

    private void setupintialView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_nav);
        binding.bottomNavView.setOnNavigationItemSelectedListener(this);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) binding.bottomNavView.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());

        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this).get(BottomNavVIewModel.class);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);

        bottomSheetBehavior = BottomSheetBehavior.from(binding.rlRentalInfo);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        binding.rentalInfo.setBottomNavActivity(this);

        loadDefaultFragment();
    }

    private void loadDefaultFragment() {
        binding.bottomNavView.setSelectedItemId(R.id.navigation_places);
        loadFragment(childFragment[0]);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);

        if(fragment.equals(childFragment[4])) { // CarmeraFragment 경우에만 BackStack 추가되도록 적용.
            transaction.addToBackStack(null);   // 사용자가 다시 대여소를 선택하거나 스캔을 취소하는 경우를 대비하여

        } else {
            transaction.disallowAddToBackStack();
        }

        transaction.setReorderingAllowed(true);
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

        observeSearchMenu(menu);

        return true;
    }

    private void observeSearchMenu(Menu menu) {
        viewModel.getHideSearchMenu().observe(this, (hideSearchMenu) -> {
            if (hideSearchMenu) {
                changeMenuItemVisible(menu, false);
            } else {
                changeMenuItemVisible(menu, true);
            }
        });
    }

    private void changeMenuItemVisible(Menu menu, Boolean visibility) {
        menu.findItem(R.id.menu_search).setVisible(visibility);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_places:
                viewModel.showSearchMenu();
                loadFragment(childFragment[0]);
                return true;

            case R.id.navigation_dashboard:
                viewModel.hideSearchMenu();
                loadFragment(childFragment[1]);
                return true;

            case R.id.navigation_mygreen:
                viewModel.hideSearchMenu();
                loadFragment(childFragment[2]);
                return true;

            case R.id.navigation_notifications:
                viewModel.hideSearchMenu();
                loadFragment(childFragment[3]);
                return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDefaultFragment();
        Timber.d("onResume is called");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchManager = null;
        searchView = null;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void showBottomSlide() {
        bottomSheetBehavior.setState(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED ? BottomSheetBehavior.STATE_HIDDEN : BottomSheetBehavior.STATE_EXPANDED);
    }
     public void hideBottomSlide(View view) {
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void scanQRcode(View view) {
        hideBottomSlide(view);
        loadFragment(childFragment[4]);
    }
}
