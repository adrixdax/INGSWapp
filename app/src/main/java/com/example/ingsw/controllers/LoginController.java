package com.example.ingsw.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.widget.Toast;

import com.example.ingsw.LoginScreen;
import com.example.ingsw.ToolBarActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class LoginController{

    private final Activity activity;
    final int RC_SIGN_IN = 0;


    public LoginController(Activity current){
        this.activity = current;
    }

    /**NOTA: Risulta fondamentale ai fini della funzionalità dell' interfaccia far si che determinate componenti quali l'activity per esempio, vengano passati al controller tramite appositi metodi **/

    public void signIn(GoogleSignInClient mGoogleSignInClient) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void verifyUserWithFirebase(String mail, String password, FirebaseAuth mAuth, Activity loginscreen) throws Exception {


        if(mail.isEmpty()){
            throw new Exception("Empty Mail");
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            throw new Exception("Invalid Mail");
        }

        if(password.isEmpty()){
            throw new Exception("Empty Password");
        }

        if(password.length() < 6){
            throw new Exception("Password Length");
        }

        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                SharedPreferences preferences =   LoginController.this.activity.getSharedPreferences("access",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =  preferences.edit();
                editor.putString("remember","true");
                editor.apply();

                Toast.makeText(loginscreen,"LOGIN EFFETTUATO",Toast.LENGTH_LONG).show();
                ((LoginScreen)loginscreen).stopProgressBar();
                loginscreen.startActivity(new Intent(loginscreen, ToolBarActivity.class));
                //manda al profilo utente/homepage dell' app
            }
            else{
                Toast.makeText(loginscreen,"LOGIN FALLITO",Toast.LENGTH_LONG).show();
            }
        });



    }


}
