package com.example.NYTimes.newsList;

import com.example.NYTimes.model.NYTimesData;

import java.util.List;

public interface MainActivityContract {

    public interface View{

        void getNYTimesNews(NYTimesData NYTimesData);

        void showProgressBar(boolean isDisplayProgressBar);

        void showErrorMessage();

    }

    public interface Presenter{

        void onCreate();

        void getNYTimesData();

    }
}
