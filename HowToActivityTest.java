package com.example.android.sumbangin_android;

import android.app.Instrumentation;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class HowToActivityTest {
    @Rule
    public ActivityTestRule<HowToActivity> activityTestRule = new ActivityTestRule<>(HowToActivity.class);
    private HowToActivity howTo = null;

    private Instrumentation.ActivityMonitor monitorToHomepage = getInstrumentation()
            .addMonitor(MainActivity.class.getName(), null, false);

    @Before
    public void setUp() {
        howTo = activityTestRule.getActivity();
    }

    @Test
    public void onCreateTest() {
        View view = howTo.findViewById(R.id.scrollview_howto);
        assertNotNull(view);
    }

    @After
    public void tearDown() {
        howTo = null;
    }
}
