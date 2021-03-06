/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.presentation.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.share.greencloud.R;
import com.share.greencloud.domain.model.News;
import com.share.greencloud.presentation.activity.WebViewActivity;

public class ItemGreenNewsViewModel extends BaseObservable {

    private News news;
    private Context context;

    public ItemGreenNewsViewModel(News news, Context context) {
        this.news = news;
        this.context = context;
    }

    //BindingAdapter을 활용하여 xml에서 뉴스 이미지를 로딩되도록 함.
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).placeholder(R.drawable.um_green).transition(DrawableTransitionOptions.withCrossFade()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    public void setNews(News news) {
        this.news = news;
        notifyChange();
    }

    public void onItemClick(View view) {
        Intent i = new Intent(view.getContext(), WebViewActivity.class);
        i.putExtra("URL", news.getUrl());
        i.putExtra("TITLE", news.getFrom());
        context.startActivity(i);
    }

    public String getTitle() {
        return news.getTitle();
    }

    public String getDesc() {
        return news.getDesc();
    }

    public String getFrom() {
        return news.getFrom();
    }

    public String getImageUrl() {
        return news.getImage();
    }
}
