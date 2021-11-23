package com.example.ingsw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.ingsw.controllers.RegistrationController;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import teaspoon.annotations.OnUi;

public class RegistrationScreen extends AppCompatActivity {

    private Button registerUser;
    private EditText editTextNickName, editTextMail, editTextPassword, repeatPassword;
    private ConstraintLayout mainLayout;
    private ConstraintLayout progresLayout;
    private CircularProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrationscreen);
        mainLayout = findViewById(R.id.regLayout);
        progresLayout = findViewById(R.id.regProgressLayout);
        progressBar = findViewById(R.id.regProgressBar);

        String propic = "https://i.pinimg.com/564x/f0/0c/b7/f00cb7716ff739114f49a5ecf12a6b8a.jpg";
        RegistrationController reg = new RegistrationController(RegistrationScreen.this, propic);

        CircleImageView profileImage = findViewById(R.id.propic_image);
        Glide.with(this).load(propic).into(profileImage);

        profileImage.setOnClickListener(v -> startActivity(new Intent(RegistrationScreen.this, AvatarScreenActivity.class)));

        editTextNickName = findViewById(R.id.editTextTextPersonName);
        editTextMail = findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        repeatPassword = findViewById(R.id.editTextTextRepeatPassword);

        registerUser = findViewById(R.id.registerUserButton);
        registerUser.setOnClickListener(v -> {
            this.startProgresBar();
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userNameRef = rootRef.child("Users");
            Query queries = userNameRef.orderByChild("nickname").equalTo(editTextNickName.getText().toString());
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        try {
                            reg.registerUser(editTextMail.getText().toString().trim(), editTextPassword.getText().toString().trim(), repeatPassword.getText().toString().trim(), editTextNickName.getText().toString().trim());
                        } catch (Exception e) {
                            switch (Objects.requireNonNull(e.getMessage())) {
                                case "Empty nickname":
                                    editTextNickName.setError("Inserisci un nickname");
                                    editTextNickName.requestFocus();
                                    break;
                                case "Empty Mail":
                                    editTextMail.setError("Mail vuota");
                                    editTextMail.requestFocus();
                                    break;
                                case "Invalid Mail":
                                    editTextMail.setError("Perfavore inserisci un indirizzo Mail valido");
                                    editTextMail.requestFocus();
                                    break;
                                case "Empty Password":
                                    editTextPassword.setError("La password è necessaria!");
                                    editTextPassword.requestFocus();
                                    break;
                                case "Not equal passwords":
                                    repeatPassword.setError("Le password non coincidono!");
                                    repeatPassword.requestFocus();
                                    break;
                                case "Password Length":
                                    editTextPassword.setError("La password deve contenere almeno 6 caratteri!");
                                    editTextPassword.requestFocus();
                                    break;
                                case "Weak Password":
                                    editTextPassword.setError("La password deve contenere almeno una lettera MAIUSCOLA, una minuscola ed un numero! \n esempio: Crazythanos90");
                                    editTextPassword.requestFocus();
                                    break;
                                default:
                                    e.printStackTrace();
                                    Toast.makeText(RegistrationScreen.super.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    break;
                            }

                        } finally {
                            stopProgressBar();
                        }
                    } else {
                        editTextNickName.setError("Nickname già in uso");
                        editTextNickName.requestFocus();

                    }
                }

                @Override
                public void onCancelled(@NotNull DatabaseError databaseError) {
                }
            };
            queries.addListenerForSingleValueEvent(eventListener);
        });
        repeatPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isAcceptingText()) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                registerUser.callOnClick();
                return true;
            }
            return false;
        });
        PushDownAnim.setPushDownAnimTo(registerUser, profileImage);
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME,"Registration");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,this.getClass().getSimpleName());
        mFirebaseAnalytics.logEvent("Registration",bundle);
    }

    @OnUi
    public void startProgresBar() {
        mainLayout.setAlpha(0.1f);
        progresLayout.setVisibility(View.VISIBLE);
        this.progressBar.setVisibility(View.VISIBLE);
        this.progressBar.setIndeterminateMode(true);
        this.progressBar.animate();
    }

    @OnUi
    public void stopProgressBar() {
        progresLayout.setVisibility(View.INVISIBLE);
        mainLayout.setAlpha(1.0f);
        progressBar.setVisibility(View.INVISIBLE);
    }


}




























