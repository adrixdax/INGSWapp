package com.example.ingsw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingsw.component.db.adapters.CustomListsAdapter;
import com.example.ingsw.component.db.classes.UserLists;
import com.example.ingsw.controllers.retrofit.RetrofitListInterface;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;

import java.util.List;


@SuppressWarnings("ALL")
public class MyLists extends Fragment implements RetrofitListInterface {

    private RecyclerView recycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_lists, container, false);
        ImageButton addList = root.findViewById(R.id.addListButton);
        addList.setOnClickListener(v -> {
            AddCustomList nextFragment = new AddCustomList();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, nextFragment, "7");
            transaction.addToBackStack(null);
            transaction.commit();
        });
        recycler = root.findViewById(R.id.recyclerView2);
        ((ToolBarActivity) requireActivity()).triggerProgessBar();
        RetrofitResponse.getResponse("Type=PostRequest&idUser=" + ((ToolBarActivity) requireActivity()).getUid() + "&custom=true&idFilm= -1", this, this.getContext(), "getList");
        return root;
    }

    @Override
    public void setList(List<?> newList) {
        if (newList.size() != 0) {
            recycler.setAdapter(new CustomListsAdapter((List<UserLists>) newList, this.getClass(), this));
            recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recycler.setHasFixedSize(false);
            recycler.setItemViewCacheSize(newList.size());
        }
        ((ToolBarActivity) requireActivity()).stopProgressBar();
    }

}