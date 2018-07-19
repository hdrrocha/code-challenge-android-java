package com.arctouch.codechallenge.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.util.MovieImageUrlBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailsScreenActivity extends AppCompatActivity implements DetailsScreenView {

    private final MovieImageUrlBuilder mMovieImageUrlBuilder = new MovieImageUrlBuilder();
    private  DetailsScreenPresenter mDetailsScreenPresenter;

    private ImageView imageMovieBackground;
    private ImageView imageMovie;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewLikes;
    private TextView textViewOverview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_screen);
        mapComponents();

        if(mDetailsScreenPresenter == null){
            mDetailsScreenPresenter = new DetailsScreenPresenter();
            mDetailsScreenPresenter.init(this);
        }

        Bundle data = getIntent().getExtras();
        String movieID = data.getString("serialize_data");
        mDetailsScreenPresenter.searchGenres();
        mDetailsScreenPresenter.searchMovie(Long.valueOf(movieID));

    }

    public void mapComponents() {
        imageMovieBackground = (ImageView) findViewById(R.id.imageMovieBackground);
        imageMovie = (ImageView) findViewById(R.id.imageMovie);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewYear = (TextView) findViewById(R.id.textViewYear);
        textViewLikes = (TextView) findViewById(R.id.textViewGenre);
        textViewOverview = (TextView) findViewById(R.id.textViewOverview);
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void loadMovie(Movie mMovie) {
        this.textViewTitle.setText(mMovie.title);
        this.textViewYear.setText( mMovie.releaseDate.substring(0, 4));
        this.textViewLikes.setText(TextUtils.join(", ", mMovie.genres));
        this.textViewOverview.setText(mMovie.overview);
        String posterPath = mMovie.posterPath;
        if (TextUtils.isEmpty(posterPath) == false) {
            Glide.with(getBaseContext())
                    .load(mMovieImageUrlBuilder.buildPosterUrl(posterPath))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(imageMovie);
        }
        String backdropPath = mMovie.backdropPath;
        if (TextUtils.isEmpty(backdropPath) == false) {
            Glide.with(getBaseContext())
                    .load(mMovieImageUrlBuilder.buildPosterUrl(posterPath))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(imageMovieBackground);
        }
    }
}
