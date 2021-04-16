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

import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.Controllers.UserServerController;
import com.example.INGSW.Utility.JSONDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class FilmInCustomList extends Fragment implements RetrofitListInterface {

    private RecyclerView filmInCustomList;
    private final UserLists list;

    public FilmInCustomList(UserLists idList) {
        this.list = idList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.film_in_list_custom, container, false);

        TextView title = root.findViewById(R.id.textViewTooSee);
        filmInCustomList = root.findViewById(R.id.recyclerViewFilmInListCustom);
        TextView description = root.findViewById(R.id.listDescription);
        description.setText(list.getDescription());
        title.setText(list.getTitle());

        ((ToolBarActivity) requireActivity()).triggerProgessBar();
        RetrofitResponse.getResponse(
                "Type=PostRequest&idList=" + list.getIdUserList(),
                this, this.getContext(), Film.class.getCanonicalName(), "getFilmInList");

        return root;
    }


    @Override
    public void setList(List<?> newList) {
        if (newList.size() != 0) {
            ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) newList, getContext(), this);
            adapter.setCss(FilmInCustomList.class);
            adapter.setIdList(String.valueOf(list.getIdUserList()));
            filmInCustomList.setHasFixedSize(false);
            filmInCustomList.setItemViewCacheSize(newList.size());
            LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            filmInCustomList.setLayoutManager(layoutManager);
            filmInCustomList.setAdapter(adapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(filmInCustomList.getContext(),
                    layoutManager.getOrientation());
            filmInCustomList.addItemDecoration(dividerItemDecoration);
            filmInCustomList.setVisibility(View.VISIBLE);
        }
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}
