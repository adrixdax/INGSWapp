package com.example.INGSW;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.INGSW.Component.DB.Classes.Reviews;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;

public class ReviewDetail extends Fragment {

    private Reviews review;
    private CircleImageView userImage;
    private TextView userName;
    private TextView reviewTitle;
    private TextView reviewDescription;
    private RatingBar ratingBar;


    public ReviewDetail(Reviews review) {
        this.review = review;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.review_details, container, false);

        userImage = (CircleImageView) root.findViewById(R.id.userprofilepic_view);
        userName = (TextView) root.findViewById(R.id.usernick_view);
        reviewTitle = (TextView) root.findViewById(R.id.review_title);
        reviewDescription = (TextView) root.findViewById(R.id.textViewDescriptionReview);
        ratingBar = (RatingBar)  root.findViewById(R.id.ratingBar2);

        User reviewer = null;
        reviewer = getReviewer(review.getIduser());

        if (reviewer != null) {
            Glide.with(root.getContext()).load(reviewer.getPropic()).into(userImage);
            userName.setText(reviewer.getNickname());
        }

        reviewTitle.setText(review.getTitle());
        reviewDescription.setText(review.getDescription());
        ratingBar.setRating((float) review.getVal());
        ratingBar.setClickable(false);
        ratingBar.setIsIndicator(true);


        return root;
    }


    private User getReviewer(String id) {

        final User[] reviewer = {null};
        try {
            Query query = FirebaseDatabase.getInstance().getReference("Users").equalTo(id);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        User model = dataSnapshot.getValue(User.class);
                        reviewer[0] = model;
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviewer[0];
    }

}