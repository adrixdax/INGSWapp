package com.example.INGSW;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.INGSW.Component.DB.Classes.User;
import com.example.INGSW.Controllers.Retrofit.RetrofitResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class InsertListReviewScreen extends Fragment {

    private Boolean isPositive;
    private final String idList;

    public InsertListReviewScreen(Boolean isPositive,String idList){
        this.isPositive=isPositive;
        this.idList=idList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.insert_list_review, container, false);
        ImageView likeButton = root.findViewById(R.id.likeButton);
        ImageView dislikeButton = root.findViewById(R.id.dislikeButton);
        Button insertReview = root.findViewById(R.id.insertCommentButton);
        EditText editTextDescription = root.findViewById(R.id.editTextListDescription);
        if (isPositive){
            likeButton.setBackground(requireContext().getDrawable(R.drawable.rounded_corners));
            dislikeButton.setBackgroundResource(0);
        }
        else {
            dislikeButton.setBackground(requireContext().getDrawable(R.drawable.rounded_corners));
            likeButton.setBackgroundResource(0);
        }
        likeButton.setOnClickListener(v -> {
            likeButton.setBackground(requireContext().getDrawable(R.drawable.rounded_corners));
            dislikeButton.setBackgroundResource(0);
            isPositive=true;
        });
        dislikeButton.setOnClickListener(v -> {
            dislikeButton.setBackground(requireContext().getDrawable(R.drawable.rounded_corners));
            likeButton.setBackgroundResource(0);
            isPositive=false;
        });
        insertReview.setOnClickListener(v -> {
                String descr;
                        if(editTextDescription.getText().toString().isEmpty())
                            descr = "ha lasciato "+(isPositive?"un \"mi piace\"":"un \"non mi piace\"");
                        else {
                            descr = editTextDescription.getText().toString();
                            descr=descr.replaceAll("'", String.valueOf('\''));
                        }
                RetrofitResponse.getResponse("Type=PostRequest&idRecordRef=" + idList + "&title="+"\0"+"&description=" + descr + "&val=" + (isPositive?"1.0":"0.0") + "&idUser=" + ((ToolBarActivity) requireActivity()).getUid() + "&insert=true&typeOfReview=LIST",this,getContext(),"addReview");
                getActivity().onBackPressed();
            });

        return root;
    }
}
