package com.example.android.sumbangin_android;

import android.provider.MediaStore;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

public class PopUpPhotoTest {

    @Rule
    public ActivityTestRule<PopUpPhoto> activityTestRule = new ActivityTestRule<>(PopUpPhoto.class);
    private PopUpPhoto popUpPhoto = null;

    @Before
    public void setUp() {
        popUpPhoto = activityTestRule.getActivity();
    }


    @After
    public void tearDown() {
        popUpPhoto = null;
    }

    @Test
    public void onCreate() {
        View view = popUpPhoto.findViewById(R.id.relativelayout_popphoto_container);
        assertNotNull(view);
    }

    @Test
    public void onClickChooseFromGallery() {
        onView(withId(R.id.button_popphoto_choosefromgallery))
                .perform(click());
        assertNotNull(MediaStore.Images.Media.INTERNAL_CONTENT_URI);

//        onView().perform(pressBackUnconditionally());
    }

    @Test
    public void onActivityResult() {
    }

    @Test
    public void onClickUploadPhoto() {
    }

    @Test
    public void getImageUri() {
    }
}