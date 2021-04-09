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

public class MostReviewed extends Fragment {
    TextView title;
    RecyclerView mostReviewedFilm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.most_reviewd_homepage, container, false);

        title = root.findViewById(R.id.textViewMostReviewed);
        mostReviewedFilm = root.findViewById(R.id.recyclerViewMostReviewed);

        List<Film> mostReviewedFilms = new ArrayList<>();
        try {
            mostReviewedFilms = (List<Film>) JSONDecoder.getJsonToDecode(String.valueOf(new FilmTestController(new ArrayList()).execute("mostReviewd").get()), Film.class);
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        ListOfFilmAdapter adapter = new ListOfFilmAdapter(mostReviewedFilms,getContext(),this);
        adapter.setCss(MostReviewed.class);
        mostReviewedFilm.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new GridLayoutManager(root.getContext(), 2);
        mostReviewedFilm.setLayoutManager(layoutManager);
        mostReviewedFilm.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mostReviewedFilm.getContext(),
                layoutManager.getOrientation());
        mostReviewedFilm.addItemDecoration(dividerItemDecoration);
        mostReviewedFilm.setVisibility(View.VISIBLE);
        return root;
    }

}
