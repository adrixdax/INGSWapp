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
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

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
            String uid = ((ToolBarActivity) this.requireActivity()).getUid();
            if (!custom) {
                if (!selectedLists.isEmpty()) {
                    for (Contact friend : selectedLists) {
                        RetrofitResponse.getResponse("Type=PostRequest&id_sender=" + uid + "&id_receiver=" + (friend.getUser1().equals(uid) ? friend.getUser2() : friend.getUser1()) + "&type=FILM&id_recordref=" + film + "&sendNotify=true", this, this.getContext(), "createNotify");
                    }
                }
                dismiss();
            } else {
                if (!selectedLists.isEmpty()) {
                    for (Contact friend : selectedLists) {
                        RetrofitResponse.getResponse("Type=PostRequest&id_sender=" + uid + "&id_receiver=" + (friend.getUser1().equals(uid) ? friend.getUser2() : friend.getUser1()) + "&type=LIST&id_recordref=" + idList + "&sendNotify=true", this, this.getContext(), "createNotify");
                    }
                    dismiss();
                }
                dialog.closeOptionsMenu();
                dialog.cancel();
                dialog.dismiss();
            }
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
