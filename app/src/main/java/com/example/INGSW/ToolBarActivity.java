package com.example.INGSW;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.INGSW.Component.Films.ListOfFilm;
import com.example.INGSW.Controllers.UserController;
import com.example.INGSW.home.HomepageScreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolBarActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    Fragment activeFragment;
    private List<ListOfFilm> listFilm = null;
    private ProgressDialog progressDialog;
    private Map<String, Object> contaiinerItem = new HashMap<>();
    User user = null;
    private boolean loadUser = false;
    UserController userController = new UserController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationscreen);
        getUser();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        loadFragment(new HomepageScreen(), "1");

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(this);


    }

    private boolean loadFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.nav_host_fragment);
        if (fragment != null) {
            if (!tag.equals(currentFragment.getTag())) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, tag).commit();
                activeFragment = fragment;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        String tag = "";

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

        return loadFragment(fragment, tag);
    }

    public void showProgressBar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }

    public void stopProgressBar() {
        progressDialog.dismiss();
    }

    public List<ListOfFilm> getListOfFilm() {
        return listFilm;
    }

    public void setListOfFilm(List<ListOfFilm> tempList) {
        listFilm = tempList;
    }

    public Map<String, Object> getContaiinerItem() {
        return contaiinerItem;
    }


    public void getUser() {
        System.out.println("Sono nella ricerca user");
        Object obj = null;
        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mFirebaseUser != null) {
            obj = userController.getUserprofile(mFirebaseUser);
            if (obj != null) {
                System.out.println("Trovato profilo proprietario");
                contaiinerItem.put("userProfile", obj);
            }
        } else {
            obj = userController.getAcct(this);
            if (obj != null) {
                System.out.println("Trovato profilo Google");
                contaiinerItem.put("acct", obj);
            }
        }

    }

    public boolean isLoadUser() {
        return loadUser;
    }

    public void setLoadUser(boolean loadUser) {
        this.loadUser = loadUser;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.nav_host_fragment);
        String tag2= "2";
        String tag3= "3";
        if( tag2.equals(currentFragment.getTag())  || tag3.equals(currentFragment.getTag()) ){

            Fragment fragment = new HomepageScreen();
            String tag = "1";
            fm.beginTransaction().replace(R.id.nav_host_fragment,fragment,tag).commit();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Vuoi Uscire?")
                    .setMessage("Noi siamo quello che scegliamo di essere. Ora scegli!\n\n                                     (Goblin Spiderman)")
                    //Dopotutto, domani è un altro giorno
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            ToolBarActivity.super.onBackPressed();
                        }
                    }).create().show();
        }
    }
}

//account@gmail.com