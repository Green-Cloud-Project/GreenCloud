package com.share.greencloud.presentation.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlarmRvAdt extends RecyclerView.Adapter<AlarmVH> {
    @NonNull
    @Override
    public AlarmVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AlarmVH.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 50;
    }
}
