package com.example.INGSW.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.INGSW.R;
import com.example.INGSW.ToolBarActivity;
import com.example.INGSW.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationController {
    private static Activity regActivity;
    private FirebaseAuth mAuth;
    static private String pic;

    public RegistrationController(Activity current,String propic) {
        regActivity = current;
        pic = propic;

    }

    public boolean checkPasswordFields(String password,String repassword) throws Exception{
        if(password.isEmpty()) throw new Exception("Empty Password");
        if(repassword.isEmpty()) throw new Exception("Empty rePassword");
        if(!(password.equals(repassword))) throw new Exception("Not equal passwords");
        if(password.length() < 6) throw new Exception("Password Length");
        if (!(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]$"))) throw new Exception("Weak Password");
        return true;
    }


    public void registerUser(String email, String password, String rePassword,String nickname,String propic) throws Exception {


        if (nickname.isEmpty()) {
            throw new Exception("Empty nickname");
        }

        if (email.isEmpty()) {
            throw new Exception("Empty Mail");
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new Exception("Invalid Mail");
        }

        if( checkPasswordFields(password,rePassword))

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(regActivity, authResult -> {
                    User user = new User(nickname, email,pic);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child((FirebaseAuth.getInstance().getCurrentUser().getUid()))
                            .setValue(user).addOnSuccessListener(regActivity, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(regActivity, "Utente registrato correttamente", Toast.LENGTH_LONG).show();
                            //Genera Tabelle Utente
                            UserServerController usc = new UserServerController();
                            usc.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            try {
                                usc.execute(new String("registration")).get();
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            usc.isCancelled();
                            regActivity.startActivity(new Intent(regActivity, ToolBarActivity.class));
                        }
                    });
                }).addOnFailureListener(regActivity, e -> Toast.makeText(regActivity, e.getMessage(), Toast.LENGTH_LONG).show());

    }

    public static void setAvatar(String url) {
        pic = url;
        Glide.with(regActivity).load(url).into((CircleImageView)regActivity.findViewById(R.id.propic_image));
    }
}
