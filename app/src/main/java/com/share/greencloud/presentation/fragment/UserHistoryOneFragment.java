package com.share.greencloud.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.share.greencloud.R;
import com.share.greencloud.domain.model.UserRentalHistory;
import com.share.greencloud.presentation.adapter.UserHistoryAdapter;

import java.util.ArrayList;


public class UserHistoryOneFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserHistoryAdapter adapter;
    private ArrayList<UserRentalHistory> data = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerview);

        populateDate();

        adapter = new UserHistoryAdapter(getContext(), data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    private void populateDate()  {

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        data.add(new UserRentalHistory("2019.09.14\n10:12","2019.09.16\n15:40","강남버스 터미널","잠실역",1));
        data.add(new UserRentalHistory("2019.10.14\n10:12","2019.10.16\n15:40","서울역","강남구청",3));
        data.add(new UserRentalHistory("2019.10.20\n10:12","2019.10.22\n15:40","강남역","잠실역",4));

    }

}
