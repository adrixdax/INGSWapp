package com.example.INGSW.Controllers;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FilmTestController extends AsyncTask {

    private Exception exception;
    String url = "http://87.16.144.72:8080/";

    private Object getLatestFilms() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&latest=true");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url+"film").post(body).build();
        try {
            try (Response response = client.newCall(request).execute()) {
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Object getMostViewedFilms() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&most=true");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"film")
                .post(body)
                .build();
        try {
            try (Response response = client.newCall(request).execute()) {
                return Objects.requireNonNull(response.body()).string();
               }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Object getTooSeeFilmList() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&most=true");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"film")
                .post(body)
                .build();
        try {
            try (Response response = client.newCall(request).execute()) {
                return Objects.requireNonNull(response.body()).string();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Object getMostReviewedFilms() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&most=true");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"film")
                .post(body)
                .build();
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
                case "latest":
                    return getLatestFilms();
                case "most":
                    return getMostViewedFilms();
                case "toSee":
                    return getTooSeeFilmList();
                case "mostReviewd":
                    return getMostReviewedFilms();
            }
        }
        return new String("Helooo");
    }
}
