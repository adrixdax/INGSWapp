package com.example.INGSW;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.ListOfFilm;
import com.example.INGSW.Component.ListOfFilmAdapter;
import com.example.INGSW.Controllers.FilmTestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class HomepageScreen extends AppCompatActivity implements View.OnClickListener {


    ImageView imageView;
    TextView name,email,id;

    GoogleSignInAccount mGoogleSIgn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepagescreen);
        String latestJson = "";
        FilmTestController con = new FilmTestController();
        try {
            latestJson = (String) con.execute(new String("latest")).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        List<ListOfFilm> listOfFilms = new ArrayList<>();
/*
        ListOfFilm film = new ListOfFilm("https://pad.mymovies.it/filmclub/2018/12/029/locandinapg1.jpg");
        listOfFilms.add(film);
        //for(int i=0; i<10; i++) {
        film = new ListOfFilm("https://pad.mymovies.it/filmclub/2019/10/160/locandina.jpg");
        listOfFilms.add(film);
        //}

        film = new ListOfFilm("https://pad.mymovies.it/filmclub/2021/01/037/imm.jpg");
        listOfFilms.add(film);

        film = new ListOfFilm("https://pad.mymovies.it/filmclub/2019/02/007/imm.jpg");
        listOfFilms.add(film);
*/

        List<ListOfFilm> films = new ArrayList<>();
        try {
            films = (List<ListOfFilm>) getJsonToDecode(latestJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ListOfFilmAdapter adapter = new ListOfFilmAdapter(films);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


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

       BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search_screen:
                        startActivity(new Intent(HomepageScreen.this,SearchFilmScreen.class));
                        break;
                    case R.id.home_screen:
                        break;
                    case R.id.profile_screen:
                        startActivity(new Intent(HomepageScreen.this,AvatarScreen.class));
                        break;
                }
                return false;
            }
        });


    }

    private final long backPressedTime = 0;

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toSee :
                //toSeeQuery
                break;
            case R.id.mostSeen:
                //mostViewedQuery
                break;
            case R.id.mostReviewed :
                //mostReviewedQuery
                break;
            case R.id.userPrefered :
                //userPreferedQuery
                break;
            }
        }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Vuoi chiudere l'applicazione?")
                .setMessage("Sei sicuro di voler chiudere l'applicazione?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomepageScreen.super.finish();
                    }
                }).create().show();
    }


}