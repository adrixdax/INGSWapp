package com.example.INGSW.Controllers.Retrofit;

import com.example.INGSW.Component.Films.Film;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/film")
    Call<String> getLatestMovies(@Body String body);

    @POST("/film")
    Call<String> getMovieByName(@Body String body);
}
