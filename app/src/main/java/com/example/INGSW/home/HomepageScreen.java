package com.example.INGSW.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.Controllers.NotifyUpdater;
import com.example.INGSW.MostReviewed;
import com.example.INGSW.MostSeen;
import com.example.INGSW.NotifyPopUp;
import com.example.INGSW.PersonalArea;
import com.example.INGSW.R;
import com.example.INGSW.SearchFilmScreen;
import com.example.INGSW.ToSee;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.UserPrefered;
import com.google.android.material.imageview.ShapeableImageView;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class HomepageScreen extends Fragment {

    Timer timer = new Timer();

    float x1,x2,y1,y2;

    ShapeableImageView mostSeen, tooSee, mostReviewed, userPrefered;
    static ImageButton bell;
    FilmTestController con = new FilmTestController();

    private List<Film> film = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.homepagescreen, container, false);

        mostSeen = root.findViewById(R.id.mostSeen);
        mostReviewed = root.findViewById(R.id.mostReviewed);
        tooSee = root.findViewById(R.id.toSee);
        userPrefered = root.findViewById(R.id.userPrefered);
        mostSeen.setAlpha(0.8f);
        tooSee.setAlpha(0.8f);
        mostReviewed.setAlpha(0.8f);
        userPrefered.setAlpha(0.8f);
        bell = root.findViewById(R.id.notifyBell);
        NotifyUpdater not = new NotifyUpdater(timer, bell, getActivity());
        timer.schedule(not, 1);

        PushDownAnim.setPushDownAnimTo(mostSeen, mostReviewed, tooSee, userPrefered)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);


        mostReviewed.setOnClickListener(v -> {
            MostReviewed nextFragment = new MostReviewed();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, nextFragment, "MostReviewed");
            transaction.addToBackStack(null);
            transaction.commit();
        });

        mostSeen.setOnClickListener(v -> {
            MostSeen nextFragment = new MostSeen();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, nextFragment, "MostSeen");
            transaction.addToBackStack(null);
            transaction.commit();
        });

        tooSee.setOnClickListener(v -> {
            ToSee nextFragment = new ToSee();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, nextFragment, "7");
            transaction.addToBackStack(null);
            transaction.commit();
        });

        userPrefered.setOnClickListener(v -> {
            UserPrefered nextFragment = new UserPrefered();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, nextFragment, "8");
            transaction.addToBackStack(null);
            transaction.commit();
        });


        film = ((ToolBarActivity) getActivity()).getConteinerList().get("HomepageList");

        if (film == null) {
            String latestJson = "";
            try {
                latestJson = (String) con.execute("latest").get();
                con.isCancelled();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            try {
                if (!latestJson.isEmpty()) {
                    film = (List<Film>) getJsonToDecode(latestJson, Film.class);
                    ((ToolBarActivity) getActivity()).getConteinerList().put("HomepageList", film);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bell.setOnClickListener(v -> {
            new NotifyPopUp(not.getNotify(),getActivity()).show(getActivity().getSupportFragmentManager(), "4");
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        ListOfFilmAdapter adapter = new ListOfFilmAdapter(film, getContext(), this);
        adapter.setCss(HomepageScreen.class);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return root;
    }



}