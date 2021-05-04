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

import java.util.List;

@SuppressWarnings("ALL")
public class MostReviewed extends Fragment implements RetrofitListInterface {
    TextView title;
    RecyclerView mostReviewedFilm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.most_reviewd_homepage, container, false);
        title = root.findViewById(R.id.textViewMostReviewed);
        mostReviewedFilm = root.findViewById(R.id.recyclerViewMostReviewed);
        ((ToolBarActivity) requireActivity()).triggerProgessBar();
        RetrofitResponse.getResponse(
                "Type=PostRequest&mostreviewed=true",
                this,this.getContext(),"getFilm");

        return root;
    }

    @Override
    public void setList(List<?> newList) {
        ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) newList,getContext(),this);
        adapter.setCss(MostReviewed.class);
        mostReviewedFilm.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mostReviewedFilm.setItemViewCacheSize(newList.size());
        mostReviewedFilm.setLayoutManager(layoutManager);
        mostReviewedFilm.setAdapter(adapter);
        mostReviewedFilm.setVisibility(View.VISIBLE);
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}
