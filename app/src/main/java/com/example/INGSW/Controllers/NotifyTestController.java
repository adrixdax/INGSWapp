package com.example.INGSW.Controllers;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NotifyTestController extends AsyncTask {

    private Exception exception;
    String url = "http://192.168.1.210:8080/";

    private Object getNotify(String idUser) {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "notify?idUser=" + idUser).get().build();
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
        return getNotify((String) objects[0]);
    }
}