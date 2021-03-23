package com.example.INGSW;

import android.app.Dialog;
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

import com.example.INGSW.home.HomepageScreen;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

public class LeaveReviewAlert extends AppCompatDialogFragment {

    Button ok, annulla;
    private String filmId;
    private FragmentTransaction transaction;




    public LeaveReviewAlert(){};

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);


        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.leave_review_alert);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        ok = dialog.findViewById(R.id.ok);
        annulla = dialog.findViewById(R.id.annulla_button);

        PushDownAnim.setPushDownAnimTo(ok,annulla);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().popBackStack();
                dialog.cancel();
                dialog.dismiss();
                dismiss();
            }
        });

        annulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.closeOptionsMenu();
                getDialog().dismiss();
                dialog.cancel();
                dialog.dismiss();
                dismiss();
            }
        });


        return dialog;

    }



}
