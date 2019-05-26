package com.example.NYTimes;

import android.app.Application;
import android.content.Context;

import com.example.NYTimes.dagger.AppComponent;
import com.example.NYTimes.dagger.AppModule;
import com.example.NYTimes.dagger.DaggerAppComponent;
import com.example.NYTimes.retrofit.RetrofitModule;

public class NYTimesApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .retrofitModule(new RetrofitModule())
                .build();
    }

    public AppComponent getApplicationComponent(){
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
