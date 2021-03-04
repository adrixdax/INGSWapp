package com.example.INGSW.Component.DB.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.R;

import java.util.List;

import static com.example.INGSW.R.id.notifyText;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> {
    private final List<Notify> listOfData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(notifyText);
            this.relativeLayout = itemView.findViewById(R.id.relativeLayoutNotify);
        }
    }

    public NotifyAdapter(List<Notify> listOfData) {
        this.listOfData = listOfData;
    }

    @NonNull
    @Override
    public NotifyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_notify, parent, false);
        return new NotifyAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(listOfData.get(position).getId_receiver());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "click on item: ", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfData.size();
    }


}
