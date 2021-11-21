package com.example.ingsw;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.ingsw.component.films.Film;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.thekhaeng.pushdownanim.PushDownAnim;

import teaspoon.annotations.OnUi;

public class FilmDetails extends Fragment {

    private final Film film;

    private boolean imageButtonWatchblue = false;
    private boolean imageButtonToWatchblue = false;
    private boolean imageButtonFavoritesblue = false;
    private ImageButton imageButtonWatch;
    private ImageButton imageButtonFavorites;
    private ImageButton imageButtonToWatch;
    private short counter;


    public FilmDetails(Film film) {
        this.film = film;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        counter = 0;
        View root = inflater.inflate(R.layout.film_details, container, false);

        TextView title = root.findViewById(R.id.textViewFilmTitle);
        ImageView posterPath = root.findViewById(R.id.imageViewPosterPath);
        TextView plot = root.findViewById(R.id.textViewPlot);
        TextView releaseDate = root.findViewById(R.id.textViewRelaseDate);
        TextView genres = root.findViewById(R.id.textViewCategories);
        TextView time = root.findViewById(R.id.textViewTime);

        String pic = film.getPosterPath() == null ? "https://www.joblo.com/assets/images/joblo/database-specific-img-225x333.jpg" : film.getPosterPath();

        title.setText(film.getFilm_Title());
        Glide.with(root.getContext()).load(pic).into(posterPath);
        plot.setText(film.getPlot());
        plot.setMovementMethod(new ScrollingMovementMethod());
        releaseDate.setText(film.getRelease_Date());
        StringBuilder genere = new StringBuilder();
        if (film.getGenres() != null) {
            for (int i = 0; i < film.getGenres().length; i++) {
                genere.append(film.getGenres()[i]).append(" - ");
            }
            genere = new StringBuilder(genere.substring(0, genere.length() - 3));
        }
        genres.setText(genere.toString());
        time.setText(new StringBuilder().append(film.getRuntime()).append(" min").toString());

        imageButtonWatch = root.findViewById(R.id.imageButtonWatch);
        imageButtonFavorites = root.findViewById(R.id.imageButtonFavorites);
        imageButtonToWatch = root.findViewById(R.id.imageButtonToWatch);
        ImageButton goToReview = root.findViewById(R.id.recensioni_button);


        ((ToolBarActivity) requireActivity()).triggerProgessBar();

        RetrofitResponse.getResponse(
                "Type=PostRequest&idList=" + ((ToolBarActivity) requireActivity()).getContaiinerItem().get("PREFERED")
                        + "&idFilm=" + film.getId_Film(),
                FilmDetails.this, getContext(), "isFilmInList", imageButtonFavorites);

        RetrofitResponse.getResponse(
                "Type=PostRequest&idList=" + ((ToolBarActivity) requireActivity()).getContaiinerItem().get("WATCH")
                        + "&idFilm=" + film.getId_Film(),
                FilmDetails.this, getContext(), "isFilmInList", imageButtonWatch);
        RetrofitResponse.getResponse(
                "Type=PostRequest&idList=" + ((ToolBarActivity) requireActivity()).getContaiinerItem().get("TOWATCH")
                        + "&idFilm=" + film.getId_Film(),
                FilmDetails.this, getContext(), "isFilmInList", imageButtonToWatch);

        imageButtonWatch.setOnClickListener(v -> {
            if (!imageButtonWatchblue) {
                Glide.with(root.getContext()).load(R.drawable.icons8_closed_eye_50px).into(imageButtonWatch);
                RetrofitResponse.getResponse(
                        "Type=PostRequest&idList=" + ((ToolBarActivity) requireActivity()).getContaiinerItem().get("WATCH")
                                + "&idFilm=" + film.getId_Film() + "&addFilm=true",
                        FilmDetails.this, getContext(), "addFilm");
                Toast.makeText(this.getContext(), "Film aggiunto ai film visti", Toast.LENGTH_LONG).show();
                imageButtonWatchblue = true;
            } else {
                Glide.with(root.getContext()).load(R.drawable.icons8_closed_eye_30px_4).into(imageButtonWatch);
                RetrofitResponse.getResponse(
                        "Type=PostRequest&idList=" + ((ToolBarActivity) requireActivity()).getContaiinerItem().get("WATCH")
                                + "&idFilm=" + film.getId_Film() + "&removeFilm=true",
                        FilmDetails.this, getContext(), "removeFilmInList");
                Toast.makeText(this.getContext(), "Film rimosso dai film visti", Toast.LENGTH_LONG).show();
                imageButtonWatchblue = false;
            }
        });

        imageButtonToWatch.setOnClickListener(v -> {
            if (!imageButtonToWatchblue) {
                Glide.with(root.getContext()).load(R.drawable.icons8_clock_32px_1).into(imageButtonToWatch);
                RetrofitResponse.getResponse(
                        "Type=PostRequest&idList=" + ((ToolBarActivity) requireActivity()).getContaiinerItem().get("TOWATCH")
                                + "&idFilm=" + film.getId_Film() + "&addFilm=true",
                        FilmDetails.this, getContext(), "addFilm");
                Toast.makeText(this.getContext(), "Film aggiunto ai \"da guardare\"", Toast.LENGTH_LONG).show();
                imageButtonToWatchblue = true;
            } else {
                Glide.with(root.getContext()).load(R.drawable.icons8_clock_32px).into(imageButtonToWatch);
                RetrofitResponse.getResponse(
                        "Type=PostRequest&idList=" + ((ToolBarActivity) requireActivity()).getContaiinerItem().get("TOWATCH")
                                + "&idFilm=" + film.getId_Film() + "&removeFilm=true",
                        FilmDetails.this, getContext(), "removeFilmInList");
                Toast.makeText(this.getContext(), "Film rimosso dai \"da guardare\"", Toast.LENGTH_LONG).show();
                imageButtonToWatchblue = false;
            }
        });


        imageButtonFavorites.setOnClickListener(v -> {
            if (!imageButtonFavoritesblue) {
                Glide.with(root.getContext()).load(R.drawable.icons8_star_32px).into(imageButtonFavorites);
                RetrofitResponse.getResponse(
                        "Type=PostRequest&idList=" + ((ToolBarActivity) requireActivity()).getContaiinerItem().get("PREFERED")
                                + "&idFilm=" + film.getId_Film() + "&addFilm=true",
                        FilmDetails.this, getContext(), "addFilm");
                Toast.makeText(this.getContext(), "Film aggiunto ai preferiti", Toast.LENGTH_LONG).show();
                imageButtonFavoritesblue = true;
            } else {
                Glide.with(root.getContext()).load(R.drawable.icons8_star_26px).into(imageButtonFavorites);
                RetrofitResponse.getResponse(
                        "Type=PostRequest&idList=" + ((ToolBarActivity) requireActivity()).getContaiinerItem().get("PREFERED")
                                + "&idFilm=" + film.getId_Film() + "&removeFilm=true",
                        FilmDetails.this, getContext(), "removeFilmInList");
                Toast.makeText(this.getContext(), "Film rimosso dai preferiti", Toast.LENGTH_LONG).show();
                imageButtonFavoritesblue = false;
            }
        });

        PushDownAnim.setPushDownAnimTo(goToReview);
        goToReview.setOnClickListener(v -> {
            Fragment nextFragment;
            FragmentTransaction transaction;
            nextFragment = new ReviewScreen(String.valueOf(film.getId_Film()));
            transaction = FilmDetails.this.requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, nextFragment, "FilmReviews");

            if (imageButtonWatchblue) {
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                SpoilerAlertDIalog dlg = new SpoilerAlertDIalog(transaction);
                dlg.show(getChildFragmentManager(), "SpoilerAllert");
            }
        });


