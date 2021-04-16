package com.example.INGSW;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Adapters.NotifyAdapter;
import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Component.DB.Classes.Reviews;
import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Controllers.NotifyUpdater;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import teaspoon.annotations.OnUi;

public class NotifyPopUp extends AppCompatDialogFragment {

    private RecyclerView recycler;
    private static ArrayList<Notify> notify = new ArrayList<>();
    private static TextView notifyTextError;

    public NotifyPopUp(List<?> list, Activity act){
        notify = new ArrayList<>((Collection<? extends Notify>) list);
    }

    public NotifyPopUp(ArrayList<Notify> list) {
        new NotifyPopUp(list, null);
    }

    public NotifyPopUp() {
        new NotifyPopUp(null);
    }

    @SuppressLint("SetTextI18n")
    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        NotifyUpdater.newUpdate();
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notifypopup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        recycler = dialog.findViewById(R.id.recyclerViewNotify);
        notifyTextError = dialog.findViewById(R.id.notifyTextError);
        if (notify.isEmpty()) {
            notifyTextError.setText("Nessuna nuova notifica");
        }else {
            NotifyAdapter adapter = new NotifyAdapter(notify, ToolBarActivity.getReference(), this.getContext(), this);
            recycler.setAdapter(adapter);
            recycler.setItemViewCacheSize(notify.size());
            recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recycler.setHasFixedSize(false);
        }
        return dialog;

    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        if (recycler.getAdapter() != null) ((NotifyAdapter) Objects.requireNonNull(recycler.getAdapter())).changeStatus();
        super.onCancel(dialog);
    }

    @OnUi
    private static void update() {
        notifyTextError.setText("Nessuna nuova notifica");
    }

    public static void noMoreNotify() {
        if ((notifyTextError != null) && (notify.size() == 0)) {
            update();
        }
    }

}

