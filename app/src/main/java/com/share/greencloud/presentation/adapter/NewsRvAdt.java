package com.share.greencloud.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.share.greencloud.R;
import com.share.greencloud.databinding.ItemNewsBinding;
import com.share.greencloud.domain.model.News;
import com.share.greencloud.presentation.viewmodel.ItemGreenNewsViewModel;

import java.util.List;

public class NewsRvAdt extends RecyclerView.Adapter<NewsRvAdt.NewsViewHolder> {

    private Context mContext;
    private List<News> newsList;

    public NewsRvAdt(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemNewsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_news,
                parent, false);

        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bindNews(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        ItemNewsBinding mItemNewsBinding;

        NewsViewHolder(ItemNewsBinding binding) {
            super(binding.itemNews);
            mItemNewsBinding = binding;
        }
        // 뉴스 리스트의 하나의 아이템을 뷰모델에 binding 시켜주는 함수
        void bindNews(News newsItem) {
            if (mItemNewsBinding.getViewmodel() == null) {
                mItemNewsBinding.setViewmodel(
                        new ItemGreenNewsViewModel(newsItem, itemView.getContext()));
            } else {
                mItemNewsBinding.getViewmodel().setNews(newsItem);
            }
        }
    }
}
