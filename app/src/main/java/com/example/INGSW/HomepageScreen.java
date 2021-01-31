package com.example.INGSW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.INGSW.Component.ListOfFilm;
import com.example.INGSW.Component.ListOfFilmAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        List<ListOfFilm> listOfFilms = new ArrayList<>();

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

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ListOfFilmAdapter adapter = new ListOfFilmAdapter(listOfFilms);
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

}