package com.example.ingsw;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.component.db.adapters.CustomListsAdapter;
import com.example.ingsw.component.db.classes.UserLists;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class DialogCustomlList extends AppCompatDialogFragment implements RetrofitListInterface {


    private RecyclerView recycler;
    private final List<UserLists> selectedLists = new ArrayList<>();
    private int idFilmToInsert;

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_list_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);


        recycler = dialog.findViewById(R.id.recyclerView);
        RetrofitResponse.getResponse("Type=PostRequest&idUser=" + ((ToolBarActivity) requireActivity()).getUid() + "&custom=true&idFilm="+ idFilmToInsert,this,this.getContext(),"getList" );


        Button insertInLists = (Button) dialog.getWindow().findViewById(R.id.InsertInListsbutton);
        PushDownAnim.setPushDownAnimTo(insertInLists);
        insertInLists.setOnClickListener(v -> {
            for (UserLists singlelist : selectedLists) {
                RetrofitResponse.getResponse("Type=PostRequest&idList=" + singlelist.getIdUserList() + "&idFilm="+ idFilmToInsert + "&addFilm=true",DialogCustomlList.this,DialogCustomlList.this.getContext(),"addFilm" );
            }
            selectedLists.clear();
            Toast.makeText(this.getContext(),"Film aggiunto con successo",Toast.LENGTH_LONG).show();
            dialog.closeOptionsMenu();
            dialog.cancel();
            dialog.dismiss();
        });

        return dialog;

    }

    public void setIdFilmToInsert(int idFilmToInsert) {
        this.idFilmToInsert = idFilmToInsert;
    }

    @Override
    public void setList(List<?> newList) {
        recycler.setAdapter(new CustomListsAdapter((List<UserLists>) newList, DialogCustomlList.class, this.selectedLists));
        recycler.setItemViewCacheSize(newList.size());
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(false);
    }
}
