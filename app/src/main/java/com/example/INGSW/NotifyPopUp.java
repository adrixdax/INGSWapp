package com.example.INGSW;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Adapters.NotifyAdapter;
import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Controllers.NotifyTestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotifyPopUp extends AppCompatDialogFragment {

    private RecyclerView recycler;
    private List<Notify> notify = new ArrayList<>();

    public NotifyPopUp(List<Notify> list) {
        this.notify = list;
    }

    public NotifyPopUp(){
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notifypopup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //dialog.setContentView(getActivity().getLayoutInflater().inflate(R.layout.notifypopup, new LinearLayout(getActivity()), false));
        recycler = dialog.findViewById(R.id.recyclerViewNotify);
        System.out.println(notify.size());
        recycler.setAdapter(new NotifyAdapter(notify,((ToolBarActivity)(getActivity())).getReference()));
        recycler.setLayoutManager(new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(false);
        return dialog;

    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        ((NotifyAdapter) Objects.requireNonNull(recycler.getAdapter())).changeStatus();
        super.onCancel(dialog);
    }
}