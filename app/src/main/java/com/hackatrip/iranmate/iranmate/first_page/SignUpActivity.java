package com.hackatrip.iranmate.iranmate.first_page;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.hackatrip.iranmate.iranmate.Model.User;
import com.hackatrip.iranmate.iranmate.R;
import com.hackatrip.iranmate.iranmate.main_page.MainActivity;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.signupPBar);
        progressBar.setVisibility(View.GONE);                                                       // it will be shown from the moment user press button till response
        TextView loginTextView = (TextView)findViewById(R.id.loginHyperText);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        final EditText userNameEditText = (EditText) findViewById(R.id.username_signup_editText);
        final EditText passwordEditText = (EditText) findViewById(R.id.password_signup_editText);
        final EditText emailEditText = (EditText) findViewById(R.id.email_signup_editText);
        Button signUpButton = (Button) findViewById(R.id.signup_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String userName = userNameEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString().trim();
                final String email = emailEditText.getText().toString().trim();

                if (userName.isEmpty() || password.isEmpty() || email.isEmpty()) {                  // if one of the fields are empty show a alert
                    AlertDialog.Builder userCreatingAlert = new AlertDialog.Builder(SignUpActivity.this);
                    userCreatingAlert.setMessage("Make sure you have enter all the fields");
                    userCreatingAlert.setTitle("Error ... !");
                    userCreatingAlert.setPositiveButton("Ok!", null);
                    userCreatingAlert.show();

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    BackendlessUser mNewUser = new BackendlessUser();
                    mNewUser.setEmail(email);
                    mNewUser.setPassword(password);
                    mNewUser.setProperty("username", userName);
                    if (mNewUser != null) {
                        Backendless.UserService.register(mNewUser, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser backendlessUser) {

                                Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                                    @Override
                                    public void handleResponse(BackendlessUser backendlessUser) {
                                        progressBar.setVisibility(View.GONE);

                                        Toast.makeText(SignUpActivity.this, "Successfully registered!",
                                                Toast.LENGTH_SHORT).show();
                                        User newUser = new User(userNameEditText.getText().toString(),emailEditText.getText().toString(),passwordEditText.getText().toString());
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault backendlessFault) {
                                        progressBar.setVisibility(View.GONE);


                                    }
                                }, true);


                            }

                            @Override
                            public void handleFault(BackendlessFault backendlessFault) {
                                progressBar.setVisibility(View.GONE);

                                setProgressBarIndeterminateVisibility(false);
                                AlertDialog.Builder userCreatingAlert = new AlertDialog.Builder(SignUpActivity.this);
                                userCreatingAlert.setMessage(backendlessFault.getMessage());
                                userCreatingAlert.setTitle("Error ... !");
                                userCreatingAlert.setPositiveButton("Ok!", null);
                                userCreatingAlert.show();
                                Log.d("SignUpError", backendlessFault.getMessage());
                            }
                        });
                    } else {

                    }

                        // create new user
                    }



            }
        });

    }
}
