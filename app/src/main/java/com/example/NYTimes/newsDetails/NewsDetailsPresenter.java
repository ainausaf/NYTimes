package com.example.NYTimes.newsDetails;

import com.example.NYTimes.data.Repository;

public class NewsDetailsPresenter implements NewsDetailsContract.Presenter{

    private Repository repository;

    private NewsDetailsContract.View view;

    public NewsDetailsPresenter(NewsDetailsContract.View view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onCreate(){
        view.showProgressBar(true);
    }
}
