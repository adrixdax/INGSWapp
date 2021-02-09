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

    private Object getLatestFilms() {
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "Type=PostRequest&latest=true");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://87.13.160.70:8080/film")
                .post(body)
                .build();
        try {
            try (Response response = client.newCall(request).execute()) {
                return Objects.requireNonNull(response.body()).string();
                //Request.Post("http://192.168.1.13:8080/film").bodyForm(Form.form().add("Type", "PostRequest").add("latest", "true").build()).execute().returnContent();
                //return String.valueOf(Request.Get("http://87.13.160.80:8080/user?nickname=pao").execute().returnContent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (objects[0] instanceof String)
            return getLatestFilms();
        return new Object();
    }
}
