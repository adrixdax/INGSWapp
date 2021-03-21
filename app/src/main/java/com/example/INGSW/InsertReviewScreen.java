package com.example.INGSW;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.INGSW.Controllers.ReviewsController;
import com.example.INGSW.Controllers.UserServerController;

import java.util.concurrent.ExecutionException;


public class InsertReviewScreen extends Fragment {

    
    private RatingBar ratingBar;
    private Button insert;
    private String idFilm;
    private EditText title;
    private EditText description;

    public InsertReviewScreen (String idFilm) {
        this.idFilm=idFilm;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_insert_review_screen, container, false);

        ratingBar = root.findViewById(R.id.ratingBar3);


        title = root.findViewById(R.id.review_title);

        description = root.findViewById(R.id.editTextListDescription);

        insert = root.findViewById(R.id.button_inserisci);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewsController rc = new ReviewsController();
                rc.setIdUser(((ToolBarActivity) getActivity()).getUid());
                rc.setTitle(String.valueOf(title.getText()));
                rc.setDesc(String.valueOf(description.getText()));
                rc.setIdFilm(idFilm);
                rc.setVal(String.valueOf(ratingBar.getRating()));


                try {
                    rc.execute(new String("AddReviews")).get();
                    rc.isCancelled();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ((ToolBarActivity) getActivity()).onBackPressed();

            }
        });

        return root;
    }
}