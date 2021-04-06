package com.example.INGSW;

import android.app.Dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.INGSW.Component.DB.Adapters.UserListsAdapter;

import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Controllers.FilmTestController;

import com.example.INGSW.Controllers.UserServerController;
import com.fasterxml.jackson.core.JsonProcessingException;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class DialogCustomlList extends AppCompatDialogFragment {


    private RecyclerView recycler;
    private List<UserLists> customLists = new ArrayList<>();
    private List<UserLists> selectedLists = new ArrayList<>();
    private int idFilmToInsert;

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_list_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);


        recycler = dialog.findViewById(R.id.recyclerView);
        String json = "";
        try {
            UserServerController usc = new UserServerController();
            usc.setUserId(((ToolBarActivity) getActivity()).getUid());
            usc.setIdFilm(String.valueOf(idFilmToInsert));
            json = (String) usc.execute(new String("custom")).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            customLists = (List<UserLists>) getJsonToDecode(json, UserLists.class);
            if (customLists != null) {
                recycler.setAdapter(new UserListsAdapter(customLists, DialogCustomlList.class, this.selectedLists));
                recycler.setLayoutManager(new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false));
                recycler.setHasFixedSize(false);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        Button insertInLists = (Button) dialog.getWindow().findViewById(R.id.InsertInListsbutton);
        insertInLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (UserLists singlelist : selectedLists) {

                    FilmTestController ftc = new FilmTestController();
                    ftc.setIdFilm(String.valueOf(idFilmToInsert));
                    ftc.setIdList(String.valueOf(singlelist.getIdUserList()));
                    try {
                        ftc.execute(new String("addFilm")).get();
                        ftc.isCancelled();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                selectedLists.clear();
                dialog.closeOptionsMenu();
                dialog.cancel();
                dialog.dismiss();
            }
        });

        /*Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.film_action_choose_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(getActivity().getLayoutInflater().inflate(R.layout.film_action_choose_dialog, new ConstraintLayout(getActivity()), false));
       */
        return dialog;

    }

    public int getIdFilmToInsert() {
        return idFilmToInsert;
    }

    public void setIdFilmToInsert(int idFilmToInsert) {
        this.idFilmToInsert = idFilmToInsert;
    }
}
