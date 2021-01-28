package com.example.INGSW;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomepageScreen extends AppCompatActivity {


    ImageView imageView;
    TextView name,email,id;

    GoogleSignInAccount mGoogleSIgn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepagescreen);

        /*imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.textName);
        email = findViewById(R.id.textEmail);
        id = findViewById(R.id.textID);*/

        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModelList = new ArrayList<>();
        for (int i=0 ; i<10; i++) {
            slideModelList.add(new SlideModel("https://pad.mymovies.it/filmclub/2018/12/029/locandinapg1.jpg", i +"Film"));
        }
        imageSlider.setImageList(slideModelList, true);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);



        /*if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            name.setText(personName);
            email.setText(personEmail);
            id.setText(personId);
            Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);
        }
*/
    }
}