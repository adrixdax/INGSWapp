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
import com.example.INGSW.Controllers.NotifyUpdater;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class NotifyPopUp extends AppCompatDialogFragment {

    private RecyclerView recycler;
    private static ArrayList<Notify> notify = new ArrayList<>();
    private static TextView notifyTextError;
    private static Activity activity;

    public NotifyPopUp(ArrayList<Notify> list, Activity act){
        notify = list;
        activity = act;
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
        NotifyAdapter adapter = new NotifyAdapter(notify, ToolBarActivity.getReference(), this.getContext());
        notifyTextError = dialog.findViewById(R.id.notifyTextError);
        if (adapter.getItemCount() == 0) {
            notifyTextError.setText("Nessuna nuova notifica");
        }
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(false);
        return dialog;

    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        ((NotifyAdapter) Objects.requireNonNull(recycler.getAdapter())).changeStatus();
        super.onCancel(dialog);
    }

    private static void update() {
        activity.runOnUiThread(() -> notifyTextError.setText("Nessuna nuova notifica"));
    }

    public static void noMoreNotify() {
        if ((notifyTextError != null) && (notify.size() == 0)) {
            update();
        }
    }

    public void closeDialog() {
        this.dismiss();
    }
}

