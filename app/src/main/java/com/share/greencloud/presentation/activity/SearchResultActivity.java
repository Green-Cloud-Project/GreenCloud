package com.share.greencloud.presentation.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.model.LatLng;
import com.share.greencloud.R;
import com.share.greencloud.common.BaseActivity;
import com.share.greencloud.databinding.ActivitySearchResultBinding;
import com.share.greencloud.domain.model.RentalOffice;
import com.share.greencloud.presentation.adapter.SearchResultAdapter;
import com.share.greencloud.presentation.viewmodel.MapFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class SearchResultActivity extends BaseActivity<ActivitySearchResultBinding> {

    private ActivitySearchResultBinding binding;
    public LatLng request;
    private SearchView searchView;
    private SearchResultAdapter adapter;
    private List<RentalOffice> rentalOfficeList = new ArrayList<>();

    private MapFragmentViewModel mapFragmentViewModel;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = getBinding();
        setupToolbar();
        setupViewModel();
        setupView();

    }


    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupView() {
        binding.contentSearchResult.searchResultRecyclerView.setHasFixedSize(true);

        new Handler().postDelayed(() -> getItemFromDB("test"),2000);
    }


    private void setupViewModel() {
        mapFragmentViewModel = ViewModelProviders.of(this).get(MapFragmentViewModel.class);
    }

    public void loadData(List<RentalOffice> rentalOffices) {

        adapter = new SearchResultAdapter(this, rentalOffices);
        binding.contentSearchResult.searchResultRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getItemFromDB(String searchTest) {

        mapFragmentViewModel.getAllRentalOfficesFromDB().observe(this, rentalOffices -> {
            loadData(rentalOffices);
            binding.contentSearchResult.searchResultRecyclerView.setVisibility(View.VISIBLE);
            binding.contentSearchResult.progressBar.setVisibility(View.INVISIBLE);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Timber.d("onCreateOptionsMenu is called");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.isSubmitButtonEnabled();
        searchView.setIconifiedByDefault(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
