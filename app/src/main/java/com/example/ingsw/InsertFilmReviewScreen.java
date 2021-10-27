package com.example.ingsw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.example.ingsw.utility.TitleException.IS_NUMBER;
import static com.example.ingsw.utility.TitleException.TOO_LONG;
import static com.example.ingsw.utility.TitleException.TOO_SHORT;


@SuppressWarnings("SameReturnValue")
public class InsertFilmReviewScreen extends Fragment {


    private final String idFilm;
    private RatingBar ratingBar;
    private EditText title;
    private EditText description;

    public InsertFilmReviewScreen(String idFilm) {
        this.idFilm = idFilm;
    }

    private boolean isNumber(String titolo) {
        try{
            return (Float.parseFloat(titolo) != 0.0f || Float.parseFloat(titolo) == 0.0f)
                    ||
                    (Integer.parseInt(titolo) != 0 || Integer.parseInt(titolo) == 0);
        }
        catch (NumberFormatException ex){
            return false;
        }
    }

    private boolean validTitle(String titolo) throws Exception {
        if (titolo.isEmpty()) throw new Exception(String.valueOf(TOO_SHORT));
        if (titolo.length() > 20) throw new Exception(String.valueOf(TOO_LONG));
        return !isNumber(titolo);
    }

    private boolean validValutation(float valutazione) {
        return valutazione >= 0.5f && valutazione <= 5.0f;
    }

    public boolean isValidReview(String titolo, float valutazione) throws Exception {
        return validValutation(valutazione) && validTitle(titolo);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_insert_review_screen, container, false);
        ratingBar = root.findViewById(R.id.ratingBar3);
        title = root.findViewById(R.id.insert_review_title);
        description = root.findViewById(R.id.editTextListDescription);
        Button insert = root.findViewById(R.id.button_inserisci);
        PushDownAnim.setPushDownAnimTo(insert);

        insert.setOnClickListener(v -> {
            try {
                if (isValidReview(String.valueOf(title.getText()), ratingBar.getRating())) {

                    RetrofitResponse.getResponse("Type=PostRequest&idRecordRef=" + idFilm + "&title=" + title.getText().toString().replaceAll("'", "''") + "&description=" + (description.getText().toString().isEmpty() ? "\0" : description.getText().toString().replaceAll("'", "''")) + "&val=" + ratingBar.getRating() + "&idUser=" + ((ToolBarActivity) requireActivity()).getUid() + "&insert=true&typeOfReview=FILM", this, getContext(), "addReview");
                    ((ToolBarActivity) requireActivity()).onBackPressed(true);
                } else {
                    Toast.makeText(getContext(), "Devi selezionare almeno mezzo ciak", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                if (e.getMessage().equals(IS_NUMBER.toString())) {
                    title.setError("Il titolo non può essere un numero");
                    title.requestFocus();
                } else if (e.getMessage().equals(TOO_LONG.toString())) {
                    title.setError("Il titolo è troppo lungo");
                    title.requestFocus();
                } else if (e.getMessage().equals(TOO_SHORT.toString())) {
                    title.setError("Devi inserire un titolo");
                    title.requestFocus();
                }
            }
        });
        return root;
    }
}