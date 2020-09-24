package com.example.android.sumbangin_android;

import com.onesignal.OSNotification;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.Callable;

import static junit.framework.TestCase.assertEquals;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NotificationHandlerTest {

    public NotificationReceivedHandler handler;

    @Before
    public void setUp() {
        handler = new NotificationReceivedHandler();
    }

    private Callable<Boolean> statusUpdated() {
        return new Callable<Boolean>() {
            public Boolean call() {
                return !handler.getStatus().equals("ERROR");
            }
        };
    }

    @Test
    public void notificationReceivedTest() {
        NotificationReceivedHandler handler = mock(NotificationReceivedHandler.class);
        doNothing().when(handler).notificationReceived(isA(OSNotification.class));
        OSNotification notification = new OSNotification();
        handler.notificationReceived(notification);

        verify(handler, Mockito.times(1))
                .notificationReceived(notification);
    }

    @Test
    public void statusUpdatedToWaitingForPickUp() throws JSONException {
        JSONObject payload = new JSONObject();
        payload.put("body", "Sumbanganmu akan segera dijemput!");

        JSONObject id = new JSONObject();
        id.put("transactionID", "1");
        payload.put("additionalData", id);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("payload", payload);

        OSNotification notification = new OSNotification(jsonObject);
        handler.notificationReceived(notification);

        await().until(statusUpdated());

        assertEquals("WAITING_FOR_PICK_UP", handler.getStatus());
    }

    @Test
    public void statusUpdatedToOnProcess() throws JSONException {
        JSONObject payload = new JSONObject();
        payload.put("body", "Sumbanganmu sedang kami jual!");

        JSONObject id = new JSONObject();
        id.put("transactionID", "1");
        payload.put("additionalData", id);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("payload", payload);

        OSNotification notification = new OSNotification(jsonObject);
        handler.notificationReceived(notification);

        await().until(statusUpdated());

        assertEquals("ON_PROCESS", handler.getStatus());
    }

    @Test
    public void statusUpdatedToFinished() throws JSONException {
        JSONObject payload = new JSONObject();
        payload.put("body", "Sumbanganmu telah dijual! Terima kasih :)");

        JSONObject id = new JSONObject();
        id.put("transactionID", "1");
        payload.put("additionalData", id);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("payload", payload);

        OSNotification notification = new OSNotification(jsonObject);
        handler.notificationReceived(notification);

        await().until(statusUpdated());

        assertEquals("FINISHED", handler.getStatus());
    }

}