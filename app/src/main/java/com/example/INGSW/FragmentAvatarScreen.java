package com.example.INGSW;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentAvatarScreen extends Fragment implements View.OnClickListener {


    private final String urlIron = "https://img.favpng.com/11/21/25/iron-man-cartoon-avatar-superhero-icon-png-favpng-jrRBMJQjeUwuteGtBce87yMxz.jpg";
    private final String urlSpider = "https://i.pinimg.com/236x/d4/9f/33/d49f3302e2a4e7b5a21ea3aba0cfcf03.jpg";
    private final String urlGamora = "https://i.pinimg.com/564x/48/99/65/48996519ea996aa169ca1d61e2a6c6ab.jpg";
    private final String urlThanos = "https://i.pinimg.com/236x/fa/60/b8/fa60b89014f5807b5a013e83aba32aab.jpg";
    private final String urlWidow = "https://i.pinimg.com/564x/90/15/d9/9015d92696baf129a8b4d273625fbfdd.jpg";
    private final String urlWonder = "https://i.pinimg.com/564x/5b/71/ab/5b71ab4ea082c3c11e77312a64bba835.jpg";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.avatar_screen, container, false);

        CircleImageView ironman = (CircleImageView) root.findViewById(R.id.profile_image);
        CircleImageView spiderman = (CircleImageView) root.findViewById(R.id.profile_image2);
        CircleImageView gamora = (CircleImageView) root.findViewById(R.id.profile_image3);
        CircleImageView thanos = (CircleImageView) root.findViewById(R.id.profile_image4);
        CircleImageView widow = (CircleImageView) root.findViewById(R.id.profile_image5);
        CircleImageView wonderwoman = (CircleImageView) root.findViewById(R.id.profile_image6);

        ConstraintLayout.LayoutParams paramswidow = (ConstraintLayout.LayoutParams)widow.getLayoutParams();
        paramswidow.setMargins(0, 0, 0, 100); //substitute parameters for left, top, right, bottom
        ConstraintLayout.LayoutParams paramswonder = (ConstraintLayout.LayoutParams)wonderwoman.getLayoutParams();
        paramswonder.setMargins(0, 0, 0, 100); //substitute parameters for left, top, right, bottom

        PushDownAnim.setPushDownAnimTo(ironman, spiderman, gamora, thanos, widow, wonderwoman);

        ironman.setOnClickListener(this);
        spiderman.setOnClickListener(this);
        gamora.setOnClickListener(this);
        thanos.setOnClickListener(this);
        widow.setOnClickListener(this);
        wonderwoman.setOnClickListener(this);

        Glide.with(this).load(urlIron).into(ironman);
        Glide.with(this).load(urlSpider).into(spiderman);
        Glide.with(this).load(urlThanos).into(thanos);
        Glide.with(this).load(urlGamora).into(gamora);
        Glide.with(this).load(urlWidow).into(widow);
        Glide.with(this).load(urlWonder).into(wonderwoman);
        return root;
    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile_image :
                updateWithUrl(urlIron);
                getActivity().onBackPressed();
                break;
            case R.id.profile_image2 :
                updateWithUrl(urlSpider);
                getActivity().onBackPressed();
                break;
            case R.id.profile_image3 :
                updateWithUrl(urlGamora);
                getActivity().onBackPressed();
                break;
            case R.id.profile_image4 :
                updateWithUrl(urlThanos);
                getActivity().onBackPressed();
                break;
            case R.id.profile_image5 :
                updateWithUrl(urlWidow);
                getActivity().onBackPressed();
                break;
            case R.id.profile_image6 :
                updateWithUrl(urlWonder);
                getActivity().onBackPressed();
                break;

        }
    }

    private void updateWithUrl(String url){
        String uid = ((ToolBarActivity) getActivity()).getUid();
        Query query = ToolBarActivity.getReference().orderByKey().equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User model = dataSnapshot.getValue(User.class);
                    model.propic = url;
                    ToolBarActivity.getReference().child(uid).setValue(model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
