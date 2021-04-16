package com.example.INGSW.Component.DB.Classes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Contact {

    @JsonProperty("user1")
    private String user1;
    @JsonProperty("user2")
    private String user2;

    @JsonCreator
    public Contact(@JsonProperty("user1") String user1,
                  @JsonProperty("user2") String user2) {
        this.user1=user1;
        this.user2=user2;
    }

    public String getUser1() {
        return user1;
    }

    @JsonSetter("user1")
    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    @JsonSetter("user2")
    public void setUser2(String user2) {
        this.user2 = user2;
    }
}
