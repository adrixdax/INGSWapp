package com.example.INGSW.Component.DB.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.R;

import java.util.List;

public class UserListsAdapter extends RecyclerView.Adapter {
    private final List<Object> listofdata;

    public UserListsAdapter(List<Object> listofdata) {
        this.listofdata = listofdata;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.lists_of_lists, parent, false);
        return new UserListsAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (listofdata.get(position) instanceof UserLists) {
            holder.imageButton.setText(listOfdata.get(position));
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "click on item: ", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            //holder.imageButton
        }
    }

    @Override
    public int getItemCount() {
        return listofdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton imageButton;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageButton = itemView.findViewById(R.id.listComponent);
            this.relativeLayout = itemView.findViewById(R.id.relativeLayoutNotify);
        }
    }

}
