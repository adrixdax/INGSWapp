package com.example.INGSW;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import com.example.INGSW.Controllers.FilmTestController;

import java.util.concurrent.ExecutionException;

public class SearchFilmScreen extends Fragment {


    private EditText Text_of_search;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.search_film_screen, container, false);

        progressBar=  (ProgressBar) root.findViewById(R.id.progressBar2);
        ImageView bt_search = root.findViewById(R.id.search_button);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressDialog = new ProgressDialog(root.getContext());
                progressDialog.show();
                progressDialog.setContentView(R.layout.custom_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                Text_of_search = (EditText) root.findViewById(R.id.Text_of_search);
                try {

                    FilmTestController filmTestController = new FilmTestController();
                    filmTestController.setNameOfFilm(Text_of_search.getText().toString().trim());
                    String film = filmTestController.getNameOfFilm();
                    System.out.println("Il film che stai cercando -> "+ film);

                    String prova = (String) filmTestController.execute(new String("search")).get();

                    System.out.println("I Film trovati -> "+ prova);
                    boolean boh = filmTestController.isCancelled();


                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Toast.makeText(root.getContext(), "Il click funziona  : " + Text_of_search.getText().toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });

       return root;

    }

    

}