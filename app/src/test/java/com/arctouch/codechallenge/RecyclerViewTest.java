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

public class RecyclerViewTest {
    List<Movie> movies = new ArrayList<>();

    @InjectMocks
    private HomeActivity mHomeActivity;
    RecyclerView recyclerView;

    @Before
    public void setUp() {
        // ==== get actvitiv ===
        mHomeActivity = mock(HomeActivity.class);
        MockitoAnnotations.initMocks(this);
        recyclerView = (RecyclerView) mHomeActivity.findViewById(R.id.recyclerView);
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
    public void testClickAdapter() {
        HomeAdapter adapter = Mockito.mock(HomeAdapter.class);
        adapter.addAll(movies);
        recyclerView.setAdapter(adapter);

        boolean result = recyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();

        Assert.assertThat(result, is(true));
    }

}
