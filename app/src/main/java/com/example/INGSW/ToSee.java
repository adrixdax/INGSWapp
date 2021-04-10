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
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.Utility.JSONDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ToSee extends Fragment implements RetrofitListInterface {

    private TextView title;
    private RecyclerView toSeeFilms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.to_see_homepage, container, false);

        title = root.findViewById(R.id.textViewTooSee);
        toSeeFilms = root.findViewById(R.id.recyclerViewToSee);
        ((ToolBarActivity)getActivity()).triggerProgessBar();
            RetrofitResponse.getResponse(
                    "Type=PostRequest&idList=" + String.valueOf(((ToolBarActivity) getActivity()).getContaiinerItem().get("TOWATCH")),
                    this,this.getContext(),Film.class.getCanonicalName(),"getList");

        return root;
    }


    @Override
    public void setList(List<?> newList) {
        ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) newList, getContext(), this);
        adapter.setCss(ToSee.class);
        adapter.setIdList( String.valueOf(((ToolBarActivity) getActivity()).getContaiinerItem().get("TOWATCH")));
        toSeeFilms.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        toSeeFilms.setLayoutManager(layoutManager);
        toSeeFilms.setAdapter(adapter);
        toSeeFilms.setItemViewCacheSize(newList.size());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(toSeeFilms.getContext(),
                layoutManager.getOrientation());
        toSeeFilms.addItemDecoration(dividerItemDecoration);
        toSeeFilms.setVisibility(View.VISIBLE);
        ((ToolBarActivity)getActivity()).stopProgressBar();
    }
}