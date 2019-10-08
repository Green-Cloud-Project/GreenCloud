package com.share.greencloud.presentation.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.share.greencloud.domain.model.News;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class GreenNewsViewModel extends Observable {
    private MutableLiveData<List<News>> newsData = new MutableLiveData<List<News>>();
    private List<News> mNewsList;
    private Context context;

    public GreenNewsViewModel(@NonNull Context context) {
        this.context = context;
        fetchNewsData();
    }

    private void fetchNewsData() {
        mNewsList = new ArrayList<>();

        mNewsList.add(new News("https://dimg.donga.com/wps/NEWS/IMAGE/2019/04/05/94914984.2.jpg",
                "日 우산 공유 서비스 회수율 100%…中? 3개월 만에 100% 소실",
                "일본의 높은 시민의식",
                "동아일보",
                "http://www.donga.com/news/article/all/20190405/94914959/2"

        ));

        mNewsList.add(new News("http://cfile23.uf.tistory.com/image/99C4C7335A2A3737238F3B",
                "완벽히 실패한 공유우산 사업을 성공으로..",
                "기발한 성공 전략",
                "스마트라이",
                "https://smartaedi.tistory.com/141"
        ));

        mNewsList.add(new News("https://img.sbs.co.kr/newimg/news/20190926/201358370_1280.jpg",
                "티백 제품, 끓는 물에서 다량의 미세 플라스틱 조각 나와",
                "티백 끊는 물에 147억개 미세플라스틱 나왔",
                "중앙일보",
                "https://news.joins.com/article/23587906?fbclid=IwAR3gNo53CvJFSnFgZy3J7kO9LHk6vKxgewV7HN8bLnbOL_TzFdZK-W_hxhM"
        ));

        mNewsList.add(new News("http://www.yuhan-kimberly.co.kr/data/editor/20190724014835908897425.png",
                "버려진 우산에 불어넣는 초록빛 생명! 우산 업사이클링, 큐클리프",
                "다양한 가치를 실현해내는 업사이클링 아이템",
                "유한킴벌리",
                "http://www.yuhan-kimberly.co.kr/Mobile/Newsroom/YkstoryView/987"
        ));

        mNewsList.add(new News("",
                "중국서 날아오는 게 미세먼지뿐? 산성비도 있다",
                "40곳 중 35곳은 연중 산성비",
                "중일보",
                "https://news.joins.com/article/23375740"
        ));

        newsData.setValue(mNewsList);
    }

    public MutableLiveData<List<News>> getNewsData() {
        return newsData;
    }

}
