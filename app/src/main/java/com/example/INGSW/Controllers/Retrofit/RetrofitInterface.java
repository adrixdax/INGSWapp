package com.example.INGSW.Controllers.Retrofit;

import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Component.DB.Classes.Reviews;
import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Component.Films.Film;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("/film")
    Call<List<Film>> getFilm(@Body String body);

    @POST("/film")
    Call<List<Film>> getFilmById(@Body String body);

    @GET("/notify")
    Call<List<Notify>> getNotify(@Query("idUser") String param);

    @GET("/notify")
    Call<String> setSeen(@Query("Seen") String param);

    @GET("/notify")
    Call<String> setAccepted(@Query("Accepted") String param);

    @GET("/notify")
    Call<String> setRefused(@Query("Refused") String param);

    @POST("/list")
    Call<List<UserLists>> getList(@Body String body);

    @POST("/review")
    Call<List<Reviews>> getReview(@Body String body);

    @POST("/registration")
    Call<Boolean> getRegistration(@Body String body);

}
