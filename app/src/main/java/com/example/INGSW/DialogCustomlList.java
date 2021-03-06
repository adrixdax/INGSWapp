package com.example.INGSW;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Adapters.NotifyAdapter;
import com.example.INGSW.Component.DB.Adapters.UserListsAdapter;
import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Controllers.NotifyTestController;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class DialogCustomlList extends AppCompatDialogFragment {


    private RecyclerView recycler;
    private List<UserLists> customLists = new ArrayList<>();


    public static DialogCustomlList newInstance() {
        DialogCustomlList dialogCustomlList = new DialogCustomlList();
        Bundle args = new Bundle();
        dialogCustomlList.setArguments(args);
        return dialogCustomlList;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = getActivity().getLayoutInflater().inflate(R.layout.custom_list_dialog, new ConstraintLayout(getActivity()), false);
        View view = inflater.inflate(R.layout.custom_list_dialog, container, false);

        //recycler = view.findViewById(R.id.recyclerViewNotify);
        /*String json = "";
        try {
            json = (String) new NotifyTestController().execute(((ToolBarActivity) getActivity()).getUid()).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            customLists = (List<UserLists>) getJsonToDecode(json, Notify.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/
       // recycler.setAdapter(new UserListsAdapter(customLists, this.getClass()));
        //recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        //recycler.setHasFixedSize(false);
        view.setBackgroundColor(Color.TRANSPARENT);
        return view;
    }

}
