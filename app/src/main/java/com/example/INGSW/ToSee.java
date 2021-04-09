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
import com.example.INGSW.Utility.JSONDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ToSee extends Fragment {

    private TextView title;
    private RecyclerView toSeeFilms;
    private FilmTestController con = new FilmTestController();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.to_see_homepage, container, false);

        title = root.findViewById(R.id.textViewTooSee);
        toSeeFilms = root.findViewById(R.id.recyclerViewToSee);

        List<Film> toSee = new ArrayList<>();
        try {
            con = new FilmTestController();
            con.setIdList(String.valueOf(((ToolBarActivity) getActivity()).getContaiinerItem().get("TOWATCH")));
            toSee = (List<Film>) JSONDecoder.getJsonToDecode(String.valueOf(con.execute("filmInList").get()), Film.class);
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        ListOfFilmAdapter adapter = new ListOfFilmAdapter(toSee, getContext(), this);
        adapter.setCss(ToSee.class);
        adapter.setIdList(con.getIdList());
        toSeeFilms.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new GridLayoutManager(root.getContext(), 2);
        toSeeFilms.setLayoutManager(layoutManager);
        toSeeFilms.setAdapter(adapter);
        toSeeFilms.setItemViewCacheSize(toSee.size());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(toSeeFilms.getContext(),
                layoutManager.getOrientation());
        toSeeFilms.addItemDecoration(dividerItemDecoration);
        toSeeFilms.setVisibility(View.VISIBLE);
        return root;
    }


}