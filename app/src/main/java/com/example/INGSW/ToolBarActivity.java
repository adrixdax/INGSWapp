package com.example.INGSW;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Controllers.UserController;
import com.example.INGSW.Controllers.UserServerController;
import com.example.INGSW.home.HomepageScreen;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class ToolBarActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    Fragment activeFragment;

    Map<String,List<Film>> conteinerList= new HashMap<>();
    private ProgressDialog progressDialog;
    private Map<String, Object> contaiinerItem = new HashMap<>();
    User user = null;
    private boolean loadUser = false;
    UserController userController = new UserController();
    private UserServerController usc = new UserServerController();
    private static final FirebaseDatabase ref = FirebaseDatabase.getInstance();
    private String uid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationscreen);
        getUser();
        ref.setPersistenceEnabled(true);
        try {
            usc.setUserId(uid);
            String temp =(String) usc.execute(new String("getDefaultListOfUser")).get();
            List<UserLists> list = (List<UserLists>) getJsonToDecode(temp, UserLists.class);
            for (UserLists singlelist:  list){
                if(singlelist.getType().equals("PREFERED")){
                    contaiinerItem.put("PREFERED", singlelist.getIdUserList());
                }else if(singlelist.getType().equals("WATCH")){
                    contaiinerItem.put("WATCH", singlelist.getIdUserList());
                }else{
                    contaiinerItem.put("TOWATCH", singlelist.getIdUserList());
                }
            }
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


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

    public Map<String, List<Film>> getConteinerList() {
        return conteinerList;
    }


    public Map<String, Object> getContaiinerItem() {
        return contaiinerItem;
    }


    public void getUser() {
        System.out.println("Sono nella ricerca user");
        Object obj = null;
        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mFirebaseUser != null) {
            setUid(mFirebaseUser.getUid());
            obj = userController.getUserprofile(mFirebaseUser);
            if (obj != null) {
                System.out.println("Trovato profilo proprietario");
                contaiinerItem.put("userProfile", obj);

            }
        } else {
            obj = userController.getAcct(this);
            if (obj != null) {
                setUid(((GoogleSignInAccount)obj).getId());
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
        String tag1="1";
        String tag2= "2";
        String tag3= "3";
        if( fm.getBackStackEntryCount()>0 && !(tag2.equals(currentFragment.getTag())  || tag3.equals(currentFragment.getTag())) && !(tag1.equals(currentFragment.getTag())) ){
            fm.popBackStack();
        }else if( tag2.equals(currentFragment.getTag())  || tag3.equals(currentFragment.getTag()) ){

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
                            finish();
                            //ToolBarActivity.super.onBackPressed();
                        }
                    }).create().show();
        }

    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getUid(){
        return this.uid;
    }

    public static FirebaseDatabase getReference(){
        return ref;
    }

}

//account@gmail.com