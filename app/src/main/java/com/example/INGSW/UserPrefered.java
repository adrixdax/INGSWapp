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

public class UserPrefered extends Fragment {

    private TextView title;
    private RecyclerView userPreferedFilms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.users_prefered, container, false);
        title = root.findViewById(R.id.textViewUserPrefered);
        userPreferedFilms = root.findViewById(R.id.recyclerViewUserPrefered);
        List<Film> prefered = new ArrayList<>();
        try {
            prefered = (List<Film>) JSONDecoder.getJsonToDecode(String.valueOf(new FilmTestController(new ArrayList()).execute("userPrefered").get()), Film.class);
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        ListOfFilmAdapter adapter = new ListOfFilmAdapter(prefered, getContext(), this);
        adapter.setCss(UserPrefered.class);
        userPreferedFilms.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new GridLayoutManager(root.getContext(), 2);
        userPreferedFilms.setLayoutManager(layoutManager);
        userPreferedFilms.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(userPreferedFilms.getContext(),
                layoutManager.getOrientation());
        userPreferedFilms.addItemDecoration(dividerItemDecoration);
        userPreferedFilms.setVisibility(View.VISIBLE);
        return root;
    }


}
