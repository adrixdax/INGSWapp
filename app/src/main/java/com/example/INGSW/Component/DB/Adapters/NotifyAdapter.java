package com.example.INGSW.Component.DB.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.R;
import static com.bumptech.glide.Glide.*;
import java.util.List;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder>{
    private final List<Notify> listOfData;

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
        final Notify listOfnots = listOfData.get(position);
        holder.textView.setText(listOfData.get(position).getType());
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.textView3);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }

}
