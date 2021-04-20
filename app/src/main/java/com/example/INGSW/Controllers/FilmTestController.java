package com.example.INGSW.Controllers;

import android.os.AsyncTask;
import android.widget.Toolbar;

import com.example.INGSW.ToolBarActivity;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FilmTestController extends AsyncTask {

    private Exception exception;
    private String nameOfFilm = "";
    private final String url = "http://79.19.15.166:8080/";
    private String idList = "";
    private String idFilm = "";
    private String uid = "";


/*
    private Object getMostViewedFilms() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&mostviewed=true");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "film")
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
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&toSee=" + this.getUid());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "film")
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
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&mostreviewed=true");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "film")
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


    private Object idFilmInList() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&idList=" + idList + "&idFilm=" + idFilm);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url + "list").post(body).build();
        try {
            try (Response response = client.newCall(request).execute()) {

                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Object addFilmInList() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&idList=" + idList + "&idFilm=" + idFilm +"&addFilm=true");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url + "list").post(body).build();
        try {
            try (Response response = client.newCall(request).execute()) {
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Object getUserPreferedFilms() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&userPrefered=true");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url + "film").post(body).build();
        try {
            try (Response response = client.newCall(request).execute()) {
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Object removeFilmInList() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&idList=" + idList + "&idFilm=" + idFilm + "&removeFilm=true");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url + "list").post(body).build();
        try {
            try (Response response = client.newCall(request).execute()) {

                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    private Object getFilmInList() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&idList=" + idList);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "list")
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

    private Object getFilmbyId() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&filmId=" + idFilm);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "film")
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

    private Object getList() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "list?idUserList=" + idList)
                .get()
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
                case "mostViewed":
                    return getMostViewedFilms();
                case "toSee":
                    return getTooSeeFilmList();
                case "mostReviewd":
                    return getMostReviewedFilms();
                case "isInList":
                    return idFilmInList();
                case "addFilm":
                    return addFilmInList();
                case "removeFilm":
                    return removeFilmInList();
                case "userPrefered":
                    return getUserPreferedFilms();
                case "filmInList":
                    return getFilmInList();
                case "filmById":
                    return getFilmbyId();
                case "listName":
                    return getList();
            }
        }
        return "Helooo";
    }

 */



    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public String getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(String idFilm) {
        this.idFilm = idFilm;
    }

    public String getNameOfFilm() {
        return nameOfFilm;
    }

    public void setNameOfFilm(String nameOfFilm) {
        this.nameOfFilm = nameOfFilm;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}
