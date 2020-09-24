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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;



public class OnboardScreenTest {

    @Rule
    public ActivityTestRule<OnboardScreen> activityTestRule = new ActivityTestRule<>(OnboardScreen.class);
    private OnboardScreen onboardScreen = null;
    private Instrumentation.ActivityMonitor monitorToSignUp = getInstrumentation().addMonitor(SignUp.class.getName(),null,false);

    @Before
    public void setUp() {
        onboardScreen = activityTestRule.getActivity();
    }

    @Test
    public void onCreateTest() {
        View view = onboardScreen.findViewById(R.id.relativeLayout_onboard);
        assertNotNull(view);
    }

    @Test
    public void onStartButtonClickTest() {
        assertNotNull(onboardScreen.findViewById(R.id.button_onboard_signup));

        onView(withId(R.id.button_onboard_lanjut))
                .perform(closeSoftKeyboard())
                .perform(click())
                .perform(click());

        onView(withId(R.id.button_onboard_signup))
                .perform(closeSoftKeyboard())
                .perform(click());

        Activity toSignUp = getInstrumentation().waitForMonitor(monitorToSignUp);
        assertNotNull(toSignUp);

        toSignUp.finish();
    }

    @After
    public void tearDown() {
        onboardScreen = null;
    }

}