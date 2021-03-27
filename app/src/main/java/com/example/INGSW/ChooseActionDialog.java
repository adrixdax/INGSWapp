package com.example.INGSW;

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

import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.Controllers.UserServerController;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;

public class ChooseActionDialog extends AppCompatDialogFragment {

    Context dialog_ctx;
    Button share, remove;
    TextView filmTitle;
    boolean custom = false;
    Film film;
    String idList;
    String titleList;

    public ChooseActionDialog(Context ctx, Film film, String idList) {
        this.dialog_ctx = ctx;
        this.film = film;
        this.idList = idList;
    }

    public ChooseActionDialog(Context ctx, boolean custom, String idList, String titleList) {
        this.dialog_ctx = ctx;
        this.custom = custom;
        this.titleList=titleList;
        this.idList = idList;
    }

    public ChooseActionDialog(Context ctx) {
        new ChooseActionDialog(ctx);
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);


        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.film_action_choose_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        share = dialog.findViewById(R.id.share_button);
        remove = dialog.findViewById(R.id.Remove_button);
        filmTitle = dialog.findViewById(R.id.filmTitle_view);

        PushDownAnim.setPushDownAnimTo(share, remove);
        filmTitle.setText(film==null?titleList:film.getFilm_Title());

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!custom) {
                    FilmTestController ftc = new FilmTestController();
                    try {
                        ftc.setIdList(idList);
                        ftc.setIdFilm(String.valueOf(film.getId_Film()));
                        ftc.execute(new String("removeFilm")).get();
                        ftc.isCancelled();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dismiss();

                }else{

                    UserServerController usc = new UserServerController();
                    try {
                        usc.setIdList(idList);
                        usc.setUserId(((ToolBarActivity)getActivity()).getUid());
                        usc.execute(new String("deleteCustomList")).get();
                        usc.isCancelled();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dismiss();


                }
            }
        });


        return dialog;

    }

}
