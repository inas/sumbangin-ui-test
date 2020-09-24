package com.example.android.sumbangin_android;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class ThankYouScreenActivityTest {

    @Rule
    public ActivityTestRule<ThankYouScreenActivity> activityTestRule = new ActivityTestRule<>(ThankYouScreenActivity.class);
    private ThankYouScreenActivity thankYouScreen = null;

    private Instrumentation.ActivityMonitor monitorToHomepage = getInstrumentation()
            .addMonitor(MainActivity.class.getName(), null, false);

    @Before
    public void setUp() {
        thankYouScreen = activityTestRule.getActivity();
    }

    @Test
    public void onToHomeButtonClickTest() {
        assertNotNull(thankYouScreen.findViewById(R.id.button_thankyou_tohome));
        onView(withId(R.id.button_thankyou_tohome)).perform(click());

        Activity viewHomepage = getInstrumentation().waitForMonitor(monitorToHomepage);
        assertNotNull(viewHomepage);

        viewHomepage.finish();
    }

    @Test
    public void onCreateTest() {
        View view = thankYouScreen.findViewById(R.id.relativelayout_thankyou_layoutcontainer);
        assertNotNull(view);
    }

    @After
    public void tearDown() {
        thankYouScreen = null;
    }
}
