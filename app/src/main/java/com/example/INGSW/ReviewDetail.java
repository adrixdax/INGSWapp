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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewDetail extends Fragment {

    private final Reviews review;
    private CircleImageView userImage;
    private TextView userName;
    private TextView reviewTitle;
    private TextView reviewDescription;
    private RatingBar ratingBar;
    private final DatabaseReference ref;


    public ReviewDetail(Reviews review, DatabaseReference ref) {
        this.review = review;
        this.ref = ref;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.review_details, container, false);

        userImage = (CircleImageView) root.findViewById(R.id.userprofilepic_view);
        userName = (TextView) root.findViewById(R.id.usernick_view);
        reviewTitle = (TextView) root.findViewById(R.id.review_title);
        reviewDescription = (TextView) root.findViewById(R.id.textViewDescriptionReview);
        ratingBar = (RatingBar) root.findViewById(R.id.ratingBar2);

        getReviewer(review.getIduser(), root);
        reviewTitle.setText(review.getTitle());
        reviewDescription.setText(review.getDescription());
        ratingBar.setRating((float) review.getVal());
        ratingBar.setClickable(false);
        ratingBar.setIsIndicator(true);


        return root;
    }


    private void getReviewer(String id, View root) {

        try {
            Query query = ref.orderByKey().equalTo(id);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User model = dataSnapshot.getValue(User.class);
                        Glide.with(root.getContext()).load(model.getPropic()).into(userImage);
                        userName.setText(model.getNickname());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
