package com.example.INGSW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class AvatarScreen extends AppCompatActivity implements View.OnClickListener  {


    private CircleImageView ironman;
    private CircleImageView spiderman;
    private CircleImageView gamora;
    private CircleImageView thanos;
    private CircleImageView wonderwoman;
    private CircleImageView widow;

    RegistrationController regcont;


    String urlIron = "https://img.favpng.com/11/21/25/iron-man-cartoon-avatar-superhero-icon-png-favpng-jrRBMJQjeUwuteGtBce87yMxz.jpg";
    String urlSpider = "https://i.pinimg.com/236x/d4/9f/33/d49f3302e2a4e7b5a21ea3aba0cfcf03.jpg";
    String urlGamora = "https://i.pinimg.com/564x/48/99/65/48996519ea996aa169ca1d61e2a6c6ab.jpg";
    String urlThanos = "https://i.pinimg.com/236x/fa/60/b8/fa60b89014f5807b5a013e83aba32aab.jpg";
    String urlWidow = "https://i.pinimg.com/564x/90/15/d9/9015d92696baf129a8b4d273625fbfdd.jpg";
    String urlWonder = "https://i.pinimg.com/564x/5b/71/ab/5b71ab4ea082c3c11e77312a64bba835.jpg";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar_screen);

        ironman = (CircleImageView) findViewById(R.id.profile_image);
        spiderman = (CircleImageView) findViewById(R.id.profile_image2);
        gamora = (CircleImageView) findViewById(R.id.profile_image3);
        thanos = (CircleImageView) findViewById(R.id.profile_image4);
        widow = (CircleImageView) findViewById(R.id.profile_image5);
        wonderwoman = (CircleImageView) findViewById(R.id.profile_image6);

        ironman.setOnClickListener(this);
        spiderman.setOnClickListener(this);
        gamora.setOnClickListener(this);
        thanos.setOnClickListener(this);
        widow.setOnClickListener(this);
        wonderwoman.setOnClickListener(this);





        Glide.with(this).load(urlIron).into(ironman);
        Glide.with(this).load(urlSpider).into(spiderman);
        Glide.with(this).load(urlThanos).into(thanos);
        Glide.with(this).load(urlGamora).into(gamora);
        Glide.with(this).load(urlWidow).into(widow);
        Glide.with(this).load(urlWonder).into(wonderwoman);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile_image :
                RegistrationController.setAvatar(urlIron);
                onBackPressed();
                break;
            case R.id.profile_image2 :
                    RegistrationController.setAvatar(urlSpider);
                    onBackPressed();
                    break;
            case R.id.profile_image3 :
                RegistrationController.setAvatar(urlGamora);
                onBackPressed();
                break;
            case R.id.profile_image4 :
                RegistrationController.setAvatar(urlThanos);
                onBackPressed();
                break;
            case R.id.profile_image5 :
                RegistrationController.setAvatar(urlWidow);
                onBackPressed();
                break;

            case R.id.profile_image6 :
                RegistrationController.setAvatar(urlWonder);
                onBackPressed();
                break;

        }
    }
}



