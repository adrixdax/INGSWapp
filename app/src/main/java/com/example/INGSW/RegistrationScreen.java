package com.example.INGSW;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.INGSW.Controllers.RegistrationController;
import com.google.firebase.auth.FirebaseAuth;
import com.thekhaeng.pushdownanim.PushDownAnim;

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

        ProfileImage = (CircleImageView)findViewById(R.id.propic_image);
        Glide.with(this).load(propic).into(ProfileImage);

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationScreen.this,AvatarScreen.class));
            }
        });

        editTextNickName = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextMail = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword2);
        registerUser  = (Button) findViewById(R.id.registerUserButton);
        registerUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new RegistrationController(RegistrationScreen.this,propic).registerUser(editTextMail,editTextPassword,editTextNickName,propic);
            }
        });
        editTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    registerUser.callOnClick();
                    return true;
                }
                return false;
            }
        });

        PushDownAnim.setPushDownAnimTo(registerUser,ProfileImage);


    }



}




























