package com.example.ingsw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.component.db.adapters.ReviewsAdapter;
import com.example.ingsw.component.db.classes.Reviews;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;


@SuppressWarnings("ALL")
public class ReviewScreen extends Fragment implements RetrofitListInterface {

    private final String idFilm;
    private RecyclerView recyclerViewReviews;


    public ReviewScreen(String idFilm) {
        this.idFilm = idFilm;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.review_fragment, container, false);
        ((ToolBarActivity) requireActivity()).triggerProgessBar();
        recyclerViewReviews = root.findViewById(R.id.recyclerViewReviews);
        RetrofitResponse.getResponse("Type=PostRequest&idRecordRef=" + idFilm + "&insert=false&typeOfReview=FILM", this, requireContext(), "getReview");


        Button bottone = root.findViewById(R.id.buttonwritereview);
        bottone.setOnClickListener(v -> {
            Fragment nextFragment;
            FragmentTransaction transaction;
            nextFragment = new InsertFilmReviewScreen(idFilm);
            transaction = ReviewScreen.this.requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, nextFragment, "InsertFilmReview");
            transaction.addToBackStack(null);
            transaction.commit();
        });
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME,"ReviewScreen");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,this.getClass().getSimpleName());
        ToolBarActivity.setNewBundle("ReviewScreen",bundle);
        return root;
    }

    @Override
    public void setList(List<?> newList) {
        if (newList.size() != 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            ReviewsAdapter adapter = new ReviewsAdapter((List<Reviews>) newList, this, ToolBarActivity.getReference());
            adapter.setCss(ReviewScreen.class);
            recyclerViewReviews.setHasFixedSize(false);
            recyclerViewReviews.setLayoutManager(layoutManager);
            recyclerViewReviews.setAdapter(adapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewReviews.getContext(), layoutManager.getOrientation());
            recyclerViewReviews.setItemViewCacheSize(newList.size());
            recyclerViewReviews.addItemDecoration(dividerItemDecoration);
            recyclerViewReviews.setVisibility(View.VISIBLE);
        } else {
            System.out.println("Devo inserire il text error");
        }
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}