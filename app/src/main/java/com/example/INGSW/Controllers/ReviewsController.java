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
    private final String url = "http://87.1.139.228:8080/";
    private String idFilm = "";
    private String idUser = "";
    private String title="";
    private String desc="";
    private String val="";

    private Object getFilmReviews() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&idFilm=" + idFilm + "&insert=false");
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
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&idUser=" + idUser + "&insert=false");
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

    private Object addReviews() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&idFilm=" + idFilm + "&title=" + title + "&description=" + desc + "&val=" + val + "&idUser=" + idUser + "&insert=true");
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
                case "AddReviews":
                    return addReviews();
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

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
