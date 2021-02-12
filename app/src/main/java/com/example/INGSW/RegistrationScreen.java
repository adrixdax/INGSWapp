package com.example.INGSW;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.INGSW.Controllers.RegistrationController;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationScreen extends AppCompatActivity {

    private Button registerUser;
    private EditText editTextNickName,editTextMail,editTextPassword;
    private CircleImageView ProfileImage;
    private String propic = "https://img.favpng.com/11/21/25/iron-man-cartoon-avatar-superhero-icon-png-favpng-jrRBMJQjeUwuteGtBce87yMxz.jpg";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrationscreen);

        mAuth = FirebaseAuth.getInstance();


        editTextNickName = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextMail = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword2);

        ProfileImage = (CircleImageView)findViewById(R.id.propic_image);
        Glide.with(this).load(propic).into(ProfileImage);

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationScreen.this,AvatarScreen.class));
            }
        });

        RegistrationController registrationController = new RegistrationController(this,propic);

        registerUser  = (Button) findViewById(R.id.registerUserButton);
        registerUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registrationController.registerUser(editTextMail,editTextPassword,editTextNickName,propic);
            }
        });



    }



}




























