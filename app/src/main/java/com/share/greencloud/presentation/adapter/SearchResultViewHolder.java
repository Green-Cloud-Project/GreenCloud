package com.share.greencloud.presentation.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.share.greencloud.databinding.ItemSearchResultBinding;
import com.share.greencloud.domain.model.RentalOffice;
import com.share.greencloud.presentation.viewmodel.ItemSearchResultViewModel;

public class SearchResultViewHolder  extends RecyclerView.ViewHolder {
    ItemSearchResultBinding itemSearchResultBinding;

    SearchResultViewHolder(ItemSearchResultBinding binding) {
        super(binding.itemSearchResult);
        itemSearchResultBinding = binding;
    }

    public void bindNews(RentalOffice rentalOfficeItem) {
        if (itemSearchResultBinding.getViewmodel() == null) {
            itemSearchResultBinding.setViewmodel(
                    new ItemSearchResultViewModel(rentalOfficeItem, itemView.getContext()));
        } else {
            itemSearchResultBinding.getViewmodel().setRentalOffice(rentalOfficeItem);
        }
    }
}

