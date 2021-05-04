package com.example.ingsw;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class AddCustomList extends Fragment {

    private EditText title;
    private EditText description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.add_list_custom, container, false);

        Button cancel = root.findViewById(R.id.button);
        Button add = root.findViewById(R.id.button2);

        PushDownAnim.setPushDownAnimTo(cancel, add)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);

        title = root.findViewById(R.id.editTextViewTitleList);
        description = root.findViewById(R.id.editTextListDescription);

        cancel.setOnClickListener(v -> requireActivity().onBackPressed());

        add.setOnClickListener(v -> {
            RetrofitResponse.getResponse("Type=PostRequest&idUser=" + ((ToolBarActivity) requireActivity()).getUid() + "&addList=true&listTitle=" + title.getText() + (String.valueOf(description.getText()).length() != 0 ? "&listDescription=" + description.getText() : ""), AddCustomList.this, getContext(), "addList");
            requireActivity().onBackPressed();

        });

        return root;
    }

}
