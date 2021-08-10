package com.example.ingsw.component.db.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.FriendsListComments;
import com.example.ingsw.NotifyPopUp;
import com.example.ingsw.R;
import com.example.ingsw.ReviewDetail;
import com.example.ingsw.ToolBarActivity;
import com.example.ingsw.component.db.classes.Notify;
import com.example.ingsw.component.db.classes.Reviews;
import com.example.ingsw.component.db.classes.User;
import com.example.ingsw.component.db.classes.UserLists;
import com.example.ingsw.component.films.Film;
import com.example.ingsw.controllers.NotifyUpdater;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;

@SuppressWarnings("ALL")
public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> implements RetrofitListInterface {
    private final ArrayList<Notify> listOfData;
    private final ArrayList<UserLists> lists = new ArrayList<>();
    private final ArrayList<UserLists> userLists = new ArrayList<>();
    private final ArrayList<Film> films = new ArrayList<>();
    private final ArrayList<Reviews> reviews = new ArrayList<>();
    private final DatabaseReference ref;
    private final Context myContext;
    private final NotifyPopUp dialog;

    public NotifyAdapter(ArrayList<Notify> listOfData, DatabaseReference ref, Context myContext, NotifyPopUp dialog) {
        this.listOfData = listOfData;
        for (Notify not : listOfData) {
            switch (not.getType()) {
                case "FILM":
                    RetrofitResponse.getResponse("Type=PostRequest&filmId=" + not.getId_recordref(), NotifyAdapter.this, null, "getFilmById");
                    break;
                case "LIST":
                    RetrofitResponse.getResponse(String.valueOf(not.getId_recordref()), this, myContext, "getListById");
                    break;
                case "REVIEW":
                case "LIST_REVIEW":{
                    RetrofitResponse.getResponse(String.valueOf(not.getId_recordref()), this, myContext, "getSingleReview");
                    break;
                }
                default:
                    break;
            }
        }
        this.ref = ref;
        this.myContext = myContext;
        this.dialog = dialog;
    }

    private void getUser(String id, ViewHolder holder, int position) {
        final User[] reviewer = new User[1];
        try {
            Query query = ref.orderByKey().equalTo(id);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User model = dataSnapshot.getValue(User.class);
                        assert model != null;
                        holder.userName.setText(model.getNickname());
                        with(holder.itemView).load(model.getPropic()).into((ImageView) holder.itemView.findViewById(R.id.userImageNotify));
                        if (listOfData.get(position).getState().equals("PENDING")) {
                            holder.newNotify.setVisibility(View.VISIBLE);
                        } else {
                            holder.newNotify.setVisibility(View.INVISIBLE);
                        }
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

    @SuppressWarnings("unchecked")
    @Override
    public void setList(List<?> newList) {
        if (newList.size() != 0) {
            if (Film.class.equals(newList.get(0).getClass())) {
                films.addAll(films.size(), (Collection<? extends Film>) newList);
                notifyDataSetChanged();
            } else if (UserLists.class.equals(newList.get(0).getClass())) {
                if (((UserLists)newList.get(0)).getIdUser().equals(((ToolBarActivity)(myContext)).getUid()))
                    userLists.addAll(userLists.size(), (Collection<? extends UserLists>) newList);
                else
                    lists.addAll(lists.size(), (Collection<? extends UserLists>) newList);
                notifyDataSetChanged();
            } else {
                for (Object r : newList) {
                    if (((Reviews) r).getTypeOfReview().equals("FILM")) {
                        RetrofitResponse.getResponse("Type=PostRequest&filmId=" + ((Reviews) r).getIdRecordRef(), this, this.myContext, "getFilmById");
                    }
                    else //getListById
                        RetrofitResponse.getResponse(String.valueOf(((Reviews) r).getIdRecordRef()), this, this.myContext, "getListById");
                }
                reviews.addAll(reviews.size(), (Collection<? extends Reviews>) newList);
                notifyDataSetChanged();
            }
        }
    }

    @NonNull
    @Override
    public NotifyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_notify, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        getUser(listOfData.get(position).getId_sender(), holder, position);
        PushDownAnim.setPushDownAnimTo(holder.yes, holder.no)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
        switch (listOfData.get(position).getType()) {
            case "FILM":
                for (Film f : films) {
                    if (f.getId_Film() == listOfData.get(position).getId_recordref()) {
                        holder.notifyText.setText("ti consiglia:\n" + f.getFilm_Title());
                        break;
                    }
                }
                break;
            case "FRIENDSHIP_REQUEST":
                holder.notifyText.setText("vuole essere tuo Amico");
                break;
            case "LIST":
                for (UserLists l : lists) {
                    if (l.getIdUser().equals(listOfData.get(position).getId_sender())) {
                        holder.notifyText.setText("ti consiglia la sua lista " + l.getTitle());
                    }
                }
                break;
            case "REVIEW":
                for (Film f : films) {
                    if (listOfData.get(position).getId_recordref() == f.getId_Film()) {
                        holder.notifyText.setText("vuole farti vedere la recensione riguardo:\n" + f.getFilm_Title());
                        break;
                    }
                }
            case "LIST_REVIEW":
                for (Reviews r : reviews) {
                    for (UserLists u : userLists) {
                        if (listOfData.get(position).getId_recordref() == r.getId_reviews() && r.getIdRecordRef() == u.getIdUserList())
                            holder.notifyText.setText("Ha recensito la tua lista:\n" + u.getTitle());
                    }
                }
                break;
        }
        holder.yes.setOnClickListener(v -> {
            holder.newNotify.setVisibility(View.INVISIBLE);
            listOfData.get(position).setState("ACCEPTED");
            RetrofitResponse.getResponse(String.valueOf(listOfData.get(position).getId_Notify()), this, this.myContext, "setAccepted");
            switch (listOfData.get(position).getType()) {
                case "FILM": {
                    Toast.makeText(v.getContext(), "Ora questo film è nei tuoi film da vedere :)", Toast.LENGTH_SHORT).show();
                    listOfData.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, listOfData.size());
                    NotifyUpdater.newUpdate();
                    break;
                }
                case "FRIENDSHIP_REQUEST": {
                    RetrofitResponse.getResponse("Type=PostRequest&addFriends=true&idUser=" + ((ToolBarActivity) myContext).getUid() + "&idOtherUser=" + listOfData.get(position).getId_sender(), this, myContext, "addFriend");
                    Toast.makeText(v.getContext(), "Ora siete amici", Toast.LENGTH_SHORT).show();
                    listOfData.remove(position);
                    this.notifyItemRemoved(position);
                    notifyItemRangeChanged(position, listOfData.size());
                    NotifyUpdater.newUpdate();
                    break;
                }
                case "LIST": {
                    Toast.makeText(v.getContext(), "Hai una nuova lista", Toast.LENGTH_SHORT).show();
                    listOfData.remove(position);
                    this.notifyItemRemoved(position);
                    notifyItemRangeChanged(position, listOfData.size());
                    NotifyUpdater.newUpdate();
                    break;
                }
                case "REVIEW": {
                    Reviews revObj = null;
                    for (Reviews r : reviews) {
                        if (r.getId_reviews() == listOfData.get(position).getId_recordref())
                            revObj = r;
                    }
                    listOfData.remove(position);
                    this.notifyItemRemoved(position);
                    notifyItemRangeChanged(position, listOfData.size());
                    NotifyUpdater.newUpdate();
                    FragmentTransaction transaction = ((ToolBarActivity) (myContext)).getSupportFragmentManager().beginTransaction();
                    ReviewDetail rev = new ReviewDetail(revObj, ToolBarActivity.getReference());
                    transaction.replace(R.id.nav_host_fragment, rev, "Review");
                    transaction.addToBackStack(null);
                    transaction.commit();
                    dialog.dismiss();
                    break;
                }
                case "LIST_REVIEW": {
                    FragmentTransaction transaction = ((ToolBarActivity) (myContext)).getSupportFragmentManager().beginTransaction();
                    FriendsListComments rev = null;
                    for (Reviews r : reviews) {
                        for (UserLists u : userLists) {
                            if (listOfData.get(position).getId_recordref() == r.getId_reviews() && r.getIdRecordRef() == u.getIdUserList())
                                rev = new FriendsListComments(true, String.valueOf(u.getIdUserList()));
                            transaction.replace(R.id.nav_host_fragment, rev, "FriendsReviews");
                            transaction.addToBackStack(null);
                            transaction.commit();
                            listOfData.remove(position);
                            this.notifyItemRemoved(position);
                            notifyItemRangeChanged(position, listOfData.size());
                            NotifyUpdater.newUpdate();
                            dialog.dismiss();
                            break;
                        }
                    }
                }
            }
            verifyNoMoreNotify();
        });
        holder.no.setOnClickListener(v -> {
            RetrofitResponse.getResponse(String.valueOf(listOfData.get(position).getId_Notify()), this, myContext, "setRefused");
            listOfData.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listOfData.size());
            NotifyUpdater.newUpdate();
            verifyNoMoreNotify();
        });

    }

    @Override
    public int getItemCount() {
        return listOfData.size();
    }

    public void changeStatus() {
        for (Notify not : listOfData) {
            if (not.getState().equals("PENDING"))
                RetrofitResponse.getResponse(String.valueOf(not.getId_Notify()), this, this.myContext, "setSeen");
        }
    }

    private void verifyNoMoreNotify() {
        if (getItemCount() == 0) {
            NotifyPopUp.noMoreNotify();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView yes;
        public final ImageView no;
        public final TextView notifyText;
        public final CircleImageView imageView;
        public final TextView userName;
        public final RelativeLayout relativeLayout;
        private final ImageView newNotify;

        public ViewHolder(View itemView) {
            super(itemView);
            this.notifyText = itemView.findViewById(R.id.notifyText);
            this.imageView = itemView.findViewById(R.id.userImageNotify);
            this.userName = itemView.findViewById(R.id.userName);
            this.no = itemView.findViewById(R.id.denyNotify);
            this.yes = itemView.findViewById(R.id.acceptNotify);
            this.relativeLayout = itemView.findViewById(R.id.relativeLayoutNotify);
            this.newNotify = itemView.findViewById(R.id.newNotify);
        }
    }

}
