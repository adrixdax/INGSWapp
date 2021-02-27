package com.example.INGSW;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.INGSW.Controllers.LoginController;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.thekhaeng.pushdownanim.PushDownAnim;

/** Tale activity sostiene la schermata principale dell'app. ovvero la prima schermata che si aprirà difronte all' utente all' apertura dell'app */

public class LoginScreen extends AppCompatActivity{

    /**Dichiarazione dei campi di testo e bottoni con i quali interagirà l'utente**/

    LoginController loginController;
    int RC_SIGN_IN = 0;


    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button LoginButton;


    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    SignInButton GoogleLogin;
    GoogleSignInClient mGoogleSignInClient;


    /**Metodo che alla creazione dell' activity prepara le nuove variabili e riconosce le componenti**/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        PushDownAnim.setPushDownAnimTo(GoogleLogin,LoginButton)
                .setDurationPush( PushDownAnim.DEFAULT_PUSH_DURATION )
                .setDurationRelease( PushDownAnim.DEFAULT_RELEASE_DURATION )
                .setInterpolatorPush( PushDownAnim.DEFAULT_INTERPOLATOR )
                .setInterpolatorRelease( PushDownAnim.DEFAULT_INTERPOLATOR );

        SharedPreferences preferences = getSharedPreferences("access",MODE_PRIVATE);
        String access = preferences.getString("remember","");

        if(access.equals("true")){
            Intent intent = new Intent(LoginScreen.this, ToolBarActivity.class);
            startActivity(intent);
        }

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loginController.verifyUserWithFirebase(editTextEmail.getText().toString().trim(),editTextPassword.getText().toString().trim(),mAuth,LoginScreen.this);
                } catch (Exception e) {
                    if( e.getMessage().equals("Empty Mail") ) {
                        editTextEmail.setError("Mail vuota");
                        editTextEmail.requestFocus();
                    }
                    else if(e.getMessage().equals("Invalid Mail")){
                        editTextEmail.setError("Mail non valida");
                        editTextEmail.requestFocus();
                    }
                    else if(e.getMessage().equals("Empty password")){
                        editTextPassword.setError("La password è necessaria!");
                        editTextPassword.requestFocus();
                    }
                    else if(e.getMessage().equals("Password Length")){
                        editTextPassword.setError("La password deve contenere almeno 6 caratteri!");
                        editTextPassword.requestFocus();
                    }else
                    {
                        e.printStackTrace();
                        Toast.makeText(LoginScreen.super.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
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
        GoogleLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.sign_in_button) {
                    loginController.signIn(mGoogleSignInClient);
                }
            }
        });



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

            // Signed in successfully, show authenticated UI.
            SharedPreferences preferences = getSharedPreferences("access",MODE_PRIVATE);
            SharedPreferences.Editor editor =  preferences.edit();
            editor.putString("remember","true");
            editor.apply();

            Intent intent = new Intent(LoginScreen.this, ToolBarActivity.class);
            startActivity(intent);
            finish();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ERROR", "signInResult:failed code=" + e.getStatusCode());
        }
    }



}

