package com.example.INGSW.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.INGSW.R;
import com.example.INGSW.ToolBarActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import teaspoon.annotations.OnBackground;
import teaspoon.annotations.OnUi;

public class LoginController {

    private final CircularProgressBar progressBar;
    private final Activity activity;
    int RC_SIGN_IN = 0;


    public LoginController(Activity current) {
        this.activity = current;
        progressBar = activity.findViewById(R.id.progressBar);
    }

    /**
     * NOTA: Risulta fondamentale ai fini della funzionalità dell' interfaccia far si che determinate componenti quali l'activity per esempio, vengano passati al controller tramite appositi metodi
     **/

    @OnBackground
    public void signIn(GoogleSignInClient mGoogleSignInClient) {
        this.triggerProgessBar();
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

        triggerProgessBar();
        mAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    SharedPreferences preferences = LoginController.this.activity.getSharedPreferences("access", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();

                    stopProgressBar();
                    Toast.makeText(loginscreen, "LOGIN EFFETTUATO", Toast.LENGTH_LONG).show();
                    loginscreen.startActivity(new Intent(loginscreen, ToolBarActivity.class));
                    //manda al profilo utente/homepage dell' app
                } else {
                    Toast.makeText(loginscreen, "LOGIN FALLITO", Toast.LENGTH_LONG).show();
                    stopProgressBar();
                }
            }
        });


    }

    @OnUi
    public void triggerProgessBar() {
        this.progressBar.setVisibility(View.VISIBLE);
        this.progressBar.setIndeterminateMode(true);
        this.progressBar.animate();
    }

    @OnUi
    public void stopProgressBar() {
        this.progressBar.setVisibility(View.INVISIBLE);
    }


}
