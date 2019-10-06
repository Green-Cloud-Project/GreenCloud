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

    View    view;
    Context mContext;

    List<News>   mNewsList;
    RecyclerView recyclerView;
    NewsRvAdt    adapter;

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

        mNewsList.add(new News(R.drawable.um_green,
                "해리 왕자: '자연보호를 히피라고 업신여겨선 안돼",
                "누군가에겐 히피 같으 소리처럼 들릴 수도 있겠",
                "BBC News",
                "https://www.bbc.com/korean/international-49875987"

        ));


        mNewsList.add(new News(R.drawable.um_green,
                "기후변화로 해수면 최대 1.1m(2100년) 상승 전망",
                "IPCC 해양·빙권 특별보고서",
                "EMS",
                "http://www.iemnews.com/view.htm?id=747&code=news&category_no=0"
        ));


        mNewsList.add(new News(R.drawable.um_green,
                "티백 제품, 끓는 물에서 다량의 미세 플라스틱 조각 나와",
                "티백 끊는 물에 147억개 미세플라스틱 나왔",
                "중앙일보",
                "https://news.joins.com/article/23587906?fbclid=IwAR3gNo53CvJFSnFgZy3J7kO9LHk6vKxgewV7HN8bLnbOL_TzFdZK-W_hxhM"
        ));


        mNewsList.add(new News(R.drawable.um_green,
                "3일에 한번 비, 역대 가장 많은 태풍 영향",
                "더운 공기와 찬 공기로 인한 정체전선 형성해 잦은 비",
                "환경일보",
                "http://www.hkbs.co.kr/news/articleView.html?idxno=534021"
        ));


        mNewsList.add(new News(R.drawable.um_green,
                "중국서 날아오는 게 미세먼지뿐? 산성비도 있다",
                "40곳 중 35곳은 연중 산성비",
                "중일보",
                "https://news.joins.com/article/23375740"
        ));

    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



}
