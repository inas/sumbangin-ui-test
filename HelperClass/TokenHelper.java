package com.example.android.sumbangin_android.HelperClass;

import android.util.Log;

import com.example.android.sumbangin_android.service.Api;
import com.example.android.sumbangin_android.service.ServiceGenerator;
import com.example.android.sumbangin_android.service.UserSerializer;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenHelper {

    private String token;

    public TokenHelper() {
        Map<String, String> fields = new HashMap<>();
        fields.put("email", "test1@test.com");
        fields.put("password", "testtest");

        Api api = ServiceGenerator.createService(Api.class);
        Call<UserSerializer> users = api.loginRequest(fields);

        users.enqueue(new Callback<UserSerializer>() {
            @Override
            public void onResponse(Call<UserSerializer> call, Response<UserSerializer> response) {
                if (response.isSuccessful()) {
                    setToken(response.body().getToken());
                    Log.d("logTokenHelper", getToken());
                }
            }

            @Override
            public void onFailure(Call<UserSerializer> call, Throwable t) {
                Log.d("logTokenHelper", "no network");
            }
        });

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
