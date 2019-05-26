package com.example.NYTimes.data;

import com.example.NYTimes.model.NYTimesData;

import java.util.List;

import rx.Observable;

public class Repository {

    private DataSource remoteDataSource;

    public Repository(DataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    //to get the news List
    public Observable<NYTimesData> getNYTimesData() {

        Observable<NYTimesData> observable;

        observable = remoteDataSource.getNYTimesData();

        return observable;
    }
}
