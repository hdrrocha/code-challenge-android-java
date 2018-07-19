package com.arctouch.codechallenge.details;

import com.arctouch.codechallenge.model.Movie;


public class DetailsScreenPresenter implements DetailsScreenView {

    private DetailsScreenRest mMovieRest;
    private DetailsScreenView mRootDetailsCreenView;

    public void init(DetailsScreenView aDetailsScreenView) {
        mRootDetailsCreenView = aDetailsScreenView;
        mMovieRest = new DetailsScreenRest(this);
    }


    public void searchMovie(Long movieID) {
        mMovieRest.callMovie(movieID);
    }

    public void searchGenres() {
        mMovieRest.callGenres();
    }

    @Override
    public void loadMovie(Movie mMovie) {
        mRootDetailsCreenView.loadMovie(mMovie);
    }
}

