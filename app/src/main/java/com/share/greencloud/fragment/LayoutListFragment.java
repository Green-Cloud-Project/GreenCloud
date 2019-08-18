package com.share.greencloud.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.share.greencloud.R;


/**
 A simple {@link Fragment} subclass.
 */
public class LayoutListFragment extends Fragment {

    public interface CommunicateListener {
        void clickItem(FragmentList fragmentListType);
    }

    private CommunicateListener mCommunicateListener;

    public enum FragmentList {
        LOGIN,
        MYGREEN,
        GREEN_CLOUD_INFO,
        CAMERA,
        DRAWER,
        COMPLETE,
        CODE,
        MAP,
        NEWS,
        MACHINE,
        NAVIGATION
    }

    public LayoutListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_layout_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCommunicateListener = (CommunicateListener) getContext();

        RecyclerView rv_layout_list = view.findViewById(R.id.rv_layout_list);
        rv_layout_list.setAdapter(new LayoutRvAdt());
    }

    private class LayoutHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private RelativeLayout rl_item;

        public LayoutHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            rl_item = itemView.findViewById(R.id.rl_item);
        }
    }

    private class LayoutRvAdt extends RecyclerView.Adapter<LayoutHolder> {
        @NonNull
        @Override
        public LayoutHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            return new LayoutHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout_list, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull LayoutHolder layoutHolder, final int position) {
            layoutHolder.name.setText("" + FragmentList.values()[position]);
            layoutHolder.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCommunicateListener.clickItem(FragmentList.values()[position]);
                }
            });
        }

        @Override
        public int getItemCount() {
            return FragmentList.values().length;
        }
    }

}
