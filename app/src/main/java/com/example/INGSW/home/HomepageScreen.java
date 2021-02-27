package com.example.INGSW.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.Films.ListOfFilm;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.NotifyPopUpDialog;
import com.example.INGSW.R;
import com.example.INGSW.ToolBarActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class HomepageScreen extends Fragment implements View.OnClickListener {


    ImageView imageView;
    TextView name,email,id;
    GoogleSignInAccount mGoogleSIgn;
    Button mostSeen,mostReviewed,tooSee,userPrefered;
    ImageButton bell;
    FilmTestController con = new FilmTestController();
    DialogFragment fragment;

    private List<ListOfFilm> film = new ArrayList<>();
    /*
    private boolean loadFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = fm.findFragmentById(R.id.nav_host_fragment);
        if(fragment!=null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.notifyPopUp, fragment).commit();
                activeFragment= fragment;
                return true;
            }
        return false;
    }*/

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.homepagescreen, container, false);

        mostSeen = root.findViewById(R.id.mostSeen);
        mostReviewed = root.findViewById(R.id.mostReviewed);
        tooSee = root.findViewById(R.id.toSee);
        userPrefered = root.findViewById(R.id.userPrefered);

        PushDownAnim.setPushDownAnimTo(mostSeen,mostReviewed,tooSee,userPrefered)
                .setDurationPush( PushDownAnim.DEFAULT_PUSH_DURATION )
                .setDurationRelease( PushDownAnim.DEFAULT_RELEASE_DURATION )
                .setInterpolatorPush( PushDownAnim.DEFAULT_INTERPOLATOR )
                .setInterpolatorRelease( PushDownAnim.DEFAULT_INTERPOLATOR );


        film = ((ToolBarActivity)getActivity()).getListOfFilm();

        if(film ==null) {
            String latestJson = "";
            try {
                latestJson = (String) con.execute(new String("latest")).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(latestJson);


            try {
                film = (List<ListOfFilm>) getJsonToDecode(latestJson,ListOfFilm.class);
                ((ToolBarActivity)getActivity()).setListOfFilm(film);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bell = root.findViewById(R.id.notifyBell);
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    System.out.println("Click on bell");
                FragmentManager fm = getActivity().getSupportFragmentManager();
                NotifyPopUpDialog pop= NotifyPopUpDialog.newInstance();
                pop.show(fm,"4");
                    /*
                    fragment = new NotifyPopUpDialog();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();*/
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        ListOfFilmAdapter adapter = new ListOfFilmAdapter(film,getContext(),this);
        adapter.setImageListFilm(true);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onClick(View v) {

    }
     /*
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepagescreen);
        String latestJson = "";
        try {
            latestJson = (String) con.execute(new String("latest")).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(latestJson);

        List<ListOfFilm> films = new ArrayList<>();
        try {
            films = (List<ListOfFilm>) getJsonToDecode(latestJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ListOfFilmAdapter adapter = new ListOfFilmAdapter(films);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        Button mostSeen = findViewById(R.id.mostSeen);
        Button mostReviewed = findViewById(R.id.mostReviewed);
        Button tooSee = findViewById(R.id.toSee);
        Button userPrefered = findViewById(R.id.userPrefered);

        mostSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomepageScreen.this, "Clicked most", Toast.LENGTH_SHORT).show();
                FilmTestController f = new FilmTestController();
                try {
                    System.out.println(f.execute("most").get());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

       BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search_screen:
                        startActivity(new Intent(HomepageScreen.this, SearchFilmScreen.class));
                        break;
                    case R.id.home_screen:
                        break;
                    case R.id.profile_screen:
                        startActivity(new Intent(HomepageScreen.this, AvatarScreen.class));
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
        System.out.println("Inside "+v.getId());
        switch (v.getId()){
            case R.id.toSee :
                //toSeeQuery
                break;
            case R.id.mostSeen:
                mostSeen.performClick();
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

*/
}