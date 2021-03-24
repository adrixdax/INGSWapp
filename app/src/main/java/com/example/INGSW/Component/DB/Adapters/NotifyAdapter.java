package com.example.INGSW.Component.DB.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Controllers.NotifyTestController;
import com.example.INGSW.R;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> {
    private final List<Notify> listOfData;
    private FirebaseDatabase ref;

    private User getUser(String id, ViewHolder holder, int position) {
        final User[] reviewer = new User[1];
        try {
            Query query = ref.getReference("Users").orderByKey().equalTo(id);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User model = dataSnapshot.getValue(User.class);
                        holder.userName.setText(model.getNickname());
                        with(holder.itemView).load(model.getPropic()).into((ImageView) holder.itemView.findViewById(R.id.userImageNotify));
                        holder.notifyText.setText(model.getNickname()+" ti consiglia: "+listOfData.get(position).getId_recordref());
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
        private ImageView newNotify;
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

    public NotifyAdapter(List<Notify> listOfData, FirebaseDatabase ref) {
        this.listOfData = listOfData;
        this.ref = ref;
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
        PushDownAnim.setPushDownAnimTo(holder.yes,holder.no)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
        holder.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.newNotify.setVisibility(View.INVISIBLE);
                listOfData.get(position).setState("ACCEPTED");
                new NotifyTestController().execute("Accepted="+listOfData.get(position).getId_Notify());
                Toast.makeText(v.getContext(),"Accept",Toast.LENGTH_SHORT).show();
                
                //set status to Acceoted
            }
        });
        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOfData.get(position).setState("REFUSED");
                Toast.makeText(v.getContext(),"Decline",Toast.LENGTH_SHORT).show();
                holder.newNotify.setVisibility(View.INVISIBLE);
                new NotifyTestController().execute("Refused="+listOfData.get(position).getId_Notify());
            }
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

}
