package com.example.INGSW.Component;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.INGSW.R;

import java.util.List;

public class ListOfFilmAdapter extends RecyclerView.Adapter<ListOfFilmAdapter.ViewHolder> {

    private List<ListOfFilm> listOfData;

    public ListOfFilmAdapter(List<ListOfFilm> listOfData) {
        this.listOfData = listOfData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_image_film, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfFilmAdapter.ViewHolder holder, int position) {
        final ListOfFilm listOfFilm=listOfData.get(position);
        Glide.with(holder.itemView).load(listOfData.get(position).getImageUrl()).into((ImageView) holder.itemView.findViewById(R.id.imageView));
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
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}

