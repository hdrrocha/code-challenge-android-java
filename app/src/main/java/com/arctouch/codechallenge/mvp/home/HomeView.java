package com.arctouch.codechallenge.mvp.home;

import com.arctouch.codechallenge.model.Movie;

import java.util.List;

public interface HomeView {
    void loadMovies(List<Movie> mList);
    void onMovieClick(Movie mMovie);
    void showLoading(boolean aShow);
    void homeAdapterLoadingFooter(boolean aShow);


}
