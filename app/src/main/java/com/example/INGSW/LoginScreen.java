package com.example.INGSW;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.INGSW.Controllers.LoginController;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/** Tale activity sostiene la schermata principale dell'app. ovvero la prima schermata che si aprirà difronte all' utente all' apertura dell'app */

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    /**Dichiarazione dei campi di testo e bottoni con i quali interagirà l'utente**/

    LoginController loginController;
    int RC_SIGN_IN = 0;


    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;

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
        register.setOnClickListener(this);

        GoogleLogin = findViewById(R.id.sign_in_button);
        signIn = (Button) findViewById(R.id.LoginButton);
        signIn.setOnClickListener(this);


        signIn = (Button) findViewById(R.id.LoginButton);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.TextLoginEmail);
        editTextPassword = (EditText) findViewById(R.id.TextLoginPassword);

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
            Intent intent = new Intent(LoginScreen.this, HomepageScreen.class);
            startActivity(intent);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("ERROR", "signInResult:failed code=" + e.getStatusCode());
        }
    }


/** Se il click corrisponde al  register si passa alla schermata di Registrazione dell' utente, altrimenti se clicca su Login vengono effettuati i necessari controlli sulla compilazione dei campi **/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RegisterText:
                startActivity(new Intent(this, RegistrationScreen.class));
                break;
            case R.id.LoginButton:
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
                        Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();

                    }

                }
                break;
        }

    }


}

