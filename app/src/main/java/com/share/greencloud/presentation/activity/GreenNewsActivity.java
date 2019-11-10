package com.share.greencloud.presentation.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.recyclerview.widget.RecyclerView;

import com.share.greencloud.R;
import com.share.greencloud.common.BaseActivity;
import com.share.greencloud.databinding.ActivityGreenNewsBinding;
import com.share.greencloud.domain.model.News;
import com.share.greencloud.presentation.adapter.NewsRvAdt;
import com.share.greencloud.presentation.viewmodel.GreenNewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class GreenNewsActivity extends BaseActivity<ActivityGreenNewsBinding> {
    private ActivityGreenNewsBinding binding;
    private NewsRvAdt adapter;
    private List<News> mNewsList = new ArrayList<>();
    private GreenNewsViewModel viewModel;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_green_news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        setupToolbar();
        setupViewModel();
        setupView();
    }

    private void setupViewModel() {
        viewModel = new GreenNewsViewModel(this);
        binding.contentGreenNews.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.getNewsData().observe(this, news -> {
            mNewsList = news;
            loadData(news);
        });

    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setupView() {
        binding.contentGreenNews.greenNewsRecyclerView.setHasFixedSize(true);

        binding.contentGreenNews.swipeLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                loadData(mNewsList);
                binding.contentGreenNews.swipeLayout.setRefreshing(false);
            }, 1000);

        });
        binding.contentGreenNews.swipeLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light
        );
    }

    public void loadData(List<News> newsList) {
        //todo replace with rest API later..
        if (adapter == null) {
            adapter = new NewsRvAdt(this, newsList);
        }
        binding.contentGreenNews.greenNewsRecyclerView.setAdapter(adapter);
        runLayoutAnimation(binding.contentGreenNews.greenNewsRecyclerView);
        adapter.notifyDataSetChanged();
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
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
