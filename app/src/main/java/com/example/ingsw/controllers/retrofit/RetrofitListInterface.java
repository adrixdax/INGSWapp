package com.example.ingsw.controllers.retrofit;

import java.util.List;

import teaspoon.annotations.OnBackground;

public interface RetrofitListInterface {

    @OnBackground
    void setList(List<?> newList);

}
