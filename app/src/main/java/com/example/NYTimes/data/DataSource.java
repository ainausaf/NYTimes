package com.example.NYTimes.data;

import com.example.NYTimes.model.NYTimesData;
import com.example.NYTimes.retrofit.APIinterface;

import java.util.Map;

import rx.Observable;

public class DataSource {

    private APIinterface apiInterface;

    private Map<String, String> queryMap;

    public DataSource(APIinterface apiInterface) {
        this.apiInterface = apiInterface;

    }

    //network call to get the news list
    Observable<NYTimesData> getNYTimesData() {

        return apiInterface.getNYTimesData()
                .flatMap(Observable::just);
    }
}
