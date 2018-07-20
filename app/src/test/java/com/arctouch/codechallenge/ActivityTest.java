package com.arctouch.codechallenge;

import android.app.Application;

import com.arctouch.codechallenge.details.DetailsScreenActivity;
import com.arctouch.codechallenge.home.HomeActivity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ActivityTest {

    @InjectMocks
    private HomeActivity homeActivity;
    private DetailsScreenActivity detailsScreenActivity;

    Application app;

    @Before
    public void setUp() {
        // ==== get actvitiv ===
        homeActivity = mock(HomeActivity.class);
        detailsScreenActivity = mock(DetailsScreenActivity.class);
        app = mock(Application.class);
    }

    @Test
    public void testPresentHomeActivity() {
        when(homeActivity.getApplication()).thenReturn(app);
        assertThat(app, is(equalTo(homeActivity.getApplication())));

        verify(homeActivity).getApplication();
        verifyNoMoreInteractions(homeActivity);

    }

    @Test
    public void testDetailsScreenActivity() {
        when(detailsScreenActivity.getApplication()).thenReturn(app);
        assertThat(app, is(equalTo(detailsScreenActivity.getApplication())));

        verify(detailsScreenActivity).getApplication();
        verifyNoMoreInteractions(detailsScreenActivity);
    }

}
