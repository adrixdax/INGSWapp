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
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.Utility.JSONDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.concurrent.ExecutionException;

import teaspoon.annotations.OnUi;

public class SeenFilms extends Fragment implements RetrofitListInterface {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_seen_films, container, false);
        recyclerView = root.findViewById(R.id.recyclerViewUserSeen);

        RetrofitResponse.getResponse("Type=PostRequest&idList="+((ToolBarActivity)getContext()).getContaiinerItem().get("WATCH"),this,((ToolBarActivity)getContext()),Film.class.getCanonicalName(),"getFilmInList");
        return root;
    }

    @Override
    @OnUi
    public void setList(List<?> newList) {
        ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) newList, getContext(), this);
        adapter.setCss(SeenFilms.class);
        adapter.setIdList(String.valueOf(((ToolBarActivity)getContext()).getContaiinerItem().get("WATCH")));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(newList.size());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
    }
}