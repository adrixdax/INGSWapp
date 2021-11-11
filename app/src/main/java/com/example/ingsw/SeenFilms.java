package com.example.ingsw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.component.films.Film;
import com.example.ingsw.component.films.ListOfFilmAdapter;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import teaspoon.annotations.OnUi;

@SuppressWarnings("ALL")
public class SeenFilms extends Fragment implements RetrofitListInterface {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_seen_films, container, false);
        recyclerView = root.findViewById(R.id.recyclerViewUserSeen);
        ((ToolBarActivity) requireActivity()).triggerProgessBar();
        RetrofitResponse.getResponse("Type=PostRequest&idList=" + ((ToolBarActivity) requireContext()).getContaiinerItem().get("WATCH"), this, getContext(), "getFilmInList");
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME,"SeenFilms");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,this.getClass().getSimpleName());
        ToolBarActivity.setNewBundle("SeenFilms",bundle);
        return root;
    }

    @Override
    @OnUi
    public void setList(List<?> newList) {
        if (newList.size() != 0) {
            ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) newList, getContext(), this);
            adapter.setCss(SeenFilms.class);
            adapter.setIdList(String.valueOf(((ToolBarActivity) requireContext()).getContaiinerItem().get("WATCH")));
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemViewCacheSize(newList.size());
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setAdapter(adapter);
        }
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}