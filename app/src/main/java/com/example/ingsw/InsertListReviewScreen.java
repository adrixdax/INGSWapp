package com.example.ingsw;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ingsw.controllers.retrofit.RetrofitResponse;

public class InsertListReviewScreen extends Fragment {

    private final String idList;

    public Boolean getPositive() {
        return isPositive;
    }

    private Boolean isPositive;

    public InsertListReviewScreen(Boolean isPositive, String idList) {
        this.isPositive = isPositive;
        this.idList = idList;
    }

    public String valutaLista(String text,Boolean isPositive){
        String descr;
        if (text.isEmpty())
            descr = "ha lasciato " + (isPositive ? "un \"mi piace\"" : "un \"non mi piace\"");
        else {
            descr = text;
            descr = descr.replaceAll("'", "''");
        }
        return descr;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.insert_list_review, container, false);
        ImageView likeButton = root.findViewById(R.id.likeButton);
        ImageView dislikeButton = root.findViewById(R.id.dislikeButton);
        Button insertReview = root.findViewById(R.id.insertCommentButton);
        EditText editTextDescription = root.findViewById(R.id.editTextListDescription);
        if (isPositive) {
            likeButton.setBackground(requireContext().getDrawable(R.drawable.rounded_corners));
            dislikeButton.setBackgroundResource(0);
        } else {
            dislikeButton.setBackground(requireContext().getDrawable(R.drawable.rounded_corners));
            likeButton.setBackgroundResource(0);
        }
        likeButton.setOnClickListener(v -> {
            likeButton.setBackground(requireContext().getDrawable(R.drawable.rounded_corners));
            dislikeButton.setBackgroundResource(0);
            isPositive = true;
        });
        dislikeButton.setOnClickListener(v -> {
            dislikeButton.setBackground(requireContext().getDrawable(R.drawable.rounded_corners));
            likeButton.setBackgroundResource(0);
            isPositive = false;
        });
        insertReview.setOnClickListener(v -> {
            String descr = valutaLista(editTextDescription.getText().toString(),isPositive);
            RetrofitResponse.getResponse("Type=PostRequest&idRecordRef=" + idList + "&title=" + "\0" + "&description=" + descr + "&val=" + (isPositive ? "1.0" : "0.0") + "&idUser=" + ((ToolBarActivity) requireActivity()).getUid() + "&insert=true&typeOfReview=LIST", this, getContext(), "addReview");
            requireActivity().onBackPressed();
        });

        return root;
    }
}
