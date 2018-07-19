package com.arctouch.codechallenge.home;

import com.arctouch.codechallenge.model.Movie;
import java.util.List;

public class HomePresenter implements HomeView {

    private HomeRest mHomeRest;

    private HomeView mRootHomeView;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    private int currentPage ;

    public void init(HomeView aHomeView) {
        mRootHomeView = aHomeView;
        mHomeRest = new HomeRest(this);
    }

    public void refresh(HomeView aHomeView) {
        mRootHomeView = aHomeView;
    }

    public void searchGenres() {
        mHomeRest.searchGenres();
    }

    public void onGenresLoaded() {
        loadFistPage();
    }

    public void loadFistPage() {
        isLoading = true;
        currentPage = PAGE_START;
        mHomeRest.searchMovies(currentPage);

    }

    public void loadNextPage() {
        isLoading = true;
        currentPage += 1;

        if (currentPage <= TOTAL_PAGES) {
            mRootHomeView.homeAdapterLoadingFooter(true);
        }
        mHomeRest.searchMovies(currentPage);
    }

    public void returnApi(List<Movie> aMovieList) {
        mRootHomeView.loadMovies(aMovieList);
    }

    public void restFinish() {
        isLoading = false;
        mRootHomeView.showLoading(false);

    }

    @Override
    public void loadMovies(List<Movie> mList) {
    }

    @Override
    public void onMovieClick(Movie mMovie) {

    }

    @Override
    public void showLoading(boolean aShow) {

    }

    @Override
    public void homeAdapterLoadingFooter(boolean aShow) {
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public int getTotalPages() {
        return TOTAL_PAGES;
    }

    public boolean isLoading() {
        return isLoading;

    }


}
