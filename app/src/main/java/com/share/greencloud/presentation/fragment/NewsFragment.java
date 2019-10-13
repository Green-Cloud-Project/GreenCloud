package com.share.greencloud.presentation.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.share.greencloud.R;
import com.share.greencloud.domain.model.News;
import com.share.greencloud.presentation.adapter.NewsRvAdt;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment {

    View         view;
    List<News>   mNewsList;
    RecyclerView recyclerView;
    NewsRvAdt    adapter;
    Context      mContext;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mContext  = container.getContext();
        view =  inflater.inflate(R.layout.fragment_news, container, false);
        initalizeView();
        return view;

    }


    public void initalizeView()   {


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light
        );

        populateData();
        loadData();


        //adapter = new NewsAdapter(getActivity(), mNewsList);
        //recyclerView.setAdapter(adapter);

    }


    public void loadData()  {

        //TODO: replace with rest API later..
        adapter = new NewsRvAdt(getActivity(), mNewsList);
        recyclerView.setAdapter(adapter);
        runLayoutAnimation(recyclerView);

    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }


    public void populateData()  {


        mNewsList = new ArrayList<>();

//        mNewsList.add(new News(R.drawable.um_green,
//                "日 우산 공유 서비스 회수율 100%…中? 3개월 만에 100% 소실",
//                "일본의 높은 시민의식",
//                "동아일보",
//                "http://www.donga.com/news/article/all/20190405/94914959/2"
//
//        ));
//
//
//        mNewsList.add(new News(R.drawable.um_green,
//                "완벽히 실패한 공유우산 사업을 성공으로..",
//                "기발한 성공 전략",
//                "스마트라이",
//                "https://smartaedi.tistory.com/141"
//        ));
//
//
//        mNewsList.add(new News(R.drawable.um_green,
//                "티백 제품, 끓는 물에서 다량의 미세 플라스틱 조각 나와",
//                "티백 끊는 물에 147억개 미세플라스틱 나왔",
//                "중앙일보",
//                "https://news.joins.com/article/23587906?fbclid=IwAR3gNo53CvJFSnFgZy3J7kO9LHk6vKxgewV7HN8bLnbOL_TzFdZK-W_hxhM"
//        ));
//
//
//        mNewsList.add(new News(R.drawable.um_green,
//                "버려진 우산에 불어넣는 초록빛 생명! 우산 업사이클링, 큐클리프",
//                "다양한 가치를 실현해내는 업사이클링 아이템",
//                "유한킴벌리",
//                "http://www.yuhan-kimberly.co.kr/Mobile/Newsroom/YkstoryView/987"
//        ));
//
//
//        mNewsList.add(new News(R.drawable.um_green,
//                "중국서 날아오는 게 미세먼지뿐? 산성비도 있다",
//                "40곳 중 35곳은 연중 산성비",
//                "중일보",
//                "https://news.joins.com/article/23375740"
//        ));

    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



}
