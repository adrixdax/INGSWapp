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

import com.example.INGSW.Component.DB.Adapters.CustomListsAdapter;
import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Controllers.FilmTestController;
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
                RetrofitResponse.getResponse("Type=PostRequest&idList=" + singlelist.getIdUserList() + "&idFilm="+ idFilmToInsert + "&addFilm=true",DialogCustomlList.this,DialogCustomlList.this.getContext(),"getList" );
            }

            selectedLists.clear();
            dialog.closeOptionsMenu();
            dialog.cancel();
            dialog.dismiss();
        });

        return dialog;

    }

    public int getIdFilmToInsert() {
        return idFilmToInsert;
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
