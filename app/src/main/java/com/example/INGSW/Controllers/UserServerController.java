package com.example.INGSW.Controllers;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserServerController extends AsyncTask {

    String url = "http://192.168.1.210:8080/";
    String UserId = "";

    private Object getRegistrationUser() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&registration=" + UserId);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url + "user").post(body).build();
        System.out.println("----------------------------------------------------------------------------"+request.toString() + UserId);

        try {
            try (Response response = client.newCall(request).execute()) {

                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (objects[0] instanceof String) {
            switch (objects[0].toString()) {
                case "registration":
                    return getRegistrationUser();

            }
        }
        return new String("Helooo");
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}

