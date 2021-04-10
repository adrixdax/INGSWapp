package com.example.INGSW;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.Utility.JSONDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class MyFavs extends Fragment implements RetrofitListInterface {

    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_favs, container, false);
        ((ToolBarActivity)getActivity()).triggerProgessBar();
        RetrofitResponse.getResponse("Type=PostRequest&idList=" +((ToolBarActivity)getContext()).getContaiinerItem().get("PREFERED"),MyFavs.this,this.getContext(), Film.class.getCanonicalName(),"getList");
        recyclerView = root.findViewById(R.id.recyclerViewUserMyPrefered);
        return root;
    }

    @Override
    public void setList(List<?> newList) {

        ListOfFilmAdapter adapter = new ListOfFilmAdapter((List<Film>) newList, getContext(), this);
        adapter.setCss(MyFavs.class);
        adapter.setIdList(String.valueOf(((ToolBarActivity)getContext()).getContaiinerItem().get("PREFERED")));

        recyclerView.setHasFixedSize(false);
        recyclerView.setItemViewCacheSize(newList.size());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        ((ToolBarActivity)getActivity()).stopProgressBar();

    }
}