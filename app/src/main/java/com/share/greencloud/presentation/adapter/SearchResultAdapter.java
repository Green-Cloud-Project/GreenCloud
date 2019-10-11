package com.share.greencloud.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.share.greencloud.R;
import com.share.greencloud.databinding.ItemSearchResultBinding;
import com.share.greencloud.domain.model.RentalOffice;
import com.share.greencloud.presentation.viewmodel.ItemSearchResultViewModel;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {

    private Context mContext;
    private List<RentalOffice> rentalOfficeList;

    public SearchResultAdapter(Context context, List<RentalOffice> rentalOffices) {
        mContext = context;
        rentalOfficeList = rentalOffices;
    }

    public SearchResultAdapter() {
    }

    @NonNull
    @Override
    public SearchResultAdapter.SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchResultBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_search_result,
                parent, false);

        return new SearchResultViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.SearchResultViewHolder holder, int position) {
        holder.bindNews(rentalOfficeList.get(position));
    }

    @Override
    public int getItemCount() {
        return rentalOfficeList.size();
    }

    static class SearchResultViewHolder extends RecyclerView.ViewHolder {

        ItemSearchResultBinding itemSearchResultBinding;

        SearchResultViewHolder(ItemSearchResultBinding binding) {
            super(binding.itemSearchResult);
            itemSearchResultBinding = binding;
        }

        void bindNews(RentalOffice rentalOfficeItem) {
            if (itemSearchResultBinding.getViewmodel() == null) {
                itemSearchResultBinding.setViewmodel(
                        new ItemSearchResultViewModel(rentalOfficeItem, itemView.getContext()));
            } else {
                itemSearchResultBinding.getViewmodel().setRentalOffice(rentalOfficeItem);
            }
        }
    }
}
