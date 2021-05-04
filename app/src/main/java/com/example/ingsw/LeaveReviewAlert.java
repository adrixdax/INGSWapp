package com.example.ingsw;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ingsw.controllers.NotifyUpdater;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LeaveReviewAlert extends AppCompatDialogFragment {

    Button ok, annulla;

    public LeaveReviewAlert(){

    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.leave_review_alert);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        ok = dialog.findViewById(R.id.ok);
        annulla = dialog.findViewById(R.id.annulla_button);
        PushDownAnim.setPushDownAnimTo(ok,annulla);
        ok.setOnClickListener(v -> {
            NotifyUpdater.stopUpdate();
            requireActivity().getSupportFragmentManager().popBackStack();
            dialog.cancel();
            dialog.dismiss();
            dismiss();
        });
        annulla.setOnClickListener(v -> {
            dialog.closeOptionsMenu();
            Objects.requireNonNull(getDialog()).dismiss();
            dialog.cancel();
            dialog.dismiss();
            dismiss();
        });
        return dialog;
    }
}
