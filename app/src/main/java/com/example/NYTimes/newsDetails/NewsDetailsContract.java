package com.example.NYTimes.newsDetails;

public interface NewsDetailsContract {

    public interface View{

        void showProgressBar(boolean isDisplayProgressBar);
        void showErrorMessage();
    }

    public interface Presenter{

        void onCreate();
    }
}
