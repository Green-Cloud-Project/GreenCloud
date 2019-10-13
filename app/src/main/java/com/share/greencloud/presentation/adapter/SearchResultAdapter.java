package com.share.greencloud.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.share.greencloud.R;
import com.share.greencloud.databinding.ItemSearchResultBinding;
import com.share.greencloud.domain.model.RentalOffice;
import com.share.greencloud.presentation.viewmodel.ItemSearchResultViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>
        implements Filterable {

    private Context mContext;
    private List<RentalOffice> rentalOfficeList;
    private List<RentalOffice> rentalOffices;
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RentalOffice> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(rentalOffices);
            } else {
                String filteredPattern = constraint.toString().toLowerCase().trim();

                for (RentalOffice rentalOffice : rentalOffices) {
                    if (rentalOffice.getOffice_location().toLowerCase().trim().contains(filteredPattern) && rentalOffice != null) {
                        filteredList.add(rentalOffice);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            rentalOfficeList.clear();
            rentalOfficeList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public SearchResultAdapter(Context context, List<RentalOffice> rentalOffices) {
        mContext = context;
        rentalOfficeList = rentalOffices;
        this.rentalOffices = rentalOffices;
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

    @Override
    public Filter getFilter() {
        return filter;
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
