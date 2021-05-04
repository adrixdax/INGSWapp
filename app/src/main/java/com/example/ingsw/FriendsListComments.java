package com.example.ingsw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.component.db.adapters.ReviewsAdapter;
import com.example.ingsw.component.db.classes.Reviews;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;

import java.util.List;

@SuppressWarnings("ALL")
public class FriendsListComments extends Fragment implements RetrofitListInterface {

    private final Boolean isUserOwner;
    private final String idList;
    private RecyclerView friendsCommentsRecyclerView;

    public FriendsListComments(Boolean isUserOwner, String idList) {
        this.isUserOwner = isUserOwner;
        this.idList = idList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.friend_list_comments, container, false);
        ((ToolBarActivity) getActivity()).triggerProgessBar();
        TextView title = root.findViewById(R.id.textViewCommentList);
        friendsCommentsRecyclerView = root.findViewById(R.id.recyclerViewFriendsComment);
        ImageView like = root.findViewById(R.id.likeList);
        ImageView dislike = root.findViewById(R.id.dislikeList);
        RetrofitResponse.getResponse("Type=PostRequest&idRecordRef=" + idList + "&insert=false&typeOfReview=LIST", this, getContext(), "getReview");
        if (isUserOwner) {
            title.setText("I commenti dei tuoi amici:");
            like.setVisibility(View.GONE);
            dislike.setVisibility(View.GONE);
        } else {
            title.setText("Valuta questa lista:");
            title.setWidth(root.getWidth());
            like.setVisibility(View.VISIBLE);
            dislike.setVisibility(View.VISIBLE);
            like.setOnClickListener(v -> {
                InsertListReviewScreen nextFragment = new InsertListReviewScreen(true, idList);
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "listComments");
                transaction.addToBackStack(null);
                transaction.commit();
            });
            dislike.setOnClickListener(v -> {
                InsertListReviewScreen nextFragment = new InsertListReviewScreen(false, idList);
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "listComments");
                transaction.addToBackStack(null);
                transaction.commit();
            });
        }

        return root;
    }

    @Override
    public void setList(List<?> newList) {
        if (newList.size() != 0) {
            ReviewsAdapter adapter = new ReviewsAdapter((List<Reviews>) newList, this, ToolBarActivity.getReference());
            adapter.setCss(FriendsListComments.class);
            friendsCommentsRecyclerView.setHasFixedSize(false);
            friendsCommentsRecyclerView.setItemViewCacheSize(newList.size());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            friendsCommentsRecyclerView.setLayoutManager(layoutManager);
            friendsCommentsRecyclerView.setAdapter(adapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(friendsCommentsRecyclerView.getContext(),
                    layoutManager.getOrientation());
            friendsCommentsRecyclerView.addItemDecoration(dividerItemDecoration);
            friendsCommentsRecyclerView.setVisibility(View.VISIBLE);
        }
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}
