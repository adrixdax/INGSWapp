package com.example.INGSW.Controllers.Retrofit;

import android.content.Context;
import android.widget.Toast;

import com.example.INGSW.Utility.JSONDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitResponse {

    private String request="";


    public static void getResponse(String body, Object c, Context context, String structureClass) {
        RetrofitInterface service = RetrofitSingleton.getRetrofit().create(RetrofitInterface.class);
        Call<String> call = service.getLatestMovies(body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Method m = c.getClass().getMethod("setList", List.class);
                    m.invoke(c,((List<?>) JSONDecoder.getJsonToDecode(response.body(), Class.forName(structureClass))));

                } catch (JsonProcessingException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG);
            }
        });
    }
}
