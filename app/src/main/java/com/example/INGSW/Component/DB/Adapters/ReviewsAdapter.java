package com.example.INGSW.Component.DB.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Classes.Reviews;
import com.example.INGSW.R;
import com.example.INGSW.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private final List<Reviews> listofdata;
    private View listItem;

    public ReviewsAdapter(List<Reviews> listofdata) {
        this.listofdata = listofdata;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        listItem = layoutInflater.inflate(R.layout.film_review, parent, false);
        return new ReviewsAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {
        try {

            User reviewer = null;
            reviewer = getReviewer(listofdata.get(position).getIduser());

            if (reviewer != null) {
                System.out.println("_------------------------------------------------------------> Non sono vuoto");
                with(holder.itemView).load(reviewer.getPropic()).into((CircleImageView) holder.userImage.findViewById(R.id.userprofilepic_view));
                holder.userNickView.setText(reviewer.getNickname());
            }
            System.out.println("----------------------------------------------------------------------------> " + listofdata.get(position).getTitle());
            holder.ratingBar.setRating((float) listofdata.get(position).getVal());
            holder.reviewTitle.setText(listofdata.get(position).getTitle());
            holder.reviewDescription.setText(listofdata.get(position).getDesc());
            holder.relativeLayoutReviewList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        try {
            if (listofdata != null) {
                return listofdata.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView userImage;
        public TextView userNickView;
        public TextView reviewTitle;
        public TextView reviewDescription;
        public RatingBar ratingBar;
        public RelativeLayout relativeLayoutReviewList;


        public ViewHolder(View itemView) {
            super(itemView);
            this.userImage = itemView.findViewById(R.id.userprofilepic_view);
            this.userNickView = itemView.findViewById(R.id.usernick_view);
            this.reviewTitle = itemView.findViewById(R.id.review_title);
            this.reviewDescription = itemView.findViewById(R.id.textViewDescriptionReview);
            this.ratingBar = itemView.findViewById(R.id.ratingBar2);
            relativeLayoutReviewList = itemView.findViewById(R.id.relativeLayoutReviewList);

        }
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
