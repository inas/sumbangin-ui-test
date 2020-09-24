package com.example.android.sumbangin_android;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.android.sumbangin_android.HelperClass.TokenHelper;
import com.example.android.sumbangin_android.preferences.AppPreference;
import com.example.android.sumbangin_android.service.UserSerializer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static android.content.Intent.CATEGORY_HOME;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBackUnconditionally;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class
MainActivityTest {
    private static TokenHelper tokenHelper;
    private MainActivity mainActivity = null;
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
    };
    private AppPreference appPreference;
    private Context context;
    private Instrumentation.ActivityMonitor monitorToTransaction = getInstrumentation()
            .addMonitor(MakeTransactionActivity.class.getName(), null, false);
    private Instrumentation.ActivityMonitor monitorToDetailProfile = getInstrumentation()
            .addMonitor(DetailProfile.class.getName(), null, false);
    private Instrumentation.ActivityMonitor monitorToSignIn = getInstrumentation()
            .addMonitor(SignIn.class.getName(), null, false);

    @BeforeClass
    public static void setUpClass() {
        tokenHelper = new TokenHelper();
    }

    @Before
    public void setUp() {
        mainActivity = activityTestRule.getActivity();
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        UserSerializer user = new UserSerializer();
        String email = getApplicationContext().getString(R.string.test_email);
        user.setEmail(email);
        user.setToken(tokenHelper.getToken());

        appPreference = AppPreference.getInstance(context);
        if (appPreference.getUser() == null) {
            appPreference.saveUser(user);
        }
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
    public void testNavigationDetailProfile() {
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
        assertNotNull(mainActivity.findViewById(R.id.button_profile_detail));

        onView(withId(R.id.button_profile_detail)).check(matches(allOf(isEnabled(), isClickable()))).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled(); // no constraints, they are checked above
                    }

                    @Override
                    public String getDescription() {
                        return "click detail profile button";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );

        Activity transactionActivity = getInstrumentation().waitForMonitor(monitorToDetailProfile);
        assertNotNull(transactionActivity);
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
    public void testNavigationAdd() {
        assertNotNull(mainActivity.findViewById(R.id.item_bottomnavigation_add));

        onView(withId(R.id.item_bottomnavigation_add)).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled(); // no constraints, they are checked above
                    }

                    @Override
                    public String getDescription() {
                        return "click add icon";
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

    @Test
    public void testLoadFragmentFail() {
        Fragment fragment = null;
        assertFalse(mainActivity.loadFragment(R.id.linearlayout_main_fragmentcontainer, fragment));
    }

    @Test
    public void testOnBackPressed() {
        onView(withId(R.id.linearlayout_main_fragmentcontainer))
                .perform(pressBackUnconditionally())
                .perform(pressBackUnconditionally());
        assertNotNull(CATEGORY_HOME);
    }

    @Test
    public void testOnLogoutButtonClick() {
        assertNotNull(mainActivity.findViewById(R.id.item_bottomnavigation_profile));

        onView(withId(R.id.item_bottomnavigation_profile)).check(matches(allOf(isEnabled(), isClickable()))).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return ViewMatchers.isEnabled();
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

        assertNotNull(mainActivity.findViewById(R.id.btnLogout));
        onView(withId(R.id.btnLogout))
                .perform(click());

        Activity setHomepageSignIn = getInstrumentation().waitForMonitor(monitorToSignIn);
        assertNull(appPreference.getUser());
        assertNotNull(setHomepageSignIn);
        setHomepageSignIn.finish();
    }

    @Test
    public void testOnActivityResult() {
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

        Intents.init();
        intending(hasComponent(DetailProfile.class.getName()))
                .respondWith(new Instrumentation
                        .ActivityResult(-1, mainActivity.getIntent()));
        mainActivity.startActivityForResult(
                new Intent(mainActivity, DetailProfile.class), 0);
        Intents.release();
    }
}