        ImageButton imageButtonCustomList = root.findViewById(R.id.imageButtonCustomList);
        imageButtonCustomList.setOnClickListener(v -> {
            DialogCustomlList fragment = new DialogCustomlList();
            fragment.setIdFilmToInsert(film.getId_Film());
            fragment.show(getChildFragmentManager(), "CustomList");
        });

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME,"FilmDetail");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,this.getClass().getSimpleName());
        ToolBarActivity.setNewBundle("FilmDetail",bundle);

        return root;


    }

    @OnUi
    public void glideObject(Boolean b, Object toGlide) {
        if (toGlide.equals(imageButtonFavorites) && b) {
            Glide.with(requireContext()).load(R.drawable.icons8_star_32px).into(imageButtonFavorites);
            imageButtonFavoritesblue = true;
        }
        if (toGlide.equals(imageButtonWatch) && b) {
            Glide.with(requireContext()).load(R.drawable.icons8_closed_eye_50px).into(imageButtonWatch);
            imageButtonWatchblue = true;
        }

        if (toGlide.equals(imageButtonToWatch) && b) {
            Glide.with(requireContext()).load(R.drawable.icons8_clock_32px_1).into(imageButtonToWatch);
            imageButtonToWatchblue = true;
        }
        counter++;
        if (counter == 3) ((ToolBarActivity) requireActivity()).stopProgressBar();
    }
}
