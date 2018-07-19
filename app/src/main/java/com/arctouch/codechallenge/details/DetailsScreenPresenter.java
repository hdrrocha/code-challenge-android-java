package com.arctouch.codechallenge.details;

import com.arctouch.codechallenge.model.Movie;


public class DetailsScreenPresenter {

    private DetailsScreenRest mMovieRest;
    private DetailsScreenView mRootDetailsCreenView;

    public void init(DetailsScreenView aDetailsScreenView) {
        mRootDetailsCreenView = aDetailsScreenView;
        mMovieRest = new DetailsScreenRest(this);
    }


    public void searchMovie(Long movieID) {
        mMovieRest.searchMovie(movieID);
    }

    public void searchGenres() {
        mMovieRest.searchGenres();
    }


    public void loadMovie(Movie mMovie) {
        mRootDetailsCreenView.loadMovie(mMovie);
    }
}

