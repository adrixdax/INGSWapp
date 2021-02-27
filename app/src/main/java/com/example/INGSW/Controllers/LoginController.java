package com.example.INGSW.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.home.HomepageScreen;
import com.example.INGSW.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginController{

    private ProgressBar progressBar;
    private Activity activity;
    int RC_SIGN_IN = 0;


    public LoginController(Activity current){
        this.activity = current;
        progressBar = (ProgressBar)activity.findViewById(R.id.progressBar);
    }

    /**NOTA: Risulta fondamentale ai fini della funzionalità dell' interfaccia far si che determinate componenti quali l'activity per esempio, vengano passati al controller tramite appositi metodi **/

    public void signIn(GoogleSignInClient mGoogleSignInClient) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void verifyUserWithFirebase(String mail, String password, FirebaseAuth mAuth, Activity loginscreen) throws Exception {


        if(mail.isEmpty()){
            throw new Exception("Empty Mail"); /**C'è da creare le  nostre custom exceptions**/
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            throw new Exception("Invalid Mail"); /**C'è da creare le  nostre custom exceptions**/
        }

        if(password.isEmpty()){
            throw new Exception("Empty Password"); /**C'è da creare le  nostre custom exceptions**/
        }

        if(password.length() < 6){
            throw new Exception("Password Length"); /**C'è da creare le  nostre custom exceptions**/
        }

        progressBar.setVisibility(View.VISIBLE); /** Non riesco a far funzionare la progress bar :( **/
        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    SharedPreferences preferences =   LoginController.this.activity.getSharedPreferences("access",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =  preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(loginscreen,"LOGIN EFFETTUATO",Toast.LENGTH_LONG).show();
                    loginscreen.startActivity(new Intent(loginscreen, ToolBarActivity.class));
                    //manda al profilo utente/homepage dell' app
                }
                else{
                    Toast.makeText(loginscreen,"LOGIN FALLITO",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });



    }










}
