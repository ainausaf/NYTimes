package com.example.NYTimes.newsDetails;

import com.example.NYTimes.dagger.AppComponent;
import com.example.NYTimes.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {AppComponent.class},
        modules = {NewsDetailsModule.class}
)
public interface NewsDetailsComponent {

    NewsDetailsActivity inject(NewsDetailsActivity newsDetailsActivity);
}
