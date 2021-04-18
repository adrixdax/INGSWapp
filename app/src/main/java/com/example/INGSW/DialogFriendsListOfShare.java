package com.example.INGSW;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Adapters.ContactListAdapter;
import com.example.INGSW.Component.DB.Classes.Contact;
import com.example.INGSW.Controllers.NotifyTestController;
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.Controllers.UserServerController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;

public class DialogFriendsListOfShare extends AppCompatDialogFragment implements RetrofitListInterface {


    private RecyclerView recycler;
    private final List<Contact> selectedLists = new ArrayList<>();
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
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_list_friends);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        recycler = dialog.findViewById(R.id.recyclerView);

        RetrofitResponse.getResponse("Type=PostRequest&isFriends=true&idUser=" + ((ToolBarActivity) requireActivity()).getUid(),this,getContext(),"getFriends");


        Button insertInLists = dialog.getWindow().findViewById(R.id.ShareWithFriends);
        PushDownAnim.setPushDownAnimTo(insertInLists);
        insertInLists.setOnClickListener(v -> {
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
                            ntc.execute("shareFriendsContent").get();
                            ntc.isCancelled();
                        } catch (ExecutionException | InterruptedException e) {
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
                            ntc.execute("shareFriendsContent").get();
                            ntc.isCancelled();
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                dismiss();
            }

            dialog.closeOptionsMenu();
            dialog.cancel();
            dialog.dismiss();
        });
        return dialog;
    }

    @Override
    public void setList(List<?> newList) {
        ContactListAdapter adapter = new ContactListAdapter((List<Contact>) newList, getContext(), this.selectedLists);
        adapter.setCss(this.getClass());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(false);
    }
}
