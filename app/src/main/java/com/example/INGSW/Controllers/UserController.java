package com.example.INGSW.Controllers;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.INGSW.PersonalArea;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserController {

    private User userprofile = null;
    private DatabaseReference reference;
    private String userID;
    User tempuser = null;

    public User getTempuser() {
        return tempuser;
    }

    public void setTempuser(User tempuser) {
        this.tempuser = tempuser;
    }

    public User getUserprofile(FirebaseUser mFirebaseUser) {
        try {
            if (mFirebaseUser != null) {
                System.out.println("Cerco profilo proprietario");
                reference = FirebaseDatabase.getInstance().getReference("Users");
                userID = mFirebaseUser.getUid();
                reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    User profile = null;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        setTempuser(profile = snapshot.getValue(User.class));

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return getTempuser();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GoogleSignInAccount getAcct(Context context) {
        System.out.println("Cerco profilo Google");
        GoogleSignInAccount acct = null;
        try {
            if (context != null) {
                acct = GoogleSignIn.getLastSignedInAccount(context);
            }
            return acct;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acct;
    }
}