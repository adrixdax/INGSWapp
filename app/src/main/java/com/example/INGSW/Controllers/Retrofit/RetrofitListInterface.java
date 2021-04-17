package com.example.INGSW.Controllers.Retrofit;
import java.util.List;
import teaspoon.annotations.OnBackground;

public interface RetrofitListInterface {

    @OnBackground
    void setList(List<?> newList);

}
