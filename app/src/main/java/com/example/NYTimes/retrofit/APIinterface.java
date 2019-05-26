package com.example.NYTimes.retrofit;

import com.example.NYTimes.model.NYTimesData;

import retrofit2.http.GET;
import rx.Observable;

public interface APIinterface {

    @GET("/svc/topstories/v2/world.json?api-key=OKsEwghCzAPR3kRr7Hp51cFn2tMfXWgj")
    Observable<NYTimesData> getNYTimesData();
}
