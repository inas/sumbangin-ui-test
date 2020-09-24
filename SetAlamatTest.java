package com.example.android.sumbangin_android;


import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class SetAlamatTest {

    @Rule
    public ActivityTestRule<SetAlamat> activityTestRule = new ActivityTestRule<>(SetAlamat.class);
    private SetAlamat setAlamat = null;

    private Instrumentation.ActivityMonitor monitorToHomepage = getInstrumentation()
            .addMonitor(SignIn.class.getName(), null, false);


    @Before
    public void setUp() {
        setAlamat = activityTestRule.getActivity();
    }

    @After
    public void tearDown() {
        setAlamat = null;
    }

    @Test
    public void onCreateTest() {
        View view = setAlamat.findViewById(R.id.textview_setalamat_pagetitle);
        assertNotNull(view);
    }


    @Test
    @Ignore
    public void onMakeAccountButtonClickTest() {
        onView(withId(R.id.edittext_setalamat_address))
                .perform(scrollTo())
                .perform(typeText("Fasilkom UI"));
        onView(withId(R.id.edittext_setalamat_district))
                .perform(scrollTo())
                .perform(typeText("Pondok Cina"));
        onView(withId(R.id.edittext_setalamat_city))
                .perform(scrollTo())
                .perform(typeText("Depok"));
        onView(withId(R.id.edittext_setalamat_province))
                .perform(scrollTo())
                .perform(typeText("Jawa Barat"));
        onView(withId(R.id.edittext_setalamat_postalcode))
                .perform(scrollTo())
                .perform(typeText("14045"));

        assertNotNull(setAlamat.findViewById(R.id.button_setalamat_makeaccount));
        onView(withId(R.id.button_setalamat_makeaccount))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        Activity setHomepageDone = getInstrumentation().waitForMonitor(monitorToHomepage);
        assertNotNull(setHomepageDone);

        setHomepageDone.finish();
    }

    @Test
    @Ignore
    public void onSkipAddressButtonClick() {
        assertNotNull(setAlamat.findViewById(R.id.textview_setalamat_skipbutton));
        onView(withId(R.id.textview_setalamat_skipbutton))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        Activity setHomepageSkip = getInstrumentation().waitForMonitor(monitorToHomepage);
        assertNotNull(setHomepageSkip);

        setHomepageSkip.finish();
    }

//    @Test
//    public void onSkipButtonCallbackTest(){
//        Api api = ServiceGenerator.createService(Api.class);
//
//        Map<String, String> fields = new HashMap<>();
//        fields.put("email", "tora@bika.com");
//        fields.put("password", "torabika");
//        fields.put("name","Tora Bika");
//        fields.put("phone_number","+62811111111");
//
//        SetAlamatHelper activity = new SetAlamatHelper(setAlamat);
//        activity.makeRequest(api, fields);
//
//        String message = getApplicationContext().getString(R.string.setalamat_success);
//
//        onView(Matchers.allOf(ViewMatchers.withText(message), ViewMatchers.isDisplayed()));
//
//    }
//
//    @Test
//    public void onMakeAccountCallbackTest(){
//        Api api = ServiceGenerator.createService(Api.class);
//
//        Map<String, String> fields = new HashMap<>();
//        fields.put("email", "tora@bika.com");
//        fields.put("password", "torabika");
//        fields.put("name", "Tora Bika");
//        fields.put("phone_number", "+62811111111");
//        fields.put("address", "Pocin");
//
//        SetAlamatHelper activity = new SetAlamatHelper(setAlamat);
//        activity.makeRequest(api, fields);
//
//        String message = getApplicationContext().getString(R.string.setalamat_success);
//
//        onView(Matchers.allOf(ViewMatchers.withText(message), ViewMatchers.isDisplayed()));
//
//    }
//
//    @Test
//    public void onSetAlamatErrorTest(){
//        Api api = ServiceGenerator.createService(Api.class);
//
//        Map<String, String> fields = new HashMap<>();
//        fields.put("email", "tora@bika.com");
//        fields.put("password", "torabika");
//        fields.put("name", "Tora Bika");
//        fields.put("phone_number", "+62811111111");
//        fields.put("address", "Pocin");
//
//        SetAlamatHelper activity = new SetAlamatHelper(setAlamat);
//        activity.makeRequest(api, fields);
//
//        String message = getApplicationContext().getString(R.string.setalamat_error);
//
//        onView(Matchers.allOf(ViewMatchers.withText(message), ViewMatchers.isDisplayed()));
//    }
//
//    @Test
//    public void onSetAlamatNetworkTest(){
//        Api api = ServiceGenerator.createService(Api.class,
//                "http://152.118.201.222:22311/api/");
//
//        Map<String, String> fields = new HashMap<>();
//        fields.put("email", "tora@bika.com");
//        fields.put("password", "torabika");
//        fields.put("name", "Tora Bika");
//        fields.put("phone_number", "+62811111111");
//        fields.put("address", "Pocin");
//
//        SetAlamatHelper activity = new SetAlamatHelper(setAlamat);
//        activity.makeRequest(api, fields);
//
//        String message = getApplicationContext().getString(R.string.setalamat_nonetwork);
//
//        onView(Matchers.allOf(ViewMatchers.withText(message), ViewMatchers.isDisplayed()));
//    }
}