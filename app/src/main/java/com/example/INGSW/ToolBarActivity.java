package com.example.INGSW;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Controllers.UserController;
import com.example.INGSW.Controllers.UserServerController;
import com.example.INGSW.home.HomepageScreen;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import teaspoon.TeaSpoon;
import teaspoon.annotations.OnUi;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class ToolBarActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    Fragment activeFragment;

    private FirebaseAnalytics mFirebaseAnalytics;

    float x1, x2, y1, y2;

    Map<String, List<Film>> conteinerList = new HashMap<>();
    private ProgressDialog progressDialog;
    private final Map<String, Object> contaiinerItem = new HashMap<>();
    User user = null;
    private boolean loadUser = false;
    UserController userController = new UserController();
    private final UserServerController usc = new UserServerController();
    private static DatabaseReference ref;
    private String uid = "";
    CircularProgressBar progressBar;
    ConstraintLayout mainLayout;
    ConstraintLayout progressLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TeaSpoon.initialize();
        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            ref = db.getReference("Users");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.navigationscreen);
        getUser();


        try {
            usc.setUserId(uid);
            String temp = (String) usc.execute("getDefaultListOfUser").get();
            List<UserLists> list = (List<UserLists>) getJsonToDecode(temp, UserLists.class);
            for (UserLists singlelist : list) {
                if (singlelist.getType().equals("PREFERED")) {
                    contaiinerItem.put("PREFERED", singlelist.getIdUserList());
                } else if (singlelist.getType().equals("WATCH")) {
                    contaiinerItem.put("WATCH", singlelist.getIdUserList());
                } else {
                    contaiinerItem.put("TOWATCH", singlelist.getIdUserList());
                }
            }
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        loadFragment(new HomepageScreen(), "1", false);

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(this);
        progressBar = findViewById(R.id.activityProgressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressLayout = findViewById(R.id.layoutProgress);
        progressLayout.setVisibility(View.INVISIBLE);
        mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setVisibility(View.VISIBLE);

    }

    private boolean loadFragment(Fragment fragment, String tag, boolean swipe) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.nav_host_fragment);
        if (fragment != null) {
            if ((!tag.equals(currentFragment.getTag())) && swipe) {
                if (tag.equals("3"))
                    getSupportFragmentManager()
                            .beginTransaction().setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                            .replace(R.id.nav_host_fragment, fragment, tag)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else if (tag.equals("2"))
                    getSupportFragmentManager()
                            .beginTransaction().setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                            .replace(R.id.nav_host_fragment, fragment, tag)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else if (currentFragment.getTag() != null) {
                    if (tag.equals("1") && currentFragment.getTag().equals("3"))
                        getSupportFragmentManager()
                                .beginTransaction().setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left)
                                .replace(R.id.nav_host_fragment, fragment, tag)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                    else if (tag.equals("1") && currentFragment.getTag().equals("2"))
                        getSupportFragmentManager()
                                .beginTransaction().setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                                .replace(R.id.nav_host_fragment, fragment, tag)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                    else
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, fragment, tag)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                }
            } else if (!tag.equals(currentFragment.getTag())) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, tag)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
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

        return loadFragment(fragment, tag, false);
    }

    public Map<String, List<Film>> getConteinerList() {
        return conteinerList;
    }


    public Map<String, Object> getContaiinerItem() {
        return contaiinerItem;
    }


    public void getUser() {
        Object obj = null;
        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mFirebaseUser != null) {
            setUid(mFirebaseUser.getUid());
            obj = userController.getUserprofile(mFirebaseUser, ref);
            if (obj != null) {
                contaiinerItem.put("userProfile", obj);

            }
        } else {
            obj = userController.getAcct(this);
            if (obj != null) {
                setUid(((GoogleSignInAccount) obj).getId());
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
        String tag1 = "1";
        String tag2 = "2";
        String tag3 = "3";

        if (currentFragment.getTag().equals("InsertFilmReview")) {
            LeaveReviewAlert dlg = new LeaveReviewAlert();
            dlg.show(this.getSupportFragmentManager(), "LeaveReview");
        } else if (fm.getBackStackEntryCount() > 0 && !(tag2.equals(currentFragment.getTag()) || tag3.equals(currentFragment.getTag())) && !(tag1.equals(currentFragment.getTag()))) {
            fm.popBackStack();
        } else if (tag2.equals(currentFragment.getTag()) || tag3.equals(currentFragment.getTag())) {

            Fragment fragment = new HomepageScreen();
            String tag = "1";
            fm.beginTransaction().replace(R.id.nav_host_fragment, fragment, tag).commit();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Vuoi Uscire?")
                    .setMessage("Noi siamo quello che scegliamo di essere. Ora scegli!\n\n                                     (Goblin Spiderman)")
                    //Dopotutto, domani è un altro giorno
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, (arg0, arg1) -> {
                        finish();
                    }).create().show();
        }

    }

    public void onBackPressed(boolean completed) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.nav_host_fragment);
        String tag1 = "1";
        String tag2 = "2";
        String tag3 = "3";

        if (currentFragment.getTag().equals("InsertFilmReview") && (completed)) {
            fm.popBackStack();
        } else
            this.onBackPressed();
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return this.uid;
    }

    public static DatabaseReference getReference() {
        if (ref == null) {
            ref = FirebaseDatabase.getInstance().getReference("Users");
        }
        return ref;
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        Fragment fragment = null;
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        String tag = currentFragment.getTag();

        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 < x2) {
                    switch (tag) {
                        case "1":
                            fragment = new SearchFilmScreen();
                            tag = "3";
                            break;
                        case "2":
                            fragment = new HomepageScreen();
                            tag = "1";
                            break;
                        case "3":
                            break;

                    }
                } else if (x2 < x1) {
                    switch (tag) {
                        case "1":
                            fragment = new PersonalArea();
                            tag = "2";
                            break;
                        case "2":
                            break;
                        case "3":
                            fragment = new HomepageScreen();
                            tag = "1";
                            break;
                    }
                }
                return loadFragment(fragment, tag, true);

        }

        return false;
    }

    @Override
    @OnUi
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.progressBar.isShown()) {
            return false;
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    @OnUi
    public void triggerProgessBar() {
        mainLayout.setAlpha(0.1f);
        progressLayout.setVisibility(View.VISIBLE);
        this.progressBar.setVisibility(View.VISIBLE);
        this.progressBar.setIndeterminateMode(true);
        this.progressBar.animate();
    }

    @OnUi
    public void stopProgressBar() {
        progressLayout.setVisibility(View.INVISIBLE);
        mainLayout.setAlpha(1f);
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    public CircularProgressBar getProgressBar() {
        return this.progressBar;
    }

    public Fragment getActiveFragment() {
        return this.activeFragment;
    }

    public void setActiveFragment(Fragment fragment) {
        this.activeFragment = fragment;
    }

}

//account@gmail.com