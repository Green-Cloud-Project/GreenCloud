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


public class UserHistoryTwoFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserHistoryAdapter adapter;

    private ArrayList<UserRentalHistory> data = new ArrayList<>();
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        populateDate();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    private void populateDate()  {

        data.add(new UserRentalHistory("2019.11.14 \n10:11",
                "",
                "강남역",
                "",
                2));
        adapter = new UserHistoryAdapter(getContext(), data);

    }
}
