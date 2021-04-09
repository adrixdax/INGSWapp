package com.example.INGSW.home;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.example.INGSW.R;
import com.example.INGSW.ToSee;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.UserPrefered;
import com.example.INGSW.Utility.UpdateRecyclers;
import com.google.android.material.imageview.ShapeableImageView;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import teaspoon.annotations.OnBackground;
import teaspoon.annotations.OnUi;

public class HomepageScreen extends Fragment implements UpdateRecyclers {

    Timer timer = new Timer();
    ShapeableImageView mostSeen, tooSee, mostReviewed, userPrefered;
    private static ImageButton bell;
    CircularProgressBar progressBar;
    List<Film> film = new ArrayList<>();
    FilmTestController con;
    RecyclerView recyclerView;

    @OnBackground
    public static ImageButton getBell() {
        return bell;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.homepagescreen, container, false);
        progressBar = root.findViewById(R.id.activityProgressBar);
        mostSeen = root.findViewById(R.id.mostSeen);
        mostReviewed = root.findViewById(R.id.mostReviewed);
        tooSee = root.findViewById(R.id.toSee);
        userPrefered = root.findViewById(R.id.userPrefered);
        mostSeen.setAlpha(0.8f);
        tooSee.setAlpha(0.8f);
        mostReviewed.setAlpha(0.8f);
        userPrefered.setAlpha(0.8f);
        bell = root.findViewById(R.id.notifyBell);
        NotifyUpdater not = new NotifyUpdater(timer, getActivity());
        timer.schedule(not, 1);

        PushDownAnim.setPushDownAnimTo(mostSeen, mostReviewed, tooSee, userPrefered)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);


        mostReviewed.setOnClickListener(new View.OnClickListener() {
            @Override
            @OnUi
            public void onClick(View v) {
                ((ToolBarActivity) getActivity()).triggerProgessBar();
                MostReviewed nextFragment = new MostReviewed();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "MostReviewed");
                transaction.addToBackStack(null);
                ((ToolBarActivity) getActivity()).stopProgressBar();
                transaction.commit();
            }
        });

        mostSeen.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            MostSeen nextFragment = new MostSeen();
            ((ToolBarActivity) getActivity()).setActiveFragment(nextFragment);
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

        if (film == null || (film.size() == 0)) {
            film = new ArrayList<>();
            con = new FilmTestController(film, getActivity());
            con.execute("latest");
        }
        bell.setOnClickListener(v -> {
            new NotifyPopUp(not.getNotify(), getActivity()).show(getActivity().getSupportFragmentManager(), "not");
        });
        recyclerView = root.findViewById(R.id.recyclerView);
        updateRecyclerView();
        return root;
    }

    @Override
    @OnUi
    public void updateRecyclerView() {
        film = ((ToolBarActivity) getActivity()).getConteinerList().get("HomepageList");
        if (film != null && film.size() != 0) {
            ((ToolBarActivity) getActivity()).triggerProgessBar();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            ListOfFilmAdapter adapter = new ListOfFilmAdapter(film, getContext(), this);
            adapter.setCss(HomepageScreen.class);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemViewCacheSize(film.size());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            ((ToolBarActivity) getActivity()).stopProgressBar();
        }
    }

}