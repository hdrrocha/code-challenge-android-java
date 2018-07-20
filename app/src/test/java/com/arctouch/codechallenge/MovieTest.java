package com.arctouch.codechallenge;

import android.util.Log;

import com.arctouch.codechallenge.model.Movie;

import org.junit.Assert;
import org.junit.Test;

public class MovieTest {

    @Test
    public void MovieNull() {
        Movie movie = new Movie();
//        movie = null;
        movie.id = 1;
        movie.title = "unit test";
        movie.backdropPath = "";
        movie.posterPath = "";
        movie.genreIds =  null;
        movie.overview = "test";
//        movie = null;
        boolean movieNull = movie == null;
        Assert.assertFalse(movieNull);
    }
}
