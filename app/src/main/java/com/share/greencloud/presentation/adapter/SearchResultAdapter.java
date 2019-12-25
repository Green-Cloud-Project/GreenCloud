/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.presentation.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.share.greencloud.R;
import com.share.greencloud.databinding.ItemSearchResultBinding;
import com.share.greencloud.domain.model.RentalOffice;

import java.util.List;

public class SearchResultAdapter extends DynamicSearchAdapter<RentalOffice> {

    private List<RentalOffice> rentalOfficeList;

    public SearchResultAdapter(List<RentalOffice> rentalOffices) {
        super(rentalOffices);
        rentalOfficeList = rentalOffices;

    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchResultBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_search_result,
                parent, false);

        return new SearchResultViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        holder.bindNews(rentalOfficeList.get(position));
    }

    @Override
    public int getItemCount() {
        return rentalOfficeList.size();
    }

}
