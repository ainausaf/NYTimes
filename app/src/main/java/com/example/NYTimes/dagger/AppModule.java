package com.example.NYTimes.dagger;

import com.example.NYTimes.NYTimesApplication;
import com.example.NYTimes.retrofit.RetrofitModule;

import dagger.Module;

@Module(includes = RetrofitModule.class)
public class AppModule {

    private NYTimesApplication baseApplication;

    public AppModule(NYTimesApplication baseApplication) {
        this.baseApplication = baseApplication;
    }

}
