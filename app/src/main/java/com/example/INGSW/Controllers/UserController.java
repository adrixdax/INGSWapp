package com.example.INGSW.Controllers;

import android.content.Context;

import com.example.INGSW.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserController {

    private User userprofile = null;
    private DatabaseReference reference;
    private String userID;
    User tempUser = null;

    public User getTempUser() {
        return tempUser;
    }

    public void setTempUser(User tempuser) {
        this.tempUser = tempuser;
    }

    public User getUserprofile(FirebaseUser mFirebaseUser) {
        try {
            if (mFirebaseUser != null) {
                System.out.println("Cerco profilo proprietario");
                userID = mFirebaseUser.getUid();
                tempUser = new User();
                FirebaseDatabase.getInstance().getReference(userID).child("nickname").setValue(tempUser);
                FirebaseDatabase.getInstance().getReference(userID).child("email").setValue(tempUser);
                FirebaseDatabase.getInstance().getReference(userID).child("propick").setValue(tempUser);
                setTempUser(tempUser);
                System.out.println(tempUser.email+" "+tempUser.propic+" "+tempUser.nickname);
                return getTempUser();
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