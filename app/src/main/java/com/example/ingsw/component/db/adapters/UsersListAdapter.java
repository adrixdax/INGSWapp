package com.example.ingsw.component.db.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.component.db.classes.Contact;
import com.example.ingsw.component.db.classes.Notify;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.example.ingsw.R;
import com.example.ingsw.ToolBarActivity;
import com.example.ingsw.component.db.classes.User;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UsersViewHolder> {

    private final int size = 0;
    private final ArrayList<User> userlist;
    private final ArrayList<Contact> areFriends;
    private final ArrayList<Notify> isFriendRequestSent;
    final Context context;

    public UsersListAdapter(Context context, ArrayList<User> userlist, ArrayList<Contact> areFriends, ArrayList<Notify> isFriendRequestSent) {
        this.context = context;
        this.userlist = userlist;
        this.areFriends = areFriends;
        this.isFriendRequestSent = isFriendRequestSent;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.friend_component_search, parent, false);
        return new UsersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User model = userlist.get(position);
        holder.nick.setText(model.getNickname());
        with(holder.itemView).load(model.getPropic()).into((CircleImageView) holder.itemView.findViewById(R.id.userprofilepic_view));
        boolean areFriend = false;
        for (Contact c : areFriends) {
            if (userlist.get(position).getIdUser().equals(c.getUser1()) || userlist.get(position).getIdUser().equals(c.getUser2())) {
                holder.addButton.setImageResource(R.drawable.icons8_expand_arrow_48px);
                areFriend = true;
                break;
            } else {
                holder.addButton.setImageResource(R.drawable.icons8_plus_26px);
            }
        }
        if (!areFriend) {
            for (Notify not : isFriendRequestSent) {
                if (not.getState().equals("PENDING") || not.getState().equals("SEEN")) {
                    if (userlist.get(position).getIdUser().equals(not.getId_receiver())) {
                        holder.addButton.setImageResource(R.drawable.icons8_expand_arrow_48px);
                        areFriend = true;
                        break;
                    } else {
                        holder.addButton.setImageResource(R.drawable.icons8_plus_26px);
                    }
                } else {
                    isFriendRequestSent.remove(not);
                    notifyDataSetChanged();
                }
            }
        }
        if (!areFriend) {
            holder.addButton.setOnClickListener((v) -> {
                    {
                        RetrofitResponse.getResponse("Type=PostRequest&id_sender=" + ((ToolBarActivity)context).getUid() + "&id_receiver=" + userlist.get(position).getIdUser() + "&type=FRIENDSHIP_REQUEST&id_recordref=0&sendNotify=true", this, context, "createNotify");
                        holder.addButton.setImageResource(R.drawable.icons8_expand_arrow_48px);
                    }
                });

            }
        }


    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        final TextView nick;
        final CircleImageView userpic;
        final RelativeLayout relativeLayoutNotify;
        final ImageButton addButton;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            nick = itemView.findViewById(R.id.usernick_view);
            userpic = itemView.findViewById(R.id.userprofilepic_view);
            relativeLayoutNotify = itemView.findViewById(R.id.relativeLayoutNotify);
            addButton = itemView.findViewById(R.id.addFriendButton);
            PushDownAnim.setPushDownAnimTo(addButton);
        }
    }
}

