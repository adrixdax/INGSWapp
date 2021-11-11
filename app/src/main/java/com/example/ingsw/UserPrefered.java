package com.example.ingsw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class UserPrefered extends Fragment implements RetrofitListInterface {

    private RecyclerView userPreferedFilms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.users_prefered, container, false);
        userPreferedFilms = root.findViewById(R.id.recyclerViewUserPrefered);

        ((ToolBarActivity) requireActivity()).triggerProgessBar();
        RetrofitResponse.getResponse(
                "Type=PostRequest&userPrefered=true",
                this, this.getContext(), "getFilm");
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME,"UserPrefered");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,this.getClass().getSimpleName());
        ToolBarActivity.setNewBundle("UserPrefered",bundle);
        return root;
    }


    @Override
    public void setList(List<?> newList) {
        ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) newList, getContext(), this);
        adapter.setCss(UserPrefered.class);
        userPreferedFilms.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        userPreferedFilms.setLayoutManager(layoutManager);
        userPreferedFilms.setAdapter(adapter);
        userPreferedFilms.setItemViewCacheSize(newList.size());
        userPreferedFilms.setVisibility(View.VISIBLE);
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}
