package com.example.INGSW.Controllers.Retrofit;

import retrofit2.Call;
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

    @POST("/registration")
    Call<String> getRegistration(@Body String body);

}
