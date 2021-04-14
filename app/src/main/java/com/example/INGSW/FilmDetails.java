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
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.thekhaeng.pushdownanim.PushDownAnim;

import teaspoon.annotations.OnUi;

public class FilmDetails extends Fragment implements RetrofitBooleanInterface {

    private Film film;

    private TextView title;
    private ImageView posterPath;
    private TextView plot;
    private TextView releaseDate;
    private TextView genres;
    private TextView time;
    private boolean imageButtonWatchblue = false;
    private boolean imageButtonToWatchblue = false;
    private boolean imageButtonFavoritesblue = false;
    private FilmTestController ftc = new FilmTestController();
    private ImageButton imageButtonWatch;
    private ImageButton imageButtonFavorites;
    private ImageButton imageButtonToWatch;
    private ImageButton imageButtonCustomList;
    private ImageButton goToReview;
    private short counter=0;


    public FilmDetails(Film film) {
        this.film = film;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.film_details, container, false);

        title = (TextView) root.findViewById(R.id.textViewFilmTitle);
        posterPath = (ImageView) root.findViewById(R.id.imageViewPosterPath);
        plot = (TextView) root.findViewById(R.id.textViewPlot);
        releaseDate = (TextView) root.findViewById(R.id.textViewRelaseDate);
        genres = (TextView) root.findViewById(R.id.textViewCategories);
        time = (TextView) root.findViewById(R.id.textViewTime);

        String pic = film.getPosterPath() == "" ? "https://www.joblo.com/assets/images/joblo/database-specific-img-225x333.jpg" : film.getPosterPath();

        title.setText(film.getFilm_Title());
        Glide.with(root.getContext()).load(pic).into(posterPath);
        plot.setText(film.getPlot());
        releaseDate.setText(film.getRelease_Date());
        StringBuilder genere = new StringBuilder();
        if (film.getGenres() != null)
        {
            for (int i = 0; i < film.getGenres().length; i++) {
                genere.append(film.getGenres()[i]).append(" - ");
            }
            genere = new StringBuilder(genere.substring(0, genere.length() - 3));
        }
        genres.setText(genere.toString());
        time.setText(String.valueOf(film.getRuntime()));

        imageButtonWatch = (ImageButton) root.findViewById(R.id.imageButtonWatch);
        imageButtonFavorites = (ImageButton) root.findViewById(R.id.imageButtonFavorites);
        imageButtonToWatch = (ImageButton) root.findViewById(R.id.imageButtonToWatch);
        goToReview = (ImageButton) root.findViewById(R.id.recensioni_button);


        ((ToolBarActivity)getActivity()).triggerProgessBar();

        RetrofitResponse.getResponse(
                    "Type=PostRequest&idList=" + ((ToolBarActivity) getActivity()).getContaiinerItem().get("PREFERED")
                            + "&idFilm=" + film.getId_Film(),
                    FilmDetails.this,getContext(),Film.class.getCanonicalName(),"isFilmInList",imageButtonFavorites);

        RetrofitResponse.getResponse(
                    "Type=PostRequest&idList=" + ((ToolBarActivity) getActivity()).getContaiinerItem().get("WATCH")
                            + "&idFilm=" + film.getId_Film(),
                    FilmDetails.this,getContext(),Film.class.getCanonicalName(),"isFilmInList",imageButtonWatch);
        RetrofitResponse.getResponse(
                    "Type=PostRequest&idList=" + ((ToolBarActivity) getActivity()).getContaiinerItem().get("TOWATCH")
                            + "&idFilm=" + film.getId_Film(),
                    FilmDetails.this,getContext(),Film.class.getCanonicalName(),"isFilmInList",imageButtonToWatch);

        imageButtonWatch.setOnClickListener(v -> {
            if (!imageButtonWatchblue) {
                Glide.with(root.getContext()).load(R.drawable.icons8_closed_eye_50px).into(imageButtonWatch);
                RetrofitResponse.getResponse(
                        "Type=PostRequest&idList=" + ((ToolBarActivity) getActivity()).getContaiinerItem().get("WATCH")
                                + "&idFilm=" + film.getId_Film() +"&addFilm=true",
                        FilmDetails.this,getContext(),Film.class.getCanonicalName(),"addFilm");

                imageButtonWatchblue = true;
            } else {
                Glide.with(root.getContext()).load(R.drawable.icons8_closed_eye_30px_4).into(imageButtonWatch);
                RetrofitResponse.getResponse(
                        "Type=PostRequest&idList=" + ((ToolBarActivity) getActivity()).getContaiinerItem().get("WATCH")
                                + "&idFilm=" + film.getId_Film() +"&removeFilm=true",
                        FilmDetails.this,getContext(),Film.class.getCanonicalName(),"removeFilmInList");

                imageButtonWatchblue = false;
            }
        });

