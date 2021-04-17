package com.example.INGSW.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.NotifyUpdater;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.MostReviewed;
import com.example.INGSW.MostSeen;
import com.example.INGSW.NotifyPopUp;
import com.example.INGSW.R;
import com.example.INGSW.ToSee;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.UserPrefered;
import com.google.android.material.imageview.ShapeableImageView;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import teaspoon.annotations.OnBackground;
import teaspoon.annotations.OnUi;

public class HomepageScreen extends Fragment implements RetrofitListInterface {
    Timer timer = new Timer();
    List<Film> film;
    ShapeableImageView mostSeen, tooSee, mostReviewed, userPrefered;
    ImageButton bell;
    private boolean exist = false;
    RecyclerView recyclerView;

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


        film = ((ToolBarActivity) requireActivity()).getConteinerList().get("HomepageList") ;

        if (((ToolBarActivity) requireActivity()).getConteinerList().get("HomepageList") == null) {
            ((ToolBarActivity)requireActivity()).triggerProgessBar();
            film = new ArrayList<>();
            RetrofitResponse.getResponse("Type=PostRequest&latest=true",this,this.getContext(),"getFilm");
        }else {
            exist = true;
            setList(film);
        }
        bell.setOnClickListener(v -> {
            new NotifyPopUp(not.getNotify(), requireActivity()).show(requireActivity().getSupportFragmentManager(), "not");
        });
        return root;
    }


    @Override
    @OnBackground
    public void setList(List<?> list) {
        if(!exist) {
            ((ToolBarActivity) requireActivity()).getConteinerList().put("HomepageList", (List<Film>) list);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) list, getContext(), this);
        adapter.setCss(HomepageScreen.class);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(list.size());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}