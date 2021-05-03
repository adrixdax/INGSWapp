package com.example.INGSW;

import android.annotation.SuppressLint;
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
import com.example.INGSW.Controllers.NotifyUpdater;
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.Controllers.UserController;
import com.example.INGSW.home.HomepageScreen;
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
import java.util.Objects;

import teaspoon.annotations.OnUi;

public class ToolBarActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, RetrofitListInterface {

    Fragment activeFragment;
    CircularProgressBar progressBar;
    ConstraintLayout mainLayout;
    ConstraintLayout progressLayout;

    float x1, x2, y1, y2;

    @Override
    protected void onPause() {
        super.onPause();
        NotifyUpdater.stopUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotifyUpdater.newUpdate();
    }

    Map<String, List<Film>> conteinerList = new HashMap<>();
    private final Map<String, Object> contaiinerItem = new HashMap<>();
    private boolean loadUser = false;
    UserController userController = new UserController();
    FirebaseAnalytics mFirebaseAnalytics;
    private static DatabaseReference ref;
    private String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            ref = db.getReference("Users");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.navigationscreen);
        getUser();
        mFirebaseAnalytics.setUserId(getUid());

        progressBar = findViewById(R.id.activityProgressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressLayout = findViewById(R.id.layoutProgress);
        progressLayout.setVisibility(View.INVISIBLE);
        mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setVisibility(View.VISIBLE);

        RetrofitResponse.getResponse("Type=PostRequest&idUser=" + uid + "&searchDefaultList=true", this, this.getApplicationContext(), "getDefaultList");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        loadFragment(new HomepageScreen(), "1", false);

        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(this);


    }

    private boolean loadFragment(Fragment fragment, String tag, boolean swipe) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.nav_host_fragment);
        if (fragment != null) {
            assert currentFragment != null;
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
                if (activeFragment == null){
                    ((BottomNavigationView)findViewById(R.id.nav_view)).getMenu().getItem(1).setChecked(true);
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, tag)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
            activeFragment = fragment;
            return true;
        }
        return false;
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected (MenuItem item, boolean swipe){
        Fragment fragment = null;
        String tag = "";
        switch (item.getItemId()) {
            case R.id.navigation_home:
                item.setChecked(item.getItemId() == R.id.navigation_home);
                fragment = new HomepageScreen();
                tag = "1";
                break;
            case R.id.navigation_personal_area:
                item.setChecked(item.getItemId() == R.id.navigation_personal_area);
                fragment = new PersonalArea();
                tag = "2";
                break;
            case R.id.navigation_search:
                item.setChecked(item.getItemId() == R.id.navigation_search);
                fragment = new SearchFilmScreen();
                tag = "3";
                break;
        }
        return loadFragment(fragment, tag, swipe);
    }



    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return onNavigationItemSelected(item,false);
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
        stopProgressBar();

        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.nav_host_fragment);
        String tag1 = "1";
        String tag2 = "2";
        String tag3 = "3";

        assert currentFragment != null;
        assert currentFragment.getTag() != null;
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

        assert currentFragment != null;
        assert currentFragment.getTag() != null;
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
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert currentFragment != null;
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
                    switch (Objects.requireNonNull(tag)) {
                        case "1":
                            return onNavigationItemSelected(((BottomNavigationView)findViewById(R.id.nav_view)).getMenu().getItem(0),true);
                        case "2":
                            return onNavigationItemSelected(((BottomNavigationView)findViewById(R.id.nav_view)).getMenu().getItem(1),true);
                        case "3":
                            break;

                    }
                } else if (x2 < x1) {
                    switch (Objects.requireNonNull(tag)) {
                        case "1":
                            return onNavigationItemSelected(((BottomNavigationView)findViewById(R.id.nav_view)).getMenu().getItem(2),true);
                        case "2":
                            break;
                        case "3":
                            return onNavigationItemSelected(((BottomNavigationView)findViewById(R.id.nav_view)).getMenu().getItem(1),true);
                    }
                }
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


    @Override
    public void setList(List<?> newList) {
        for (Object singlelist : newList) {
            if (((UserLists) singlelist).getType().equals("PREFERED")) {
                contaiinerItem.put("PREFERED", ((UserLists) singlelist).getIdUserList());
            } else if (((UserLists) singlelist).getType().equals("WATCH")) {
                contaiinerItem.put("WATCH", ((UserLists) singlelist).getIdUserList());
            } else {
                contaiinerItem.put("TOWATCH", ((UserLists) singlelist).getIdUserList());
            }
        }
    }
}

//account@gmail.com