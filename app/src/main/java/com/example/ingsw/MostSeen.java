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

@SuppressWarnings("ALL")
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
                this, this.getContext(), "getFilm");
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME,"MostSeen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,this.getClass().getSimpleName());
        ToolBarActivity.setNewBundle("MostSeen",bundle);
        return root;
    }

    @Override
    public void setList(List<?> newList) {
        ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) newList, getContext(), this);
        adapter.setCss(MostSeen.class);
        mostSeenFilm.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mostSeenFilm.setLayoutManager(layoutManager);
        mostSeenFilm.setItemViewCacheSize(newList.size());
        mostSeenFilm.setAdapter(adapter);
        mostSeenFilm.setVisibility(View.VISIBLE);
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}
