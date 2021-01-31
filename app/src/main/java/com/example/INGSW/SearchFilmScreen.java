package com.example.INGSW;



import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchFilmScreen extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_film_screen);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.search_screen:

                        break;
                    case R.id.home_screen:
                        startActivity(new Intent(SearchFilmScreen.this, HomepageScreen.class));
                        break;
                    case R.id.profile_screen:
                        startActivity(new Intent(SearchFilmScreen.this, AvatarScreen.class));
                        break;
                }

                return false;
            }
        });

    }
}