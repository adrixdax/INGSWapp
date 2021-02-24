package com.example.INGSW;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.INGSW.Component.DB.Adapters.NotifyAdapter;
import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Component.Films.ListOfFilm;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.NotifyTestController;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class NotifyPopUp extends Fragment {

    private RecyclerView recycler;
    private List<Notify> notify=new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.notifypopup, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler = new RecyclerView(requireContext());
        recycler = root.findViewById(R.id.recyclerViewNotify);
        List<Notify> notify = new ArrayList<>();
            String latestJson = "";
            try {
                latestJson = (String) new NotifyTestController().execute(new String("1")).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        try {
            notify= (List<Notify>) getJsonToDecode(latestJson,Notify.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        NotifyAdapter adapter = new NotifyAdapter(notify);
        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(false);
        recycler.setLayoutManager(layoutManager);
        return root;
    }
}