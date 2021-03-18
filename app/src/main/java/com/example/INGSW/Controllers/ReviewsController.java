package com.example.INGSW.Controllers;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReviewsController extends AsyncTask {

    private Exception exception;
    private final String url = "http://192.168.1.211:8080/";
    private String idFilm = "";
    private String idUser = "";

    private Object getFilmReviews() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&idFilm=" + idFilm);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url + "review").post(body).build();
        try {
            try (Response response = client.newCall(request).execute()) {

                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Object getUserReviews() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&idUser=" + idUser);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url + "review").post(body).build();
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
                case "FilmReviews":
                    return getFilmReviews();
                case "UserReviews":
                    return getUserReviews();
            }
        }
        return "Helooo";
    }

    public String getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(String idFilm) {
        this.idFilm = idFilm;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
