package com.example.INGSW.Controllers.Retrofit;

import com.example.INGSW.Component.Films.Film;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("/film")
    Call<String> getFilm(@Body String body);

    @GET("/notify")
    Call<String> getNotify(@Query("mid") String param1);

    @POST("/list")
    Call<String> getList(@Body String body);



}
