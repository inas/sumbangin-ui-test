package com.example.android.sumbangin_android;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.android.sumbangin_android.HelperClass.TokenHelper;
import com.example.android.sumbangin_android.preferences.AppPreference;
import com.example.android.sumbangin_android.service.AddressSerializer;
import com.example.android.sumbangin_android.service.UserSerializer;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import androidx.appcompat.app.ActionBar;
import androidx.test.espresso.intent.Intents;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class DetailProfileTest {
    @Rule
    public ActivityTestRule<DetailProfile> activityTestRule = new ActivityTestRule<>(DetailProfile.class);
    private DetailProfile detailProfile = null;
    private Instrumentation.ActivityMonitor monitorToEditProfile = getInstrumentation().addMonitor(EditProfileActivity.class.getName(), null, false);
    private Instrumentation.ActivityMonitor monitorToMainActivity =
            getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);

    @BeforeClass
    public static void setUpClass() {
        TokenHelper tokenHelper = new TokenHelper();
        UserSerializer user = new UserSerializer();
        String email = getApplicationContext().getString(R.string.test_email);
        user.setEmail(email);
        user.setToken(tokenHelper.getToken());
        user.setName("test");
        user.setPhone("+6281614428162");

        AddressSerializer address = new AddressSerializer();
        user.setAddress(address);

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppPreference appPreference = AppPreference.getInstance(context);
        appPreference.saveUser(user);
    }

    @Before
    public void setUp() {
        detailProfile = activityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        detailProfile = null;
    }

    @Test
    public void onCreate() {
        View view = detailProfile.findViewById(R.id.linearlayout_detailprofilefragment);
        ActionBar actionBar = detailProfile.getSupportActionBar();
        assertNotNull(view);
        assertNotNull(actionBar);
    }

    @Ignore
    @Test
    public void testActionBackButton() {
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
        Activity mainActivity = getInstrumentation().waitForMonitor(monitorToMainActivity);
        assertNotNull(mainActivity);
    }

    @Test
    public void testButtonEditProfile() {
        assertNotNull(detailProfile.findViewById(R.id.button_detailprofile_edit));

        onView(withId(R.id.button_detailprofile_edit))
                .perform(closeSoftKeyboard())
                .perform(click());

        Activity toEditProfile = getInstrumentation().waitForMonitor(monitorToEditProfile);
        assertNotNull(toEditProfile);

    }

    @Test
    public void testOnActivityResult() {
        Intents.init();
        intending(hasComponent(EditProfileActivity.class.getName()))
                .respondWith(new Instrumentation
                        .ActivityResult(-1, detailProfile.getIntent()));
        detailProfile.startActivityForResult(
                new Intent(detailProfile, EditProfileActivity.class), 0);
        Intents.release();
    }


}
