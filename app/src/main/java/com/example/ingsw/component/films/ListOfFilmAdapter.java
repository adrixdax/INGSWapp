package com.example.ingsw.component.films;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.ChooseActionDialog;
import com.example.ingsw.FilmDetails;
import com.example.ingsw.FilmInCustomList;
import com.example.ingsw.MostSeen;
import com.example.ingsw.MyFavs;
import com.example.ingsw.R;
import com.example.ingsw.SearchFilmScreen;
import com.example.ingsw.SeenFilms;
import com.example.ingsw.ToSee;
import com.example.ingsw.ToolBarActivity;
import com.example.ingsw.UserPrefered;
import com.example.ingsw.home.HomepageScreen;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;
import java.util.Objects;

import static com.bumptech.glide.Glide.with;

@SuppressWarnings("rawtypes")
public class ListOfFilmAdapter extends RecyclerView.Adapter<ListOfFilmAdapter.ViewHolder> {

    private final List<Film> listOfData;
    private final Context mContext;
    private final Fragment startFragment;
    String idList = "";
    private Class css = null;

    public ListOfFilmAdapter(List<Film> listOfData, Context mContext, Fragment startFragment) {
        this.listOfData = listOfData;
        this.mContext = mContext;
        this.startFragment = startFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        if (Objects.equals(css.getCanonicalName(), HomepageScreen.class.getCanonicalName())) {
            listItem = layoutInflater.inflate(R.layout.list_image_film, parent, false);
        } else if (Objects.equals(css.getCanonicalName(), SearchFilmScreen.class.getCanonicalName())) {
            listItem = layoutInflater.inflate(R.layout.list_of_film_in_search_screen, parent, false);
        } else {
            listItem = layoutInflater.inflate(R.layout.list_suggest_film, parent, false);
        }
        return new ViewHolder(listItem, css);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListOfFilmAdapter.ViewHolder holder, int position) {
        if (Objects.equals(css.getCanonicalName(), HomepageScreen.class.getCanonicalName())) {
            try {
                with(holder.itemView).load(listOfData.get(position).getPosterPath() == null ? "https://www.joblo.com/assets/images/joblo/database-specific-img-225x333.jpg" : listOfData.get(position).getPosterPath()).into((ImageView) holder.itemView.findViewById(R.id.userprofilepic_view));
                holder.relativeLayout.setOnClickListener(v -> {
                    FilmDetails nextFragment = new FilmDetails(listOfData.get(position));
                    FragmentTransaction transaction = startFragment.requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, nextFragment, "5");
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (Objects.equals(css.getCanonicalName(), SearchFilmScreen.class.getCanonicalName())) {

            with(holder.itemView).load(listOfData.get(position).getPosterPath() == null ? "https://www.joblo.com/assets/images/joblo/database-specific-img-225x333.jpg" : listOfData.get(position).getPosterPath())
                    .into((ImageView) holder.itemView.findViewById(R.id.userprofilepic_view));
            holder.textViewTitle.setText(listOfData.get(position).getFilm_Title());
            holder.textViewRelaseDate.setText(listOfData.get(position).getRelease_Date());
            holder.textViewTime.setText(String.valueOf(listOfData.get(position).getRuntime()));
            StringBuilder genere = new StringBuilder();
            if (listOfData.get(position).getGenres() != null) {
                for (int i = 0; i < listOfData.get(position).getGenres().length; i++) {
                    genere.append(listOfData.get(position).getGenres()[i]).append(" - ");
                }
                genere = new StringBuilder(genere.substring(0, genere.length() - 3));
            }
            holder.textViewCategories.setText(genere.toString());
            holder.textViewPlot.setText(listOfData.get(position).getPlot().isEmpty() ? "Non è stata trovata alcuna trama per questo film" : listOfData.get(position).getPlot());
            holder.relativeLayout.setOnClickListener(v -> {
                FilmDetails nextFragment = new FilmDetails(listOfData.get(position));
                FragmentTransaction transaction = startFragment.requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "5");
                transaction.addToBackStack(null);
                transaction.commit();
            });
        } else if (css.getCanonicalName().equals(MyFavs.class.getCanonicalName()) ||
                css.getCanonicalName().equals(SeenFilms.class.getCanonicalName()) ||
                css.getCanonicalName().equals(ToSee.class.getCanonicalName()) ||
                css.getCanonicalName().equals(FilmInCustomList.class.getCanonicalName())) {
            with(holder.itemView).load(listOfData.get(position).getPosterPath() == null ? "https://www.joblo.com/assets/images/joblo/database-specific-img-225x333.jpg" : listOfData.get(position).getPosterPath())
                    .into((ImageView) holder.itemView.findViewById(R.id.userprofilepic_view));
            holder.textViewUser.setText(listOfData.get(position).getFilm_Title());
            holder.relativeLayout.setOnClickListener(v -> {
                FilmDetails nextFragment = new FilmDetails(listOfData.get(position));
                FragmentTransaction transaction = startFragment.requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "5");
                transaction.addToBackStack(null);
                transaction.commit();

            });
            holder.relativeLayout.setOnLongClickListener(v -> {
                new ChooseActionDialog(listOfData.get(position), idList, startFragment).show(((ToolBarActivity) mContext).getSupportFragmentManager(), "Choose action");
                notifyDataSetChanged();
                return true;
            });
        } else {
            with(holder.itemView).load(listOfData.get(position).getPosterPath() == null ? "https://www.joblo.com/assets/images/joblo/database-specific-img-225x333.jpg" : listOfData.get(position).getPosterPath())
                    .into((ImageView) holder.itemView.findViewById(R.id.userprofilepic_view));
            if (css.getCanonicalName().equals(UserPrefered.class.getCanonicalName())) {
                holder.textViewUser.setText(listOfData.get(position).getFilm_Title() + "\nIl preferito di " + (listOfData.get(position).getCounter() == 1 ? listOfData.get(position).getCounter() + " utente" : listOfData.get(position).getCounter() + " utenti"));
                holder.relativeLayout.setOnClickListener(v -> {
                    FilmDetails nextFragment = new FilmDetails(listOfData.get(position));
                    FragmentTransaction transaction = startFragment.requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, nextFragment, "5");
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
            } else if (css.getCanonicalName().equals(MostSeen.class.getCanonicalName())) {
                holder.textViewUser.setText("Visto da " + (listOfData.get(position).getCounter() == 1 ? listOfData.get(position).getCounter() + " utente" : listOfData.get(position).getCounter() + " utenti"));
                holder.relativeLayout.setOnClickListener(v -> {
                    FilmDetails nextFragment = new FilmDetails(listOfData.get(position));
                    FragmentTransaction transaction = startFragment.requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, nextFragment, "5");
                    transaction.addToBackStack(null);
                    transaction.commit();
                });
            } else {
                holder.textViewUser.setText("Recensito da " + (listOfData.get(position).getCounter() == 1 ? listOfData.get(position).getCounter() + " utente" : listOfData.get(position).getCounter() + " utenti"));
                holder.relativeLayout.setOnClickListener(v -> {
                    FilmDetails nextFragment = new FilmDetails(listOfData.get(position));
                    FragmentTransaction transaction = startFragment.requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, nextFragment, "5");
                    transaction.addToBackStack(null);
                    transaction.commit();
                });

            }

        }
    }


