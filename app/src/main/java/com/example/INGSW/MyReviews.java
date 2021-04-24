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
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.Controllers.ReviewsController;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;


public class MyReviews extends Fragment implements RetrofitListInterface {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_myreviewslist, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        ((ToolBarActivity)requireActivity()).triggerProgessBar();
        RetrofitResponse.getResponse("Type=PostRequest&idUser=" + ((ToolBarActivity) requireActivity()).getUid() + "&insert=false&typeOfReview=FILM",this, getContext(),"getReview");

        return root;
    }

    @Override
    public void setList(List<?> newList) {
        if (newList.size() != 0) {
            ReviewsAdapter adapter = new ReviewsAdapter((List<Reviews>) newList, this, ToolBarActivity.getReference());
            adapter.setCss(MyReviews.class);
            recyclerView.setHasFixedSize(false);
            recyclerView.setItemViewCacheSize(newList.size());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        }
        ((ToolBarActivity)requireActivity()).stopProgressBar();
    }
}