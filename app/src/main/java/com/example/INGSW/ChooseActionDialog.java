package com.example.INGSW;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.INGSW.Component.DB.Adapters.NotifyAdapter;
import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Controllers.NotifyTestController;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class ChooseActionDialog extends AppCompatDialogFragment {

    Context dialog_ctx;
    Button share,remove;

    public ChooseActionDialog(Context ctx){
        this.dialog_ctx = ctx;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
                    Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.film_action_choose_dialog);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCancelable(true);


        /*Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.film_action_choose_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(getActivity().getLayoutInflater().inflate(R.layout.film_action_choose_dialog, new ConstraintLayout(getActivity()), false));
       */ return dialog;

    }


}
