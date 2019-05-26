package com.example.NYTimes.newsList;

import com.example.NYTimes.data.Repository;
import com.example.NYTimes.model.NYTimesData;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private Repository repository;


    private MainActivityContract.View view;

    public MainActivityPresenter(MainActivityContract.View view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onCreate() {
        view.showProgressBar(true);
        getNYTimesData();
    }

    //service call is being made to retrieve the list of news
    public void getNYTimesData() {
        final Observable<NYTimesData> nyTimesData = repository.getNYTimesData();
        nyTimesData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(nyTimesNews -> {
                    view.getNYTimesNews(nyTimesNews);
                    view.showProgressBar(false);
                }, throwable -> {
                    throwable.printStackTrace();
                    view.showErrorMessage();
                    view.showProgressBar(false);
                });
    }
}
