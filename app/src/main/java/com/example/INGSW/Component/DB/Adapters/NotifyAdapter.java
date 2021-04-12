package com.example.INGSW.Component.DB.Adapters;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Component.DB.Classes.Reviews;
import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.Controllers.NotifyTestController;
import com.example.INGSW.Controllers.NotifyUpdater;
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.Controllers.ReviewsController;
import com.example.INGSW.Controllers.UserServerController;
import com.example.INGSW.NotifyPopUp;
import com.example.INGSW.R;
import com.example.INGSW.ReviewDetail;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.User;
import com.example.INGSW.Utility.JSONDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> implements RetrofitListInterface {
    private final ArrayList<Notify> listOfData;
    private ArrayList<UserLists> lists;
    private ArrayList<Film> films = new ArrayList<>();
    private ArrayList<Reviews> reviews;
    private final DatabaseReference ref;
    private final Context myContext;
    private final NotifyPopUp dialog;

    private User getUser(String id, ViewHolder holder, int position) {
        final User[] reviewer = new User[1];
        try {
            Query query = ref.orderByKey().equalTo(id);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User model = dataSnapshot.getValue(User.class);
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
        return reviewer[0];
    }

    public NotifyAdapter(ArrayList<Notify> listOfData,DatabaseReference ref, Context myContext, NotifyPopUp dialog) {
        this.listOfData = listOfData;
        for (Notify not : listOfData){
            switch (not.getType()) {
                case "FILM":
                     RetrofitResponse.getResponse("Type=PostRequest&filmId=" + not.getId_recordref(), NotifyAdapter.this, null, Film.class.getCanonicalName(), "getFilmById");
                     break;
                case "FRIENDSHIP_REQUEST":
                    //holder.notifyText.setText("vuole essere tuo Amico");
                    break;
                case "LIST":

                    //Retrofit

                    FilmTestController controller = new FilmTestController();
                    //controller.setIdList(String.valueOf(listOfData.get(position).getId_recordref()));
                    try {
                        String json = (String) controller.execute("listName").get();
                        try {
                            UserLists list = ((List<UserLists>) (JSONDecoder.getJsonToDecode(json, UserLists.class))).get(0);
                      //      holder.notifyText.setText("vuole consigliarti la lista: \"" + list.getTitle() + "\"");
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case "REVIEW":

                    //Retrofit

                    ReviewsController revCon = new ReviewsController();
                    //revCon.setIdReview(String.valueOf(listOfData.get(position).getId_recordref()));
                    Reviews rev = null;
                    try {
                        rev = ((List<Reviews>) JSONDecoder.getJsonToDecode((String) revCon.execute("singleReview").get(), Reviews.class)).get(0);
                    } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    FilmTestController con = new FilmTestController();
                    con.setIdFilm(String.valueOf(rev.getIdFilm()));
                    //holder.notifyText.setText(new StringBuilder().append("vuole farti vedere la sua recensione riguardo: ").append(((List<Film>) (JSONDecoder.getJsonToDecode(String.valueOf(con.execute("filmById").get()), Film.class))).get(0).getFilm_Title()));
                    break;
            }
        }
        this.ref = ref;
        this.myContext = myContext;
        this.dialog = dialog;
    }

    @Override
    public void setList(List<?> newList) {
        if (newList.size() != 0){
            if (Film.class.equals(newList.get(0).getClass())) {
                films.addAll(films.size(), (Collection<? extends Film>) newList);
                notifyDataSetChanged();
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView yes;
        public ImageView no;
        public TextView notifyText;
        public CircleImageView imageView;
        public TextView userName;
        private final ImageView newNotify;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.notifyText=itemView.findViewById(R.id.notifyText);
            this.imageView = itemView.findViewById(R.id.userImageNotify);
            this.userName = itemView.findViewById(R.id.userName);
            this.no=itemView.findViewById(R.id.denyNotify);
            this.yes=itemView.findViewById(R.id.acceptNotify);
            this.relativeLayout = itemView.findViewById(R.id.relativeLayoutNotify);
            this.newNotify = itemView.findViewById(R.id.newNotify);
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

                //Retrofit

                FilmTestController controller = new FilmTestController();
                controller.setIdList(String.valueOf(listOfData.get(position).getId_recordref()));
                try {
                    String json = (String) controller.execute("listName").get();
                    try {
                        UserLists list = ((List<UserLists>) (JSONDecoder.getJsonToDecode(json, UserLists.class))).get(0);
                        holder.notifyText.setText("vuole consigliarti la lista: \"" + list.getTitle() + "\"");
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "REVIEW":

                //Retrofit

                try {
                    ReviewsController revCon = new ReviewsController();
                    revCon.setIdReview(String.valueOf(listOfData.get(position).getId_recordref()));
                    Reviews rev = null;
                    try {
                        rev = ((List<Reviews>) JSONDecoder.getJsonToDecode((String) revCon.execute("singleReview").get(), Reviews.class)).get(0);
                    } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    FilmTestController con = new FilmTestController();
                    con.setIdFilm(String.valueOf(rev.getIdFilm()));
                    holder.notifyText.setText(new StringBuilder().append("vuole farti vedere la sua recensione riguardo: ").append(((List<Film>) (JSONDecoder.getJsonToDecode(String.valueOf(con.execute("filmById").get()), Film.class))).get(0).getFilm_Title()));
                } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;
        }
        holder.yes.setOnClickListener(v -> {
            holder.newNotify.setVisibility(View.INVISIBLE);
            listOfData.get(position).setState("ACCEPTED");
            switch (listOfData.get(position).getType()) {
                case "FILMS": {
                    new NotifyTestController().execute("Accepted=" + listOfData.get(position).getId_Notify());
                    Toast.makeText(v.getContext(), "Ora questo film è nei tuoi film da vedere :)", Toast.LENGTH_SHORT).show();
                    listOfData.remove(position);
                    notifyItemRemoved(position);
                    NotifyUpdater.newUpdate();
                    break;
                }
                case "FRIENDSHIP_REQUEST": {
                    UserServerController usc = new UserServerController();
                    usc.setUserId(((ToolBarActivity) myContext).getUid());
                    usc.setIdOtherUser(listOfData.get(position).getId_sender());
                    try {
                        usc.execute("acceptedFriendsRequest").get();
                        usc.isCancelled();
                        new NotifyTestController().execute("Accepted=" + listOfData.get(position).getId_Notify());
                        Toast.makeText(v.getContext(), "Ora siete amici", Toast.LENGTH_SHORT).show();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    listOfData.remove(position);
                    this.notifyItemRemoved(position);
                    NotifyUpdater.newUpdate();
                    break;
                }
                case "LIST": {
                    new NotifyTestController().execute("Accepted=" + listOfData.get(position).getId_Notify());
                    Toast.makeText(v.getContext(), "Hai una nuova lista", Toast.LENGTH_SHORT).show();
                    listOfData.remove(position);
                    this.notifyItemRemoved(position);
                    NotifyUpdater.newUpdate();
                    break;
                }
                case "REVIEW": {
                    new NotifyTestController().execute("Accepted=" + listOfData.get(position).getId_Notify());
                    ReviewsController revCon = new ReviewsController();
                    revCon.setIdReview(String.valueOf(listOfData.get(position).getId_recordref()));
                    Reviews revObj = null;
                    try {
                        revObj = ((Reviews) ((List<Reviews>) JSONDecoder.getJsonToDecode((String) revCon.execute("singleReview").get(), Reviews.class)).get(0));
                    } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                    listOfData.remove(position);
                    this.notifyItemRemoved(position);
                    NotifyUpdater.newUpdate();
                    FragmentManager fm = ((ToolBarActivity) (myContext)).getSupportFragmentManager();
                    Fragment currentFragment = fm.findFragmentById(R.id.nav_host_fragment);
                    FragmentTransaction transaction = ((ToolBarActivity) (myContext)).getSupportFragmentManager().beginTransaction();
                    ReviewDetail rev = new ReviewDetail(revObj, ToolBarActivity.getReference());
                    transaction.replace(R.id.nav_host_fragment, rev, "Review");
                    transaction.addToBackStack(null);
                    transaction.commit();
                    dialog.dismiss();
                    break;
                }
            }
            verifyNoMoreNotify();
        });
        holder.no.setOnClickListener(v -> {
            listOfData.get(position).setState("REFUSED");
            Toast.makeText(v.getContext(), "Non hai accettato", Toast.LENGTH_SHORT).show();
            holder.newNotify.setVisibility(View.INVISIBLE);
            new NotifyTestController().execute("Refused=" + listOfData.get(position).getId_Notify());
            listOfData.remove(position);
            notifyItemRemoved(position);
            NotifyUpdater.newUpdate();
            verifyNoMoreNotify();
        });

    }

    @Override
    public int getItemCount() {
        return listOfData.size();
    }

    public void changeStatus(){
        for (Notify not : listOfData){
            if (not.getState().equals("PENDING"))
            new NotifyTestController().execute("Seen="+not.getId_Notify());
        }
    }

    private void verifyNoMoreNotify(){
        if (getItemCount() == 0){
            NotifyPopUp.noMoreNotify();
        }
    }


}
