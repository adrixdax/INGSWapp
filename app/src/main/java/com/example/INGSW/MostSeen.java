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
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MostSeen extends Fragment implements RetrofitListInterface {
    TextView title;
    RecyclerView mostSeenFilm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.most_seen_homepage, container, false);
        title = root.findViewById(R.id.textViewMostSeen);
        mostSeenFilm = root.findViewById(R.id.recyclerViewMostSeen);
        ((ToolBarActivity) requireActivity()).triggerProgessBar();
        RetrofitResponse.getResponse(
                "Type=PostRequest&mostviewed=true",
                this,this.getContext(),"getFilm");

        return root;
    }

    @Override
    public void setList(List<?> newList) {
        ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) newList,getContext(),this);
        adapter.setCss(MostSeen.class);
        mostSeenFilm.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mostSeenFilm.setLayoutManager(layoutManager);
        mostSeenFilm.setItemViewCacheSize(newList.size());
        mostSeenFilm.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mostSeenFilm.getContext(),
                layoutManager.getOrientation());
        mostSeenFilm.addItemDecoration(dividerItemDecoration);
        mostSeenFilm.setVisibility(View.VISIBLE);
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}
