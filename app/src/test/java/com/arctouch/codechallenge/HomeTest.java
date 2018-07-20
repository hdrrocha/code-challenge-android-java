package com.arctouch.codechallenge;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;

import com.arctouch.codechallenge.home.HomeActivity;
import com.arctouch.codechallenge.home.HomeAdapter;
import com.arctouch.codechallenge.model.Genre;
import com.arctouch.codechallenge.model.Movie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
public class HomeTest {

    @Mock
    Context context;
    @InjectMocks
    private HomeActivity mHomeActivity;
    RecyclerView recyclerView;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recyclerView = Mockito.mock(RecyclerView.class);
        MockitoAnnotations.initMocks(this);
        mHomeActivity = mock(HomeActivity.class);
    }

    @Test
    public void mockFinalMethod() {
        Activity activity = mock(HomeActivity.class);
        Application app = mock(Application.class);
        when(activity.getApplication()).thenReturn(app);

        assertThat(app, is(equalTo(activity.getApplication())));

        verify(activity).getApplication();
        verifyNoMoreInteractions(activity);
    }

    @Test
    public void mockFinalClass() {
////        Activity activity = mock(HomeActivity.class);
//        RecyclerView.Adapter homeAdapter = new HomeAdapter(mHomeActivity.getApplicationContext());
        HomeAdapter adapter = Mockito.mock(HomeAdapter.class);
        List<Movie> movies  = new ArrayList<>();
        Movie movie1 = new Movie();
        movie1.id = 1;
        movie1.title = "unit test";
        movie1.backdropPath = "";
        movie1.posterPath = "";
        movie1.genreIds =  null;
        movie1.overview = "test";
        movies.add(movie1);
        Movie movie2 = new Movie();
        movie2.id = 2;
        movie2.title = "unit test";
        movie2.backdropPath = "";
        movie2.posterPath = "";
        movie2.genreIds =  null;
        movie2.overview = "test";

        movies.add(movie2);
        movies.clear();
        adapter.addAll(movies);
        recyclerView.setAdapter(adapter);
        // simulate first item was clicked
//        recyclerView.getChildAt(1).performClick();
        //Check if method was invoked with our object
        boolean result  = adapter.isEmpty(); //Mockito.verify(adapter).isEmpty();
        Assert.assertFalse(result);
    }



}
