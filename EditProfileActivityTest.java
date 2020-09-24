package com.example.android.sumbangin_android;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.view.View;

import com.example.android.sumbangin_android.HelperClass.TokenHelper;
import com.example.android.sumbangin_android.preferences.AppPreference;
import com.example.android.sumbangin_android.service.AddressSerializer;
import com.example.android.sumbangin_android.service.UserSerializer;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import androidx.appcompat.app.ActionBar;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;


public class EditProfileActivityTest {

    private static AppPreference appPreference;
    private static TokenHelper tokenHelper;
    @Rule
    public ActivityTestRule<EditProfileActivity> activityTestRule =
            new ActivityTestRule<EditProfileActivity>(EditProfileActivity.class) {
            };
    private EditProfileActivity editProfile = null;
    private Instrumentation.ActivityMonitor monitorToEditAddress =
            getInstrumentation().addMonitor(EditAddressActivity.class.getName(), null, false);
    private Instrumentation.ActivityMonitor monitorToDetailProfile =
            getInstrumentation().addMonitor(DetailProfile.class.getName(), null, false);

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

    @Before
    public void setUp() {
        editProfile = activityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        editProfile = null;
    }

    @Test
    public void onCreate() {
        View view = editProfile.findViewById(R.id.linearlayout_editprofilefragment);
        ActionBar actionBar = editProfile.getSupportActionBar();
        assertNotNull(view);
        assertNotNull(actionBar);
    }

    @Test
    public void testActionBackButton() {
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }

    @Test
    public void testButtonEditAddress() {
        assertNotNull(editProfile.findViewById(R.id.textview_editprofil_alamatLink));

        onView(withId(R.id.textview_editprofil_alamatLink))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        Activity editAddress = getInstrumentation().waitForMonitor(monitorToEditAddress);
        assertNotNull(editAddress);

        editAddress.finish();
    }
}
