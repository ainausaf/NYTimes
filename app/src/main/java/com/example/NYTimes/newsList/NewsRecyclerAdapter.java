package com.example.NYTimes.newsList;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.NYTimes.R;
import com.example.NYTimes.model.NYTimesData;
import com.example.NYTimes.model.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>{

    private Context context;
    private List<Result> NYTimesNewsList;
    private List<Result> NYTimesNewsListFiltered;
    private final OnItemClickListener listener;


    public NewsRecyclerAdapter(Context context, List<Result> NYTimesNewsList, OnItemClickListener listener) {
        this.context = context;
        this.NYTimesNewsList = NYTimesNewsList;
        this.NYTimesNewsListFiltered = NYTimesNewsList;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cardview_feed)
        CardView cardViewFeed;
        @BindView(R.id.iv_news_image)
        ImageView newsImage;
        @BindView(R.id.news_headline)
        TextView newsHeadline;
        @BindView(R.id.news_short_url)
        TextView newsShortUrl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public NewsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsRecyclerAdapter.ViewHolder viewHolder, int position) {

        Result nyTimesData = NYTimesNewsListFiltered.get(position);
        String thumbnailUrl="";
        if(nyTimesData.getMultimedia().size()!=0 && nyTimesData.getMultimedia().get(0)!=null){
            thumbnailUrl = nyTimesData.getMultimedia().get(0).getUrl();
        }
        if(thumbnailUrl!=null && (!thumbnailUrl.isEmpty())) {
            thumbnailUrl.replaceAll("\\\\", "");
        }
        String shortUrl = nyTimesData.getShortUrl();
        if(shortUrl!=null) {
            shortUrl.replaceAll("\\\\", "");
        }
        Glide.with(context).load( thumbnailUrl ).placeholder(
                R.drawable.layout_placeholder).error(
                R.drawable.layout_placeholder).into(viewHolder.newsImage);

        viewHolder.newsHeadline.setText(nyTimesData.getTitle());
        viewHolder.newsShortUrl.setText(shortUrl);

        viewHolder.cardViewFeed.setOnClickListener(v -> listener.onItemClick(NYTimesNewsListFiltered.get(viewHolder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        if(NYTimesNewsListFiltered!=null) {
            return NYTimesNewsListFiltered.size();
        }
        return 0;
    }

    public void addAll(List<Result> NYTimesNewsList, boolean isfiltered) {
        if(isfiltered){
            this.NYTimesNewsListFiltered.clear();
        }
        this.NYTimesNewsListFiltered.addAll(NYTimesNewsList);
        notifyDataSetChanged();

    }

    public interface OnItemClickListener {
        void onItemClick(Result news);
    }
}
