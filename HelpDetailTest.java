package com.example.android.sumbangin_android;

import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.appcompat.app.ActionBar;
import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.assertNotNull;

public class HelpDetailTest {

    @Rule
    public ActivityTestRule<HelpDetail> activityTestRule = new ActivityTestRule<>(HelpDetail.class);
    private HelpDetail helpDetail = null;

    @Before
    public void setUp() {
        helpDetail = activityTestRule.getActivity();
    }

    @Test
    public void onCreate() {
        View view = helpDetail.findViewById(R.id.relativelayout_helpdetail_layoutcontainer);
        ActionBar actionBar = helpDetail.getSupportActionBar();
        assertNotNull(view);
        assertNotNull(actionBar);
    }

}