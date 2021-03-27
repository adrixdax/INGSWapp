package com.example.INGSW.Component.DB.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.style.UpdateLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.Controllers.NotifyTestController;
import com.example.INGSW.Controllers.NotifyUpdater;
import com.example.INGSW.Controllers.UserServerController;
import com.example.INGSW.NotifyPopUp;
import com.example.INGSW.R;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.User;
import com.example.INGSW.Utility.JSONDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> {
    private final ArrayList<Notify> listOfData;
    private final FirebaseDatabase ref;
    private Context myContext;
    private int removedItems=0;

    private User getUser(String id, ViewHolder holder, int position) {
        final User[] reviewer = new User[1];
        try {
            Query query = ref.getReference("Users").orderByKey().equalTo(id);
            query.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User model = dataSnapshot.getValue(User.class);
                        holder.userName.setText(model.getNickname());
                        with(holder.itemView).load(model.getPropic()).into((ImageView) holder.itemView.findViewById(R.id.userImageNotify));
                        switch (listOfData.get(position).getType()) {
                            case "Film":
                                FilmTestController ftc = new FilmTestController();
                                ftc.setIdFilm(String.valueOf(listOfData.get(position).getId_recordref()));
                                Film recordRef = null;
                                try {
                                    String json = (String) ftc.execute("filmById").get();
                                    recordRef = ((List<Film>) (JSONDecoder.getJsonToDecode(json, Film.class))).get(0);
                                } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                if (recordRef != null)
                                    holder.notifyText.setText("ti consiglia:\n" + recordRef.getFilm_Title());
                                break;
                            case "FRIENDSHIP REQUEST":
                                holder.notifyText.setText("vuole essere tuo Amico");
                                break;
                            case "List":
                                holder.notifyText.setText("vuole consigliarti la lista " + listOfData.get(position).getId_recordref());
                                break;
                            default:
                                holder.notifyText.setText("vuole farti vedere la sua recensione riguardo " + listOfData.get(position).getId_recordref());
                                break;
                        }
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

    public NotifyAdapter(ArrayList<Notify> listOfData, FirebaseDatabase ref, Context myContext) {
        this.listOfData = listOfData;
        this.ref = ref;
        this.myContext = myContext;
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
        holder.yes.setOnClickListener(v -> {
            holder.newNotify.setVisibility(View.INVISIBLE);
            listOfData.get(getCorrectIndex(position)).setState("ACCEPTED");
            switch (listOfData.get(getCorrectIndex(position)).getType()) {
                case "Film":
                    new NotifyTestController().execute("Accepted=" + listOfData.get(getCorrectIndex(position)).getId_Notify());
                    Toast.makeText(v.getContext(), "Accept", Toast.LENGTH_SHORT).show();
                    listOfData.remove(getCorrectIndex(position));
                    this.notifyItemRemoved(getCorrectIndex(position));
                    NotifyUpdater.newUpdate();
                    break;
                case "FRIENDSHIP REQUEST":
                    UserServerController usc = new UserServerController();
                    usc.setUserId(((ToolBarActivity) myContext).getUid());
                    usc.setIdOtherUser(listOfData.get(getCorrectIndex(position)).getId_sender());
                    try {
                        usc.execute(new String("acceptedFriendsRequest")).get();
                        usc.isCancelled();
                        new NotifyTestController().execute("Accepted=" + listOfData.get(getCorrectIndex(position)).getId_Notify());
                        Toast.makeText(v.getContext(), "Accept", Toast.LENGTH_SHORT).show();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    listOfData.remove(getCorrectIndex(position));
                    this.notifyItemRemoved(getCorrectIndex(position));
                    NotifyUpdater.newUpdate();
                    break;
                case "List":
                    holder.notifyText.setText(new StringBuilder().append("vuole consigliarti la lista ").append(listOfData.get(position).getId_recordref()).toString());
                    new NotifyTestController().execute("Accepted=" + listOfData.get(getCorrectIndex(position)).getId_Notify());
                    Toast.makeText(v.getContext(), "Accept", Toast.LENGTH_SHORT).show();
                    listOfData.remove(getCorrectIndex(position));
                    this.notifyItemRemoved(getCorrectIndex(position));
                    NotifyUpdater.newUpdate();
                    break;
               default:
                    holder.notifyText.setText(new StringBuilder().append("vuole farti vedere la sua recensione riguardo ").append(listOfData.get(position).getId_recordref()).toString());
                    new NotifyTestController().execute("Accepted=" + listOfData.get(position).getId_Notify());
                    Toast.makeText(v.getContext(), "Accept", Toast.LENGTH_SHORT).show();
                    listOfData.remove(getCorrectIndex(position));
                    this.notifyItemRemoved(getCorrectIndex(position));
                    NotifyUpdater.newUpdate();
                    break;
            };
            removedItems++;
            verifyNoMoreNotify();
        });
        holder.no.setOnClickListener(v -> {
            listOfData.get(getCorrectIndex(position)).setState("REFUSED");
            Toast.makeText(v.getContext(), "Decline", Toast.LENGTH_SHORT).show();
            holder.newNotify.setVisibility(View.INVISIBLE);
            new NotifyTestController().execute("Refused=" + listOfData.get(getCorrectIndex(position)).getId_Notify());
            listOfData.remove(getCorrectIndex(position));
            this.notifyItemRemoved(getCorrectIndex(position));
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
    private int getCorrectIndex(int position){
        return Math.max(position - removedItems , 0);
    }

    private void verifyNoMoreNotify(){
        if (getItemCount() == 0){
            NotifyPopUp.noMoreNotify();
        }
    }


}
