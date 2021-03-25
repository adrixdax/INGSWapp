
package com.example.INGSW;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.INGSW.Controllers.LoginController;
import com.example.INGSW.Controllers.UserServerController;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

/**
 * Tale activity sostiene la schermata principale dell'app. ovvero la prima schermata che si aprirà difronte all' utente all' apertura dell'app
 */

public class LoginScreen extends AppCompatActivity {

    /**
     * Dichiarazione dei campi di testo e bottoni con i quali interagirà l'utente
     **/

    LoginController loginController;
    int RC_SIGN_IN = 0;


    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button LoginButton;


    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    SignInButton GoogleLogin;
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference reference;

    /**
     * Metodo che alla creazione dell' activity prepara le nuove variabili e riconosce le componenti
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {


            super.onCreate(savedInstanceState);
            reference =  FirebaseDatabase.getInstance().getReference("Users");
            setContentView(R.layout.loginscreen);


            loginController = new LoginController(this);

            mAuth = FirebaseAuth.getInstance();

            register = (TextView) findViewById(R.id.RegisterText);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginScreen.super.getApplicationContext(), RegistrationScreen.class));
                }
            });

            GoogleLogin = findViewById(R.id.sign_in_button);
            LoginButton = findViewById(R.id.LoginButton);


            PushDownAnim.setPushDownAnimTo(GoogleLogin, LoginButton)
                    .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                    .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                    .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                    .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);

            SharedPreferences preferences = getSharedPreferences("access", MODE_PRIVATE);
            String access = preferences.getString("remember", "");

            if (access.equals("true")) {
                Intent intent = new Intent(LoginScreen.this, ToolBarActivity.class);
                startActivity(intent);
            }

            LoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        loginController.verifyUserWithFirebase(editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim(), mAuth, LoginScreen.this);
                    } catch (Exception e) {
                        switch (e.getMessage()) {
                            case "Empty Mail":
                                editTextEmail.setError("Mail vuota");
                                editTextEmail.requestFocus();
                                break;
                            case "Invalid Mail":
                                editTextEmail.setError("Mail non valida");
                                editTextEmail.requestFocus();
                                break;
                            case "Empty password":
                                editTextPassword.setError("La password è necessaria!");
                                editTextPassword.requestFocus();
                                break;
                            case "Password Length":
                                editTextPassword.setError("La password deve contenere almeno 6 caratteri!");
                                editTextPassword.requestFocus();
                                break;
                            default:
                                e.printStackTrace();
                                Toast.makeText(LoginScreen.super.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                break;
                        }

                    }
                }
            });

            editTextEmail = (EditText) findViewById(R.id.TextLoginEmail);
            editTextPassword = (EditText) findViewById(R.id.TextLoginPassword);
            editTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        LoginButton.callOnClick();
                        return true;
                    }
                    return false;
                }
            });

            progressBar = (ProgressBar) findViewById(R.id.progressBar);


            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

            /**Sul click nella schermata, se il click corrisponde al bottone di Google, allora viene chiamata nel login controller la funzione adibita al login con google **/
            GoogleLogin.setOnClickListener(v -> {
                if (v.getId() == R.id.sign_in_button) {
                    loginController.signIn(mGoogleSignInClient);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            User u = new User();
            final User[] model = new User[1];
            Query query = reference.orderByKey().equalTo(account.getId());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        model[0] = dataSnapshot.getValue(User.class);
                        if (model[0].getPropic().equals(u.getPropic()))
                            reference.child(Objects.requireNonNull(account.getId())).setValue(u);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            if (model[0]==null) {
                System.out.println("Updating");
            }

            // Signed in successfully, show authenticated UI.
            SharedPreferences preferences = getSharedPreferences("access", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "true");
            editor.apply();

            UserServerController usc = new UserServerController();
            usc.setUserId(account.getId());
            String req = (String) usc.execute(new String("google")).get();
            u.nickname = Objects.requireNonNull(account.getEmail()).split("@")[0];
            u.email = account.getEmail();
            int switchCase = new Random().ints(0, 5).findAny().getAsInt();
            switch (switchCase) {
                case 0:
                    u.propic = "https://img.favpng.com/11/21/25/iron-man-cartoon-avatar-superhero-icon-png-favpng-jrRBMJQjeUwuteGtBce87yMxz.jpg";
                    break;
                case 1:
                    u.propic = "https://i.pinimg.com/236x/d4/9f/33/d49f3302e2a4e7b5a21ea3aba0cfcf03.jpg";
                    break;
                case 2:
                    u.propic = "https://i.pinimg.com/564x/48/99/65/48996519ea996aa169ca1d61e2a6c6ab.jpg";
                    break;
                case 3:
                    u.propic = "https://i.pinimg.com/236x/fa/60/b8/fa60b89014f5807b5a013e83aba32aab.jpg";
                    break;
                case 4:
                    u.propic = "https://i.pinimg.com/564x/90/15/d9/9015d92696baf129a8b4d273625fbfdd.jpg";
                    break;
                case 5:
                    u.propic = "https://i.pinimg.com/564x/5b/71/ab/5b71ab4ea082c3c11e77312a64bba835.jpg";
                    break;
            }
            Intent intent = new Intent(LoginScreen.this, ToolBarActivity.class);
            startActivity(intent);
            finish();
        }
        catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ERROR", "signInResult:failed code=" + e.getStatusCode());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


}

