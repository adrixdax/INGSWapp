package com.example.INGSW;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Adapters.ReviewsAdapter;
import com.example.INGSW.Component.DB.Classes.Reviews;
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
        View root = inflater.inflate(R.layout.fragment_myreviewslist, container, false);
            String latestJson = "";
            try {
                con = new ReviewsController();
                con.setIdUser(((ToolBarActivity) getActivity()).getUid());
                latestJson = (String) con.execute("UserReviews").get();
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
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        return root;
    }
}