package com.example.android.sumbangin_android;

import com.example.android.sumbangin_android.service.Api;
import com.example.android.sumbangin_android.service.ServiceGenerator;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertTrue;

public class HomeFragmentTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity = null;
    private Api api;
    private Api wrongApi;
    private HomeFragment homeFragment;

    @Before
    public void setUp() {
        mainActivity = activityTestRule.getActivity();
        homeFragment = new HomeFragment();

        api = ServiceGenerator.createService(Api.class);
        wrongApi = ServiceGenerator.createService(Api.class,
                "http://152.118.201.222:22333/apisalah/");
    }

    @After
    public void tearDown() {
        mainActivity = null;
        activityTestRule.finishActivity();
    }

    @Test
    @Ignore
    public void testUnauthorizeUser() {
        assertTrue(mainActivity.loadFragment(R.id.linearlayout_main_fragmentcontainer, homeFragment));
        homeFragment.makeRequest(api, "token ");

        String message = getApplicationContext().getString(R.string.profile_error);
        onView(withText(message))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testButtonCaraKerja() {
        onView(withId(R.id.button_home_howto)).perform(click());
        onView(withId(R.id.scrollview_howto)).check(matches(isDisplayed()));

    }


}
