package com.example.INGSW;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.INGSW.Component.DB.Adapters.CustomListsAdapter;
import com.example.INGSW.Component.DB.Classes.Contact;
import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.Controllers.UserServerController;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChooseActionDialog extends AppCompatDialogFragment {


    Button share, remove;
    TextView filmTitle;
    Fragment fg;

    public boolean isCustom() {
        return custom;
    }

    boolean custom = false;
    Film film;
    String idList;
    String titleList;

    public ChooseActionDialog(Film film, String idList, Fragment active) {
        this.film = film;
        this.idList = idList;
        this.fg=active;
    }

    public ChooseActionDialog(boolean custom, String idList, String titleList,Fragment active) {
        this.custom = custom;
        this.titleList = titleList;
        this.idList = idList;
        this.fg=active;
    }

    public ChooseActionDialog(Context ctx) {
        new ChooseActionDialog(ctx);
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.film_action_choose_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        share = dialog.findViewById(R.id.share_button);
        remove = dialog.findViewById(R.id.Remove_button);
        filmTitle = dialog.findViewById(R.id.filmTitle_view);

        PushDownAnim.setPushDownAnimTo(share, remove);
        filmTitle.setText(film == null ? titleList : film.getFilm_Title());
        share.setOnClickListener(v -> {
            if (!custom) {
                DialogFriendsListOfShare fragment = new DialogFriendsListOfShare(String.valueOf(film.getId_Film()));
                fragment.show(getChildFragmentManager(), "DialogFriendsListOfShare");
            } else {
                DialogFriendsListOfShare fragment = new DialogFriendsListOfShare(true, idList);
                fragment.show(getChildFragmentManager(), "DialogFriendsListOfShare");
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public synchronized void onClick(View v) {
                if (!custom) {
                    RetrofitResponse.getResponse("Type=PostRequest&idList=" + idList + "&idFilm=" + film.getId_Film() + "&removeFilm=true", ChooseActionDialog.this, getContext(), Film.class.getCanonicalName(), "removeFilmInList");
                    try {
                        wait(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    RetrofitResponse.getResponse(
                            "Type=PostRequest&idList=" + idList,
                            fg, fg.getContext(), Film.class.getCanonicalName(), "getFilmInList");
                    dismiss();
                } else {
                    RetrofitResponse.getResponse("Type=PostRequest&removeList=true&idUser=" + ((ToolBarActivity) getActivity()).getUid() + "&idList=" + idList, ChooseActionDialog.this, getContext(), Film.class.getCanonicalName(), "deleteCustomList");
                    try {
                        wait(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    RetrofitResponse.getResponse("Type=PostRequest&idUser=" + ((ToolBarActivity) getActivity()).getUid() + "&custom=true&idFilm= -1", fg, fg.getContext(), UserLists.class.getCanonicalName(), "getList");
                    dismiss();
                }
            }
        });
        return dialog;
    }

}
