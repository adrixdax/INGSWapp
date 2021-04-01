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

    private final String url = "http://87.1.139.228:8080/";
    private String UserId = "";
    private String idFilm = "";
    private String idOtherUser = "";
    private String idList = "";

    private String listTitle = "";
    private String listDescription = "";


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

        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&idUser=" + UserId + "&searchDefaultList=true");
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

    private Object getCustomLists() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&idUser=" + UserId + "&custom=true&idFilm=" + idFilm);
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

    private Object addCustomList() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&idUser=" + UserId + "&addList=true&listTitle=" + listTitle + "&listDescription=" + listDescription);
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

    private Object acceptedFriendsRequest() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&addFriends=true&idUser=" + UserId + "&idOtherUser=" + idOtherUser);
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

    private Object isFriends() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&isFriends=true&idUser=" + UserId + "&idOtherUser=" + idOtherUser);
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

    private Object deleteCustomList() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&removeList=true&idUser=" + UserId + "&idList=" + idList);
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

    private Object getFriends() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&isFriends=true&idUser=" + UserId);
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
                case "custom":
                    return getCustomLists();
                case "addCustomList":
                    return addCustomList();
                case "acceptedFriendsRequest":
                    return acceptedFriendsRequest();
                case "isFriends":
                    return isFriends();
                case "deleteCustomList":
                    return deleteCustomList();
                case "getFriends":
                    return getFriends();
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

    public String getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(String idFilm) {
        this.idFilm = idFilm;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public String getIdOtherUser() {
        return idOtherUser;
    }

    public void setIdOtherUser(String idOtherUser) {
        this.idOtherUser = idOtherUser;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }
}

