package com.example.INGSW;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Adapters.ReviewsAdapter;
import com.example.INGSW.Component.DB.Classes.Reviews;
import com.example.INGSW.Controllers.ReviewsController;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;


public class ReviewScreen extends Fragment {

    private final String idFilm;
    private List<Reviews> reviews = new ArrayList<>();
    private RecyclerView recyclerViewReviews;

    public ReviewScreen(String idFilm) {
        this.idFilm = idFilm;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.review_fragment, container, false);

        ReviewsController rc = new ReviewsController();
        rc.setIdFilm(idFilm);

        try {
            String latestJson = (String) rc.execute("FilmReviews").get();

            System.out.println(latestJson);

            if (!latestJson.isEmpty()) {
                rc.isCancelled();

                reviews = (List<Reviews>) getJsonToDecode(latestJson, Reviews.class);

                LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerViewReviews = root.findViewById(R.id.recyclerViewReviews);
                ReviewsAdapter adapter = new ReviewsAdapter(reviews,this);
                recyclerViewReviews.setHasFixedSize(false);
                recyclerViewReviews.setLayoutManager(layoutManager);
                recyclerViewReviews.setAdapter(adapter);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewReviews.getContext(),
                        layoutManager.getOrientation());
                recyclerViewReviews.addItemDecoration(dividerItemDecoration);
                recyclerViewReviews.setVisibility(View.VISIBLE);
            }

        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        }


        Button bottone = root.findViewById(R.id.buttonwritereview);
        bottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nextFragment;
                FragmentTransaction transaction;
                FragmentManager fm = ReviewScreen.this.getActivity().getSupportFragmentManager();
                Fragment currentFragment = fm.findFragmentById(R.id.nav_host_fragment);
                nextFragment = new InsertReviewScreen(idFilm);
                transaction = ReviewScreen.this.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "InsertFilmReview");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }
}