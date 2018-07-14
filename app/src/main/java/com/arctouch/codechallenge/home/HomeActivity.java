package com.arctouch.codechallenge.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.details_screen.DetailsScreenActivity;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.mvp.home.HomePresenter;
import com.arctouch.codechallenge.mvp.home.HomeView;
import com.arctouch.codechallenge.util.PaginationScrollListener;
import com.arctouch.codechallenge.util.RecyclerItemClickListener;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeView{
//    private List<Movie> listMovie = new ArrayList<>();

    HomePresenter homePresenter;

    private LinearLayoutManager linearLayoutManager;

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();
        homePresenter.searchGenres();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        homeAdapter = new HomeAdapter(getApplicationContext());
        mapComponents();
        mappingComponentsActions();
        if(homeAdapter == null){
            homeAdapter = new HomeAdapter(this);
            this.recyclerView.setAdapter(homeAdapter);
        }
        if(homePresenter == null){
            homePresenter = new HomePresenter();
            homePresenter.init(this);
        }else{
            homePresenter.refresh(this);
        }
    }

    public void mapComponents() {

        this.recyclerView = findViewById(R.id.recyclerView);
        this.progressBar = findViewById(R.id.progressBar);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.recyclerView.setAdapter(homeAdapter);

    }

    public void mappingComponentsActions() {
        this.recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        homePresenter.loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return homePresenter.getTotalPages();
            }

            @Override
            public boolean isLastPage() {
                return homePresenter.isLastPage();
            }

            @Override
            public boolean isLoading() {
                return homePresenter.isLoading();
            }
        });

        this.recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        this.recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Movie movie = new Movie();
                                movie = homeAdapter.getMoviePosition(position);
                                onMovieClick(movie);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )

        );
    }

    @Override
    public void onMovieClick(Movie mMovie) {
        Context context = getApplicationContext();
        Intent intent = new Intent(getApplicationContext(), DetailsScreenActivity.class);
        intent.putExtra("serialize_data", String.valueOf(mMovie.id));
        startActivity(intent);
    }

    @Override
    public void showLoading(boolean aShow) {
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void homeAdapterLoadingFooter(boolean aShow) {
        if (aShow == true) {
            homeAdapter.addLoadingFooter();
        }else{
            homeAdapter.removeLoadingFooter();
        }
    }

    @Override
    public void loadMovies(List<Movie> mList) {
        homeAdapter.addAll(mList);
        homeAdapter.notifyDataSetChanged();
    }
}
