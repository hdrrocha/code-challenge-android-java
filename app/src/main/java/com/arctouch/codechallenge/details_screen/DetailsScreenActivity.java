package com.arctouch.codechallenge.details_screen;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.api.TmdbApi;
import com.arctouch.codechallenge.base.BaseActivity;
import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.model.Genre;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.util.MovieImageUrlBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class DetailsScreenActivity extends BaseActivity {

    private ImageView imageMovieBackground;
    private ImageView imageMovie;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewLikes;
    private TextView textViewOverview;
    private final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();
    TmdbApi api = new Retrofit.Builder()
            .baseUrl(TmdbApi.URL)
            .client(new OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TmdbApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_screen);

        imageMovieBackground = (ImageView) findViewById(R.id.imageMovieBackground);
        imageMovie  = (ImageView) findViewById(R.id.imageMovie);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewYear = (TextView) findViewById(R.id.textViewYear);
        textViewLikes = (TextView) findViewById(R.id.textViewLikes);
        textViewOverview = (TextView) findViewById(R.id.textViewOverview);

        Bundle data = getIntent().getExtras();
        String movieID =  data.getString("serialize_data");

        api.movie(Long.valueOf(movieID),TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Cache.setGenres(response.genres);

                    this.textViewTitle.setText(response.title);
                    this.textViewYear.setText(response.releaseDate);
                    this.textViewLikes.setText(TextUtils.join(", ", response.genres));
                    this.textViewOverview.setText(response.overview);
                    String posterPath = response.posterPath;
                    if (TextUtils.isEmpty(posterPath) == false) {
                        Glide.with(getBaseContext())
                                .load(movieImageUrlBuilder.buildPosterUrl(posterPath))
                                .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                                .into(imageMovie);
                    }
                    String backdropPath = response.backdropPath;
                    if (TextUtils.isEmpty(backdropPath) == false) {
                        Glide.with(getBaseContext())
                                .load(movieImageUrlBuilder.buildPosterUrl(posterPath))
                                .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                                .into(imageMovieBackground);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
