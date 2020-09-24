package com.example.android.sumbangin_android;

import android.app.Activity;
import android.app.Instrumentation;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class SignUpTest{

    @Rule
    public ActivityTestRule<SignUp> activityTestRule = new ActivityTestRule<>(SignUp.class);
    private SignUp signUp = null;

    private Instrumentation.ActivityMonitor monitorToSetAlamat = getInstrumentation()
            .addMonitor(SetAlamat.class.getName(), null, false);
    private Instrumentation.ActivityMonitor monitorToSignIn = getInstrumentation()
            .addMonitor(SignIn.class.getName(), null, false);


    @Before
    public void setUp() {
        signUp = activityTestRule.getActivity();
    }

    @Test
    public void onSignUpButtonClickTest() {
        onView(withId(R.id.edittext_signup_name))
                .perform(scrollTo())
                .perform(typeText("budi"));

        onView(withId(R.id.edittext_signup_email))
                .perform(scrollTo())
                .perform(typeText("budi@gmail.com"));

        onView(withId(R.id.edittext_signup_phonenumber))
                .perform(scrollTo())
                .perform(typeText("812345678"));

        onView(withId(R.id.edittext_signup_password))
                .perform(scrollTo())
                .perform(typeText("pplcewesemua"));

        onView(withId(R.id.edittext_signup_passwordconfirmation))
                .perform(scrollTo())
                .perform(typeText("pplcewesemua"));

        assertNotNull(signUp.findViewById(R.id.button_signup_nextstep));
        onView(withId(R.id.button_signup_nextstep))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        Activity setAlamatSignUp = getInstrumentation().waitForMonitor(monitorToSetAlamat);
        assertNotNull(setAlamatSignUp);

        setAlamatSignUp.finish();
    }

    @Test
    public void onButtonClickButNameIsNullTest() {
        onView(withId(R.id.edittext_signup_name))
                .perform(scrollTo())
                .perform(typeText(""));


        onView(withId(R.id.button_signup_nextstep))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        onView(withId(R.id.edittext_signup_name))
                .check(matches(hasErrorText("Harus diisi")));
    }

    @Test
    public void onButtonClickButEmailIsNullTest() {
        onView(withId(R.id.edittext_signup_name))
                .perform(scrollTo())
                .perform(typeText("Budi"));

        onView(withId(R.id.edittext_signup_email))
                .perform(scrollTo())
                .perform(typeText(""));

        onView(withId(R.id.button_signup_nextstep))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        onView(withId(R.id.edittext_signup_email))
                .check(matches(hasErrorText("Harus diisi")));
    }

    @Test
    public void onButtonClickButPhoneNotSuitableTest() {
        onView(withId(R.id.edittext_signup_name))
                .perform(scrollTo())
                .perform(typeText("Budi"));

        onView(withId(R.id.edittext_signup_email))
                .perform(scrollTo())
                .perform(typeText("budi@gmail.com"));

        onView(withId(R.id.edittext_signup_phonenumber))
                .perform(scrollTo())
                .perform(typeText(""));

        onView(withId(R.id.button_signup_nextstep))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        onView(withId(R.id.edittext_signup_phonenumber))
                .check(matches(hasErrorText("Jumlah karakter harus 9 - 15")));
    }

    @Test
    public void onButtonClickButPasswordIsNullTest() {
        onView(withId(R.id.edittext_signup_name))
                .perform(scrollTo())
                .perform(typeText("budi"));

        onView(withId(R.id.edittext_signup_email))
                .perform(scrollTo())
                .perform(typeText("budi@gmail.com"));

        onView(withId(R.id.edittext_signup_phonenumber))
                .perform(scrollTo())
                .perform(typeText("812345678"));

        onView(withId(R.id.edittext_signup_password))
                .perform(scrollTo())
                .perform(typeText(""));

        onView(withId(R.id.button_signup_nextstep))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        onView(withId(R.id.edittext_signup_password))
                .check(matches(hasErrorText("Harus diisi")));
    }

    @Test
    public void onButtonClickButPasswordLengthIsTooShortTest() {
        onView(withId(R.id.edittext_signup_name))
                .perform(scrollTo())
                .perform(typeText("budi"));

        onView(withId(R.id.edittext_signup_email))
                .perform(scrollTo())
                .perform(typeText("budi@gmail.com"));

        onView(withId(R.id.edittext_signup_phonenumber))
                .perform(scrollTo())
                .perform(typeText("812345678"));

        onView(withId(R.id.edittext_signup_password))
                .perform(scrollTo())
                .perform(typeText("ppl"));

        onView(withId(R.id.button_signup_nextstep))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        onView(withId(R.id.edittext_signup_password))
                .check(matches(hasErrorText("Jumlah karakter harus 6 - 15")));
    }

    @Test
    public void onButtonClickButPasswordLengthIsTooLongTest() {
        onView(withId(R.id.edittext_signup_name))
                .perform(scrollTo())
                .perform(typeText("budi"));

        onView(withId(R.id.edittext_signup_email))
                .perform(scrollTo())
                .perform(typeText("budi@gmail.com"));

        onView(withId(R.id.edittext_signup_phonenumber))
                .perform(scrollTo())
                .perform(typeText("812345678"));

        onView(withId(R.id.edittext_signup_password))
                .perform(scrollTo())
                .perform(typeText("pplcewesemuayangpalingkeren"));

        onView(withId(R.id.button_signup_nextstep))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        onView(withId(R.id.edittext_signup_password))
                .check(matches(hasErrorText("Jumlah karakter harus 6 - 15")));
    }

    @Test
    public void onButtonClickButPasswordConfirmationIsNullTest() {
        onView(withId(R.id.edittext_signup_name))
                .perform(scrollTo())
                .perform(typeText("budi"));

        onView(withId(R.id.edittext_signup_email))
                .perform(scrollTo())
                .perform(typeText("budi@gmail.com"));

        onView(withId(R.id.edittext_signup_phonenumber))
                .perform(scrollTo())
                .perform(typeText("812345678"));

        onView(withId(R.id.edittext_signup_password))
                .perform(scrollTo())
                .perform(typeText("pplcewesemua"));

        onView(withId(R.id.edittext_signup_passwordconfirmation))
                .perform(scrollTo())
                .perform(typeText(""));

        onView(withId(R.id.button_signup_nextstep))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        onView(withId(R.id.edittext_signup_passwordconfirmation))
                .check(matches(hasErrorText("Harus diisi")));
    }

    @Test
    public void onButtonClickButPasswordConfirmationNotSuitableTest() {
        onView(withId(R.id.edittext_signup_name))
                .perform(scrollTo())
                .perform(typeText("budi"));

        onView(withId(R.id.edittext_signup_email))
                .perform(scrollTo())
                .perform(typeText("budi@gmail.com"));

        onView(withId(R.id.edittext_signup_phonenumber))
                .perform(scrollTo())
                .perform(typeText("812345678"));

        onView(withId(R.id.edittext_signup_password))
                .perform(scrollTo())
                .perform(typeText("pplcewesemua"));

        onView(withId(R.id.edittext_signup_passwordconfirmation))
                .perform(scrollTo())
                .perform(typeText("pplaja"));

        onView(withId(R.id.button_signup_nextstep))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        onView(withId(R.id.edittext_signup_passwordconfirmation))
                .check(matches(hasErrorText("Password tidak sama")));
    }

    @Test
    public void toSignInButtonClickTest() {
        assertNotNull(signUp.findViewById(R.id.textview_signup_signintextbutton));
        onView(withId(R.id.textview_signup_signintextbutton))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        Activity setSignIn = getInstrumentation().waitForMonitor(monitorToSignIn);
        assertNotNull(setSignIn);

        setSignIn.finish();
    }

    @After
    public void tearDown() {
        signUp = null;
    }

}
