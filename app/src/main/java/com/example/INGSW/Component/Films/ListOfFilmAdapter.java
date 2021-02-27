package com.example.INGSW.Component.Films;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.FilmDetails;
import com.example.INGSW.R;
import com.example.INGSW.ToolBarActivity;

import java.util.List;

import static com.bumptech.glide.Glide.with;

public class ListOfFilmAdapter extends RecyclerView.Adapter<ListOfFilmAdapter.ViewHolder> {

    private final List<ListOfFilm> listOfData;
    private boolean imageListFilm = false;

    private Context mContext;
    private Fragment startFragment;

    public ListOfFilmAdapter(List<ListOfFilm> listOfData, Context mContext, Fragment startFragment) {
        this.listOfData = listOfData;
        this.mContext = mContext;
        this.startFragment = startFragment;
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
        if (isImageListFilm()) {
            with(holder.itemView).load(listOfData.get(position).getPosterPath()).into((ImageView) holder.itemView.findViewById(R.id.imageView));
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FilmDetails nextFragment = new FilmDetails( listOfData.get(holder.getAdapterPosition()));
                    FragmentTransaction transaction = startFragment.getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, nextFragment, "5");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        } else {
            with(holder.itemView).load(listOfData.get(position).getPosterPath() == "" ? "https://www.joblo.com/assets/images/joblo/database-specific-img-225x333.jpg" : listOfData.get(position).getPosterPath())
                    .into((ImageView) holder.itemView.findViewById(R.id.imageView));
            holder.textViewTitle.setText(listOfData.get(position).getFilm_Title());
            holder.textViewRelaseDate.setText(listOfData.get(position).getRelease_Date());
            holder.textViewTime.setText(String.valueOf(listOfData.get(position).getRuntime()));
            String genere="";
            for(int i = 0; i<listOfData.get(position).getGenres().length; i++){
                genere =listOfData.get(position).getGenres()[i]+" - ";
            }
            genere.substring(0,genere.length()-3);
            holder.textViewCategories.setText(genere);
            holder.textViewPlot.setText(listOfData.get(position).getPlot());
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FilmDetails nextFragment = new FilmDetails( listOfData.get(holder.getAdapterPosition()));
                    FragmentTransaction transaction = startFragment.getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, nextFragment, "5");
                    transaction.addToBackStack(null);
                    transaction.commit();
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
        private ListOfFilm film;

        public ViewHolder(View itemView, boolean image) {
            super(itemView);
            if (image) {
                this.imageView = itemView.findViewById(R.id.imageView);
            } else {
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

