package com.example.INGSW.Controllers.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("/film")
    Call<String> getFilm(@Body String body);

    @POST("/film")
    Call<String> getFilmById(@Body String body);

    @GET("/notify")
    Call<String> getNotify(@Query("idUser") String param);

    @GET("/notify")
    Call<String> setSeen(@Query("Seen") String param);

    @GET("/notify")
    Call<String> setAccepted(@Query("Accepted") String param);

    @GET("/notify")
    Call<String> setRefused(@Query("Refused") String param);

    @POST("/list")
    Call<String> getList(@Body String body);

    @POST("/review")
    Call<String> getReview(@Body String body);

    @POST("/registration")
    Call<String> getRegistration(@Body String body);

}