    @Override
    public int getItemCount() {
        try {
            if (listOfData != null) {
                return listOfData.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setCss(Class css) {
        this.css = css;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;
        public final RelativeLayout relativeLayout;
        public TextView textViewTitle;
        public TextView textViewRelaseDate;
        public TextView textViewTime;
        public TextView textViewCategories;
        public TextView textViewPlot;
        public TextView textViewUser;

        public ViewHolder(View itemView, Class css) {
            super(itemView);
            if (Objects.equals(css.getCanonicalName(), HomepageScreen.class.getCanonicalName())) {
                this.imageView = itemView.findViewById(R.id.userprofilepic_view);
                relativeLayout = itemView.findViewById(R.id.relativeLayoutNotify);
            } else if (Objects.equals(css.getCanonicalName(), SearchFilmScreen.class.getCanonicalName())) {
                this.imageView = itemView.findViewById(R.id.userprofilepic_view);
                this.textViewTitle = itemView.findViewById(R.id.usernick_view);
                this.textViewRelaseDate = itemView.findViewById(R.id.textViewRelaseDate);
                this.textViewTime = itemView.findViewById(R.id.textViewTime);
                this.textViewCategories = itemView.findViewById(R.id.textViewCategories);
                this.textViewPlot = itemView.findViewById(R.id.textViewPlot);
                relativeLayout = itemView.findViewById(R.id.relativeLayoutNotify);
            } else {
                this.imageView = itemView.findViewById(R.id.userprofilepic_view);
                this.textViewUser = itemView.findViewById(R.id.textView6);
                relativeLayout = itemView.findViewById(R.id.relativeLayoutNotify);
            }
            PushDownAnim.setPushDownAnimTo(relativeLayout);
        }
    }
}

