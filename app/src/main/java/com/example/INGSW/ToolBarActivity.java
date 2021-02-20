package com.example.INGSW;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.INGSW.Component.ListOfFilm;
import com.example.INGSW.home.HomepageScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ToolBarActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    Fragment activeFragment;
    private List<ListOfFilm> listFilm= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationscreen);

        final int maxMemorySize = (int) Runtime.getRuntime().maxMemory() / 1024;
        final int cacheSize = maxMemorySize / 10;



        loadFragment(new HomepageScreen(),"1");

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(this);


    }
    private boolean loadFragment(Fragment fragment, String tag){
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.nav_host_fragment);
        if(fragment!=null) {
            if (!tag.equals(currentFragment.getTag())) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment,tag).commit();
                activeFragment= fragment;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        String tag ="";

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomepageScreen();
                tag = "1";
                break;

            case R.id.navigation_personal_area:
                fragment = new PersonalArea();
                tag = "2";
                break;

            case R.id.navigation_search:
                fragment = new SearchFilmScreen();
                tag = "3";
                break;

        }

        return loadFragment(fragment,tag);
    }

    public List<ListOfFilm> getListOfFilm(){
        return listFilm;
    }

    public void setListOfFilm(List<ListOfFilm> tempList){
        listFilm = tempList;
    }


}

