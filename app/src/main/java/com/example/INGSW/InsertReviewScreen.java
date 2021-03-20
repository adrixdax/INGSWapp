package com.example.INGSW;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;


public class InsertReviewScreen extends Fragment {


    private float rating;
    private RatingBar ratingBar;

    public InsertReviewScreen(float rating) {
        this.rating = rating;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_insert_review_screen, container, false);

        ratingBar = root.findViewById(R.id.ratingBar3);
        ratingBar.setRating(this.rating);

        return root;
    }
}