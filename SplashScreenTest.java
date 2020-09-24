package com.example.android.sumbangin_android;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;

import com.example.android.sumbangin_android.preferences.AppPreference;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SplashScreenTest {

    @Rule
    public ActivityTestRule<SplashScreen> activityTestRule = new ActivityTestRule<>(SplashScreen.class);
    private SplashScreen splashScreen = null;
    private Context context;

    private Instrumentation.ActivityMonitor monitorToSignIn = getInstrumentation()
            .addMonitor(SignIn.class.getName(), null, false);
    private Instrumentation.ActivityMonitor monitorToOnboardScreen = getInstrumentation()
            .addMonitor(OnboardScreen.class.getName(), null, false);
    private Instrumentation.ActivityMonitor monitorToMainActivity = getInstrumentation()
            .addMonitor(MainActivity.class.getName(), null, false);

    @Before
    public void setUp() {
        splashScreen = activityTestRule.getActivity();
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();


    }

    @After
    public void tearDown() {
        splashScreen = null;

    }


    @Test
    public void onBoardingView() {
        AppPreference.getInstance(context).removeKey("firstTime");
        AppPreference.getInstance(context).setBooleanValue("firstTime", true);
        assertTrue(AppPreference.getInstance(context).onSplashScreen());
        onView(withId(R.layout.activity_logo));

        Activity setHomepageOnboardScreen = getInstrumentation().waitForMonitor(monitorToOnboardScreen);
        assertNotNull(setHomepageOnboardScreen);
        AppPreference.getInstance(context).offSplashScreen();
        setHomepageOnboardScreen.finish();

    }

    @Test
    public void mainActivityView() {
        AppPreference.getInstance(context).offSplashScreen();
        AppPreference.getInstance(context).loggedIn();
        assertFalse(AppPreference.getInstance(context).onSplashScreen());
        assertTrue(AppPreference.getInstance(context).checkLoggedIn());
        onView(withId(R.layout.activity_logo));

        Activity setHomepageMainActivity = getInstrumentation().waitForMonitor(monitorToMainActivity);
        assertNotNull(setHomepageMainActivity);
        setHomepageMainActivity.finish();
    }

    @Test
    public void signInView() {
        AppPreference.getInstance(context).offSplashScreen();
        AppPreference.getInstance(context).loggedOff();
        assertFalse(AppPreference.getInstance(context).onSplashScreen());
        assertFalse(AppPreference.getInstance(context).checkLoggedIn());
        onView(withId(R.layout.activity_logo));

        Activity setHomepageSignIn = getInstrumentation().waitForMonitor(monitorToSignIn);
        assertNotNull(setHomepageSignIn);
        setHomepageSignIn.finish();

    }


}
