package com.example.android.sumbangin_android;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.android.sumbangin_android.HelperClass.TokenHelper;
import com.example.android.sumbangin_android.preferences.AppPreference;
import com.example.android.sumbangin_android.service.AddressSerializer;
import com.example.android.sumbangin_android.service.Api;
import com.example.android.sumbangin_android.service.ServiceGenerator;
import com.example.android.sumbangin_android.service.UserSerializer;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import androidx.appcompat.app.ActionBar;
import androidx.test.espresso.intent.Intents;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;

public class EditAddressTest {
    private static AppPreference appPreference;
    private static TokenHelper tokenHelper;


    @Rule
    public ActivityTestRule<EditAddressActivity> activityTestRule = new ActivityTestRule<>(EditAddressActivity.class);
    private EditAddressActivity editAddress = null;
    private Instrumentation.ActivityMonitor monitorToEditProfile = getInstrumentation().addMonitor(EditProfileActivity.class.getName(), null, false);


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
        editAddress = activityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        editAddress = null;
        activityTestRule.finishActivity();
    }


    @Test
    public void onCreate() {
        View view = editAddress.findViewById(R.id.linearlayout_editaddress);
        ActionBar actionBar = editAddress.getSupportActionBar();
        assertNotNull(view);
        assertNotNull(actionBar);
    }

    @Test
    public void testActionBackButton() {
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

    }

    @Test
    public void testButtonCancel() {
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());
    }


    @Test
    public void onButtonClickButAddressIsNullTest() {
        onView(withId(R.id.edittext_setalamat_address))
                .perform(scrollTo())
                .perform(typeText(""));


        onView(withId(R.id.button_setalamat_simpan))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        onView(withId(R.id.edittext_setalamat_address))
                .check(matches(hasErrorText("Harus diisi")));
    }

    @Test
    public void onButtonClickButDistrictIsNullTest() {
        onView(withId(R.id.edittext_setalamat_address))
                .perform(scrollTo())
                .perform(typeText("Rumah Saya"));

        onView(withId(R.id.edittext_setalamat_district))
                .perform(scrollTo())
                .perform(typeText(""));

        onView(withId(R.id.button_setalamat_simpan))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        onView(withId(R.id.edittext_setalamat_district))
                .check(matches(hasErrorText("Harus diisi")));
    }

    @Test
    public void onButtonClickButCityIsNullTest() {
        onView(withId(R.id.edittext_setalamat_address))
                .perform(scrollTo())
                .perform(typeText("Rumah Saya"));

        onView(withId(R.id.edittext_setalamat_district))
                .perform(scrollTo())
                .perform(typeText("Duren Sawit"));

        onView(withId(R.id.edittext_setalamat_city))
                .perform(scrollTo())
                .perform(typeText(""));

        onView(withId(R.id.button_setalamat_simpan))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        onView(withId(R.id.edittext_setalamat_city))
                .check(matches(hasErrorText("Harus diisi")));
    }

    @Test
    public void onButtonClickButProvinceIsNullTest() {
        onView(withId(R.id.edittext_setalamat_address))
                .perform(scrollTo())
                .perform(typeText("Rumah Saya"));

        onView(withId(R.id.edittext_setalamat_district))
                .perform(scrollTo())
                .perform(typeText("Duren Sawit"));

        onView(withId(R.id.edittext_setalamat_city))
                .perform(scrollTo())
                .perform(typeText("Jakarta"));

        onView(withId(R.id.edittext_setalamat_province))
                .perform(scrollTo())
                .perform(typeText(""));

        onView(withId(R.id.button_setalamat_simpan))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        onView(withId(R.id.edittext_setalamat_province))
                .check(matches(hasErrorText("Harus diisi")));
    }

    @Test
    public void onButtonClickButPostalCodeIsNullTest() {
        onView(withId(R.id.edittext_setalamat_address))
                .perform(scrollTo())
                .perform(typeText("Rumah Saya"));

        onView(withId(R.id.edittext_setalamat_district))
                .perform(scrollTo())
                .perform(typeText("Duren Sawit"));

        onView(withId(R.id.edittext_setalamat_city))
                .perform(scrollTo())
                .perform(typeText("Jakarta"));

        onView(withId(R.id.edittext_setalamat_province))
                .perform(scrollTo())
                .perform(typeText("DKI Jakarta"));

        onView(withId(R.id.edittext_setalamat_postalcode))
                .perform(scrollTo())
                .perform(typeText(""));

        onView(withId(R.id.button_setalamat_simpan))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        onView(withId(R.id.edittext_setalamat_postalcode))
                .check(matches(hasErrorText("Harus diisi")));
    }

    @Test
    public void testOnActivityResult() {
        Intents.init();
        intending(hasComponent(EditProfileActivity.class.getName()))
                .respondWith(new Instrumentation
                        .ActivityResult(-1, editAddress.getIntent()));
        editAddress.startActivityForResult(
                new Intent(editAddress, EditProfileActivity.class), 0);
        Intents.release();
    }

    private Callable<Boolean> callbackIsSuccessful() {
        return new Callable<Boolean>() {
            public Boolean call() {
                return editAddress.callbackIsSuccessful();
            }
        };
    }

    @Test
    public void updateSuccessfulTest() {
        Api api = ServiceGenerator.createService(Api.class);

        Map<String, String> fields = new HashMap<>();
        fields.put("alamat", "Rumah Saya");
        fields.put("kecamatan", "Duren Sawit");
        fields.put("kota", "Jakarta");
        fields.put("provinsi", "DKI Jakarta");
        fields.put("kode_pos", "12345");

        editAddress.makeRequest(api, fields, "Token " + tokenHelper.getToken());

        await().until(callbackIsSuccessful());

    }

    @Test
    public void updateUnauthorizedTest() {
        Api api = ServiceGenerator.createService(Api.class);

        Map<String, String> fields = new HashMap<>();
        fields.put("alamat", "test unauthorized");

        editAddress.makeRequest(api, fields, "token ");

        String message = getApplicationContext().getString(R.string.editaddress_error);

        onView(withText(message))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    @Test
    public void noNetworkTest() {

        Api api = ServiceGenerator.createService(Api.class,
                "http://152.118.201.222:22333/apiku/");

        Map<String, String> fields = new HashMap<>();
        editAddress.makeRequest(api, fields, "");

        String message = getApplicationContext().getString(R.string.editaddress_nonetwork);

        onView(withText(message))
                .inRoot(withDecorView(not(activityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

    }

}
