package com.example.android.sumbangin_android;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UploadTest {

    private String name;
    private String url;
    private Upload upload;

    @Before
    public void setUp() {
        upload = new Upload("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
    }

    @Test
    public void getNameTest() {
        name = upload.getName();
        assertEquals("sumbangin", name);
    }

    @Test
    public void setName() {
        upload.setName("sumbangin-profile");
        String newName = upload.getName();
        assertEquals("sumbangin-profile", newName);
    }

    @Test
    public void getImageUrl() {
        url = upload.getImageUrl();
        assertEquals("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
                url);
    }

    @Test
    public void setImageUrl() {
        upload.setImageUrl("http://winkeyecare.com/wp-content/uploads/2013/03/Empty-Profile-Picture-450x450.jpg");
        String newUrl = upload.getImageUrl();
        assertEquals("http://winkeyecare.com/wp-content/uploads/2013/03/Empty-Profile-Picture-450x450.jpg",
                newUrl);
    }
}