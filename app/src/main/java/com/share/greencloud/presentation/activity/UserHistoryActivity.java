package com.share.greencloud.presentation.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import com.share.greencloud.R;
import com.share.greencloud.common.BaseActivity;
import com.share.greencloud.databinding.ActivityUserHistoryBinding;
import com.share.greencloud.presentation.adapter.AlarmRvAdt;

public class UserHistoryActivity extends BaseActivity<ActivityUserHistoryBinding> {
    private ActivityUserHistoryBinding binding;
    private AlarmRvAdt adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = getBinding();
        setupToolbar();
        setuoView();
        loadData();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_user_history;
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setuoView() {
        binding.contentUserHistory.userHistoryRecyclerView.setHasFixedSize(true);

        binding.contentUserHistory.userHistorySwipeLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                loadData();
                binding.contentUserHistory.userHistorySwipeLayout.setRefreshing(false);
            }, 1000);

        });
        binding.contentUserHistory.userHistorySwipeLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light
        );
    }

    public void loadData() {
        //todo replace with rest API later..
        if (adapter == null) {
            adapter = new AlarmRvAdt();
        }
        binding.contentUserHistory.userHistoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
