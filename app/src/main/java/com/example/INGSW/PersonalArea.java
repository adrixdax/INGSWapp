package com.example.INGSW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalArea extends Fragment {

    private Button logout;


    GoogleSignInAccount acct;
    private User userProfile = null;
    final String propic = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.personal_area, container, false);


        logout = (Button) root.findViewById(R.id.Logout_button);
        final TextView nicknameView = (TextView) root.findViewById(R.id.personal_profile_nick);
        final TextView mailView = (TextView) root.findViewById(R.id.personal_profile_mail);
        final CircleImageView propicView = (CircleImageView) root.findViewById(R.id.personal_profile_image);



        if (((ToolBarActivity) getActivity()).getContaiinerItem().get("userProfile") != null) {
            userProfile = (User) ((ToolBarActivity) getActivity()).getContaiinerItem().get("userProfile");


            if (userProfile != null) {
                String nickname = userProfile.nickname;
                String mail = userProfile.email;
                String propic = userProfile.propic;

                nicknameView.setText(nickname);
                mailView.setText(mail);
                Glide.with(root.getContext()).load(propic).into(propicView);
            }

        } else if (((ToolBarActivity) getActivity()).getContaiinerItem().get("acct") != null) {

            acct = (GoogleSignInAccount) ((ToolBarActivity) getActivity()).getContaiinerItem().get("acct");


            if (acct != null) {
                String nickname = acct.getDisplayName();
                String mail = acct.getEmail();
                try {
                    if (acct.getPhotoUrl() != null) {
                        String propic = acct.getPhotoUrl().toString();
                    } else {
                        String propic = "https://img.favpng.com/11/21/25/iron-man-cartoon-avatar-superhero-icon-png-favpng-jrRBMJQjeUwuteGtBce87yMxz.jpg";
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
                if((GoogleSignInAccount) ((ToolBarActivity) getActivity()).getContaiinerItem().get("acct")!=null) {
                    System.out.println("Ho trovato lo user Google--------------------------------");
                    ((ToolBarActivity) getActivity()).getContaiinerItem().remove("acct", acct);
                }else{
                    System.out.println("Ho trovato lo user proprietario----------------------------------");
                    ((ToolBarActivity) getActivity()).getContaiinerItem().remove("userProfile", userProfile);
                }
                FirebaseAuth.getInstance().signOut();
                Intent logoutIntent = new Intent(PersonalArea.this.getActivity(), LoginScreen.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);
            }
        });

        return root;
    }


}