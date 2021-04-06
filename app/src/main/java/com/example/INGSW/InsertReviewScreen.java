package com.example.INGSW;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.INGSW.Controllers.ReviewsController;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.concurrent.ExecutionException;


public class InsertReviewScreen extends Fragment {


    private RatingBar ratingBar;
    private Button insert;
    private final String idFilm;
    private EditText title;
    private EditText description;

    public InsertReviewScreen(String idFilm) {
        this.idFilm = idFilm;
    }

    private boolean isNumber(String titolo) {
        try {
            if ((Float.parseFloat(titolo) != 0 || Float.parseFloat(titolo) == 0)
                    ||
                    (Integer.parseInt(titolo) != 0 || Integer.parseInt(titolo) == 0)) {
                return true;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
        return false;
    }

    private boolean validTitle(String titolo) {
        if (titolo.isEmpty()) {
            title.setError("Inserire almeno un Titolo per la recensione!");
            title.requestFocus();
            return false;
        } else if (titolo.length() > 20) {
            title.setError("Il Titolo della recensione è troppo lungo");
            title.requestFocus();
            return false;
        } else if (isNumber(titolo)) {
            title.setError("Il Titolo non può avere solo numeri");
            title.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validValutation(float valutazione) {
        if (valutazione >= 0.5f && valutazione <= 5.0f)
            return true;
        else {
            Toast.makeText(getContext(), "Devi selezionare almeno mezzo ciak", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean isValidReview(String titolo, float valutazione) {
        return validTitle(titolo) && validValutation(valutazione);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_insert_review_screen, container, false);

        ratingBar = root.findViewById(R.id.ratingBar3);


        title = root.findViewById(R.id.review_title);

        description = root.findViewById(R.id.editTextListDescription);

        insert = root.findViewById(R.id.button_inserisci);
        PushDownAnim.setPushDownAnimTo(insert);

        insert.setOnClickListener((View.OnClickListener) v -> {
            if (isValidReview(String.valueOf(title.getText()), ratingBar.getRating())) {
                ReviewsController rc = new ReviewsController();
                rc.setIdUser(((ToolBarActivity) getActivity()).getUid());
                rc.setTitle(String.valueOf(title.getText()));
                rc.setDesc(String.valueOf(description.getText()).isEmpty() ? "\0" : String.valueOf(description.getText()));
                rc.setIdFilm(idFilm);
                rc.setVal(String.valueOf(ratingBar.getRating()));
                try {
                    rc.execute("AddReviews").get();
                    rc.isCancelled();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                ((ToolBarActivity) getActivity()).onBackPressed(true);
            }
        });

        return root;
    }





}