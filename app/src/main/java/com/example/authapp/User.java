package com.example.authapp;

import android.provider.ContactsContract;

public class User {

    public String Nickname, email;

    public User(){}

    public User(String Nickname, String email){
        this.Nickname = Nickname;
        this.email = email;
    }

}
