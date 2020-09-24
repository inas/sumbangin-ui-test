package com.example.android.sumbangin_android;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.view.View;

import com.example.android.sumbangin_android.HelperClass.TokenHelper;
import com.example.android.sumbangin_android.preferences.AppPreference;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SignInTest {

    AppPreference appPreference;
    @Rule
    public ActivityTestRule<SignIn> activityTestRule = new ActivityTestRule<>(SignIn.class);
    private SignIn signIn = null;

    private Instrumentation.ActivityMonitor monitorToHomepage = getInstrumentation()
            .addMonitor(MainActivity.class.getName(), null, false);
    private Instrumentation.ActivityMonitor monitorToSignUp = getInstrumentation()
            .addMonitor(SignUp.class.getName(), null, false);
    private static TokenHelper tokenHelper;

    @BeforeClass
    public static void setUpClass() {
        tokenHelper = new TokenHelper();
    }

    @Before
    public void setUp() {
        signIn = activityTestRule.getActivity();
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appPreference = AppPreference.getInstance(context);
    }


    @After
    public void tearDown() {
        signIn = null;
    }

    @Test
    public void onCreateTest() {
        View view = signIn.findViewById(R.id.tetxtview_signin_pagetitle);
        assertNotNull(view);
    }

    @Test
    public void onSignInButtonClick() {
        appPreference.removeKey("user");
        assertNull(appPreference.getUser());

        onView(withId(R.id.edittext_signin_email))
                .perform(typeText("test1@test.com"))
                .perform(closeSoftKeyboard());
        onView(withId(R.id.edittext_signin_password))
                .perform(typeText("testtest"));

        assertNotNull(signIn.findViewById(R.id.button_signin_signin));
        onView(withId(R.id.button_signin_signin))
                .perform(closeSoftKeyboard())
                .perform(click());

        Activity setHomepageSignIn = getInstrumentation().waitForMonitor(monitorToHomepage);
        assertNotNull(setHomepageSignIn);

        setHomepageSignIn.finish();

        assertEquals(tokenHelper.getToken(), appPreference.getUser().getToken());
    }

    @Test
    public void onButtonClickButEmailIsNullClick() {

        onView(withId(R.id.edittext_signin_email))
                .perform(typeText(""))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.button_signin_signin))
                .perform(closeSoftKeyboard())
                .perform(click());

        onView(withId(R.id.edittext_signin_email))
                .check(matches(hasErrorText("Harus diisi")));
    }

    @Test
    public void onButtonClickButPasswordIsNullClick() {

        onView(withId(R.id.edittext_signin_email))
                .perform(typeText("test@test.com"))
                .perform(closeSoftKeyboard());

        onView(withId(R.id.edittext_signin_password))
                .perform(typeText(""));

        onView(withId(R.id.button_signin_signin))
                .perform(closeSoftKeyboard())
                .perform(click());

        onView(withId(R.id.edittext_signin_password))
                .check(matches(hasErrorText("Harus diisi")));
    }

    @Test
    public void toSignUpButtonClick() {
        assertNotNull(signIn.findViewById(R.id.textview_sigin_signuptextbutton));
        onView(withId(R.id.textview_sigin_signuptextbutton))
                .perform(closeSoftKeyboard())
                .perform(click());

        Activity setSignUp = getInstrumentation().waitForMonitor(monitorToSignUp);
        assertNotNull(setSignUp);

        setSignUp.finish();
    }

}

