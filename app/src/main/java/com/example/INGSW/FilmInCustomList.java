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
import com.example.INGSW.Controllers.UserServerController;
import com.example.INGSW.Utility.JSONDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FilmInCustomList extends Fragment {

    private TextView title;
    private TextView description;
    private RecyclerView filmInCustomList;
    private UserLists list;

    public FilmInCustomList(UserLists idList) {
        this.list = idList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.film_in_list_custom, container, false);

        title = root.findViewById(R.id.textViewTooSee);
        filmInCustomList = root.findViewById(R.id.recyclerViewFilmInListCustom);
        description = root.findViewById(R.id.listDescription);
        description.setText(list.getDescription());
        title.setText(list.getTitle());

        List<Film> CustomListFilm = new ArrayList<>();
        try {
            FilmTestController con = new FilmTestController();
            con.setIdList(String.valueOf(list.getIdUserList()));
            CustomListFilm = (List<Film>) JSONDecoder.getJsonToDecode(String.valueOf(con.execute("filmInList").get()), Film.class);
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        ListOfFilmAdapter adapter = new ListOfFilmAdapter(CustomListFilm, getContext(), this);
        adapter.setCss(FilmInCustomList.class);
        adapter.setIdList(String.valueOf(list.getIdUserList()));
        filmInCustomList.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new GridLayoutManager(root.getContext(), 2);
        filmInCustomList.setLayoutManager(layoutManager);
        filmInCustomList.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(filmInCustomList.getContext(),
                layoutManager.getOrientation());
        filmInCustomList.addItemDecoration(dividerItemDecoration);
        filmInCustomList.setVisibility(View.VISIBLE);
        return root;
    }


}
