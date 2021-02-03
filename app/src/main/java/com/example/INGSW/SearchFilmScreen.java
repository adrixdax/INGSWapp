package com.example.INGSW;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchFilmScreen extends AppCompatActivity {


    private EditText Text_of_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_film_screen);

        ImageButton bt_search = findViewById(R.id.search_button);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Text_of_search = (EditText) findViewById(R.id.Text_of_search);
                Toast.makeText(SearchFilmScreen.this, "Il click funziona  : " + Text_of_search.getText().toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.search_screen:

                        break;
                    case R.id.home_screen:
                        startActivity(new Intent(SearchFilmScreen.this, HomepageScreen.class));
                        SearchFilmScreen.super.onBackPressed();
                        break;
                    case R.id.profile_screen:
                        startActivity(new Intent(SearchFilmScreen.this, AvatarScreen.class));
                        SearchFilmScreen.super.onBackPressed();
                        break;
                }

                return false;
            }
        });

    }

}