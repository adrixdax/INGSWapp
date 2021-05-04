package com.example.ingsw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.component.films.Film;
import com.example.ingsw.component.films.ListOfFilmAdapter;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;

import java.util.List;


@SuppressWarnings("ALL")
public class MyFavs extends Fragment implements RetrofitListInterface {

    RecyclerView recyclerView;
    TextView textFavsError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_favs, container, false);
        ((ToolBarActivity) requireActivity()).triggerProgessBar();
        recyclerView = root.findViewById(R.id.recyclerViewUserMyPrefered);
        textFavsError = root.findViewById(R.id.Textview_favsError);
        RetrofitResponse.getResponse("Type=PostRequest&idList=" + ((ToolBarActivity) requireContext()).getContaiinerItem().get("PREFERED"), MyFavs.this, this.getContext(), "getFilmInList");
        return root;
    }

    @Override
    public void setList(List<?> newList) {
        if (newList.size() != 0) {
            textFavsError.setText("");
            ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) newList, getContext(), this);
            adapter.setCss(MyFavs.class);
            adapter.setIdList(String.valueOf(((ToolBarActivity) requireContext()).getContaiinerItem().get("PREFERED")));
            recyclerView.setHasFixedSize(false);
            recyclerView.setItemViewCacheSize(newList.size());
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setAdapter(adapter);
        } else textFavsError.setText("Davvero non hai neanche un film preferito?");
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}