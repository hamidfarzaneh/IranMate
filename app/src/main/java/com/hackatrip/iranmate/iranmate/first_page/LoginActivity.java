package com.hackatrip.iranmate.iranmate.first_page;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.hackatrip.iranmate.iranmate.Model.MainApp;
import com.hackatrip.iranmate.iranmate.R;
import com.hackatrip.iranmate.iranmate.main_page.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private static final MainApp mainModel  = (MainApp) MainApp.getContext();
    // items
    EditText emailEdittext;
    EditText passwordEditText;
    Button loginButton;
    TextView signUpText;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        progressBar = (ProgressBar) findViewById(R.id.loginPBar);
        emailEdittext = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        signUpText = (TextView) findViewById(R.id.signUpTextView);


        progressBar.setVisibility(View.GONE);

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                               // go to sign up page

                Intent signUpIntent = new Intent(LoginActivity.this,SignUpActivity.class);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signUpIntent);
            }
        });



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });
    }

    // login function

    public void login(){
        String email = emailEdittext.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty()){
            AlertDialog.Builder userLoginError = new AlertDialog.Builder(LoginActivity.this);
            userLoginError.setMessage("Make sure you have enter all the fields");
            userLoginError.setTitle("Error ... !");
            userLoginError.setPositiveButton("Ok!",null);
            userLoginError.show();

        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {
                    String logedInUser = emailEdittext.getText().toString().split("@")[0];
                    mainModel.setBackendLogedInUser(backendlessUser);
                    progressBar.setVisibility(View.GONE);
                    setProgressBarIndeterminateVisibility(false);
                    Toast.makeText(LoginActivity.this, "Successfully logged in!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                    progressBar.setVisibility(View.GONE);
                    setProgressBarIndeterminateVisibility(false);
                    AlertDialog.Builder userLoginError = new AlertDialog.Builder(LoginActivity.this);
                    userLoginError.setMessage(backendlessFault.getMessage());
                    userLoginError.setTitle("Error...!");
                    userLoginError.setPositiveButton("Ok!",null);
                    userLoginError.show();
                }
            },true);
            // create new user
        }
    }
}
