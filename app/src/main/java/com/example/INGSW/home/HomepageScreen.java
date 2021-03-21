package com.example.INGSW.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.Controllers.NotifyTestController;
import com.example.INGSW.Controllers.NotifyUpdater;
import com.example.INGSW.MostSeen;
import com.example.INGSW.NotifyPopUp;
import com.example.INGSW.R;
import com.example.INGSW.ToSee;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.UserPrefered;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class HomepageScreen extends Fragment {

    Timer timer = new Timer();

    Button mostSeen, mostReviewed, tooSee, userPrefered;
    static ImageButton bell;
    FilmTestController con = new FilmTestController();

    private List<Film> film = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //NotifyUpdater not = new NotifyUpdater(5);
        View root = inflater.inflate(R.layout.homepagescreen, container, false);

        mostSeen = root.findViewById(R.id.mostSeen);
        mostReviewed = root.findViewById(R.id.mostReviewed);
        tooSee = root.findViewById(R.id.toSee);
        userPrefered = root.findViewById(R.id.userPrefered);

        PushDownAnim.setPushDownAnimTo(mostSeen, mostReviewed, tooSee, userPrefered)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
        mostSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostSeen nextFragment = new MostSeen();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "6");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        tooSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToSee nextFragment = new ToSee();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "7");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        userPrefered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPrefered nextFragment = new UserPrefered();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "8");
                transaction.addToBackStack(null);
                transaction.commit();
            }
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

            System.out.println(latestJson);


            try {
                if (!latestJson.isEmpty()) {
                    film = (List<Film>) getJsonToDecode(latestJson, Film.class);
                    ((ToolBarActivity) getActivity()).getConteinerList().put("HomepageList", film);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bell = root.findViewById(R.id.notifyBell);
        NotifyUpdater not = new NotifyUpdater(timer,bell,getActivity());
        timer.schedule(not,1);
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Click on bell");
                System.out.println(((ToolBarActivity) getActivity()).getUid());
                new NotifyPopUp(not.getNotify()).show(getActivity().getSupportFragmentManager(), "4");
            }
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