package com.example.INGSW;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

public class SpoilerAlertDIalog extends AppCompatDialogFragment {

    Context dialog_ctx;
    Button procedi, annulla;
    private int filmid;





    public SpoilerAlertDIalog(Context ctx,int id_film) {
        this.dialog_ctx = ctx;
        this.filmid = id_film;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);


        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.spoilerAlert_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        procedi = dialog.findViewById(R.id.procedi_button);
        annulla = dialog.findViewById(R.id.annulla_button);

        PushDownAnim.setPushDownAnimTo(procedi,annulla);

        procedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Context ctx = dialog_ctx;
                Fragment nextFragment;
                FragmentTransaction transaction;
                nextFragment = new ReviewScreen(String.valueOf(filmid);
                transaction = ctx.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "FilmReviews");
                transaction.addToBackStack(null);
                transaction.commit();

                 */
            }
        });


        return dialog;

    }



}
