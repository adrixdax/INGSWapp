package com.example.INGSW;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationScreen extends AppCompatActivity {

    private Button registerUser;
    private EditText editTextNickName,editTextMail,editTextPassword;
    private CircleImageView ProfileImage;
    private Bitmap bitmap;
    Uri imageUri;
    private static final int PICK_IMAGE = 1;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrationscreen);



        mAuth = FirebaseAuth.getInstance();

        editTextNickName = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextMail = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword2);

        ProfileImage = (CircleImageView)findViewById(R.id.profile_image);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationScreen.this,AvatarScreen.class));
                //openGallery();
            }
        });

        RegistrationController registrationController = new RegistrationController(this);

        registerUser  = (Button) findViewById(R.id.registerUserButton);
        registerUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registrationController.registerUser(editTextMail,editTextPassword,editTextNickName);
            }
        });



    }
/*
    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ProfileImage.setImageBitmap(bitmap);

        }


    }*/


}




























