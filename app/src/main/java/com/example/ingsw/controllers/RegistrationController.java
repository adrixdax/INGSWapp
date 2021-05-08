package com.example.ingsw.controllers;

import android.app.Activity;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ingsw.R;
import com.example.ingsw.RegistrationScreen;
import com.example.ingsw.ToolBarActivity;
import com.example.ingsw.component.db.classes.User;
import com.example.ingsw.controllers.retrofit.RetrofitResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("SameReturnValue")
public class RegistrationController {
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
    private static Activity regActivity;
    static private String pic;

    public RegistrationController(Activity current, String propic) {
        regActivity = current;
        pic = propic;
    }

    public static void setAvatar(String url) {
        pic = url;
        Glide.with(regActivity).load(url).into((CircleImageView) regActivity.findViewById(R.id.propic_image));
    }

    public boolean checkPasswordField(String password) throws Exception {
        if (password.isEmpty()) throw new Exception("Empty Password");
        if (password.length() < 6) throw new Exception("Password Length");
        if (!password.matches(PASSWORD_PATTERN)) throw new Exception("Weak Password");

        return true;
    }

    public void registerUser(String email, String password, String rePassword, String nickname) throws Exception {
        if (nickname.isEmpty()) {
            throw new Exception("Empty nickname");
        }
        if (email.isEmpty()) {
            throw new Exception("Empty Mail");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new Exception("Invalid Mail");
        }
        if (checkPasswordField(password))
            if (!(password.equals(rePassword))) throw new Exception("Not equal passwords");
        if (rePassword.isEmpty()) throw new Exception("Empty rePassword");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(regActivity, authResult -> {
                    User user = new User(nickname, email, pic);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child((Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()))
                            .setValue(user).addOnSuccessListener(regActivity, aVoid -> {
                                FirebaseAuth fba = FirebaseAuth.getInstance();
                        RetrofitResponse.getResponse("Type=PostRequest&registration=" + fba.getCurrentUser().getUid(), regActivity, regActivity, "getRegistration");
                        ((RegistrationScreen) regActivity).stopProgressBar();
                        Toast.makeText(regActivity, "Utente registrato correttamente", Toast.LENGTH_LONG).show();
                        regActivity.startActivity(new Intent(regActivity, ToolBarActivity.class));
                    });
                }).addOnFailureListener(regActivity, e -> Toast.makeText(regActivity, e.getMessage(), Toast.LENGTH_LONG).show());

    }
}
