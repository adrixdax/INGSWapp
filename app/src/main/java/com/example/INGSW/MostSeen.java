package com.example.INGSW;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.Utility.UpdateRecyclers;

import java.util.ArrayList;
import java.util.List;

import teaspoon.annotations.OnUi;

public class MostSeen extends Fragment implements UpdateRecyclers {
    TextView title;
    RecyclerView mostSeenFilm;
    List<Film> mostSeenFilms = new ArrayList<>();
    FilmTestController con;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        con = new FilmTestController(mostSeenFilms, getActivity());
        View root = inflater.inflate(R.layout.most_seen_homepage, container, false);

        title = root.findViewById(R.id.textViewMostSeen);
        mostSeenFilm = root.findViewById(R.id.recyclerViewMostSeen);
        con.execute("mostViewed");
        //mostSeenFilms = (List<Film>) JSONDecoder.getJsonToDecode(String.valueOf(new FilmTestController(new ArrayList(),getActivity()).execute("mostViewed").get()), Film.class);

        return root;
    }

    @Override
    @OnUi
    public void updateRecyclerView() {
        if (this.mostSeenFilms.size() != 0) {
            ((ToolBarActivity) getActivity()).triggerProgessBar();
            ListOfFilmAdapter adapter = new ListOfFilmAdapter(mostSeenFilms, getContext(), this);
            adapter.setCss(MostSeen.class);
            mostSeenFilm.setHasFixedSize(false);
            mostSeenFilm.setItemViewCacheSize(mostSeenFilms.size());
            LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            mostSeenFilm.setLayoutManager(layoutManager);
            mostSeenFilm.setAdapter(adapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mostSeenFilm.getContext(), layoutManager.getOrientation());
            mostSeenFilm.addItemDecoration(dividerItemDecoration);
            mostSeenFilm.setVisibility(View.VISIBLE);
            ((ToolBarActivity) getActivity()).stopProgressBar();
        }
    }

    public void setList(List<Film> list) {
        this.mostSeenFilms = list;
    }
}
