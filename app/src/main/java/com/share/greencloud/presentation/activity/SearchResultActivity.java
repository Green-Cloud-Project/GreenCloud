package com.share.greencloud.presentation.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;

import com.share.greencloud.R;
import com.share.greencloud.common.BaseActivity;
import com.share.greencloud.databinding.ActivitySearchResultBinding;
import com.share.greencloud.domain.model.RentalOffice;
import com.share.greencloud.presentation.adapter.SearchResultAdapter;
import com.share.greencloud.presentation.viewmodel.MapFragmentViewModel;

import java.util.List;

import timber.log.Timber;

public class SearchResultActivity extends BaseActivity<ActivitySearchResultBinding>
        implements SearchView.OnQueryTextListener {

    private ActivitySearchResultBinding binding;
    private SearchResultAdapter adapter;
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
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupView() {
        binding.contentSearchResult.searchResultRecyclerView.setHasFixedSize(true);
        new Handler().postDelayed(this::getItemFromDB, 500);
        binding.searchV.setOnQueryTextListener(this);
        binding.searchV.onActionViewExpanded();
    }


    private void setupViewModel() {
        mapFragmentViewModel = ViewModelProviders.of(this).get(MapFragmentViewModel.class);
    }

    public void loadData(List<RentalOffice> rentalOffices) {
        adapter = new SearchResultAdapter(rentalOffices);
        binding.contentSearchResult.searchResultRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getItemFromDB() {
        mapFragmentViewModel.getAllRentalOfficesFromDB().observe(this, rentalOffices -> {
            loadData(rentalOffices);
            binding.contentSearchResult.searchResultRecyclerView.setVisibility(View.VISIBLE);
            binding.contentSearchResult.progressBar.setVisibility(View.INVISIBLE);
        });

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

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("onResume");
        new Handler().postDelayed(this::getItemFromDB, 500);
        binding.searchV.onActionViewExpanded();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();  // 기존에 검색 내역 및 대야소 목록을 다시 보여주시기 위해서 액티비티를 종료 처리.
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null && !newText.equals("")) {
            adapter.search(newText, null);
            return true;
        }
        return false;
    }
}
