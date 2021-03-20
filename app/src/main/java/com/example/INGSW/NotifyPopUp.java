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

import java.util.ArrayList;
import java.util.List;

public class NotifyPopUp extends AppCompatDialogFragment {

    private RecyclerView recycler;
    private List<Notify> notify = new ArrayList<>();

    public NotifyPopUp(List<Notify> list) {
        this.notify = list;
    }

    public NotifyPopUp(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.notifypopup, new ConstraintLayout(getActivity()), false);
        recycler = view.findViewById(R.id.recyclerViewNotify);
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