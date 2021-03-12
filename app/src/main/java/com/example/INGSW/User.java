package com.example.INGSW;

public class User {

    public String nickname, email,propic;

    public User(){

    }

    public User(String nickname, String email,String propic){
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
}
