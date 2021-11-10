package com.example.ingsw.controllers.retrofit;

import com.example.ingsw.component.db.classes.Contact;
import com.example.ingsw.component.db.classes.Notify;
import com.example.ingsw.component.db.classes.Reviews;
import com.example.ingsw.component.db.classes.UserLists;
import com.example.ingsw.component.films.Film;

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
    Call<List<Notify>> getFriendShipNotify(@Query("friendId") String param);

    @GET("/notify")
    Call<String> setSeen(@Query("Seen") String param);

    @GET("/notify")
    Call<String> setAccepted(@Query("Accepted") String param);

    @GET("/notify")
    Call<String> setRefused(@Query("Refused") String param);

    @POST("/notify")
    Call<String> createNotify(@Body String body);

    @POST("/list")
    Call<List<UserLists>> getList(@Body String body);

    @GET("/list")
    Call<List<UserLists>> getListById(@Query("idUserList") String param);

    @POST("/user")
    Call<List<UserLists>> getDefaultList(@Body String body);

    @POST("/list")
    Call<Boolean> addFilm(@Body String body);

    @POST("/list")
    Call<String> removeFilmInList(@Body String body);

    @POST("/user")
    Call<String> addList(@Body String body);

    @POST("/user")
    Call<String> deleteCustomList(@Body String body);

    @POST("/list")
    Call<List<Film>> getFilmInList(@Body String body);

    @POST("/list")
    Call<Boolean> isFilmInList(@Body String body);

    @GET("/review")
    Call<List<Reviews>> getSingleReview(@Query("idReviews") String idReview);

    @POST("/review")
    Call<List<Reviews>> getReview(@Body String body);

    @POST("/review")
    Call<String> addReview(@Body String body);

    @POST("/registration")
    Call<String> getRegistration(@Body String body);

    @POST("/user")
    Call<List<Contact>> getFriends(@Body String body);

    @POST("/user")
    Call<String> addFriend(@Body String body);

    @POST("/report")
    Call<String> addReport(@Body String body);

    @GET("/online")
    Call<String> addOnlineMember(@Query("loggingIn") String value );

}
