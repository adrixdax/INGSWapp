package com.example.INGSW;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UsersViewHolder> {


    ArrayList<User> userlist;
    Context context;


    public UsersListAdapter(Context context, ArrayList<User> userlist){
        this.context = context;
        this.userlist = userlist;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.friend_component_search,parent,false);
        return new UsersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User model = userlist.get(position);
        holder.nick.setText(model.getNickname());
        with(holder.itemView).load(model.getPropic()).into((CircleImageView) holder.itemView.findViewById(R.id.userprofilepic_view));
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }


    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        TextView nick;
        CircleImageView userpic;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            nick = itemView.findViewById(R.id.usernick_view);
            userpic = itemView.findViewById(R.id.userprofilepic_view);
        }
    }
}
