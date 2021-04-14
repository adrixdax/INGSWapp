package com.example.INGSW.Controllers.Retrofit;

import android.content.Context;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitResponse {

    private static Boolean response = Boolean.FALSE;

    public static <type> void getResponse(String body, Object c, Context context, String structureClass, String callMethod) {
        RetrofitInterface service = RetrofitSingleton.getRetrofit().create(RetrofitInterface.class);
        try {
            Method methodRetrofit = service.getClass().getMethod(callMethod,String.class);
            Call<type> call = (Call<type>) methodRetrofit.invoke(service, body);
            call.enqueue(new Callback<type>() {
                @Override
                public void onResponse(Call<type> call, Response<type> response) {
                        try {
                            System.out.println(response.body().getClass().getSimpleName());
                            if (response.body() instanceof Boolean) {

                            } else {
                                Method methodClassCalled = c.getClass().getMethod("setList", List.class);
                                methodClassCalled.invoke(c, response.body());
                            }
                        }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }

                @Override
                public void onFailure(Call<type> call, Throwable t) {
                    Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
                    t.printStackTrace();
                }
            });
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Boolean getResponse() {
        return response;
    }

    public static void setResponse(Boolean response) {
        RetrofitResponse.response = response;
    }
}
