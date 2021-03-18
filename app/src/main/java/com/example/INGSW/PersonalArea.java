package com.example.INGSW;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.thekhaeng.pushdownanim.PushDownAnim;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalArea extends Fragment implements View.OnClickListener {

    private Button logout;
    private Button mylists;
    private Button myfavs;
    private Button suggested;
    private Button seenfilms;


    GoogleSignInAccount acct;
    private User userProfile = null;
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

        mylists.setOnClickListener(this);
        myfavs.setOnClickListener(this);
        suggested.setOnClickListener(this);
        seenfilms.setOnClickListener(this);


        PushDownAnim.setPushDownAnimTo(mylists, myfavs, suggested, seenfilms, logout)
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

            userProfile = (User) ((ToolBarActivity) getActivity()).getContaiinerItem().get("userProfile");


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
                nickname = acct.getDisplayName();
                mail = acct.getEmail();
                try {
                    if (acct.getPhotoUrl() != null) {
                        propic = acct.getPhotoUrl().toString();
                    } else {
                        propic = "https://img.favpng.com/11/21/25/iron-man-cartoon-avatar-superhero-icon-png-favpng-jrRBMJQjeUwuteGtBce87yMxz.jpg";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                nicknameView.setText(nickname);
                mailView.setText(mail);
                Glide.with(root.getContext()).load(propic).into(propicView);
            }

        }


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getContext().getSharedPreferences("access", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");;
                editor.apply();

                ((ToolBarActivity) getActivity()).getContaiinerItem().clear();
                FirebaseAuth.getInstance().signOut();
                Intent logoutIntent = new Intent(PersonalArea.this.getActivity(), LoginScreen.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //(ToolBarActivity).getActivity().finish();
                startActivity(logoutIntent);

                ((ToolBarActivity)getActivity()).finish();
            }
        });

        return root;
    }

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