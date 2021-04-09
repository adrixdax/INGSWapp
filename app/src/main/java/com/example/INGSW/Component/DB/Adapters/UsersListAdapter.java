package com.example.INGSW.Component.DB.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Controllers.NotifyTestController;
import com.example.INGSW.R;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.User;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UsersViewHolder> {

    private final int size = 0;
    private final ArrayList<User> userlist;
    private final ArrayList<Boolean> areFriends;
    Context context;

    public UsersListAdapter(Context context, ArrayList<User> userlist, ArrayList<Boolean> areFriends) {
        this.context = context;
        this.userlist = userlist;
        this.areFriends = areFriends;

    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.friend_component_search, parent, false);
        return new UsersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        if (!userlist.isEmpty() && userlist.size()==areFriends.size()) {
            User model = userlist.get(position);
            holder.nick.setText(model.getNickname());
            with(holder.itemView).load(model.getPropic()).into((CircleImageView) holder.itemView.findViewById(R.id.userprofilepic_view));
            if (areFriends.get(position)) {
                holder.addButton.setImageResource(R.drawable.icons8_expand_arrow_48px);
            } else {
                holder.addButton.setImageResource(R.drawable.icons8_plus_26px);
            }
            if (!areFriends.get(position)) {
                holder.addButton.setOnClickListener(new View.OnClickListener() {
                    boolean send = false;

                    @Override
                    public void onClick(View v) {
                        if (!send) {
                            NotifyTestController ntc = new NotifyTestController((ToolBarActivity) context);
                            ntc.setIdSender(((ToolBarActivity) v.getContext()).getUid());
                            ntc.setIdReceiver(model.getIdUser());
                            ntc.setType("FRIENDSHIP_REQUEST");
                            try {
                                ntc.execute("SendFriendshipRequest").get();
                                ntc.isCancelled();
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            send = true;
                            holder.addButton.setImageResource(R.drawable.icons8_expand_arrow_48px);
                            areFriends.set(position, true);
                        }
                    }

                });
            }
        }
    }


    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView nick;
        CircleImageView userpic;
        RelativeLayout relativeLayoutNotify;
        ImageButton addButton;

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

