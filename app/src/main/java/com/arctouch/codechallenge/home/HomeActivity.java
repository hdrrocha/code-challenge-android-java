package com.arctouch.codechallenge.home;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.arctouch.codechallenge.util.RecyclerItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Movie> listMovie = new ArrayList<>();

    TmdbApi api = new Retrofit.Builder()
            .baseUrl(TmdbApi.URL)
            .client(new OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TmdbApi.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        DemoAsyncTask demoAsyncTask = new DemoAsyncTask();
        demoAsyncTask.execute(1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);


        this.recyclerView = findViewById(R.id.recyclerView);
        this.progressBar = findViewById(R.id.progressBar);

        api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1L, TmdbApi.DEFAULT_REGION)
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

                    recyclerView.setAdapter(new HomeAdapter(response.results));
                    progressBar.setVisibility(View.GONE);
                });

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

     class DemoAsyncTask extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        Cache.setGenres(response.genres);
                    });
            return null;
        }
    }
}