        imageButtonToWatch.setOnClickListener(v -> {
            if (!imageButtonToWatchblue) {
                Glide.with(root.getContext()).load(R.drawable.icons8_clock_32px_1).into(imageButtonToWatch);
                RetrofitResponse.getResponse(
                        "Type=PostRequest&idList=" + ((ToolBarActivity) getActivity()).getContaiinerItem().get("TOWATCH")
                                + "&idFilm=" + film.getId_Film() +"&addFilm=true",
                        FilmDetails.this,getContext(),Film.class.getCanonicalName(),"addFilm");
                imageButtonToWatchblue = true;
            } else {
                Glide.with(root.getContext()).load(R.drawable.icons8_clock_32px).into(imageButtonToWatch);
                RetrofitResponse.getResponse(
                        "Type=PostRequest&idList=" + ((ToolBarActivity) getActivity()).getContaiinerItem().get("TOWATCH")
                                + "&idFilm=" + film.getId_Film() +"&removeFilm=true",
                        FilmDetails.this,getContext(),Film.class.getCanonicalName(),"removeFilmInList");
                imageButtonToWatchblue = false;
            }
        });


        imageButtonFavorites.setOnClickListener(v -> {
            if (!imageButtonFavoritesblue) {
                Glide.with(root.getContext()).load(R.drawable.icons8_star_32px).into(imageButtonFavorites);
                RetrofitResponse.getResponse(
                        "Type=PostRequest&idList=" + ((ToolBarActivity) getActivity()).getContaiinerItem().get("PREFERED")
                                + "&idFilm=" + film.getId_Film() +"&addFilm=true",
                        FilmDetails.this,getContext(),Film.class.getCanonicalName(),"addFilm");
                imageButtonFavoritesblue = true;
            } else {
                Glide.with(root.getContext()).load(R.drawable.icons8_star_26px).into(imageButtonFavorites);
                RetrofitResponse.getResponse(
                        "Type=PostRequest&idList=" + ((ToolBarActivity) getActivity()).getContaiinerItem().get("PREFERED")
                                + "&idFilm=" + film.getId_Film() +"&removeFilm=true",
                        FilmDetails.this,getContext(),Film.class.getCanonicalName(),"removeFilmInList");

                imageButtonFavoritesblue = false;
            }
        });

        PushDownAnim.setPushDownAnimTo(goToReview);
        goToReview.setOnClickListener(v -> {
            Fragment nextFragment;
            FragmentTransaction transaction;
            nextFragment = new ReviewScreen(String.valueOf(film.getId_Film()));
            transaction = FilmDetails.this.getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, nextFragment, "FilmReviews");

            if(imageButtonWatchblue){
                transaction.addToBackStack(null);
                transaction.commit();
            }else {
                SpoilerAlertDIalog dlg = new SpoilerAlertDIalog(String.valueOf(film.getId_Film()), transaction);
                dlg.show(getChildFragmentManager(), "SpoilerAllert");
            }
        });


        imageButtonCustomList = (ImageButton) root.findViewById(R.id.imageButtonCustomList);
        imageButtonCustomList.setOnClickListener(v -> {
            DialogCustomlList fragment = new DialogCustomlList();
            fragment.setIdFilmToInsert(film.getId_Film());
            fragment.show(getChildFragmentManager(), "CustomList");
        });


        return root;


    }

    @OnUi
    public void glideObject(Boolean b, Object toGlide) {
        if (((ImageButton)toGlide).equals(imageButtonFavorites) && b){
            Glide.with(getContext()).load(R.drawable.icons8_star_32px).into(imageButtonFavorites);
            imageButtonFavoritesblue=true;
        }
        if (((ImageButton)toGlide).equals(imageButtonWatch) && b) {
            Glide.with(getContext()).load(R.drawable.icons8_closed_eye_50px).into(imageButtonWatch);
            imageButtonWatchblue=true;
        }

        if (((ImageButton)toGlide).equals(imageButtonToWatch) && b) {
            Glide.with(getContext()).load(R.drawable.icons8_clock_32px_1).into(imageButtonToWatch);
            imageButtonToWatchblue=true;
        }
        counter++;
        if (counter==3) ((ToolBarActivity)getActivity()).stopProgressBar();
    }
}
