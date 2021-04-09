package com.example.INGSW.Component.DB.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.INGSW.Component.DB.Classes.Reviews;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.MyReviews;
import com.example.INGSW.R;
import com.example.INGSW.ReviewDetail;
import com.example.INGSW.User;
import com.example.INGSW.Utility.JSONDecoder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private final List<Reviews> listofdata;
    private Class css = null;
    private View listItem;
    private final Fragment startFragment;
    private final DatabaseReference ref;

    public ReviewsAdapter(List<Reviews> listofdata, Fragment startFragment, DatabaseReference ref) {
        this.listofdata = listofdata;
        this.startFragment = startFragment;
        this.ref = ref;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if( css.getCanonicalName().equals(MyReviews.class.getCanonicalName())){
            listItem = layoutInflater.inflate(R.layout.component_myreviewlist, parent, false);
        }
        else {
            listItem = layoutInflater.inflate(R.layout.film_review, parent, false);
        }
        return new ReviewsAdapter.ViewHolder(listItem,css);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {
        if (css.getCanonicalName().equals(MyReviews.class.getCanonicalName())) {
            try {
                FilmTestController con = new FilmTestController(new ArrayList());
                con.setIdFilm(String.valueOf(listofdata.get(position).getIdFilm()));
                Film film = ((List<Film>) JSONDecoder.getJsonToDecode((String) con.execute("filmById").get(),Film.class)).get(0);
                Glide.with(startFragment).load(film.getPosterPath()).into(holder.moviepic);

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
                        ReviewDetail nextFragment = new ReviewDetail(listofdata.get(position), ref);
                        FragmentTransaction transaction = startFragment.getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, nextFragment, "ListFilmCustom");
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {

                getReviewer(listofdata.get(position).getIduser(), holder);

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
                holder.relativeLayoutReviewList.setOnClickListener(v -> {
                    ReviewDetail nextFragment = new ReviewDetail(listofdata.get(position), ref);
                    FragmentTransaction transaction = startFragment.getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, nextFragment, "ListFilmCustom");
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            Query query = ref.orderByKey().equalTo(id);
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
        public ImageView moviepic;
        public TextView userNickView;
        public TextView reviewTitle;
        public TextView reviewDescription;
        public RatingBar ratingBar;
        public RelativeLayout relativeLayoutReviewList;


        public ViewHolder(View itemView, Class css) {
            super(itemView);
            if(css.getCanonicalName().equals(MyReviews.class.getCanonicalName()) ){
                this.moviepic = itemView.findViewById(R.id.moviepic_view);
            }else {
                this.userImage = itemView.findViewById(R.id.userprofilepic_view);
                this.userNickView = itemView.findViewById(R.id.usernick_view);
            }
            this.reviewTitle = itemView.findViewById(R.id.review_title);
            this.reviewDescription = itemView.findViewById(R.id.textViewDescriptionReview);
            this.ratingBar = itemView.findViewById(R.id.ratingBar2);
            relativeLayoutReviewList = itemView.findViewById(R.id.relativeLayoutReviewList);

            PushDownAnim.setPushDownAnimTo(relativeLayoutReviewList);
        }


    }

    public Class getCss() {
        return css;
    }

    public void setCss(Class css) {
        this.css = css;
    }
}
