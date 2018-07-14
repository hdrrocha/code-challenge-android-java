package com.arctouch.codechallenge.home;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.base.BaseActivity;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.details_screen.DetailsScreenActivity;
import com.arctouch.codechallenge.model.Genre;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.util.PaginationScrollListener;
import com.arctouch.codechallenge.util.RecyclerItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class HomeActivity extends AppCompatActivity {


    private static final String TAG = "HomeActivity";

    private RecyclerView recyclerView;
    private  HomeAdapter homeAdapter;
    private ProgressBar progressBar;
    private List<Movie> listMovie = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;




    TmdbApi api = new Retrofit.Builder()
            .baseUrl(TmdbApi.URL)
            .client(new OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TmdbApi.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);


        this.recyclerView = findViewById(R.id.recyclerView);
        this.progressBar = findViewById(R.id.progressBar);

        homeAdapter = new HomeAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        this.recyclerView.setAdapter(homeAdapter);

        this.recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        loadFirstPage();


    }

    private void loadFirstPage() {
        Log.d(TAG, "loadFirstPage: ");

        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, (long) currentPage, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    listMovie.addAll(response.results);
                    for (Movie movie : response.results) {
                        movie.genres = new ArrayList<>();
                        for (Genre genre : Cache.getGenres()) {
                            if (movie.genreIds.contains(genre.id)) {
                                movie.genres.add(genre);
                            }
                        }
                    }

//                    recyclerView.setAdapter(homeAdapter);
                    progressBar.setVisibility(View.GONE);
                    homeAdapter.addAll(response.results);

                    if (currentPage <= TOTAL_PAGES) homeAdapter.addLoadingFooter();
                    else isLastPage = true;
                });

    }

    private void loadNextPage() {
        Log.d(TAG, "loadNextPage: " + currentPage);

        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, (long) currentPage, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    listMovie.addAll(response.results);
                    for (Movie movie : response.results) {
                        movie.genres = new ArrayList<>();
                        for (Genre genre : Cache.getGenres()) {
                            if (movie.genreIds.contains(genre.id)) {
                                movie.genres.add(genre);
                            }
                        }
                    }
                    homeAdapter.removeLoadingFooter();
                    isLoading = false;
                    homeAdapter.addAll(response.results);
                    progressBar.setVisibility(View.GONE);

                    if (currentPage != TOTAL_PAGES) homeAdapter.addLoadingFooter();
                    else isLastPage = true;
                });
    }

    private  void setAdapter(){
        this.recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        this.recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Movie movie = new Movie();
                                movie =  listMovie.get(position);
                                Toast.makeText(getApplicationContext(), "item precionado" + movie.title.toString(), Toast.LENGTH_LONG).show();
                                Context context = view.getContext();
                                Intent intent = new Intent(getApplicationContext(), DetailsScreenActivity.class);
                                intent.putExtra("serialize_data",  String.valueOf(movie.id));
                                startActivity(intent);

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


}
