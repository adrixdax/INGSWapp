package com.example.INGSW.Component.Films;

import android.content.Context;
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

import com.example.INGSW.ChooseActionDialog;
import com.example.INGSW.FilmDetails;
import com.example.INGSW.R;
import com.example.INGSW.SearchFilmScreen;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.home.HomepageScreen;

import java.util.List;

import static com.bumptech.glide.Glide.with;

public class ListOfFilmAdapter extends RecyclerView.Adapter<ListOfFilmAdapter.ViewHolder> {

    private final List<ListOfFilm> listOfData;


    private Class css = null;

    private final Context mContext;
    private final Fragment startFragment;

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
        if (css.getCanonicalName().equals(HomepageScreen.class.getCanonicalName())) {
            listItem = layoutInflater.inflate(R.layout.list_image_film, parent, false);
        } else if (css.getCanonicalName().equals(SearchFilmScreen.class.getCanonicalName())) {
            listItem = layoutInflater.inflate(R.layout.list_of_film_in_search_screen, parent, false);
        } else {
            listItem = layoutInflater.inflate(R.layout.list_suggest_film, parent, false);
        }
        return new ViewHolder(listItem, css);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfFilmAdapter.ViewHolder holder, int position) {
        if (css.getCanonicalName().equals(HomepageScreen.class.getCanonicalName())) {
            try {
                with(holder.itemView).load(listOfData.get(position).getPosterPath()).into((ImageView) holder.itemView.findViewById(R.id.userprofilepic_view));
                holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FilmDetails nextFragment = new FilmDetails(listOfData.get(holder.getAdapterPosition()));
                        FragmentTransaction transaction = startFragment.getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment, nextFragment, "5");
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (css.getCanonicalName().equals(SearchFilmScreen.class.getCanonicalName())) {

            with(holder.itemView).load(listOfData.get(position).getPosterPath().equals("") ? "https://www.joblo.com/assets/images/joblo/database-specific-img-225x333.jpg" : listOfData.get(position).getPosterPath())
                    .into((ImageView) holder.itemView.findViewById(R.id.userprofilepic_view));
            holder.textViewTitle.setText(listOfData.get(position).getFilm_Title());
            holder.textViewRelaseDate.setText(listOfData.get(position).getRelease_Date());
            holder.textViewTime.setText(String.valueOf(listOfData.get(position).getRuntime()));
            String genere = "";
            for (int i = 0; i < listOfData.get(position).getGenres().length; i++) {
                genere = genere + listOfData.get(position).getGenres()[i] + " - ";
            }
            genere = genere.substring(0, genere.length() - 3);
            holder.textViewCategories.setText(genere);
            holder.textViewPlot.setText(listOfData.get(position).getPlot());
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FilmDetails nextFragment = new FilmDetails(listOfData.get(holder.getAdapterPosition()));
                    FragmentTransaction transaction = startFragment.getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, nextFragment, "5");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        } else {
                with(holder.itemView).load(listOfData.get(position).getPosterPath() == "" ? "https://www.joblo.com/assets/images/joblo/database-specific-img-225x333.jpg" : listOfData.get(position).getPosterPath())
                        .into((ImageView) holder.itemView.findViewById(R.id.userprofilepic_view));
                holder.textViewUser.setText(listOfData.get(position).getFilm_Title());
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FilmDetails nextFragment = new FilmDetails(listOfData.get(holder.getAdapterPosition()));
                    FragmentTransaction transaction = startFragment.getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, nextFragment, "5");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });


            holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(mContext,"its the vibe for me",Toast.LENGTH_SHORT).show();
                    System.out.println("VERAMENTE NON LI MOSTRA, A ME NON LI MOSTRA MAI 0.25");

                    //ChooseActionDialog dlg = new ChooseActionDialog(mContext);
                    ChooseActionDialog dlg = new ChooseActionDialog(mContext,listOfData.get(holder.getAdapterPosition()).getFilm_Title());

                    dlg.show(((ToolBarActivity) mContext).getSupportFragmentManager(), "Choose action");








                    /*Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.film_action_choose_dialog);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCancelable(true);
                    dialog.show();

                     */



                    return true;
                }
            });
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


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        public TextView textViewTitle;
        public TextView textViewRelaseDate;
        public TextView textViewTime;
        public TextView textViewCategories;
        public TextView textViewPlot;
        public TextView textViewUser;


        private ListOfFilm film;

        public ViewHolder(View itemView, Class css) {
            super(itemView);
            if (css.getCanonicalName().equals(HomepageScreen.class.getCanonicalName())) {
                this.imageView = itemView.findViewById(R.id.userprofilepic_view);
                relativeLayout = itemView.findViewById(R.id.relativeLayoutNotify);
            } else if (css.getCanonicalName().equals(SearchFilmScreen.class.getCanonicalName())) {

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
        }
    }

    public Class getCss() {
        return css;
    }

    public void setCss(Class css) {
        this.css = css;
    }
}

