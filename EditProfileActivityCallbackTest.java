package com.example.android.sumbangin_android;

import android.content.Context;
import android.view.View;

import com.example.android.sumbangin_android.HelperClass.TokenHelper;
import com.example.android.sumbangin_android.preferences.AppPreference;
import com.example.android.sumbangin_android.service.AddressSerializer;
import com.example.android.sumbangin_android.service.Api;
import com.example.android.sumbangin_android.service.ServiceGenerator;
import com.example.android.sumbangin_android.service.UserSerializer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;

public class EditProfileActivityCallbackTest {

    private static AppPreference appPreference;
    private static TokenHelper tokenHelper;
    @Rule
    public ActivityTestRule<EditProfileActivity> activityTestRule =
            new ActivityTestRule<EditProfileActivity>(EditProfileActivity.class) {
            };
    private EditProfileActivity editProfile;

    @BeforeClass
    public static void setUpClass() {
        tokenHelper = new TokenHelper();
        UserSerializer user = new UserSerializer();
        String email = getApplicationContext().getString(R.string.test_email);
        user.setEmail(email);
        user.setToken(tokenHelper.getToken());
        user.setName("test");
        user.setPhone("+6281614428162");

        AddressSerializer address = new AddressSerializer();
        user.setAddress(address);

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appPreference = AppPreference.getInstance(context);
        if (appPreference.getUser() == null) {
            appPreference.saveUser(user);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        AppPreference.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext())
                .removeKey("user");
    }

    @Before
    public void setUp() {
        editProfile = activityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        editProfile = null;
        activityTestRule.finishActivity();
    }

    @Test
    public void onCreateTest() {
        View view = editProfile.findViewById(R.id.button_save);
        assertNotNull(view);
    }

    private Callable<Boolean> callbackIsSuccessful() {
        return new Callable<Boolean>() {
            public Boolean call() {
                return true;
            }
        };
    }

    @Test
    public void updateSuccessfulTest() {
        Api api = ServiceGenerator.createService(Api.class);

        Map<String, String> fields = new HashMap<>();
        fields.put("name", "test update success");
        editProfile.makeRequest(api, fields, tokenHelper.getToken());

        await().until(callbackIsSuccessful());

        String message = getApplicationContext().getString(R.string.editprofile_success);
    }

    @Test
    public void updateUnauthorizedTest() {
        Api api = ServiceGenerator.createService(Api.class);

        Map<String, String> fields = new HashMap<>();
        fields.put("name", "test unauthorized");
        editProfile.makeRequest(api, fields
                , "token ");

        String message = getApplicationContext().getString(R.string.editprofile_error);

        onView(withText(message))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void noNetworkTest() {

        Api api = ServiceGenerator.createService(Api.class,
                "http://152.118.201.222:22333/apiku/");

        Map<String, String> fields = new HashMap<>();
        editProfile.makeRequest(api, fields, "");

        String message = getApplicationContext().getString(R.string.editprofile_nonetwork);

        onView(withText(message))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }

}
