package com.example.android.sumbangin_android;

import com.example.android.sumbangin_android.service.Api;
import com.example.android.sumbangin_android.service.ServiceGenerator;
import com.example.android.sumbangin_android.service.UserSerializer;
import com.google.gson.Gson;

import org.awaitility.Duration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.ActionBar;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import retrofit2.Call;
import retrofit2.mock.Calls;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class ChangePasswordTest {

    @Mock
    private static Api mockApi;
    final String passwordEmpty = "";
    final String passwordShort = "test";
    final String passwordLong = "teeeeeeeeeeeeeeeeeeeeeeeestttttttttt";
    final String passwordCorrect = "testtest";
    final String passwordWrong = "nononono";
    @Rule
    public ActivityTestRule<ChangePasswordActivity> activityTestRule = new ActivityTestRule<ChangePasswordActivity>(ChangePasswordActivity.class);
    private ChangePasswordActivity changePasswordActivity;

    @BeforeClass
    public static void setUpClass() {
        mockApi = mock(Api.class);
        setupLoginMock(mockApi);
        setupUpdateProfileMock(mockApi);
        ServiceGenerator.setApi(mockApi);
    }

    @AfterClass
    public static void tearDownClass() {
        ServiceGenerator.setApi(ServiceGenerator.createService(Api.class));
    }

    private static void setupLoginMock(Api mockApi) {
        String jsonString = "{"
                + "'token' : 'apa gitu',"
                + "'email' : 'anisha@gmail.com'"
                + "}";

        Gson gson = new Gson();
        UserSerializer response = gson.fromJson(jsonString, UserSerializer.class);
        Call<UserSerializer> call = Calls.response(response);
        Mockito.doReturn(call)
                .when(mockApi)
                .loginRequest(Mockito.anyMap());
    }

    private static void setupUpdateProfileMock(Api mockApi) {
        String jsonString = "{"
                + "'email' : 'anisha@gmail.com'"
                + "}";

        Gson gson = new Gson();
        UserSerializer response = gson.fromJson(jsonString, UserSerializer.class);
        Call<UserSerializer> call = Calls.response(response);
        Mockito.doReturn(call)
                .when(mockApi)
                .editProfile(Mockito.anyMap(), Mockito.anyString());
    }

    @After
    public void tearDown() {
        changePasswordActivity = null;
        activityTestRule.finishActivity();
    }

    private Callable<Boolean> callbackIsSuccessful() {
        return new Callable<Boolean>() {
            public Boolean call() {
                return changePasswordActivity.callbackIsSuccessful(); // The condition that must be fulfilled
            }
        };
    }

    @Test
    public void onCreate() {
        onView(withId(R.id.linearlayout_changepassword)).check(matches(isDisplayed()));
        ActionBar actionBar = changePasswordActivity.getSupportActionBar();
        assertNotNull(actionBar);
        String actionBarTitle = getApplicationContext().getString(R.string.changepassword_title);
        assertEquals(actionBarTitle, actionBar.getTitle().toString());
    }

    @Test
    public void InputCurrentPasswordSuccessTest() {

        await().atLeast(new Duration(2, TimeUnit.SECONDS));
        changePasswordActivity = activityTestRule.getActivity();

        onView(withId(R.id.linearlayout_changepassword)).check(matches(isDisplayed()));

        onView(withId(R.id.edittext_changepassword_currentpassword))
                .perform(typeText(passwordWrong))
                .perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.button_changepassword_continue)).perform(click());

        await().atLeast(new Duration(1, TimeUnit.SECONDS));

        onView(withId(R.id.button_changepassword_continue)).check(matches(not(isDisplayed())));
        onView(withId(R.id.edittext_changepassword_newpassword))
                .check(matches(isDisplayed()))
                .perform(typeText(passwordCorrect));
        onView(withId(R.id.edittext_changepassword_confirmpassword))
                .check(matches(isDisplayed()))
                .perform(typeText(passwordCorrect))
                .perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.button_changepassword_confirm)).perform(click());

        await().atLeast(new Duration(1, TimeUnit.SECONDS));

    }

    @Test
    public void onButtonClickButPasswordIsNullTest() {
        onView(withId(R.id.button_changepassword_continue))
                .perform(ViewActions.closeSoftKeyboard(), click());

        String message = getApplicationContext().getString(R.string.changepassword_errornull);

        onView(withId(R.id.edittext_changepassword_currentpassword))
                .check(matches(hasErrorText(message)));
    }

    @Test
    public void onButtonClickButNewPasswordIsNullTest() {
        onView(withId(R.id.edittext_changepassword_currentpassword))
                .perform(typeText(passwordCorrect), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.button_changepassword_continue)).perform(click());

        await().until(callbackIsSuccessful());

        onView(withId(R.id.button_changepassword_confirm))
                .perform(ViewActions.closeSoftKeyboard(), click());

        String message = getApplicationContext().getString(R.string.changepassword_errornull);

        onView(withId(R.id.edittext_changepassword_newpassword))
                .check(matches(hasErrorText(message)));

        onView(withId(R.id.edittext_changepassword_confirmpassword))
                .check(matches(hasErrorText(message)));

        onView(withId(R.id.edittext_changepassword_newpassword))
                .check(matches(isDisplayed()))
                .perform(typeText(passwordCorrect));

        onView(withId(R.id.button_changepassword_confirm))
                .perform(ViewActions.closeSoftKeyboard(), click());

        onView(withId(R.id.edittext_changepassword_confirmpassword))
                .check(matches(hasErrorText(message)));

        onView(withId(R.id.edittext_changepassword_newpassword))
                .check(matches(isDisplayed()))
                .perform(clearText());

        onView(withId(R.id.edittext_changepassword_confirmpassword))
                .check(matches(isDisplayed()))
                .perform(typeText(passwordCorrect));

        onView(withId(R.id.button_changepassword_confirm))
                .perform(ViewActions.closeSoftKeyboard(), click());

        onView(withId(R.id.edittext_changepassword_newpassword))
                .check(matches(hasErrorText(message)));
    }

    @Test
    public void onButtonClickButNewPasswordLengthIsTooShortTest() {
        onView(withId(R.id.edittext_changepassword_currentpassword))
                .perform(typeText(passwordCorrect), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.button_changepassword_continue)).perform(click());

        await().until(callbackIsSuccessful());

        onView(withId(R.id.edittext_changepassword_newpassword))
                .check(matches(isDisplayed()))
                .perform(typeText(passwordShort));
        onView(withId(R.id.edittext_changepassword_confirmpassword))
                .check(matches(isDisplayed()))
                .perform(typeText(passwordShort))
                .perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.button_changepassword_confirm)).perform(click());

        String message = getApplicationContext().getString(R.string.changepassword_errorpasswordlength);

        onView(withId(R.id.edittext_changepassword_newpassword))
                .check(matches(hasErrorText(message)));
    }

    @Test
    public void onButtonClickButNewPasswordLengthIsTooLongTest() {
        onView(withId(R.id.edittext_changepassword_currentpassword))
                .perform(typeText(passwordCorrect), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.button_changepassword_continue)).perform(click());

        await().until(callbackIsSuccessful());

        onView(withId(R.id.edittext_changepassword_newpassword))
                .check(matches(isDisplayed()))
                .perform(typeText(passwordLong));

        onView(withId(R.id.edittext_changepassword_confirmpassword))
                .check(matches(isDisplayed()))
                .perform(typeText(passwordLong))
                .perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.button_changepassword_confirm)).perform(click());

        String message = getApplicationContext().getString(R.string.changepassword_errorpasswordlength);

        onView(withId(R.id.edittext_changepassword_newpassword))
                .check(matches(hasErrorText(message)));
    }

    @Test
    public void onButtonClickButNewPasswordDoesNotMatchTest() {
        onView(withId(R.id.edittext_changepassword_currentpassword))
                .perform(typeText(passwordCorrect), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.button_changepassword_continue)).perform(click());

        await().until(callbackIsSuccessful());

        onView(withId(R.id.edittext_changepassword_newpassword))
                .check(matches(isDisplayed()))
                .perform(typeText(passwordCorrect));
        onView(withId(R.id.edittext_changepassword_confirmpassword))
                .check(matches(isDisplayed()))
                .perform(typeText(passwordWrong))
                .perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.button_changepassword_confirm)).perform(click());

        String message = getApplicationContext().getString(R.string.changepassword_errorpasswordshouldmatch);

        onView(withId(R.id.edittext_changepassword_confirmpassword))
                .check(matches(hasErrorText(message)));
    }

}
