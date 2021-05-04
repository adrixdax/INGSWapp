package com.example.ingsw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.component.db.adapters.ReviewsAdapter;
import com.example.ingsw.component.db.classes.Reviews;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;

import java.util.List;


@SuppressWarnings("ALL")
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