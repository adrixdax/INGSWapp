package com.example.INGSW;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Component.DB.Adapters.CustomListsAdapter;
import com.example.INGSW.Component.DB.Classes.UserLists;
import com.example.INGSW.Controllers.Retrofit.RetrofitListInterface;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.example.INGSW.Controllers.UserServerController;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Retrofit;

import static com.example.INGSW.Utility.JSONDecoder.getJsonToDecode;


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
        ((ToolBarActivity)requireActivity()).triggerProgessBar();
        RetrofitResponse.getResponse("Type=PostRequest&idUser=" + ((ToolBarActivity) requireActivity()).getUid() + "&custom=true&idFilm= -1",this,this.getContext(),UserLists.class.getCanonicalName(),"getList" );
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
        ((ToolBarActivity)requireActivity()).stopProgressBar();
    }

}