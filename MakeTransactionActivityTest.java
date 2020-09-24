package com.example.android.sumbangin_android;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.sumbangin_android.preferences.AppPreference;
import com.example.android.sumbangin_android.service.AddressSerializer;
import com.example.android.sumbangin_android.service.UserSerializer;

import org.hamcrest.Description;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import androidx.appcompat.app.ActionBar;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
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
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class MakeTransactionActivityTest {

    @Mock
    private static AppPreference mockAppPreference;
    private static AppPreference oldAppPreference;
    @Rule
    public ActivityTestRule<MakeTransactionActivity> activityTestRule =
            new ActivityTestRule<>(MakeTransactionActivity.class);
    private MakeTransactionActivity makeTransaction = null;
    private Instrumentation.ActivityResult mActivityResult;

    @BeforeClass
    public static void setUpClass() {
        oldAppPreference = AppPreference.getInstance(getApplicationContext());
        mockAppPreference = mock(AppPreference.class);

        AddressSerializer address = new AddressSerializer();
        UserSerializer user = new UserSerializer("test", "test@test.com", "testtest",
                "019181171711", address);

        Mockito.doReturn(user).when(mockAppPreference).getUser();
        AppPreference.setAppPreference(mockAppPreference);
    }

    @AfterClass
    public static void tearDownClass() {
        AppPreference.setAppPreference(oldAppPreference);
    }

    public static BoundedMatcher<View, ImageView> hasDrawable() {
        return new BoundedMatcher<View, ImageView>(ImageView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has drawable");
            }

            @Override
            public boolean matchesSafely(ImageView imageView) {
                return imageView.getDrawable() != null;
            }
        };
    }

    @Before
    public void setUp() {

        makeTransaction = activityTestRule.getActivity();

        Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources
                .getResourcePackageName(
                        R.mipmap.ic_launcher) + '/' + resources.getResourceTypeName(
                R.mipmap.ic_launcher) + '/' + resources.getResourceEntryName(
                R.mipmap.ic_launcher));
        Intent resultData = new Intent();
        resultData.putExtra("item_image_uri", imageUri.toString());
        mActivityResult = new Instrumentation.ActivityResult(
                Activity.RESULT_OK, resultData);

        Intents.init();
    }

    @After
    public void tearDown() {
        makeTransaction = null;
        Intents.release();
    }

    @Test
    public void onCreate() {
        onView(withId(R.id.linearlayout_maketransaction_container)).check(matches(isDisplayed()));
        ActionBar actionBar = makeTransaction.getSupportActionBar();
        assertNotNull(actionBar);
        String actionBarTitle = getApplicationContext().getString(R.string.maketransaction_title);
        assertEquals(actionBarTitle, actionBar.getTitle().toString());

        TextView address = makeTransaction.findViewById(R.id.textview_maketransaction_address);

        String message = getApplicationContext().getString(R.string.maketransaction_noaddress);
        assertEquals(message, address.getText().toString());
    }

    @Test
    public void onMakeTransactionButtonClickTest() {

        intending(hasComponent(PopUpPhoto.class.getName())).respondWith(mActivityResult);
        onView(withId(R.id.button_maketransaction_choosephoto)).perform(click());

        assertNotNull(makeTransaction.findViewById(R.id.button_maketransaction_maketransaction));
        onView(withId(R.id.button_maketransaction_maketransaction))
                .perform(closeSoftKeyboard())
                .perform(scrollTo())
                .perform(click());

        String message = getApplicationContext().getString(R.string.maketransaction_errornull);

        onView(withId(R.id.edittext_maketransaction_itemname))
                .check(matches(hasErrorText(message)));

    }

    @Test
    public void emptyAddressRedirectedToEditAddress() {

        intending(hasComponent(PopUpPhoto.class.getName())).respondWith(mActivityResult);
        onView(withId(R.id.button_maketransaction_choosephoto)).perform(click());

        onView(withId(R.id.edittext_maketransaction_itemname))
                .perform(typeText("kursi"), closeSoftKeyboard());
        onView(withId(R.id.edittext_maketransaction_itemdetail))
                .perform(typeText("kursi kayu jati"), closeSoftKeyboard());

        onView(withId(R.id.button_maketransaction_maketransaction)).perform(click());

        onView(withId(R.id.linearlayout_editaddress)).check(matches(isDisplayed()));
    }

    @Test
    public void addPhotoTest() {

        intending(hasComponent(PopUpPhoto.class.getName())).respondWith(mActivityResult);
        onView(withId(R.id.button_maketransaction_choosephoto)).perform(click());

        onView(withId(R.id.imageview_maketransaction_photo)).check(matches(hasDrawable()));
    }

}