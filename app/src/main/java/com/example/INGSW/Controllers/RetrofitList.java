package com.example.INGSW.Controllers;
import java.util.List;
import teaspoon.annotations.OnBackground;

public interface RetrofitList {

    @OnBackground
    public void setList(List<?> newList);

}
