package com.share.greencloud.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.share.greencloud.R;
import com.share.greencloud.domain.model.News;
import com.share.greencloud.presentation.activity.WebViewActivity;

import java.util.List;

public class NewsRvAdt extends RecyclerView.Adapter<NewsRvAdt.NewsViewHolder> {


    private Context mContext;
    private int lastPosition = -1;

    private List<News> newsList;

    public NewsRvAdt(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_news, null);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        News news = newsList.get(position);

        holder.tv_title.setText(news.getTitle());
        holder.tv_desc.setText(news.getDesc());
        holder.tv_from.setText(news.getFrom());
        holder.imageView.setImageDrawable(mContext.getResources().getDrawable(news.getImage(), null));

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_desc, tv_from;
        RelativeLayout rl_layout;
        ImageView imageView;
        CardView cardview;

        public NewsViewHolder(View itemView) {
            super(itemView);

            tv_title  = itemView.findViewById(R.id.tv_title);
            tv_desc   = itemView.findViewById(R.id.tv_desc);
            tv_from   = itemView.findViewById(R.id.tv_from);
            imageView = itemView.findViewById(R.id.iv_image);
            rl_layout = itemView.findViewById(R.id.rl_vv);
            cardview  = itemView.findViewById(R.id.cardview);
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news = newsList.get(getAdapterPosition());
                    Intent i = new Intent(mContext, WebViewActivity.class);
                    i.putExtra("URL", news.getUrl());
                    i.putExtra("TITLE",news.getFrom());
                    mContext.startActivity(i);

                }
            });


        }

    }
}
