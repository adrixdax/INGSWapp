package com.example.INGSW;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.INGSW.Controllers.RegistrationController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationScreen extends AppCompatActivity {

    private Button registerUser;
    private EditText editTextNickName,editTextMail,editTextPassword,repeatPassword;
    private CircleImageView ProfileImage;
    private String propic = "https://img.favpng.com/11/21/25/iron-man-cartoon-avatar-superhero-icon-png-favpng-jrRBMJQjeUwuteGtBce87yMxz.jpg";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrationscreen);

        RegistrationController reg = new RegistrationController(RegistrationScreen.this,propic);

        mAuth = FirebaseAuth.getInstance();

        ProfileImage = (CircleImageView)findViewById(R.id.propic_image);
        Glide.with(this).load(propic).into(ProfileImage);

        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationScreen.this, AvatarScreenActivity.class));
            }
        });

        editTextNickName = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextMail = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        repeatPassword = (EditText) findViewById(R.id.editTextTextRepeatPassword);

        registerUser  = (Button) findViewById(R.id.registerUserButton);
        registerUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference userNameRef = rootRef.child("Users");
                Query queries=userNameRef.orderByChild("nickname").equalTo(editTextNickName.getText().toString());
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()) {
                            try{
                            reg.registerUser(editTextMail.getText().toString().trim(),editTextPassword.getText().toString().trim(),repeatPassword.getText().toString().trim(),editTextNickName.getText().toString().trim(),propic);
                            } catch (Exception e) {
                                switch (e.getMessage()) {
                                    case "Empty nickname":
                                        editTextNickName.setError("Inserisci un nickname");
                                        editTextNickName.requestFocus();
                                        break;
                                    case "Empty Mail":
                                        editTextMail.setError("Mail vuota");
                                        editTextMail.requestFocus();
                                        break;
                                    case "Invalid Mail":
                                        editTextMail.setError("Perfavore inserisci un indirizzo Mail valido");
                                        editTextMail.requestFocus();
                                        break;
                                    case "Empty password":
                                        editTextPassword.setError("La password è necessaria!");
                                        editTextPassword.requestFocus();
                                        break;
                                    case "Not equal passwords":
                                        repeatPassword.setError("Le password non coincidono!");
                                        repeatPassword.requestFocus();
                                        break;
                                    case "Password Length":
                                        editTextPassword.setError("La password deve contenere almeno 6 caratteri!");
                                        editTextPassword.requestFocus();
                                        break;
                                    case "Weak Password":
                                        editTextPassword.setError("La password deve contenere almeno una lettera MAIUSCOLA, una minuscola ed un numero! \n esempio: Crazythanos90");
                                        editTextPassword.requestFocus();
                                        break;
                                    default:
                                        e.printStackTrace();
                                        Toast.makeText(RegistrationScreen.super.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                        break;
                                }

                            }
                        }
                        else{
                            editTextNickName.setError("Nickname già in uso");
                            editTextNickName.requestFocus();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                };
                queries.addListenerForSingleValueEvent(eventListener);
            }
        });
        editTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    registerUser.callOnClick();
                    return true;
                }
                return false;
            }
        });
        PushDownAnim.setPushDownAnimTo(registerUser,ProfileImage);
    }



}




























