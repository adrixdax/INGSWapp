package com.example.INGSW;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.INGSW.Controllers.UserServerController;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyLists #newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyLists extends Fragment {

    private ImageButton addList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_lists, container, false);
        RecyclerView rv = root.findViewById(R.id.recyclerView2);

        addList =(ImageButton) root.findViewById(R.id.addListButton);
        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserServerController usc = new UserServerController();
                usc.setUserId(((ToolBarActivity) getActivity()).getUid());
                try {
                    usc.execute(new String("addCustomList")).get();
                    usc.isCancelled();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        //  rv.setAdapter(new UserListsAdapter(new));  //Controller to get the list

        return root;
    }


}