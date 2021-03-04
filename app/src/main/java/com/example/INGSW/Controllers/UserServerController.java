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

    String url = "http://87.16.144.72:8080/";
    String UserId = "";


    private Object getRegistrationUser() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&registration=" + UserId);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url + "registration").post(body).build();

        try {
            try (Response response = client.newCall(request).execute()) {

                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Object getRegistrationUserFromGoogle() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&google=" + UserId);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url + "registration").post(body).build();

        try {
            try (Response response = client.newCall(request).execute()) {

                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Object getDefaultList() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&idUser=" + UserId +"&searchDefaultList=true");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url + "user").post(body).build();

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
                case "google":
                    return getRegistrationUserFromGoogle();
                case "getDefaultListOfUser":
                    return getDefaultList();


            }
        }
        return "Helooo";
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}

