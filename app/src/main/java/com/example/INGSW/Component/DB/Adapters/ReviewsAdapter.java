package com.example.INGSW.Component.DB.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Classes.Reviews;
import com.example.INGSW.FilmInCustomList;
import com.example.INGSW.R;
import com.example.INGSW.ReviewDetail;
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
    private final Fragment startFragment;
    private FirebaseDatabase ref;

    public ReviewsAdapter(List<Reviews> listofdata, Fragment startFragment) {
        this.listofdata = listofdata;
        this.startFragment = startFragment;
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
            getReviewer(listofdata.get(position).getIduser(), holder);

            if (reviewer != null) {
                with(holder.itemView).load(reviewer.getPropic()).into((CircleImageView) holder.userImage.findViewById(R.id.userprofilepic_view));
                holder.userNickView.setText(reviewer.getNickname());
            }
            holder.ratingBar.setRating((float) listofdata.get(position).getVal());
            holder.ratingBar.setClickable(false);
            holder.ratingBar.setIsIndicator(true);
            holder.reviewTitle.setText(listofdata.get(position).getTitle());
            holder.reviewDescription.setText(listofdata.get(position).getDescription());
            String[] line = listofdata.get(position).getDescription().split(System.getProperty("line.separator"));
            if (line.length > 5) {
                int lunghezza = 0;
                for (int i = 0; i < 5; i++) {
                    lunghezza = lunghezza + line[i].length();
                }
                holder.reviewDescription.setText(listofdata.get(position).getDescription().substring(0, lunghezza + 4) + "...");
            } else if (listofdata.get(position).getDescription().length() >= 300) {
                holder.reviewDescription.setText(listofdata.get(position).getDescription().substring(0, 150 - 3) + "...");
            } else {
                holder.reviewDescription.setText(listofdata.get(position).getDescription());
            }
            holder.relativeLayoutReviewList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReviewDetail nextFragment = new ReviewDetail(listofdata.get(position));
                    FragmentTransaction transaction = startFragment.getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, nextFragment, "ListFilmCustom");
                    transaction.addToBackStack(null);
                    transaction.commit();
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

    private void getReviewer(String id, ReviewsAdapter.ViewHolder holder) {
        try {
            Query query = FirebaseDatabase.getInstance().getReference("Users").orderByKey().equalTo(id);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User model = dataSnapshot.getValue(User.class);
                        with(holder.itemView).load(model.getPropic()).into((CircleImageView) holder.userImage.findViewById(R.id.userprofilepic_view));
                        holder.userNickView.setText(model.getNickname());
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


}
