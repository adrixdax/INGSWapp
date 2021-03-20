package com.example.INGSW;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.Utility.JSONDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SeenFilms extends Fragment {

    private List<Film> listofFilm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_seen_films, container, false);
        FilmTestController FTC = new FilmTestController();
        FTC.setIdList(String.valueOf(((ToolBarActivity)getContext()).getContaiinerItem().get("WATCH")));
        try {
            listofFilm = (List<Film>) JSONDecoder.getJsonToDecode((String) FTC.execute("filmInList").get(),Film.class);
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        ListOfFilmAdapter adapter = new ListOfFilmAdapter(listofFilm, getContext(), this);
        adapter.setCss(SuggestedFIlms.class);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewUserSeen);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        recyclerView.setAdapter(adapter);

        return root;
    }
}