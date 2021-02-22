package com.example.INGSW.home;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.AvatarScreen;
import com.example.INGSW.Component.ListOfFilm;
import com.example.INGSW.Component.ListOfFilmAdapter;
import com.example.INGSW.Controllers.FilmTestController;

import com.example.INGSW.R;
import com.example.INGSW.SearchFilmScreen;
import com.example.INGSW.ToolBarActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class HomepageScreen extends Fragment implements View.OnClickListener {


    ImageView imageView;
    TextView name,email,id;
    GoogleSignInAccount mGoogleSIgn;
    Button mostSeen,mostReviewed,tooSee,userPrefered;
    FilmTestController con = new FilmTestController();

    private List<ListOfFilm> film = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.homepagescreen, container, false);


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
                film = (List<ListOfFilm>) getJsonToDecode(latestJson);
                ((ToolBarActivity)getActivity()).setListOfFilm(film);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        ListOfFilmAdapter adapter = new ListOfFilmAdapter(film);
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