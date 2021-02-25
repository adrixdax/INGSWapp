package com.example.INGSW;


import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.Films.ListOfFilm;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.FilmTestController;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class SearchFilmScreen extends Fragment {


    private EditText Text_of_search;
    private RecyclerView recyclerView;
    private List<ListOfFilm> filmInSearch = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.search_film_screen, container, false);

        ImageView bt_search = root.findViewById(R.id.search_button);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) requireActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                ((ToolBarActivity)getActivity()).showProgressBar();
                Text_of_search = (EditText) root.findViewById(R.id.Text_of_search);
                try {

                    FilmTestController filmTestController = new FilmTestController();
                    filmTestController.setNameOfFilm(Text_of_search.getText().toString().trim());
                    String film = filmTestController.getNameOfFilm();
                    System.out.println("Il film che stai cercando -> "+ film);

                    String latestJson = (String) filmTestController.execute(new String("search")).get();

                    System.out.println("I Film trovati -> "+ latestJson);
                    filmTestController.isCancelled();

                    filmInSearch = (List<ListOfFilm>) getJsonToDecode(latestJson, ListOfFilm.class);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView = root.findViewById(R.id.recyclerView);
                    ListOfFilmAdapter adapter = new ListOfFilmAdapter(filmInSearch);
                    recyclerView.setHasFixedSize(false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                            layoutManager.getOrientation());
                    recyclerView.addItemDecoration(dividerItemDecoration);


                    ((ToolBarActivity)getActivity()).stopProgressBar();

                } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
                    e.printStackTrace();
                }

            }
        });
        Text_of_search = (EditText) root.findViewById(R.id.Text_of_search);
        System.out.println(Text_of_search != null);
        Text_of_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    bt_search.callOnClick();
                    return true;
                }
                return false;
            }
        });

       return root;

    }

}