package com.example.INGSW.Component.DB.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Classes.Contact;
import com.example.INGSW.R;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.Glide.with;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private final List<Contact> listofdata;
    private final Context myContext;
    private final List<Contact> selectedLists;

    public ContactListAdapter(List<Contact> listofdata, Context myContext, List<Contact> selectedLists) {
        this.listofdata = listofdata;
        this.myContext = myContext;
        this.selectedLists = selectedLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_friend_selected, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ViewHolder holder, int position) {
        try {

            if (listofdata.get(position).getUser1().equals(((ToolBarActivity) myContext).getUid())) {
                getReviewer(listofdata.get(position).getUser2(), holder);
            } else {
                getReviewer(listofdata.get(position).getUser1(), holder);
            }
            holder.checkButtonShare.setOnCheckedChangeListener(null);
            holder.checkButtonShare.setChecked(false);
            holder.checkButtonShare.setClickable(false);

            holder.relativeLayoutShareWhitFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!holder.checkButtonShare.isChecked()) {
                        holder.checkButtonShare.setChecked(true);
                        selectedLists.add(listofdata.get(position));

                    } else {
                        holder.checkButtonShare.setChecked(false);
                        selectedLists.remove(listofdata.get(position));
                    }
                }
        });
    } catch(
    Exception e)

    {
        e.printStackTrace();
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

    private void getReviewer(String id, ContactListAdapter.ViewHolder holder) {
        try {
            Query query = ToolBarActivity.getReference().orderByKey().equalTo(id);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User model = dataSnapshot.getValue(User.class);
                        with(holder.itemView).load(model.getPropic()).into((CircleImageView) holder.userImage.findViewById(R.id.user_image));
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
        public TextView userNickView;
        public RelativeLayout relativeLayoutShareWhitFriend;
        public AppCompatCheckBox checkButtonShare;


        public ViewHolder(View itemView) {
            super(itemView);
            this.userImage = itemView.findViewById(R.id.user_image);
            this.userNickView = itemView.findViewById(R.id.NickName);
            this.checkButtonShare = itemView.findViewById(R.id.checkButtonShare);
            relativeLayoutShareWhitFriend = itemView.findViewById(R.id.relativeLayoutShareWhitFriend);

            PushDownAnim.setPushDownAnimTo(relativeLayoutShareWhitFriend);
        }


    }


}


