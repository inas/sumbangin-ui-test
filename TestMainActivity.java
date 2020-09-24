package com.example.android.sumbangin_android;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.appcompat.app.ActionBar;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;

public class
TestMainActivity {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity = null;

    private Instrumentation.ActivityMonitor monitorToTransaction = getInstrumentation()
            .addMonitor(MakeTransactionActivity.class.getName(), null, false);

    @Before
    public void setUp() {
        mainActivity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        mainActivity = null;
    }

    @Test
    public void onCreate() {
        View view = mainActivity.findViewById(R.id.linearlayout_main_fragmentcontainer);
        BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottomnavigation_main);
        ActionBar actionBar = mainActivity.getSupportActionBar();
        assertNotNull(view);
        assertNotNull(bottomNavigationView);
        assertNotNull(actionBar);
    }

    @Test
    public void testNavigationProfile() {
        assertNotNull(mainActivity.findViewById(R.id.item_bottomnavigation_profile));

        onView(withId(R.id.item_bottomnavigation_profile)).check(matches(allOf(isEnabled(), isClickable()))).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled(); // no constraints, they are checked above
                    }

                    @Override
                    public String getDescription() {
                        return "click profile icon";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );

        onView(withId(R.id.linearlayout_profilefragment)).check(matches(isDisplayed()));
    }

    @Test
    public void testNavigationHome() {
        assertNotNull(mainActivity.findViewById(R.id.item_bottomnavigation_home));

        onView(withId(R.id.item_bottomnavigation_home)).check(matches(allOf(isEnabled(), isClickable()))).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled(); // no constraints, they are checked above
                    }

                    @Override
                    public String getDescription() {
                        return "click home icon";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );

        onView(withId(R.id.linearlayout_homefragment)).check(matches(isDisplayed()));
    }


    @Test
    public void testButtonTransaction() {
        assertNotNull(mainActivity.findViewById(R.id.fab_bottomnavigation));
        onView(withId(R.id.fab_bottomnavigation))
                .perform(click());

        Activity transactionActivity = getInstrumentation().waitForMonitor(monitorToTransaction);
        assertNotNull(transactionActivity);
    }
}
