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
    private final String url = "http://87.16.144.72:8080/";

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

    private Object setSeen(String notifyId) {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "notify?Seen=" + notifyId).get().build();
        try {
            try (Response response = client.newCall(request).execute()) {
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error changing Status";
    }

    private Object setAccepted(String notifyId) {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "notify?Accepted=" + notifyId).get().build();
        try {
            try (Response response = client.newCall(request).execute()) {
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error changing Status";
    }

    private Object setRefused(String notifyId) {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "notify?Refused=" + notifyId).get().build();
        try {
            try (Response response = client.newCall(request).execute()) {
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error changing Status";
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (objects[0].toString().startsWith("idUser="))
            return getNotify(((String)objects[0]).substring(((String)objects[0]).indexOf('=')+1));
        else if (objects[0].toString().startsWith("Seen="))
            return setSeen(((String)objects[0]).substring(((String)objects[0]).indexOf('=')+1));
        else if (objects[0].toString().startsWith("Accepted="))
            return setAccepted(((String)objects[0]).substring(((String)objects[0]).indexOf('=')+1));
        else
            return setRefused(((String)objects[0]).substring(((String)objects[0]).indexOf('=')+1));
    }


}