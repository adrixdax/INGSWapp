package com.example.INGSW;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.INGSW.Component.DB.Adapters.ReviewsAdapter;
import com.example.INGSW.Component.DB.Classes.Reviews;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.Controllers.ReviewsController;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;


public class MyReviews extends Fragment {

    private List<Reviews> review = null;
    private ReviewsController con = new ReviewsController();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_myreviewslist, container, false);



            String latestJson = "";
            try {
                con.setIdUser(((ToolBarActivity)(getActivity())).getUid());
                latestJson = (String) con.execute(new String("UserReviews")).get();
                con.isCancelled();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            try {
                review = (List<Reviews>) getJsonToDecode(latestJson, Reviews.class);
            } catch (Exception e) {
                e.printStackTrace();
            }


        ReviewsAdapter adapter = new ReviewsAdapter(review, this,ToolBarActivity.getReference());
        adapter.setCss(MyReviews.class);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        recyclerView.setAdapter(adapter);

        return root;
    }
}