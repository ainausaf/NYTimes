package com.example.NYTimes.newsDetails;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.NYTimes.BaseActivity;
import com.example.NYTimes.R;
import com.example.NYTimes.data.Repository;
import com.example.NYTimes.model.Multimedium;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailsActivity extends BaseActivity implements NewsDetailsContract.View {

    @BindView(R.id.iv_news_detail_image)
    ImageView newsDetailImage;

    @BindView(R.id.news_detail)
    TextView newsHeadLine;

    @BindView(R.id.share_button)
    Button shareButton;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    NewsDetailsContract.Presenter newsDetailsPresenter;

    @Inject
    Repository repository;

    private NewsDetailsComponent newsDetailsComponent;
    private Multimedium newsData;
    private String sharedUrl;


    @Override
    protected void onCreate(Bundle onSaveInstance){
        super.onCreate(onSaveInstance);
        setContentView(R.layout.news_details);
        ButterKnife.bind(this);
        initNewsDetailDaggerComponent();
        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));
        newsData = getIntent().getParcelableExtra("newsDetails");
        sharedUrl = getIntent().getStringExtra("shortUrl");
        newsDetailsPresenter.onCreate();
        initViews();
        setOnButtonClickListener();
    }
    //share functionality of the news Url
    private void setOnButtonClickListener() {
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, sharedUrl);
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this site!");
                startActivity(Intent.createChooser(intent, "Share"));
            }
        });
    }

    public void initViews() {
        showProgressBar(false);
        String imageUrl="";
       if(newsData!=null){
            imageUrl = newsData.getUrl();
        }
        if(imageUrl!=null && (!imageUrl.isEmpty())) {
            imageUrl.replaceAll("\\\\", "");
        }

        Glide.with(this).load( imageUrl ).placeholder(
                R.drawable.layout_placeholder).error(
                R.drawable.layout_placeholder).into(newsDetailImage);
        if(newsData.getCaption()!=null && (!newsData.getCaption().isEmpty())){
        newsHeadLine.setText(newsData.getCaption());
        }
    }

    @Override
    public void showProgressBar(boolean isDisplayProgressBar) {
        if (isDisplayProgressBar)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorMessage() {
        System.out.println("APP Crashed");
    }

    private void initNewsDetailDaggerComponent() {
        super.initDaggerComponent();
        if (newsDetailsComponent == null) {
            newsDetailsComponent = DaggerNewsDetailsComponent.builder()
                    .appComponent(appComponent)
                    .newsDetailsModule(new NewsDetailsModule (this))
                    .build();
            newsDetailsComponent.inject(this);
        }
    }
}
