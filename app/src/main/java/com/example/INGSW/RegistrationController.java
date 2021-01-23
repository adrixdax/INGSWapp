package com.example.INGSW;

import android.app.Activity;
import android.content.Intent;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationController {
    private Activity regActivity;
    private FirebaseAuth mAuth;

    public RegistrationController(Activity current) {
        this.regActivity = current;
        mAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(EditText editTextMail, EditText editTextPassword, EditText editTextNickName) {
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
                        User user = new User(nickname, email);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child((FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                .setValue(user).addOnSuccessListener(regActivity, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(regActivity, "Utente registrato correttamente", Toast.LENGTH_LONG).show();
                                regActivity.startActivity(new Intent(regActivity, HomepageScreen.class));
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
}
