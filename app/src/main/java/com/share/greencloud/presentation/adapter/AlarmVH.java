package com.share.greencloud.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.share.greencloud.R;

public class AlarmVH extends RecyclerView.ViewHolder {

    public static AlarmVH create(ViewGroup viewGroup) {
        return new AlarmVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_noti, viewGroup, false));
    }

    public AlarmVH(@NonNull View itemView) {
        super(itemView);
    }
}
