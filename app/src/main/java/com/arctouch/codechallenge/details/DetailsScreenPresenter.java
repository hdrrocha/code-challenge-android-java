package com.arctouch.codechallenge.details;

import com.arctouch.codechallenge.model.Movie;


public class DetailsScreenPresenter implements DetailsScreenView {
    private DetailsScreenRest movieRest;

    private DetailsScreenView mRootDetailsCreenView;


    public void init(DetailsScreenView aDetailsScreenView) {
        mRootDetailsCreenView = aDetailsScreenView;
        movieRest = new DetailsScreenRest(this);
    }


    public void searchMovie(Long movieID) {
        movieRest.callMovie(movieID);
    }

    public void searchGenres() {
        movieRest.callGenres();
    }


    public void returnApi(Movie aMovie) {
        mRootDetailsCreenView.loadMovie(aMovie);
    }


    @Override
    public void loadMovie(Movie mMovie) {
        mRootDetailsCreenView.loadMovie(mMovie);

    }
}

