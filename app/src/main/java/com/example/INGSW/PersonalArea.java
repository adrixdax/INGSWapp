package com.example.INGSW;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalArea extends Fragment implements View.OnClickListener {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.personal_area, container, false);
        Button mylists = (Button) root.findViewById(R.id.mylists_button);
        Button myfavs = (Button) root.findViewById(R.id.myfavs_button);
        Button seenfilms = (Button) root.findViewById(R.id.seenfilms_button);
        Button logout = (Button) root.findViewById(R.id.Logout_button);
        Button myreviews = (Button) root.findViewById(R.id.myreviews_button);
        ImageView pencil = (ImageView) root.findViewById(R.id.pencilPersonalArea);
        Button friends = (Button) root.findViewById(R.id.Friends_button);

        pencil.setOnClickListener(this);
        mylists.setOnClickListener(this);
        myfavs.setOnClickListener(this);
        seenfilms.setOnClickListener(this);
        myreviews.setOnClickListener(this);
        friends.setOnClickListener(this);

        PushDownAnim.setPushDownAnimTo(mylists, myfavs, seenfilms, logout, myreviews, pencil,friends)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
        final TextView nicknameView = (TextView) root.findViewById(R.id.personal_profile_nick);
        final TextView mailView = (TextView) root.findViewById(R.id.personal_profile_mail);
        final CircleImageView propicView = (CircleImageView) root.findViewById(R.id.personal_profile_image);
        propicView.setOnClickListener(this);
        if (!(((ToolBarActivity) getActivity()).isLoadUser())) {
            ((ToolBarActivity) getActivity()).setLoadUser(loadingUser());
        }
        Query query = ToolBarActivity.getReference().orderByKey().equalTo((((ToolBarActivity) getActivity()).getUid()));
                query.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            User model = dataSnapshot.getValue(User.class);
                            nicknameView.setText(model.getNickname());
                            mailView.setText(model.getEmail());
                            if (getActivity()  != null) Glide.with(root.getContext()).load(model.getPropic()).into(propicView);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


        logout.setOnClickListener(v -> {

            SharedPreferences preferences = getContext().getSharedPreferences("access", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "false");
            editor.apply();
            ((ToolBarActivity) getActivity()).getContaiinerItem().clear();
            FirebaseAuth.getInstance().signOut();
            Intent logoutIntent = new Intent(PersonalArea.this.getActivity(), LoginScreen.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(logoutIntent);

            ((ToolBarActivity)getActivity()).finish();
        });
        return root;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Fragment nextFragment;
        FragmentTransaction transaction;
        switch (v.getId()) {
            case R.id.mylists_button:
                nextFragment = new MyLists();
                transaction = PersonalArea.this.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "MyListCustom");
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.myfavs_button:
                nextFragment = new MyFavs();
                transaction = PersonalArea.this.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "7");
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.myreviews_button:
                nextFragment = new MyReviews();
                transaction = PersonalArea.this.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "8");
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.seenfilms_button:
                nextFragment = new SeenFilms();
                transaction = PersonalArea.this.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "9");
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.pencilPersonalArea:
            case R.id.personal_profile_image:
                nextFragment = new FragmentAvatarScreen();
                transaction = PersonalArea.this.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "avatar");
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.Friends_button:
                nextFragment = new ListOfFriendsScreen();
                transaction = PersonalArea.this.getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nextFragment, "friends");
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }

    private boolean loadingUser() {
        boolean load = false;
        try {
            ((ToolBarActivity) getActivity()).getUser();
            return load = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return load;
    }

}