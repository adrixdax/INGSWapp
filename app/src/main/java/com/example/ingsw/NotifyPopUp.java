package com.example.ingsw;

import android.annotation.SuppressLint;
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

import com.example.ingsw.component.db.adapters.NotifyAdapter;
import com.example.ingsw.component.db.classes.Notify;
import com.example.ingsw.controllers.NotifyUpdater;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import teaspoon.annotations.OnUi;

public class NotifyPopUp extends AppCompatDialogFragment {

    private static ArrayList<Notify> notify = new ArrayList<>();
    private static TextView notifyTextError;
    private RecyclerView recycler;

    public NotifyPopUp(ArrayList<Notify> list) {
        notify = new ArrayList<>(list);
    }

    public NotifyPopUp() {
        new NotifyPopUp(null);
    }

    @OnUi
    private static void update() {
        NotifyUpdater.newUpdate();
        notifyTextError.setText("Nessuna nuova notifica");
    }

    public static void noMoreNotify() {
        if ((notifyTextError != null) && (notify.isEmpty())) {
            update();
        }
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
        } else {
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
        if (recycler.getAdapter() != null)
            ((NotifyAdapter) Objects.requireNonNull(recycler.getAdapter())).changeStatus();
        super.onCancel(dialog);
    }

}

