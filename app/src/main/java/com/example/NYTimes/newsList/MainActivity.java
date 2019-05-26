package com.example.NYTimes.newsList;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.NYTimes.BaseActivity;
import com.example.NYTimes.R;
import com.example.NYTimes.model.Multimedium;
import com.example.NYTimes.model.NYTimesData;
import com.example.NYTimes.model.Result;
import com.example.NYTimes.newsDetails.NewsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements  MainActivityContract.View{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    MainActivityContract.Presenter mainActivityPresenter;

    private MainActivityComponent mainActivityComponent;
    private NewsRecyclerAdapter recyclerAdapter;
    private List<Result> NYTimesNewsList;
    private Multimedium  multimedium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initDaggerAppComponent();
        ActionBar actionbar = this.getSupportActionBar();
        if(actionbar!=null) {
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));
            actionbar.setIcon(R.drawable.ic_launcher_foreground);
        }
        NYTimesNewsList = new ArrayList<>();
        initViews();
        mainActivityPresenter.onCreate();
    }

    private void initViews() {
        recyclerAdapter = new NewsRecyclerAdapter(this, NYTimesNewsList,  news -> startDetailsActivity(news));
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    // response to the on click event on the recycler view
   private void startDetailsActivity(Result news) {
        if(news.getMultimedia().size()!=0 && news.getMultimedia().get(3)!=null){
            multimedium = news.getMultimedia().get(3);
        }
        Intent i = new Intent(this, NewsDetailsActivity.class);
        i.putExtra("newsDetails", (Parcelable) multimedium);
        i.putExtra("shortUrl",news.getUrl());
        startActivity(i);
    }

    public void getNYTimesNews(NYTimesData NYTimesData){
        NYTimesNewsList = NYTimesData.getResults();
        recyclerAdapter.addAll(NYTimesNewsList,false);
    }

    @Override
    public void showProgressBar(boolean isDisplayProgressBar) {
        if (isDisplayProgressBar)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorMessage() {
        System.out.println("APP Crashed");
    }

    private void initDaggerAppComponent() {
        super.initDaggerComponent();
        if (mainActivityComponent == null) {
            mainActivityComponent = DaggerMainActivityComponent.builder()
                    .appComponent(appComponent)
                    .mainActivityModule(new MainActivityModule(this))
                    .build();
            mainActivityComponent.inject(this);
        }
    }

    //add the search bar functionality in the search bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                getNewsBasedOnSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if(query.isEmpty()){
                    searchView.clearFocus();
                    getNewsBasedOnSearch(query);
                    return true;
                }
                return false;
            }

        });

        return true;
    }

    private void getNewsBasedOnSearch(String query) {
        List<Result> searchedList= new ArrayList<>();
        if(query.isEmpty()){
           recyclerAdapter.addAll(NYTimesNewsList,true);
        }else {
            for (Result news : NYTimesNewsList) {
                if (news.getTitle() != null) {
                    String text = news.getTitle().toLowerCase();
                    if (text.contains(query)) {
                        searchedList.add(news);
                    }
                }
            }
            if(searchedList.isEmpty()){
                Context context = getApplicationContext();
                CharSequence text = "Couldn't find the search result";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        recyclerAdapter.addAll(searchedList,true);
        }
    }

    @Override
    public void onBackPressed() {
        recyclerAdapter.addAll(NYTimesNewsList,true);
    }

}
