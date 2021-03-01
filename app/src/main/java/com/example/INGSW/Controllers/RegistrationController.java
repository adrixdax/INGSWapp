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
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationController {
    private static Activity regActivity;
    private CircleImageView circle;
    private FirebaseAuth mAuth;
    static private String pic;

    public RegistrationController(Activity current,String propic) {
        regActivity = current;
        this.circle = regActivity.findViewById(R.id.propic_image);
        this.pic = propic;

        mAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(EditText editTextMail, EditText editTextPassword, EditText editTextNickName,String propic) {
        String email = editTextMail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String nickname = editTextNickName.getText().toString().trim();

        if (nickname.isEmpty()) {
            editTextNickName.setError("NickName non compilato!");
            editTextNickName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextMail.setError("Mail non compilata!");
            editTextMail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextMail.setError("Perfavore inserisci un indirizzo Mail valido");
            editTextMail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password non compilata!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password  deve contenere almeno 6 caratteri.");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(regActivity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
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
                                    System.out.println(usc.execute(new String("registration")).get());
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                usc.isCancelled();

                                regActivity.startActivity(new Intent(regActivity, ToolBarActivity.class));
                            }
                        }).addOnFailureListener(regActivity, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                                Toast.makeText(regActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                });

    }

    public static void setAvatar(String url) {
        pic = url;
        Glide.with(regActivity).load(url).into((CircleImageView)regActivity.findViewById(R.id.propic_image));
    }
}
