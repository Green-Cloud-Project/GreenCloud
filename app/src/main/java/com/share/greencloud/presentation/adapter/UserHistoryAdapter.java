/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.share.greencloud.R;
import com.share.greencloud.domain.model.UserRentalHistory;
import java.util.ArrayList;


public class UserHistoryAdapter extends RecyclerView.Adapter<UserHistoryAdapter.UserRentalHistoryViewHolder>{

    private Context context;
    private ArrayList<UserRentalHistory> userRentalHistory;
    private LayoutInflater layoutInflater;

    public UserHistoryAdapter(Context context, ArrayList<UserRentalHistory> data) {

        this.context         = context;
        this.userRentalHistory = data;
        layoutInflater       = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public UserRentalHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.rental_list_item, parent, false);
        UserRentalHistoryViewHolder history  = new UserRentalHistoryViewHolder(view);
        return history;
    }

    @Override
    public void onBindViewHolder(@NonNull UserRentalHistoryViewHolder holder, int position) {

        UserRentalHistory currentData = userRentalHistory.get(position);
        holder.rentDate.setText(currentData.getRentDate());
        holder.returnDate.setText(currentData.getReturnDate());
        holder.rentLocation.setText(currentData.getRentLocation());
        holder.returnLocation.setText(currentData.getReturnLocation());
        holder.status.setText(getStatus(currentData.getStatus()));

    }

    @Override
    public int getItemCount() {
        return userRentalHistory.size();
    }

    public class UserRentalHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView  rentDate;
        private TextView  returnDate;
        private TextView  rentLocation;
        private TextView  returnLocation;
        private TextView  status;


        public UserRentalHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            rentDate       =  itemView.findViewById(R.id.tv_rent_date);
            returnDate     =  itemView.findViewById(R.id.tv_return_date);
            rentLocation   =  itemView.findViewById(R.id.tv_rent_location);
            returnLocation =  itemView.findViewById(R.id.tv_return_location);
            status         =  itemView.findViewById(R.id.tv_rental_status);
        }
    }

    public String getStatus(int status) {

        switch(status)  {
            case 1:
                return "완료";
            case 2:
                return "1일 6시간 사용중";
            case 3:
                return "구매";
            case 4:
                return "분실";
            default:
        }
        return null;
    }
}
