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
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.share.greencloud.R;
import com.share.greencloud.common.BottomNavigationBehavior;
import com.share.greencloud.databinding.ActivityBottomNavBinding;
import com.share.greencloud.fragment.MapFragment;
import com.share.greencloud.fragment.NewsFragment;
import com.share.greencloud.fragment.WeatherFragment;
import com.share.greencloud.presenter.BottomNavVIewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import timber.log.Timber;

public class BottomNavActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        WeatherFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener{

    private ActionBar toolbar;
    private ActivityBottomNavBinding binding;

    private SearchManager searchManager;
    private SearchView searchView;

    private BottomNavVIewModel viewModel;

    private final Fragment[] childFragment = new Fragment[]{
            new MapFragment(),
            new NewsFragment(),
            new WeatherFragment()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("onCreate is called");

        setupintialView();

        if (!checkPermissions()) {
            getLocationPermission();
        }

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.rl_rental_info));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private void setupintialView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_nav);
        binding.bottomNavView.setOnNavigationItemSelectedListener(this);

        toolbar = getSupportActionBar();
        toolbar.setDisplayShowHomeEnabled(true);
//        toolbar.setLogo(R.drawable.beach_access); //  추후 툴바에 로고 추가할때 필요한 코드
        toolbar.setDisplayUseLogoEnabled(true);
//        toolbar.setDisplayShowTitleEnabled(false); // 툴바에 타이틀 제거할때 필요한 코드

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) binding.bottomNavView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        binding.setLifecycleOwner(this);
//        binding.setViewmodel(ViewModelProviders.of(this).get(BottomNavVIewModel.class));
        viewModel = ViewModelProviders.of(this).get(BottomNavVIewModel.class);

        loadDefaultFragment();
    }

    private void loadDefaultFragment() {
        binding.bottomNavView.setSelectedItemId(R.id.navigation_places);
        loadFragment(childFragment[0]);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
//        transaction.addToBackStack(null); // 프레그먼트 백스택 설정:  addToBackStack을 호출하지 않으면 백스택이 생성되지 않음
        transaction.disallowAddToBackStack();
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
    public void onFragmentInteraction(Uri uri) {

    }

    public void showBottomSlide(){
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.rl_rental_info));

        bottomSheetBehavior.setState(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED ? BottomSheetBehavior.STATE_HIDDEN : BottomSheetBehavior.STATE_EXPANDED);
    }
}
