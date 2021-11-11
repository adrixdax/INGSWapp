package com.example.ingsw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.component.films.Film;
import com.example.ingsw.component.films.ListOfFilmAdapter;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import teaspoon.annotations.OnUi;

@SuppressWarnings("ALL")
public class ToSee extends Fragment implements RetrofitListInterface {

    private RecyclerView toSeeFilms;
    private TextView textToSeeError;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.to_see_homepage, container, false);

        toSeeFilms = root.findViewById(R.id.recyclerViewToSee);
        textToSeeError = root.findViewById(R.id.textToSeeError);
        ((ToolBarActivity) requireActivity()).triggerProgessBar();
        RetrofitResponse.getResponse(
                "Type=PostRequest&idList=" + ((ToolBarActivity) requireActivity()).getContaiinerItem().get("TOWATCH"),
                this, this.getContext(), "getFilmInList");
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME,"ToSee");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,this.getClass().getSimpleName());
        ToolBarActivity.setNewBundle("ToSee",bundle);
        return root;
    }


    @Override
    @OnUi
    public void setList(List<?> newList) {
        if (newList.size() != 0) {
            textToSeeError.setText("");
            ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) newList, getContext(), this);
            adapter.setCss(ToSee.class);
            System.out.println(((ToolBarActivity) requireActivity()).getContaiinerItem().get("TOWATCH"));
            adapter.setIdList(String.valueOf(((ToolBarActivity) requireActivity()).getContaiinerItem().get("TOWATCH")));
            toSeeFilms.setHasFixedSize(false);
            LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            toSeeFilms.setLayoutManager(layoutManager);
            toSeeFilms.setAdapter(adapter);
            toSeeFilms.setItemViewCacheSize(newList.size());
            toSeeFilms.setVisibility(View.VISIBLE);
        } else {
            textToSeeError.setText("Non ci sono film da vedere");
        }
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}