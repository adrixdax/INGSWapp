package com.example.INGSW.Controllers;

import android.app.Activity;
import android.os.AsyncTask;

import androidx.fragment.app.Fragment;

import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.MostSeen;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.Utility.JSONDecoder;
import com.example.INGSW.home.HomepageScreen;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import teaspoon.annotations.OnBackground;

public class FilmTestController extends AsyncTask {

    private Exception exception;
    private ToolBarActivity act;
    private String nameOfFilm = "";
    private final String url = "http://87.1.139.228:8080/";
    private String idList = "";
    private String idFilm = "";
    private String uid = "";
    private String command;
    private List<?> list;

    public FilmTestController(List list, Activity act) {
        this.list = list;
        this.act = (ToolBarActivity) act;
    }

    public FilmTestController(List list) {
        new FilmTestController(list, null);
    }


    private Object getLatestFilms() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&latest=true");
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

    private Object getSearchFilms() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&name=" + nameOfFilm);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).build();
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
    @OnBackground
    protected void onPreExecute() {
        super.onPreExecute();
        act.triggerProgessBar();
    }

    @Override
    @OnBackground
    protected Object doInBackground(Object[] objects) {
        if (objects[0] instanceof String) {
            command = objects[0].toString();
            switch (objects[0].toString()) {
                case "latest":
                    return getLatestFilms();
                case "mostViewed":
                    return getMostViewedFilms();
                case "toSee":
                    return getTooSeeFilmList();
                case "mostReviewd":
                    return getMostReviewedFilms();
                case "search":
                    return getSearchFilms();
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

    @Override
    @OnBackground
    protected void onPostExecute(Object o) {
        try {
            list = (List<Film>) JSONDecoder.getJsonToDecode(o.toString(), Film.class);
            if (command.equals("latest") && (act != null)) {
                act.getConteinerList().put("HomepageList", (List<Film>) list);
                if (act.getActiveFragment().getClass().equals(HomepageScreen.class))
                    ((HomepageScreen) act.getActiveFragment()).updateRecyclerView();
            } else returnValue();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        act.stopProgressBar();
    }

    public Object returnValue() {
        Fragment active = act.getActiveFragment();
        switch (active.getClass().getSimpleName()) {
            case "MostSeen": {
                ((MostSeen) (active)).setList((List<Film>) list);
                ((MostSeen) (active)).updateRecyclerView();
                break;
            }
        }
        return list;
    }


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

}
