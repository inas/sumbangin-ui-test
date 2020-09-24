package com.example.android.sumbangin_android.HelperClass;

import com.example.android.sumbangin_android.EditProfileActivity;
import com.example.android.sumbangin_android.service.Api;

import java.util.Map;

public class EditProfileHelper {

    private EditProfileActivity editProfile;

    public EditProfileHelper(EditProfileActivity activity) {
        editProfile = activity;
    }

    public void makeRequest(Api userService, Map<String, String> fields, String token) {
        editProfile.makeRequest(userService, fields, token);
    }
}
