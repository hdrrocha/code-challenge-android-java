package com.arctouch.codechallenge.details_screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.base.BaseActivity;
import com.arctouch.codechallenge.model.Movie;

public class DetailsScreenActivity extends BaseActivity {

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

        imageMovieBackground = (ImageView) findViewById(R.id.imageMovieBackground);
        imageMovie  = (ImageView) findViewById(R.id.imageMovie);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewYear = (TextView) findViewById(R.id.textViewYear);
        textViewLikes = (TextView) findViewById(R.id.textViewLikes);
        textViewOverview = (TextView) findViewById(R.id.textViewOverview);

        Bundle data = getIntent().getExtras();

        Movie movie = (Movie) data.getSerializable("movie_selected");
        textViewTitle.setText(movie.title);
    }
}
