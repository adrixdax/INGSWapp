package com.example.INGSW;

import android.app.Dialog;
import android.content.Context;
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

import com.example.INGSW.Component.DB.Adapters.ContactListAdapter;
import com.example.INGSW.Component.DB.Adapters.UserListsAdapter;
import com.example.INGSW.Component.DB.Classes.Contact;
import com.example.INGSW.Component.DB.Classes.Notify;
import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Component.Films.Film;
import com.example.INGSW.Controllers.FilmTestController;
import com.example.INGSW.Controllers.NotifyTestController;
import com.example.INGSW.Controllers.UserServerController;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class DialogFriendsListOfShare extends AppCompatDialogFragment {


    private RecyclerView recycler;
    private List<Contact> listsFirends = new ArrayList<>();
    private List<Contact> selectedLists = new ArrayList<>();
    private String film;
    private boolean custom;
    private String idList;

    public DialogFriendsListOfShare(String film) {
        this.film = film;
    }

    public DialogFriendsListOfShare(boolean custom, String idList) {
        this.custom = custom;
        this.idList = idList;
    }


    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_list_friends);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);


        recycler = dialog.findViewById(R.id.recyclerView);
        String json = "";
        try {
            UserServerController usc = new UserServerController();
            usc.setUserId(((ToolBarActivity) getActivity()).getUid());
            json = (String) usc.execute(new String("getFriends")).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            listsFirends = (List<Contact>) getJsonToDecode(json, Contact.class);
            if (listsFirends != null) {
                recycler.setAdapter(new ContactListAdapter(listsFirends, getContext(), this.selectedLists));
                recycler.setLayoutManager(new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false));
                recycler.setHasFixedSize(false);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        Button insertInLists = (Button) dialog.getWindow().findViewById(R.id.ShareWhitFriends);
        insertInLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!custom) {
                    if (!selectedLists.isEmpty()) {
                        for (Contact singlelist : selectedLists) {
                            NotifyTestController ntc = new NotifyTestController();
                            try {
                                ntc.setIdSender(((ToolBarActivity) getActivity()).getUid());
                                if (singlelist.getUser1().equals(ntc.getIdSender())) {
                                    ntc.setIdReceiver(singlelist.getUser2());
                                } else {
                                    ntc.setIdReceiver(singlelist.getUser1());
                                }
                                ntc.setType("FILM");
                                ntc.setIdRecordref(film);
                                ntc.execute(new String("shareFriendsContent")).get();
                                ntc.isCancelled();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    dismiss();

                } else {

                    if (!selectedLists.isEmpty()) {
                        for (Contact singlelist : selectedLists) {
                            NotifyTestController ntc = new NotifyTestController();
                            try {
                                ntc.setIdSender(((ToolBarActivity) getActivity()).getUid());
                                if (singlelist.getUser1().equals(ntc.getIdSender())) {
                                    ntc.setIdReceiver(singlelist.getUser2());
                                } else {
                                    ntc.setIdReceiver(singlelist.getUser1());
                                }
                                ntc.setType("LIST");
                                ntc.setIdRecordref(idList);
                                ntc.execute(new String("shareFriendsContent")).get();
                                ntc.isCancelled();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    dismiss();


                }

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

}
