package com.example.INGSW.Component.Films;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.R;

import java.util.List;

import static com.bumptech.glide.Glide.with;

public class ListOfFilmAdapter extends RecyclerView.Adapter<ListOfFilmAdapter.ViewHolder> {

    private final List<ListOfFilm> listOfData;
    private boolean imageListFilm = false;

    public ListOfFilmAdapter(List<ListOfFilm> listOfData) {
        this.listOfData = listOfData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = null;
        if (isImageListFilm()) {
            listItem = layoutInflater.inflate(R.layout.list_image_film, parent, false);
        } else {
            listItem = layoutInflater.inflate(R.layout.list_of_film_in_search_screen, parent, false);
        }
        return new ViewHolder(listItem, isImageListFilm());
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfFilmAdapter.ViewHolder holder, int position) {
        final ListOfFilm listOfFilm = listOfData.get(position);
        if(isImageListFilm()) {
            with(holder.itemView).load(listOfData.get(position).getPosterPath()).into((ImageView) holder.itemView.findViewById(R.id.imageView));
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "click on item: ", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            with(holder.itemView).load(listOfData.get(position).getPosterPath()).into((ImageView) holder.itemView.findViewById(R.id.imageView));
            holder.textViewTitle.setText(listOfData.get(position).getFilm_Title());
            holder.textViewRelaseDate.setText(listOfData.get(position).getRelease_Date());
            holder.textViewTime.setText(String.valueOf(listOfData.get(position).getRuntime()));
            holder.textViewCategories.setText(listOfData.get(position).getGenres()[0]);
            holder.textViewPlot.setText(listOfData.get(position).getPlot());
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listOfData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        public TextView textViewTitle;
        public TextView textViewRelaseDate;
        public TextView textViewTime;
        public TextView textViewCategories;
        public TextView textViewPlot;

        public ViewHolder(View itemView, boolean image) {
            super(itemView);
            if(image) {
                this.imageView = itemView.findViewById(R.id.imageView);
            }else {
                this.imageView = itemView.findViewById(R.id.imageView);
                this.textViewTitle = itemView.findViewById(R.id.textViewTitle);
                this.textViewRelaseDate = itemView.findViewById(R.id.textViewRelaseDate);
                this.textViewTime = itemView.findViewById(R.id.textViewTime);
                this.textViewCategories = itemView.findViewById(R.id.textViewCategories);
                this.textViewPlot = itemView.findViewById(R.id.textViewPlot);

            }
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }

    public boolean isImageListFilm() {
        return imageListFilm;
    }

    public void setImageListFilm(boolean imageListFilm) {
        this.imageListFilm = imageListFilm;
    }

}

