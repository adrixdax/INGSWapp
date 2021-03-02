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
import androidx.fragment.app.DialogFragment;
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

public class NotifyPopUpDialog extends DialogFragment {

    private RecyclerView recycler;
    private List<Notify> notify = new ArrayList<>();

    public NotifyPopUpDialog() {
    }

    public static NotifyPopUpDialog newInstance() {
        NotifyPopUpDialog frag = new NotifyPopUpDialog();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notifypopup, container);
        //return onCreateDialog(savedInstanceState).getCurrentFocus();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notifypopup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayoutManager layoutManager = new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false);
        recycler = new RecyclerView(requireContext());
        recycler = dialog.findViewById(R.id.recyclerViewNotify);
        String json="";
        try {
            json = (String) new NotifyTestController().execute(new String(((ToolBarActivity)getActivity()).getUid())).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            notify = (List<Notify>)getJsonToDecode(json,Notify.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        NotifyAdapter adapter = new NotifyAdapter(notify);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(false);

        return dialog;
    }

}