package com.example.INGSW;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Adapters.UserListsAdapter;
import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Controllers.UserServerController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;


public class MyLists extends Fragment {

    private ImageButton addList;
    private RecyclerView recycler;
    private List<UserLists> customLists = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_lists, container, false);


        addList = (ImageButton) root.findViewById(R.id.addListButton);
        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddCustomList nextFragment = new AddCustomList();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "7");
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        recycler = root.findViewById(R.id.recyclerView2);
        String json = "";
        try {
            UserServerController usc = new UserServerController();
            usc.setUserId(((ToolBarActivity) getActivity()).getUid());
            usc.setIdFilm("-1");
            json = (String) usc.execute(new String("custom")).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            customLists = (List<UserLists>) getJsonToDecode(json, UserLists.class);
            if (customLists != null) {
                recycler.setAdapter(new UserListsAdapter(customLists, this.getClass(), this));
                recycler.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
                recycler.setHasFixedSize(false);
            } else {
                System.out.println("è vuota boh");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        //  rv.setAdapter(new UserListsAdapter(new));  //Controller to get the list

        return root;
    }


}