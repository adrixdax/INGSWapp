package com.example.INGSW;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalArea extends Fragment implements View.OnClickListener {

    private Button logout, mylists, myfavs, suggested, seenfilms, myreviews;
    private ImageView pencil;
    GoogleSignInAccount acct;
    String propic = "";
    String nickname;
    String mail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.personal_area, container, false);



        mylists = (Button) root.findViewById(R.id.mylists_button);
        myfavs = (Button) root.findViewById(R.id.myfavs_button);
        suggested = (Button) root.findViewById(R.id.friendsuggests_button);
        seenfilms = (Button) root.findViewById(R.id.seenfilms_button);
        logout = (Button) root.findViewById(R.id.Logout_button);
        myreviews = (Button) root.findViewById(R.id.myreviews_button);
        pencil = (ImageView) root.findViewById(R.id.pencilPersonalArea);
        pencil.setOnClickListener(this);
        mylists.setOnClickListener(this);
        myfavs.setOnClickListener(this);
        suggested.setOnClickListener(this);
        seenfilms.setOnClickListener(this);


        PushDownAnim.setPushDownAnimTo(mylists, myfavs, suggested, seenfilms, logout,myreviews,pencil)
                .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);


        final TextView nicknameView = (TextView) root.findViewById(R.id.personal_profile_nick);
        final TextView mailView = (TextView) root.findViewById(R.id.personal_profile_mail);
        final CircleImageView propicView = (CircleImageView) root.findViewById(R.id.personal_profile_image);

        if (!(((ToolBarActivity) getActivity()).isLoadUser())) {
            ((ToolBarActivity) getActivity()).setLoadUser(loadingUser());
        }


        if (((ToolBarActivity) getActivity()).getContaiinerItem().get("userProfile") != null) {

            User userProfile = (User) ((ToolBarActivity) getActivity()).getContaiinerItem().get("userProfile");


            if (userProfile != null) {
                nickname = userProfile.nickname;
                mail = userProfile.email;
                propic = userProfile.propic;

                nicknameView.setText(nickname);
                mailView.setText(mail);
                Glide.with(root.getContext()).load(propic).into(propicView);
            }

        } else if (((ToolBarActivity) getActivity()).getContaiinerItem().get("acct") != null) {

            acct = (GoogleSignInAccount) ((ToolBarActivity) getActivity()).getContaiinerItem().get("acct");


            if (acct != null) {
                Query query = ToolBarActivity.getReference().getReference("Users").orderByKey().equalTo(acct.getId());
                query.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            User model = dataSnapshot.getValue(User.class);;
                            nicknameView.setText(model.getNickname());
                            mailView.setText(model.getEmail());
                            Glide.with(root.getContext()).load(model.getPropic()).into(propicView);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        }


        logout.setOnClickListener((View.OnClickListener) v -> {

            SharedPreferences preferences = getContext().getSharedPreferences("access", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "false");;
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
            case R.id.friendsuggests_button:
                nextFragment = new SuggestedFIlms();
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
                FragmentAvatarScreen changeAvatar = new FragmentAvatarScreen();
                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, changeAvatar, "avatar");
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