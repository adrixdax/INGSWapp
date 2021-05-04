package com.example.ingsw;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.jetbrains.annotations.NotNull;

public class ListComment extends AppCompatDialogFragment {

    private final String username;
    private final String text;
    private final double valutation;

    public ListComment (String username, String text,double valutation){
        this.username = username;
        this.text = text;
        this.valutation = valutation;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.list_comment);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        TextView user = dialog.findViewById(R.id.usernameTextView);
        user.setText(username);
        TextView comment = dialog.findViewById(R.id.commentTextView);
        comment.setText(text);
        comment.setMovementMethod(new ScrollingMovementMethod());
        ImageView reaction = dialog.findViewById(R.id.reaction);
        if (Double.compare(valutation, 1.0f) == 0){
            reaction.setBackground(requireActivity().getDrawable(R.drawable.like_no_background));
        }
        else {
            reaction.setBackground(requireActivity().getDrawable(R.drawable.dislike_no_background));
        }
        return dialog;
    }

}
