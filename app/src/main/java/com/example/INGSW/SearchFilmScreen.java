package com.example.INGSW;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchFilmScreen extends Fragment {


    private EditText Text_of_search;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.search_film_screen, container, false);


        ImageView bt_search = root.findViewById(R.id.search_button);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Text_of_search = (EditText) root.findViewById(R.id.Text_of_search);
                Toast.makeText(root.getContext(), "Il click funziona  : " + Text_of_search.getText().toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        Text_of_search = root.findViewById(R.id.Text_of_search);
        Text_of_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId==EditorInfo.IME_ACTION_SEARCH)
                {
                    //AsyncTaskToSearch
                }
                return true;
            }
        });


       return root;

    }

}