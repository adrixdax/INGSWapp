package com.example.INGSW;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class ChooseActionDialog extends AppCompatDialogFragment {

    Context dialog_ctx;
    Button share,remove;
    TextView filmTitle;
    String title;

    public ChooseActionDialog(Context ctx,String title){
        this.dialog_ctx = ctx;
        this.title = title;
    }

    public ChooseActionDialog(Context ctx){
        new ChooseActionDialog(ctx,"");
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

        share = dialog.findViewById(R.id.Share_button);
        remove = dialog.findViewById(R.id.Remove_button);
        filmTitle = dialog.findViewById(R.id.filmTitle_view);

        PushDownAnim.setPushDownAnimTo(share,remove);
        filmTitle.setText(this.title);


        /*Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.film_action_choose_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(getActivity().getLayoutInflater().inflate(R.layout.film_action_choose_dialog, new ConstraintLayout(getActivity()), false));
       */ return dialog;

    }

}
