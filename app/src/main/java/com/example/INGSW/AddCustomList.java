package com.example.INGSW;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.INGSW.Component.Films.ListOfFilm;
import com.example.INGSW.Component.Films.ListOfFilmAdapter;
import com.example.INGSW.Controllers.UserServerController;
import com.example.INGSW.Utility.JSONDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddCustomList extends Fragment {

    private EditText title;
    private EditText description;
    private Button cancel;
    private Button add;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.add_list_custom, container, false);

        cancel = root.findViewById(R.id.button);
        add = root.findViewById(R.id.button2);

        PushDownAnim.setPushDownAnimTo(cancel, add)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);

        title = root.findViewById(R.id.editTextViewTitleList);

        description = root.findViewById(R.id.editTextListDescription);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ToolBarActivity) getActivity()).onBackPressed();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserServerController usc = new UserServerController();
                usc.setUserId(((ToolBarActivity) getActivity()).getUid());
                usc.setListTitle(String.valueOf(title.getText()));

                System.out.println("-------------------------------------------------->"+String.valueOf(title.getText()));
                usc.setListDescription(String.valueOf(description.getText()));
                System.out.println("\n ------------------------------------------->"+String.valueOf(description.getText()));

                try {

                    usc.execute(new String("addCustomList")).get();
                    usc.isCancelled();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ((ToolBarActivity) getActivity()).onBackPressed();

            }
        });

        return root;
    }

}
