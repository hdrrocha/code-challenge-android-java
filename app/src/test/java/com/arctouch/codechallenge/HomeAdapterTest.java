package com.arctouch.codechallenge;

import android.support.v7.widget.RecyclerView;

import com.arctouch.codechallenge.home.HomeActivity;
import com.arctouch.codechallenge.home.HomeAdapter;
import com.arctouch.codechallenge.model.Movie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
public class HomeAdapterTest {

    List<Movie> movies = new ArrayList<>();

    @InjectMocks
    private HomeActivity mHomeActivity;
    RecyclerView recyclerView;

    @Before
    public void setUp() {
        // ==== get actvitiv ===
        mHomeActivity = mock(HomeActivity.class);
        MockitoAnnotations.initMocks(this);
        recyclerView = Mockito.mock(RecyclerView.class);
        MockitoAnnotations.initMocks(this);

        //==== Configure some movies to add in list====

        Movie movie0 = new Movie(0, "unit test 0", "test", null, null, "test", "test", "test");
        movies.add(movie0);
        Movie movie1 = new Movie(1, "unit test 1", "test", null, null, "test", "test", "test");
        movies.add(movie1);
        Movie movie2 = new Movie(2, "unit test 2", "test", null, null, "test", "test", "test");
        movies.add(movie2);

    }

    @Test
    public void testAdapterIsEmpty() {
        HomeAdapter adapter = Mockito.mock(HomeAdapter.class);
        movies = null;
        adapter.addAll(movies);
        recyclerView.setAdapter(adapter);

        boolean result = adapter.isEmpty();
        System.out.println("result = adapter.isEmpty(): " +result);

        Assert.assertThat(result, is(false));
    }

    @Test
    public void testAdapterNotIsEmpty() {
        HomeAdapter adapter = Mockito.mock(HomeAdapter.class);
        adapter.addAll(movies);
        recyclerView.setAdapter(adapter);

        boolean result = adapter.isEmpty();

        Assert.assertThat(result, is(false));
    }


}
