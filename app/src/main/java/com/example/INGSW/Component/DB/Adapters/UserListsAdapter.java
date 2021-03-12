package com.example.INGSW.Component.DB.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Classes.UserLists;


import com.example.INGSW.DialogCustomlList;
import com.example.INGSW.R;
import com.example.INGSW.home.HomepageScreen;

import java.util.List;

import static com.bumptech.glide.Glide.with;

public class UserListsAdapter extends RecyclerView.Adapter<UserListsAdapter.ViewHolder> {
    private final List<UserLists> listofdata;

    private Class css = null;
    private View listItem;


    public UserListsAdapter(List<UserLists> listofdata, Class css) {
        this.listofdata = listofdata;
        this.css = css;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (css.getCanonicalName().equals(HomepageScreen.class.getCanonicalName())) {
             listItem = layoutInflater.inflate(R.layout.lists_of_lists, parent, false);
        }else  if (css.getCanonicalName().equals(DialogCustomlList.class.getCanonicalName())) {
             listItem = layoutInflater.inflate(R.layout.list_custom_list_selected, parent, false);
        }
        return new UserListsAdapter.ViewHolder(listItem, css);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(css.getCanonicalName().equals(DialogCustomlList.class.getCanonicalName())){
            try {
                holder.textView.setText(listofdata.get(position).getTitle());
                with(holder.itemView).load("http://cdn.onlinewebfonts.com/svg/img_568523.png").into((ImageView) holder.itemView.findViewById(R.id.userprofilepic_view));
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton imageButton;
        public RelativeLayout relativeLayout;
        public TextView textView;
        public RadioButton selectItem;

        public ViewHolder(View itemView, Class css) {
            super(itemView);
            if (css.getCanonicalName().equals(HomepageScreen.class.getCanonicalName())) {
                this.imageButton = itemView.findViewById(R.id.listComponent);
                this.relativeLayout = itemView.findViewById(R.id.relativeLayoutNotify);
            }else if (css.getCanonicalName().equals(DialogCustomlList.class.getCanonicalName())) {

                this.imageButton = itemView.findViewById(R.id.list_image);
                relativeLayout = itemView.findViewById(R.id.relativeLayoutAddInCustomList);
                this.selectItem= itemView.findViewById(R.id.radioButtonAddList);
                this.textView= itemView.findViewById(R.id.NameOfCustomList);


            }
        }
    }

    public Class getCss() {
        return css;
    }

    public void setCss(Class css) {
        this.css = css;
    }
}
