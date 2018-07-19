package com.arctouch.codechallenge;

import com.arctouch.codechallenge.model.Movie;

import org.junit.Assert;
import org.junit.Test;

public class MovieTest {

    @Test
    public void MovieNull() {
        Movie movie = new Movie();
        movie = null;
        boolean movieNull = movie.equals(null);
        Assert.assertFalse(movieNull);
    }
}
