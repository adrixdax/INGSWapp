package com.example.INGSW;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Adapters.NotifyAdapter;
import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Controllers.NotifyTestController;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class NotifyPopUp extends AppCompatDialogFragment {

    private RecyclerView recycler;
    private List<Notify> notify = new ArrayList<>();

    public NotifyPopUp() {
    }

    public static NotifyPopUp newInstance() {
        NotifyPopUp frag = new NotifyPopUp();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.notifypopup, new ConstraintLayout(getActivity()), false);
        recycler = view.findViewById(R.id.recyclerViewNotify);
        String json = "";
        try {
            json = (String) new NotifyTestController().execute(((ToolBarActivity) getActivity()).getUid()).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            notify = (List<Notify>) getJsonToDecode(json, Notify.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        recycler.setAdapter(new NotifyAdapter(notify));
        recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(false);
        return view;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notifypopup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(getActivity().getLayoutInflater().inflate(R.layout.notifypopup, new ConstraintLayout(getActivity()), false));
        return dialog;

    }


}