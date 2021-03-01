package com.example.INGSW;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notifypopup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayoutManager layoutManager = new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycler = new RecyclerView(requireContext());
        recycler = dialog.findViewById(R.id.recyclerViewNotify);
        List<Notify> notify = new ArrayList<>();
        Notify not = new Notify("Prova");
       notify.add(not);
        NotifyAdapter adapter = new NotifyAdapter(notify);
        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(false);
        recycler.setLayoutManager(layoutManager);

        return dialog;
    }

}