package com.example.android.sumbangin_android;

import org.junit.Assert;
import org.junit.Test;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class ServiceGeneratorTest {
    @Test
    public void serverTesting() throws Exception{
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("success"));
        server.start();

        HttpUrl baseUrl = server.url("/");
        String bodyOfRequest = sendGetRequest(new OkHttpClient(), baseUrl);
        Assert.assertEquals("success", bodyOfRequest);

        RecordedRequest request = server.takeRequest();
        String requestBody = request.getBody().toString();
        String requestToken = request.getHeader("token");

        Assert.assertEquals("[text=passed]", requestBody);
        Assert.assertEquals("1234", requestToken);
    }

    private String sendGetRequest(OkHttpClient okHttpClient, HttpUrl urlBase) throws Exception{
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), "passed");

        okhttp3.Request request = new okhttp3.Request.Builder()
                .post(body)
                .header("token", "1234")
                .url(urlBase)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }
}
