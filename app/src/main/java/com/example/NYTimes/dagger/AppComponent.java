package com.example.NYTimes.dagger;

import com.example.NYTimes.BaseActivity;
import com.example.NYTimes.data.Repository;
import com.example.NYTimes.newsDetails.NewsDetailsModule;
import com.example.NYTimes.newsList.MainActivityModule;
import com.example.NYTimes.retrofit.RetrofitModule;
import com.example.NYTimes.scope.ApplicationScope;

import dagger.Component;

/**
 * Dagger App Component with Application Scope
 */

@ApplicationScope
@Component(modules = {AppModule.class, RetrofitModule.class, MainActivityModule.class, NewsDetailsModule.class})
public interface  AppComponent {

    Repository getDataRepository();

    void inject(BaseActivity baseActivity);
}
