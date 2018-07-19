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
import com.arctouch.codechallenge.details.DetailsScreenActivity;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.util.PaginationScrollListener;
import com.arctouch.codechallenge.util.RecyclerItemClickListener;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeView{
    HomePresenter mHomePresenter;

    private LinearLayoutManager mLinearLayoutManager;

    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onStart() {
        super.onStart();
        mHomePresenter.searchGenres();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mHomeAdapter = new HomeAdapter(getApplicationContext());
        mapComponents();
        mappingComponentsActions();
        if(mHomeAdapter == null){
            mHomeAdapter = new HomeAdapter(this);
            this.mRecyclerView.setAdapter(mHomeAdapter);
        }
        if(mHomePresenter == null){
            mHomePresenter = new HomePresenter();
            mHomePresenter.init(this);
        }else{
            mHomePresenter.refresh(this);
        }
    }

    public void mapComponents() {

        this.mRecyclerView = findViewById(R.id.recyclerView);
        this.mProgressBar = findViewById(R.id.progressBar);

        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        this.mRecyclerView.setLayoutManager(mLinearLayoutManager);
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        this.mRecyclerView.setAdapter(mHomeAdapter);

    }

    public void mappingComponentsActions() {
        this.mRecyclerView.addOnScrollListener(new PaginationScrollListener(mLinearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHomePresenter.loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return mHomePresenter.getTotalPages();
            }

            @Override
            public boolean isLastPage() {
                return mHomePresenter.isLastPage();
            }

            @Override
            public boolean isLoading() {
                return mHomePresenter.isLoading();
            }
        });

        this.mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        this.mRecyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Movie movie;
                                movie = mHomeAdapter.getMoviePosition(position);
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
        this.mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void homeAdapterLoadingFooter(boolean aShow) {
        if (aShow == true) {
            mHomeAdapter.addLoadingFooter();
        }else{
            mHomeAdapter.removeLoadingFooter();
        }
    }

    @Override
    public void loadMovies(List<Movie> mList) {
        mHomeAdapter.addAll(mList);
        mHomeAdapter.notifyDataSetChanged();
    }
}
