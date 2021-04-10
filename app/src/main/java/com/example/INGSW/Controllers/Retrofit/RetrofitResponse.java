package com.example.INGSW.Controllers.Retrofit;

import android.content.Context;
import android.widget.Toast;

import com.example.INGSW.Utility.JSONDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitResponse {

    private static Boolean response= new Boolean(false);



    public static void getResponse(String body, Object c, Context context, String structureClass, String callMethod) {
        RetrofitInterface service = RetrofitSingleton.getRetrofit().create(RetrofitInterface.class);
        Method methodRetrofit = null;
        try {
            methodRetrofit = service.getClass().getMethod(callMethod,String.class);
            Call<String> call = (Call<String>) methodRetrofit.invoke(service, body);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    try {
                        if(response.body().isEmpty()){

                        }else if(response.body().startsWith("[")){
                            Method methodClassCalled = c.getClass().getMethod("setList", List.class);
                            methodClassCalled.invoke(c,((List<?>) JSONDecoder.getJsonToDecode(response.body(), Class.forName(structureClass))));
                        }else if((response.body().equals("true")) || (response.body().equals("false") )){
                           setResponse(Boolean.parseBoolean(response.body()));
                        }else {
                            System.out.println(response.body());
                        }


                    } catch (JsonProcessingException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG);
                }
            });
        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
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
