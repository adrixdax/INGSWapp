package com.example.ingsw.home;

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

import com.example.ingsw.MostReviewed;
import com.example.ingsw.MostSeen;
import com.example.ingsw.NotifyPopUp;
import com.example.ingsw.R;
import com.example.ingsw.ToSee;
import com.example.ingsw.ToolBarActivity;
import com.example.ingsw.UserPrefered;
import com.example.ingsw.component.db.classes.Notify;
import com.example.ingsw.component.films.Film;
import com.example.ingsw.component.films.ListOfFilmAdapter;
import com.example.ingsw.controllers.NotifyUpdater;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.makeramen.roundedimageview.RoundedImageView;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import teaspoon.annotations.OnBackground;

@SuppressWarnings("ALL")
public class HomepageScreen extends Fragment implements RetrofitListInterface {
    final Timer timer = new Timer();
    List<Film> film;
    RoundedImageView mostSeen, tooSee, mostReviewed, userPrefered;
    ImageButton bell;
    RecyclerView recyclerView;
    private boolean exist = false;

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
        recyclerView = root.findViewById(R.id.recyclerView);
        PushDownAnim.setPushDownAnimTo(mostSeen, mostReviewed, tooSee, userPrefered)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);


        mostReviewed.setOnClickListener(v -> {
            MostReviewed nextFragment = new MostReviewed();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, nextFragment, "MostReviewed");
            transaction.addToBackStack(null);
            transaction.commit();
        });

        mostSeen.setOnClickListener(v -> {
            MostSeen nextFragment = new MostSeen();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, nextFragment, "MostSeen");
            transaction.addToBackStack(null);
            transaction.commit();
        });

        tooSee.setOnClickListener(v -> {
            ToSee nextFragment = new ToSee();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, nextFragment, "7");
            transaction.addToBackStack(null);
            transaction.commit();
        });

        userPrefered.setOnClickListener(v -> {
            UserPrefered nextFragment = new UserPrefered();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, nextFragment, "8");
            transaction.addToBackStack(null);
            transaction.commit();
        });


        film = ((ToolBarActivity) requireActivity()).getConteinerList().get("HomepageList");

        if (((ToolBarActivity) requireActivity()).getConteinerList().get("HomepageList") == null) {
            ((ToolBarActivity) requireActivity()).triggerProgessBar();
            film = new ArrayList<>();
            RetrofitResponse.getResponse("Type=PostRequest&latest=true", this, this.getContext(), "getFilm");
        } else {
            exist = true;
            setList(film);
        }
        bell.setOnClickListener(v -> new NotifyPopUp((ArrayList<Notify>) not.getNotify()).show(requireActivity().getSupportFragmentManager(), "not"));
        return root;
    }


    @Override
    @OnBackground
    public void setList(List<?> list) {
        if (!exist) {
            ((ToolBarActivity) requireActivity()).getConteinerList().put("HomepageList", (List<Film>) list);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) list, getContext(), this);
        adapter.setCss(HomepageScreen.class);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(list.size());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}