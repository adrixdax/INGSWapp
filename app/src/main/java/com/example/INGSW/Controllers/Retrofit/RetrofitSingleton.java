package com.example.INGSW.Controllers.Retrofit;

import android.text.Spannable;

import com.example.INGSW.Utility.JSONDecoder;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonFactoryBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitSingleton {

    private final static String url = "http://79.19.15.166:8080";
    private static retrofit2.Retrofit retrofit;
    public static retrofit2.Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder().baseUrl(url).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(JacksonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
