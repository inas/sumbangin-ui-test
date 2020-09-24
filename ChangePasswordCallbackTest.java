package com.example.android.sumbangin_android;

import com.example.android.sumbangin_android.HelperClass.TokenHelper;
import com.example.android.sumbangin_android.service.Api;
import com.example.android.sumbangin_android.service.ServiceGenerator;

import junit.framework.AssertionFailedError;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.not;

public class ChangePasswordCallbackTest {

    private static TokenHelper tokenHelper;

    @Rule
    public ActivityTestRule<ChangePasswordActivity> activityTestRule =
            new ActivityTestRule<ChangePasswordActivity>(ChangePasswordActivity.class);
    private ChangePasswordActivity changePasswordActivity;

    @BeforeClass
    public static void setUpClass() {
        tokenHelper = new TokenHelper();
    }

    @Before
    public void setUp() {
        changePasswordActivity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        changePasswordActivity = null;
        activityTestRule.finishActivity();
    }

    private Callable<Boolean> callbackIsSuccessful() {
        return new Callable<Boolean>() {
            public Boolean call() {
                return changePasswordActivity.callbackIsSuccessful();
            }
        };
    }

    private Callable<Boolean> errorIsShown(final int viewId, final String message) {
        return new Callable<Boolean>() {
            public Boolean call() {
                try {
                    onView(withId(viewId)).check(matches(hasErrorText(message)));
                    return true;
                } catch (AssertionFailedError e) {
                    return false;
                }
            }
        };
    }

    @Ignore
    @Test
    public void currentPasswordIsCorrectTest() {
        Api api = ServiceGenerator.createService(Api.class);

        Map<String, String> fields = new HashMap<>();
        fields.put("password", changePasswordActivity.getString(R.string.test_password));
        fields.put("email", changePasswordActivity.getString(R.string.test_email));
        changePasswordActivity.verifyCurrentPassword(fields);

        await().until(callbackIsSuccessful());

        onView(withId(R.id.linearlayout_changepassword_new)).check(matches(isDisplayed()));
    }

    @Ignore
    @Test
    public void currentPasswordIsWrongTest() {
        Api api = ServiceGenerator.createService(Api.class);

        Map<String, String> fields = new HashMap<>();
        fields.put("password", changePasswordActivity.getString(R.string.test_wrongpassword));
        fields.put("email", changePasswordActivity.getString(R.string.test_email));
        changePasswordActivity.verifyCurrentPassword(fields);

        String message = getApplicationContext().getString(
                R.string.changepassword_errorwrongpassword);

        await().until(errorIsShown(R.id.edittext_changepassword_currentpassword, message));
    }

    @Ignore
    @Test
    public void currentPasswordNoNetworkTest() {
        Api api = ServiceGenerator.createService(Api.class
                , "http://152.118.201.222:22311/api/");

        Map<String, String> fields = new HashMap<>();
        fields.put("password", changePasswordActivity.getString(R.string.test_password));
        changePasswordActivity.verifyCurrentPassword(fields);

        String message = getApplicationContext().getString(R.string.changepassword_errornonetwork);

        onView(withText(message))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Ignore
    @Test
    public void passwordChangeNoNetworkTest() {
        Api api = ServiceGenerator.createService(Api.class
                , "http://152.118.201.222:22311/api/");

        Map<String, String> fields = new HashMap<>();
        fields.put("password", changePasswordActivity.getString(R.string.test_password));
        changePasswordActivity.changePassword(api, fields, tokenHelper.getToken());

        String message = getApplicationContext().getString(R.string.changepassword_errornonetwork);

        onView(withText(message))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
}
