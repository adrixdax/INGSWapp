package com.example.ingsw.component.db.classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class User {

    private String nickname, email, propic, idUser;
    @JsonProperty("isAdmin")
    private boolean isAdmin;

    public User() {
    }

    public User(String nickname, String email, String propic, boolean isAdmin) {
        this.nickname = nickname;
        this.email = email;
        this.propic = propic;
        this.isAdmin=isAdmin;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPropic() {
        return propic;
    }

    public void setPropic(String propic) {
        this.propic = propic;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @JsonSetter("isAdmin")
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
