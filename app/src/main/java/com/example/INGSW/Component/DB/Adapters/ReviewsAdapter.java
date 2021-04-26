package com.example.INGSW.Component.DB.Adapters;

import android.annotation.SuppressLint;
import android.text.method.ScrollingMovementMethod;
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
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.FriendsListComments;
import com.example.INGSW.ListComment;
import com.example.INGSW.MyReviews;
import com.example.INGSW.R;
import com.example.INGSW.ReviewDetail;
import com.example.INGSW.Component.DB.Classes.User;
import com.example.INGSW.ReviewScreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import teaspoon.annotations.OnBackground;
import teaspoon.annotations.OnUi;

import static com.bumptech.glide.Glide.with;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> implements RetrofitListInterface {

    private final List<Reviews> listofdata;
    private final List<Film> filmList = new ArrayList<>();
    private Class css = null;
    private View listItem;
    private final Fragment startFragment;
    private final DatabaseReference ref;
    private User model;

    private Boolean isUserOwner;

    public ReviewsAdapter(List<Reviews> listofdata, Fragment startFragment, DatabaseReference ref) {
        this.listofdata = listofdata;
        if (listofdata.size() != 0 && listofdata.get(0).getTypeOfReview().equals("FILM")) {
            for (Reviews rev : listofdata) {
                RetrofitResponse.getResponse("Type=PostRequest&filmId=" + rev.getIdRecordRef(), ReviewsAdapter.this, null, "getFilmById");
            }
        }
        this.startFragment = startFragment;
        this.ref = ref;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(Objects.equals(css, MyReviews.class)){
            listItem = layoutInflater.inflate(R.layout.component_myreviewlist, parent, false);
        }
        else if (Objects.equals(css, ReviewScreen.class)){
            listItem = layoutInflater.inflate(R.layout.film_review, parent, false);
        }
        else listItem = layoutInflater.inflate(R.layout.component_list_review, parent, false);
        return new ReviewsAdapter.ViewHolder(listItem,css);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {
        if (Objects.equals(css, MyReviews.class)) {
            try {
              if( position < filmList.size() && filmList.size()==listofdata.size()) {
                  for (Film f : filmList) {
                      if (listofdata.get(position).getIdRecordRef() == f.getId_Film()) {
                          Glide.with(startFragment).load(f.getPosterPath()).into(holder.moviepic);
                          break;
                      }
                  }
                }
                holder.ratingBar.setRating((float) listofdata.get(position).getVal());
                holder.ratingBar.setClickable(false);
                holder.ratingBar.setIsIndicator(true);
                holder.reviewTitle.setText(listofdata.get(position).getTitle());
                holder.reviewDescription.setText(listofdata.get(position).getDescription());
                String[] line = listofdata.get(position).getDescription().split(Objects.requireNonNull(System.getProperty("line.separator")));
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
                    FragmentTransaction transaction = startFragment.requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, nextFragment, "ListFilmCustom");
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (Objects.equals(css, ReviewScreen.class)){
            try {
                getReviewer(listofdata.get(position).getIduser(), holder);
                holder.ratingBar.setRating((float) listofdata.get(position).getVal());
                holder.ratingBar.setClickable(false);
                holder.ratingBar.setIsIndicator(true);
                holder.reviewTitle.setText(listofdata.get(position).getTitle());
                holder.reviewDescription.setText(listofdata.get(position).getDescription());
                String[] line = listofdata.get(position).getDescription().split(Objects.requireNonNull(System.getProperty("line.separator")));
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
                    FragmentTransaction transaction = startFragment.requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, nextFragment, "ListFilmCustom");
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            getReviewer(listofdata.get(position).getIduser(), holder);
            System.out.println(listofdata.get(position).getDescription());
            holder.reviewDescription.setText(listofdata.get(position).getDescription());
            String[] line = listofdata.get(position).getDescription().split(Objects.requireNonNull(System.getProperty("line.separator")));
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
            if (Double.compare(listofdata.get(position).getVal(), 1.0f) == 0){
                holder.friendReaction.setBackground(startFragment.getActivity().getDrawable(R.drawable.like_no_background));
            }
            else {
                holder.friendReaction.setBackground(startFragment.getActivity().getDrawable(R.drawable.dislike_no_background));
            }
            holder.relativeLayoutReviewList.setOnClickListener(v -> {
                ListComment listCommentDialog = new ListComment(model.getNickname(),listofdata.get(position).getDescription(),listofdata.get(position).getVal());
                listCommentDialog.show(startFragment.getActivity().getSupportFragmentManager(), "comments");
            });

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

    @OnBackground
    private void getReviewer(String id, ReviewsAdapter.ViewHolder holder) {
        try {
            Query query = ref.orderByKey().equalTo(id);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                @OnUi
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        model = dataSnapshot.getValue(User.class);
                        assert model != null;
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

    @Override
    @OnBackground
    public void setList(List<?> newList) {
        this.filmList.addAll((Collection<? extends Film>) newList);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView userImage;
        public ImageView moviepic;
        public TextView userNickView;
        public TextView reviewTitle;
        public TextView reviewDescription;
        public RatingBar ratingBar;
        public RelativeLayout relativeLayoutReviewList;
        public ImageView friendReaction;


        public ViewHolder(View itemView, Class css) {
            super(itemView);
            if(Objects.equals(css, MyReviews.class)){
                this.moviepic = itemView.findViewById(R.id.moviepic_view);
            }else {
                this.userImage = itemView.findViewById(R.id.userprofilepic_view);
                this.userNickView = itemView.findViewById(R.id.usernick_view);
            }
            if (!(Objects.equals(css,FriendsListComments.class))) {
                this.reviewTitle = itemView.findViewById(R.id.review_title);
            }
            this.reviewDescription = itemView.findViewById(R.id.textViewDescriptionReview);
            if (Objects.equals(css, MyReviews.class) || Objects.equals(css,ReviewScreen.class)) {
                this.ratingBar = itemView.findViewById(R.id.ratingBar2);
            }
            else {
                this.friendReaction = itemView.findViewById(R.id.friendReaction);
            }
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
