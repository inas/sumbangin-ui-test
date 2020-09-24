package com.example.android.sumbangin_android;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class ConfirmationActivityTest {

    @Rule
    public ActivityTestRule<ConfirmationActivity> activityTestRule = new ActivityTestRule<>(ConfirmationActivity.class);
    private ConfirmationActivity confirmation = null;

    private Instrumentation.ActivityMonitor monitorToThankyou = getInstrumentation()
            .addMonitor(ThankYouScreenActivity.class.getName(), null, false);

    @Before
    public void setUp() {
        confirmation = activityTestRule.getActivity();
    }

    @Test
    public void onCreateTest() {
        View view = confirmation.findViewById(R.id.linearlayout_confirmation_container);
        assertNotNull(view);
    }

    @Test
    public void finishMakeTransactionButtonClickTest() {
        assertNotNull(confirmation.findViewById(R.id.textview_confirmation_itemname));
        assertNotNull(confirmation.findViewById(R.id.textview_confirmation_category));
        assertNotNull(confirmation.findViewById(R.id.textview_confirmation_description));
        assertNotNull(confirmation.findViewById(R.id.textview_confirmation_username));
        assertNotNull(confirmation.findViewById(R.id.textview_confirmation_phone));
        assertNotNull(confirmation.findViewById(R.id.textview_confirmation_address));


        assertNotNull(confirmation.findViewById(R.id.button_confirmation_confirm));

        onView(withId(R.id.button_confirmation_confirm))
                .perform(scrollTo())
                .perform(click());

        Activity toThankYou = getInstrumentation().waitForMonitor(monitorToThankyou);
        assertNotNull(toThankYou);

        toThankYou.finish();
    }

    @After
    public void tearDown() {
        confirmation = null;
    }
}