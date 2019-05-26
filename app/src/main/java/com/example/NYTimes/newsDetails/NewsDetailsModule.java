package com.example.NYTimes.newsDetails;

import com.example.NYTimes.data.Repository;
import com.example.NYTimes.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsDetailsModule {

    NewsDetailsActivity newsDetailsActivity;

    public NewsDetailsModule(NewsDetailsActivity newsDetailsActivity) {
        this.newsDetailsActivity = newsDetailsActivity;
    }

    @Provides
    @ActivityScope
    public NewsDetailsContract.View provideMainActivityView() {
        return this.newsDetailsActivity;
    }

    @Provides
    @ActivityScope
    public NewsDetailsContract.Presenter providePresenter(NewsDetailsContract.View view,
                                                           Repository repository) {
        return new NewsDetailsPresenter(view, repository);
    }
}
