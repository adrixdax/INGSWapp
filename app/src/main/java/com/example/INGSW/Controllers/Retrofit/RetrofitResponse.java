package com.example.INGSW.Controllers.Retrofit;

import android.content.Context;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import teaspoon.annotations.OnBackground;

public class RetrofitResponse {

    @OnBackground
    public static <type> void getResponse(String body, Object c, Context context, String callMethod, Object toGlide) {
        RetrofitInterface service = RetrofitSingleton.getRetrofit().create(RetrofitInterface.class);
        System.out.println(body);
        try {
            Method methodRetrofit = service.getClass().getMethod(callMethod,String.class);
            Call<type> call = (Call<type>) methodRetrofit.invoke(service, body);
            call.enqueue(new Callback<type>() {
                @Override
                public void onResponse(Call<type> call, Response<type> response) {
                        try {
                            if (response.body() instanceof List) {
                                Method methodClassCalled = c.getClass().getMethod("setList", List.class);
                                methodClassCalled.invoke(c, response.body() != null ? response.body() : new ArrayList<>());
                            }
                            else if (response.body() instanceof String){
                                System.out.println(response.body());
                            }
                            else if (response.body() instanceof Boolean && toGlide != null){
                                Method methodClassCalled = c.getClass().getMethod("glideObject", Boolean.class,Object.class);
                                methodClassCalled.invoke(c, response.body(),toGlide);
                            }
                        }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }

                @Override
                public void onFailure(Call<type> call, Throwable t) {
                    if (!(t instanceof SocketTimeoutException)){
                        Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void getResponse(String body, Object c, Context context, String callMethod) {
        getResponse(body,c,context,callMethod,null);
    }

}
