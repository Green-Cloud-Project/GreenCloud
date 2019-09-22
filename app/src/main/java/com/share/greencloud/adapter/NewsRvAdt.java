package com.share.greencloud.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRvAdt extends RecyclerView.Adapter<NewsVH> {
    @NonNull
    @Override
    public NewsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return NewsVH.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 50;
    }
}
