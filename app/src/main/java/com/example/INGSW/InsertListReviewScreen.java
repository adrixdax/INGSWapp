package com.example.INGSW;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InsertListReviewScreen extends Fragment {

    private Boolean isPositive;

    public InsertListReviewScreen(Boolean positive) {
        this.isPositive=positive;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.insert_list_review, container, false);
        ImageView likeButton = root.findViewById(R.id.likeButton);
        ImageView dislikeButton = root.findViewById(R.id.dislikeButton);
        if (isPositive){
            likeButton.setBackground(requireContext().getDrawable(R.drawable.rounded_corners));
            dislikeButton.setBackgroundResource(0);
        }
        else {
            dislikeButton.setBackground(requireContext().getDrawable(R.drawable.rounded_corners));
            likeButton.setBackgroundResource(0);
        }
        likeButton.setOnClickListener(v -> {
            likeButton.setBackground(requireContext().getDrawable(R.drawable.rounded_corners));
            dislikeButton.setBackgroundResource(0);
            isPositive=true;
        });
        dislikeButton.setOnClickListener(v -> {
            dislikeButton.setBackground(requireContext().getDrawable(R.drawable.rounded_corners));
            likeButton.setBackgroundResource(0);
            isPositive=false;
        });

        return root;
    }
}
