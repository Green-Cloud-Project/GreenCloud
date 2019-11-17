package com.example.tabbedproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TabProcessing extends Fragment {

    private RecyclerView recyclerView;
    private MyRentalHistoryAdapter adapter;

    private ArrayList<MyRentalHistory> data = new ArrayList<>();
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabfragment, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        populateDate();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    private void populateDate()  {

        data.add(new MyRentalHistory("2019.11.14 \n10:11",
                "",
                "강남역",
                "",
                2));
        adapter = new MyRentalHistoryAdapter(getContext(), data);

    }
}
