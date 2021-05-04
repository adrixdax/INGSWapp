package com.example.ingsw;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ingsw.component.db.adapters.CustomListsAdapter;
import com.example.ingsw.component.db.classes.User;
import com.example.ingsw.component.db.classes.UserLists;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("ALL")
public class FriendProfile extends Fragment implements RetrofitListInterface {

    private final String friendId;
    private CircleImageView friendPic;
    private TextView textError, friendNick;
    private RecyclerView friendRecyclerView;

    public FriendProfile(String friendId) {
        this.friendId = friendId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.friend_profile, container, false);
        friendPic = root.findViewById(R.id.personal_profile_friend_image);
        friendNick = root.findViewById(R.id.personal_profile_friend_nick);
        friendRecyclerView = root.findViewById(R.id.recyclerViewFriendLists);
        textError = root.findViewById(R.id.errorFriendProfile);
        Query query = ToolBarActivity.getReference().orderByKey().equalTo(friendId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User model = dataSnapshot.getValue(User.class);
                    assert model != null;
                    friendNick.setText(model.getNickname());
                    Glide.with(requireContext()).load(model.getPropic()).into(friendPic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        RetrofitResponse.getResponse("Type=PostRequest&idUser=" + friendId + "&custom=true&idFilm=-1", FriendProfile.this, getContext(), "getList");

        return root;
    }

    @Override
    public void setList(List<?> newList) {
        if (newList.size() != 0) {
            friendRecyclerView.setAdapter(new CustomListsAdapter((List<UserLists>) newList, this.getClass(), this));
            friendRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            friendRecyclerView.setHasFixedSize(false);
            friendRecyclerView.setItemViewCacheSize(newList.size());
        } else textError.setText("Il tuo amico non ha alcuna lista :(");
    }
}
