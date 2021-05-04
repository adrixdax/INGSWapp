package com.example.ingsw.component.db.classes;

public class User {

    public String nickname, email, propic, idUser;


    public User() {

    }

    public User(String nickname, String email, String propic) {
        this.nickname = nickname;
        this.email = email;
        this.propic = propic;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPropic() {
        return propic;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
