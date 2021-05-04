package com.example.ingsw;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ingsw.component.db.adapters.ContactListAdapter;
import com.example.ingsw.component.db.classes.Contact;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;

import java.util.List;


@SuppressWarnings("ALL")
public class ListOfFriendsScreen extends Fragment implements RetrofitListInterface {

    RecyclerView recyclerView;
    TextView textFriendsError;

    public ListOfFriendsScreen() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_friends, container, false);
        ((ToolBarActivity)requireActivity()).triggerProgessBar();
        textFriendsError = root.findViewById(R.id.Textview_friendsError);
        recyclerView = root.findViewById(R.id.recyclerViewUserMyFriends);
        RetrofitResponse.getResponse("Type=PostRequest&isFriends=true&idUser=" + ((ToolBarActivity)requireActivity()).getUid(),ListOfFriendsScreen.this,this.getContext(),"getFriends");
        return root;

    }

    @Override
    public void setList(List<?> newList) {
        if(newList.size() != 0) {
            textFriendsError.setText("");
            ContactListAdapter adapter = new ContactListAdapter((List<Contact>) newList, getContext(), null);
            adapter.setCss(this.getClass());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(false);
            recyclerView.setItemViewCacheSize(newList.size());
        }
        else textFriendsError.setText("Davvero non hai neanche un amico?");
        ((ToolBarActivity)requireActivity()).stopProgressBar();
    }
}