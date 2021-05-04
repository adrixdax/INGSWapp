package com.example.ingsw;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.example.ingsw.component.films.Film;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

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
            DialogFriendsListOfShare fragment;
            if (!custom) {
                fragment = new DialogFriendsListOfShare(String.valueOf(film.getId_Film()));
            } else {
                fragment = new DialogFriendsListOfShare(true, idList);
            }
            fragment.show(getParentFragmentManager(), "DialogFriendsListOfShare");
            dismiss();
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public synchronized void onClick(View v) {
                if (!custom) {
                    RetrofitResponse.getResponse("Type=PostRequest&idList=" + idList + "&idFilm=" + film.getId_Film() + "&removeFilm=true", ChooseActionDialog.this, getContext(), "removeFilmInList");
                    try {
                        wait(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    RetrofitResponse.getResponse(
                            "Type=PostRequest&idList=" + idList,
                            fg, fg.getContext(), "getFilmInList");
                } else {
                    RetrofitResponse.getResponse("Type=PostRequest&removeList=true&idUser=" + ((ToolBarActivity) requireActivity()).getUid() + "&idList=" + idList, ChooseActionDialog.this, getContext(), "deleteCustomList");
                    try {
                        wait(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    RetrofitResponse.getResponse("Type=PostRequest&idUser=" + ((ToolBarActivity) requireActivity()).getUid() + "&custom=true&idFilm= -1", fg, fg.getContext(), "getList");
                }
                dismiss();
            }
        });
        return dialog;
    }

}
