package com.example.INGSW;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.INGSW.Component.Films.ListOfFilm;

import de.hdodenhof.circleimageview.CircleImageView;

public class FilmDetails extends Fragment {

    private ListOfFilm film;

    private TextView title;
    private ImageView posterPath;
    private TextView plot;
    private TextView releaseDate;
    private TextView genres;
    private TextView time;
    private boolean imageButtonWatchblue=false;
    private boolean imageButtonToWatchblue=false;
    private boolean imageButtonFavoritesblue=false;


    public FilmDetails(ListOfFilm film) {
        this.film = film;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.film_details,container,false);

        title = (TextView) root.findViewById(R.id.textViewFilmTitle);
        posterPath = (ImageView) root.findViewById(R.id.imageViewPosterPath);
        plot = (TextView) root.findViewById(R.id.textViewPlot);
        releaseDate = (TextView) root.findViewById(R.id.textViewRelaseDate);
        genres = (TextView) root.findViewById(R.id.textViewCategories);
        time = (TextView) root.findViewById(R.id.textViewTime);

        String pic =  film.getPosterPath() == "" ? "https://www.joblo.com/assets/images/joblo/database-specific-img-225x333.jpg" : film.getPosterPath();

        if(film != null){
            System.out.println("Titolo ddel film" + film.getFilm_Title());
        }else{
            System.out.println("Posrco il topocazzo");
        }

        title.setText(film.getFilm_Title());
        Glide.with(root.getContext()).load(pic).into(posterPath);
        plot.setText(film.getPlot());
        releaseDate.setText(film.getRelease_Date());
        String genere="";
        for(int i = 0; i<film.getGenres().length; i++){
            genere =film.getGenres()[i]+" - ";
        }
        genere.substring(0,genere.length()-3);

        genres.setText(genere);
        time.setText(String.valueOf(film.getRuntime()));

        ImageButton imageButtonWatch = root.findViewById(R.id.imageButtonWatch);
        imageButtonWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageButtonWatchblue) {
                    Glide.with(root.getContext()).load(R.drawable.icons8_closed_eye_50px).into(imageButtonWatch);
                    imageButtonWatchblue=true;
                }else{
                    Glide.with(root.getContext()).load(R.drawable.icons8_closed_eye_30px_4).into(imageButtonWatch);
                    imageButtonWatchblue=false;
                }
            }
        });

        ImageButton imageButtonToWatch = root.findViewById(R.id.imageButtonToWatch);
        imageButtonToWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageButtonToWatchblue) {
                    Glide.with(root.getContext()).load(R.drawable.icons8_clock_32px_1).into(imageButtonToWatch);
                    imageButtonToWatchblue=true;
                }else{
                    Glide.with(root.getContext()).load(R.drawable.icons8_clock_32px).into(imageButtonToWatch);
                    imageButtonToWatchblue=false;
                }
            }
        });

        ImageButton imageButtonFavorites = root.findViewById(R.id.imageButtonFavorites);
        imageButtonFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!imageButtonFavoritesblue) {
                    Glide.with(root.getContext()).load(R.drawable.icons8_star_32px).into(imageButtonFavorites);
                    imageButtonFavoritesblue=true;
                }else{
                    Glide.with(root.getContext()).load(R.drawable.icons8_star_26px).into(imageButtonFavorites);
                    imageButtonFavoritesblue=false;
                }
            }
        });

        return root;

    }
}
