package com.share.greencloud.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.share.greencloud.R;

public class NewsVH extends RecyclerView.ViewHolder {

    public static NewsVH create(ViewGroup viewGroup) {
        return new NewsVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false));
    }

    public NewsVH(@NonNull View itemView) {
        super(itemView);
    }
}
