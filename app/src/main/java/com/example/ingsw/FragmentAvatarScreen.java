package com.example.ingsw;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ingsw.component.db.classes.User;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentAvatarScreen extends Fragment implements View.OnClickListener {


    private final String urlIron = "https://i.pinimg.com/564x/f0/0c/b7/f00cb7716ff739114f49a5ecf12a6b8a.jpg";
    private final String urlSpider = "https://i.pinimg.com/236x/d4/9f/33/d49f3302e2a4e7b5a21ea3aba0cfcf03.jpg";
    private final String urlGamora = "https://i.pinimg.com/564x/48/99/65/48996519ea996aa169ca1d61e2a6c6ab.jpg";
    private final String urlThanos = "https://i.pinimg.com/236x/fa/60/b8/fa60b89014f5807b5a013e83aba32aab.jpg";
    private final String urlWidow = "https://i.pinimg.com/564x/90/15/d9/9015d92696baf129a8b4d273625fbfdd.jpg";
    private final String urlWonder = "https://i.pinimg.com/564x/5b/71/ab/5b71ab4ea082c3c11e77312a64bba835.jpg";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.avatar_screen, container, false);
        FrameLayout.LayoutParams newLayoutParams = (FrameLayout.LayoutParams) root.getLayoutParams();
        newLayoutParams.setMargins(0, 0, 0, requireActivity().getWindow().getDecorView().getRootView().findViewById(R.id.nav_view).getHeight());
        root.setLayoutParams(newLayoutParams);
        CircleImageView ironman = root.findViewById(R.id.profile_image);
        CircleImageView spiderman = root.findViewById(R.id.profile_image2);
        CircleImageView gamora = root.findViewById(R.id.profile_image3);
        CircleImageView thanos = root.findViewById(R.id.profile_image4);
        CircleImageView widow = root.findViewById(R.id.profile_image5);
        CircleImageView wonderwoman = root.findViewById(R.id.profile_image6);


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

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME,"ChangeAvatar");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS,this.getClass().getSimpleName());
        ToolBarActivity.setNewBundle("ChangeAvatar",bundle);

        return root;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_image:
                updateWithUrl(urlIron);
                requireActivity().onBackPressed();
                break;
            case R.id.profile_image2:
                updateWithUrl(urlSpider);
                requireActivity().onBackPressed();
                break;
            case R.id.profile_image3:
                updateWithUrl(urlGamora);
                requireActivity().onBackPressed();
                break;
            case R.id.profile_image4:
                updateWithUrl(urlThanos);
                requireActivity().onBackPressed();
                break;
            case R.id.profile_image5:
                updateWithUrl(urlWidow);
                requireActivity().onBackPressed();
                break;
            case R.id.profile_image6:
                updateWithUrl(urlWonder);
                requireActivity().onBackPressed();
                break;

        }
    }

    private void updateWithUrl(String url) {
        String uid = ((ToolBarActivity) requireActivity()).getUid();
        Query query = ToolBarActivity.getReference().orderByKey().equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User model = dataSnapshot.getValue(User.class);
                    assert model != null;
                    model.setPropic(url);
                    ToolBarActivity.getReference().child(uid).setValue(model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
