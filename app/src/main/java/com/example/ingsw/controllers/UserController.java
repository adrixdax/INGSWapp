package com.example.ingsw.controllers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.ingsw.component.db.classes.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class UserController {

    User tempUser = null;

    public User getTempUser() {
        return tempUser;
    }

    public void setTempUser(User tempuser) {
        this.tempUser = tempuser;
    }

    public User getUserprofile(FirebaseUser mFirebaseUser, DatabaseReference ref) {
        try {
            if (mFirebaseUser != null) {
                String userID = mFirebaseUser.getUid();
                ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        setTempUser(snapshot.getValue(User.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                return getTempUser();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GoogleSignInAccount getAcct(Context context) {
        try {
            if (context != null) {
                return GoogleSignIn.getLastSignedInAccount(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}