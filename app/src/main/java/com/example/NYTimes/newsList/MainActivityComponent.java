package com.example.NYTimes.newsList;

import com.example.NYTimes.dagger.AppComponent;
import com.example.NYTimes.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = {AppComponent.class},
        modules = {MainActivityModule.class}
)

public interface MainActivityComponent {

    MainActivity inject (MainActivity mainActivity);
}
