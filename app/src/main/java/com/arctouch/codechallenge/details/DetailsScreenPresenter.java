package com.arctouch.codechallenge.details;

import android.support.annotation.NonNull;

import com.arctouch.codechallenge.model.Movie;


public class DetailsScreenPresenter {

    private DetailsScreenRest mMovieRest;
    private DetailsScreenView mRootDetailsCreenView;

    public void init(DetailsScreenView aDetailsScreenView) {
        mRootDetailsCreenView = aDetailsScreenView;
        mMovieRest = new DetailsScreenRest(this);
    }

//    public DetailsScreenPresenter(@NonNull final DetailsScreenView viewContract) {
//        this.mRootDetailsCreenView = viewContract;
////        this.mMovieRest = repository;
//    }


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

