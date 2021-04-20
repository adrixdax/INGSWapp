package com.example.INGSW.Controllers;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotifyTestController extends AsyncTask {

    private Exception exception;
    private final String url = "http://79.19.15.166:8080/";
    private String idSender ="";
    private String idReceiver ="";
    private String type ="";
    private String idRecordref ="";


    private Object getNotify(String idUser) {
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

    private Object sendFriendshipRequest() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "notify?id_sender=" + idSender + "&id_receiver=" + idReceiver +"&type=" + type + "&id_recordref=" + idRecordref + "&sendNotify=true").get().build();

        try {
            try (Response response = client.newCall(request).execute()) {

                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error to send Notify";
    }

    private Object shareFriendsContent() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "notify?id_sender=" + idSender + "&id_receiver=" + idReceiver +"&type=" + type + "&id_recordref=" + idRecordref + "&sendNotify=true").get().build();

        try {
            try (Response response = client.newCall(request).execute()) {

                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error to send Notify";
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (objects[0].toString().startsWith("idUser="))
            return getNotify(((String)objects[0]).substring(((String)objects[0]).indexOf('=')+1));
        else if (objects[0].toString().startsWith("Seen="))
            return setSeen(((String)objects[0]).substring(((String)objects[0]).indexOf('=')+1));
        else if (objects[0].toString().startsWith("Accepted="))
            return setAccepted(((String)objects[0]).substring(((String)objects[0]).indexOf('=')+1));
        else if (objects[0].toString().startsWith("Refused="))
            return setRefused(((String)objects[0]).substring(((String)objects[0]).indexOf('=')+1));
        else if (objects[0].toString().equals("SendFriendshipRequest"))
            return sendFriendshipRequest();
        else if (objects[0].toString().equals("shareFriendsContent"))
            return shareFriendsContent();

        return null;
    }


    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdRecordref() {
        return idRecordref;
    }

    public void setIdRecordref(String idRecordref) {
        this.idRecordref = idRecordref;
    }
}