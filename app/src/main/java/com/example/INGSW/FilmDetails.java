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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Controllers.FilmTestController;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.concurrent.ExecutionException;

public class FilmDetails extends Fragment {

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

        if (film != null) {
            System.out.println("Titolo ddel film" + film.getFilm_Title());
        } else {
            System.out.println("bad");
        }

        title.setText(film.getFilm_Title());
        Glide.with(root.getContext()).load(pic).into(posterPath);
        plot.setText(film.getPlot());
        releaseDate.setText(film.getRelease_Date());
        String genere = "";
        for (int i = 0; i < film.getGenres().length; i++) {
            genere = genere + film.getGenres()[i] + " - ";
        }
        genere = genere.substring(0, genere.length() - 3);

        genres.setText(genere);
        time.setText(String.valueOf(film.getRuntime()));

        imageButtonWatch = (ImageButton) root.findViewById(R.id.imageButtonWatch);
        imageButtonFavorites = (ImageButton) root.findViewById(R.id.imageButtonFavorites);
        imageButtonToWatch = (ImageButton) root.findViewById(R.id.imageButtonToWatch);
        goToReview = (ImageButton) root.findViewById(R.id.recensioni_button);


        try {
            ftc = new FilmTestController();
            ftc.setIdFilm(String.valueOf(film.getId_Film()));
            ftc.setIdList(String.valueOf(((ToolBarActivity) getActivity()).getContaiinerItem().get("PREFERED")));
            imageButtonFavoritesblue = Boolean.parseBoolean((String) ftc.execute(new String("isInList")).get());
            ftc.isCancelled();
            System.out.println(imageButtonFavoritesblue + " -------------------------------------------");

            ftc = new FilmTestController();
            ftc.setIdFilm(String.valueOf(film.getId_Film()));
            ftc.setIdList(String.valueOf(((ToolBarActivity) getActivity()).getContaiinerItem().get("WATCH")));
            imageButtonWatchblue = Boolean.parseBoolean((String) ftc.execute(new String("isInList")).get());

            ftc.isCancelled();
            System.out.println(imageButtonWatchblue);


            ftc = new FilmTestController();
            ftc.setIdFilm(String.valueOf(film.getId_Film()));
            ftc.setIdList(String.valueOf(((ToolBarActivity) getActivity()).getContaiinerItem().get("TOWATCH")));
            imageButtonToWatchblue = Boolean.parseBoolean((String) ftc.execute(new String("isInList")).get());

            ftc.isCancelled();
            System.out.println(imageButtonToWatchblue);


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (imageButtonFavoritesblue) {

            Glide.with(root.getContext()).load(R.drawable.icons8_star_32px).into(imageButtonFavorites);
        }

        if (imageButtonWatchblue) {

            Glide.with(root.getContext()).load(R.drawable.icons8_closed_eye_50px).into(imageButtonWatch);
        }

        if (imageButtonToWatchblue) {

            Glide.with(root.getContext()).load(R.drawable.icons8_clock_32px_1).into(imageButtonToWatch);
        }

        imageButtonWatch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!imageButtonWatchblue) {
                    Glide.with(root.getContext()).load(R.drawable.icons8_closed_eye_50px).into(imageButtonWatch);
                    ftc = new FilmTestController();
                    ftc.setIdFilm(String.valueOf(film.getId_Film()));
                    ftc.setIdList(String.valueOf(((ToolBarActivity) getActivity()).getContaiinerItem().get("WATCH")));
                    try {
                        ftc.execute(new String("addFilm")).get();
                        ftc.isCancelled();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    imageButtonWatchblue = true;
                } else {
                    Glide.with(root.getContext()).load(R.drawable.icons8_closed_eye_30px_4).into(imageButtonWatch);
                    ftc = new FilmTestController();
                    ftc.setIdFilm(String.valueOf(film.getId_Film()));
                    ftc.setIdList(String.valueOf(((ToolBarActivity) getActivity()).getContaiinerItem().get("WATCH")));
                    try {
                        ftc.execute(new String("removeFilm")).get();
                        ftc.isCancelled();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    imageButtonWatchblue = false;
                }
            }
        });

        PushDownAnim.setPushDownAnimTo(goToReview);
        goToReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment nextFragment;
                FragmentTransaction transaction;
                nextFragment = new ReviewScreen(String.valueOf(film.getId_Film()));
                transaction = FilmDetails.this.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "FilmReviews");

                SpoilerAlertDIalog dlg = new SpoilerAlertDIalog(String.valueOf(film.getId_Film()),transaction);
                dlg.show(getChildFragmentManager(), "SpoilerAllert");
            }
        });

        imageButtonToWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageButtonToWatchblue) {
                    Glide.with(root.getContext()).load(R.drawable.icons8_clock_32px_1).into(imageButtonToWatch);
                    ftc = new FilmTestController();
                    ftc.setIdFilm(String.valueOf(film.getId_Film()));
                    ftc.setIdList(String.valueOf(((ToolBarActivity) getActivity()).getContaiinerItem().get("TOWATCH")));
                    try {
                        ftc.execute(new String("addFilm")).get();
                        ftc.isCancelled();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    imageButtonToWatchblue = true;
                } else {
                    Glide.with(root.getContext()).load(R.drawable.icons8_clock_32px).into(imageButtonToWatch);
                    ftc = new FilmTestController();
                    ftc.setIdFilm(String.valueOf(film.getId_Film()));
                    ftc.setIdList(String.valueOf(((ToolBarActivity) getActivity()).getContaiinerItem().get("TOWATCH")));
                    try {
                        ftc.execute(new String("removeFilm")).get();
                        ftc.isCancelled();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    imageButtonToWatchblue = false;
                }
            }
        });


        imageButtonFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imageButtonFavoritesblue) {
                    Glide.with(root.getContext()).load(R.drawable.icons8_star_32px).into(imageButtonFavorites);
                    ftc = new FilmTestController();
                    ftc.setIdFilm(String.valueOf(film.getId_Film()));
                    ftc.setIdList(String.valueOf(((ToolBarActivity) getActivity()).getContaiinerItem().get("PREFERED")));
                    try {
                        ftc.execute(new String("addFilm")).get();
                        ftc.isCancelled();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    imageButtonFavoritesblue = true;
                } else {
                    Glide.with(root.getContext()).load(R.drawable.icons8_star_26px).into(imageButtonFavorites);
                    ftc = new FilmTestController();
                    ftc.setIdFilm(String.valueOf(film.getId_Film()));
                    ftc.setIdList(String.valueOf(((ToolBarActivity) getActivity()).getContaiinerItem().get("PREFERED")));
                    try {
                        ftc.execute(new String("removeFilm")).get();
                        ftc.isCancelled();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    imageButtonFavoritesblue = false;
                }
            }
        });

        imageButtonCustomList = (ImageButton) root.findViewById(R.id.imageButtonCustomList);
        imageButtonCustomList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCustomlList fragment = new DialogCustomlList();
                fragment.setIdFilmToInsert(film.getId_Film());
                fragment.show(getChildFragmentManager(), "6");
                //new DialogCustomlList().show(getActivity().getSupportFragmentManager(), "6");
            }
        });


        return root;


    }

}
