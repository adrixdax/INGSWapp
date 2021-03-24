package com.example.INGSW;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Controllers.NotifyTestController;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;
import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UsersViewHolder> {


    ArrayList<User> userlist;
    Context context;


    public UsersListAdapter(Context context, ArrayList<User> userlist) {
        this.context = context;
        this.userlist = userlist;
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

        boolean friend = false;
        List<Notify> notifyList = new ArrayList<>();
        try {
            notifyList = (List<Notify>) getJsonToDecode(String.valueOf(new NotifyTestController().execute("idUser=" + model.getIdUser()).get()), Notify.class);
            System.out.println(((ToolBarActivity) context).getUid());
            if (!notifyList.isEmpty()) {
                int i = 0;
                while (i < notifyList.size() && !friend) {
                    System.out.println("Size = " + notifyList.size() + "  indixe = " + i);
                    System.out.println(((ToolBarActivity) context).getUid());
                    if (notifyList.get(i).getId_sender().equals(((ToolBarActivity) context).getUid())) {
                        friend = true;
                    }
                    i++;
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (friend) {
            holder.addButton.setImageResource(R.drawable.icons8_expand_arrow_48px);
        } else {
            holder.addButton.setImageResource(R.drawable.icons8_plus_26px);
        }
        with(holder.itemView).load(model.getPropic()).into((CircleImageView) holder.itemView.findViewById(R.id.userprofilepic_view));
        if (!friend) {
            holder.addButton.setOnClickListener(new View.OnClickListener() {
                boolean send=false;
                @Override
                public void onClick(View v) {
                    if (!send) {
                        NotifyTestController ntc = new NotifyTestController();
                        ntc.setIdSender(((ToolBarActivity) v.getContext()).getUid());
                        ntc.setIdReceiver(model.getIdUser());
                        ntc.setType("FRIENDSHIP REQUEST");
                        try {
                            ntc.execute(new String("SendFriendshipRequest")).get();
                            ntc.isCancelled();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        send=true;
                        holder.addButton.setImageResource(R.drawable.icons8_expand_arrow_48px);
                    }
                }

            });
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
        }
    }
}
