package com.example.INGSW;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;

import java.util.List;

public class FriendsListComments extends Fragment implements RetrofitListInterface {

    private final Boolean isUserOwner;
    private RecyclerView friendsCommentsRecyclerView;
    private ImageView like;
    private ImageView dislike;

    public FriendsListComments(Boolean isUserOwner){
        this.isUserOwner=isUserOwner;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.friend_list_comments, container, false);

        TextView title = root.findViewById(R.id.textViewCommentList);
        friendsCommentsRecyclerView = root.findViewById(R.id.recyclerViewFriendsComment);
        like = root.findViewById(R.id.likeList);
        dislike = root.findViewById(R.id.dislikeList);
        if (isUserOwner){
            title.setText("I commenti dei tuoi amici:");
            like.setVisibility(View.GONE);
            dislike.setVisibility(View.GONE);
        }
        else {
            title.setText("Valuta questa lista:");
            title.setWidth(root.getWidth());
            like.setVisibility(View.VISIBLE);
            dislike.setVisibility(View.VISIBLE);
        }

        return root;
    }

    @Override
    public void setList(List<?> newList) {
    }
}
