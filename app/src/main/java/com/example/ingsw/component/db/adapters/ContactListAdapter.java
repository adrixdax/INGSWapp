package com.example.ingsw.component.db.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.DialogFriendsListOfShare;
import com.example.ingsw.FriendProfile;
import com.example.ingsw.ListOfFriendsScreen;
import com.example.ingsw.R;
import com.example.ingsw.ToolBarActivity;
import com.example.ingsw.component.db.classes.Contact;
import com.example.ingsw.component.db.classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import teaspoon.annotations.OnUi;

import static com.bumptech.glide.Glide.with;

@SuppressWarnings("rawtypes")
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private final List<Contact> listofdata;
    private final Context myContext;
    private final List<Contact> selectedLists;
    private Class css = null;

    public ContactListAdapter(List<Contact> listofdata, Context myContext, List<Contact> selectedLists) {
        this.listofdata = listofdata;
        this.myContext = myContext;
        this.selectedLists = selectedLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        if (css.equals(ListOfFriendsScreen.class))
            listItem = layoutInflater.inflate(R.layout.friend_component_personal_area, parent, false);
        else
            listItem = layoutInflater.inflate(R.layout.list_friend_selected, parent, false);
        return new ViewHolder(listItem, css);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ViewHolder holder, int position) {
        if (Objects.equals(css.getCanonicalName(), ListOfFriendsScreen.class.getCanonicalName())) {
            if (listofdata.get(position).getUser1().equals(((ToolBarActivity) myContext).getUid())) {
                getUser(listofdata.get(position).getUser2(), holder);
            } else {
                getUser(listofdata.get(position).getUser1(), holder);
            }
            holder.relativeLayoutFriend.setOnClickListener(v -> {
                FriendProfile nextFragment = new FriendProfile(ContactListAdapter.this.listofdata.get(position).getUser1().equals(((ToolBarActivity) myContext).getUid()) ? ContactListAdapter.this.listofdata.get(position).getUser2() : ContactListAdapter.this.listofdata.get(position).getUser1());
                FragmentTransaction transaction = ((ToolBarActivity) myContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "Friend Profile");
                transaction.addToBackStack(null);
                transaction.commit();
            });
        } else {
            try {
                if (listofdata.get(position).getUser1().equals(((ToolBarActivity) myContext).getUid())) {
                    getUser(listofdata.get(position).getUser2(), holder);
                } else {
                    getUser(listofdata.get(position).getUser1(), holder);
                }
                holder.checkButtonShare.setOnCheckedChangeListener(null);
                holder.checkButtonShare.setChecked(false);
                holder.checkButtonShare.setClickable(false);

                holder.relativeLayoutFriend.setOnClickListener(v -> {
                    if (!holder.checkButtonShare.isChecked()) {
                        holder.checkButtonShare.setChecked(true);
                        selectedLists.add(listofdata.get(position));
                    } else {
                        holder.checkButtonShare.setChecked(false);
                        selectedLists.remove(listofdata.get(position));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @OnUi
    private void getUser(String id, ContactListAdapter.ViewHolder holder) {
        try {
            Query query = ToolBarActivity.getReference().orderByKey().equalTo(id);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User model = dataSnapshot.getValue(User.class);
                        assert model != null;
                        if (css.equals(DialogFriendsListOfShare.class)) {
                            with(holder.itemView).load(model.getPropic()).into((CircleImageView) holder.userImage.findViewById(R.id.user_image));
                        } else if (css.equals(ListOfFriendsScreen.class)) {
                            with(holder.itemView).load(model.getPropic()).into((CircleImageView) holder.userImage.findViewById(R.id.friend_pic));
                        }
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

    public void setCss(Class pclass) {
        this.css = pclass;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView userImage;
        public TextView userNickView;
        public RelativeLayout relativeLayoutFriend;
        public AppCompatCheckBox checkButtonShare;


        public ViewHolder(View itemView, Class css) {
            super(itemView);
            if (css.equals(DialogFriendsListOfShare.class)) {
                this.userImage = itemView.findViewById(R.id.user_image);
                this.userNickView = itemView.findViewById(R.id.NickName);
                this.checkButtonShare = itemView.findViewById(R.id.checkButtonShare);
                this.relativeLayoutFriend = itemView.findViewById(R.id.relativeLayoutShareWhitFriend);
            } else if (css.equals(ListOfFriendsScreen.class)) {
                this.userImage = itemView.findViewById(R.id.friend_pic);
                this.userNickView = itemView.findViewById(R.id.username_nickView);
                this.relativeLayoutFriend = itemView.findViewById(R.id.friend_component_personal_area);
            }
            PushDownAnim.setPushDownAnimTo(relativeLayoutFriend);
        }
    }


}


