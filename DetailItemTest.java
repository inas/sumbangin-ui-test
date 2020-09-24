package com.example.android.sumbangin_android;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import androidx.appcompat.app.ActionBar;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class DetailItemTest {

    @Rule
    public ActivityTestRule<DetailItemActivity> activityTestRule = new ActivityTestRule<>(DetailItemActivity.class);
    private DetailItemActivity detailItem = null;

    private Instrumentation.ActivityMonitor monitorToMainActivity =
            getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);


    @Before
    public void setUp() {
        detailItem = activityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        detailItem = null;
    }

    @Test
    public void onCreate() {
        View view = detailItem.findViewById(R.id.scrollview_detailitem);
        ActionBar actionBar = detailItem.getSupportActionBar();
        assertNotNull(view);
        assertNotNull(actionBar);
    }

    @Ignore
    @Test
    public void testActionBackButton() {
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        Activity mainActivity = getInstrumentation().waitForMonitor(monitorToMainActivity);
        assertNotNull(mainActivity);
    }
}
