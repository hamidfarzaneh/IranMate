package com.hackatrip.iranmate.iranmate.first_page;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.hackatrip.iranmate.iranmate.Model.MainApp;
import com.hackatrip.iranmate.iranmate.R;
import com.hackatrip.iranmate.iranmate.main_page.MainActivity;

/*
FirstPage activity : the first page that will be shown until a user log in
it have three button : guest mode , login , sign up
guest mode : no login needed , no appdrawer , only public post and public groups are shown
 */

public class FirstPageActivity extends AppCompatActivity {
    ViewPager viewPaper;
    private static final MainApp mainModel  = (MainApp) MainApp.getContext();                               // link to application module

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        viewPaper = (ViewPager)findViewById(R.id.viewPager);
        PagerAdapter adapter = new CustomSlideShowAdapter(FirstPageActivity.this);                         // slide show
        viewPaper.setAdapter(adapter);
        Button loginButton = (Button) findViewById(R.id.LoginButton);
        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        Button loginAsAGuestButton = (Button) findViewById(R.id.loginAsAGuestButton);

        BackendlessUser currentUser = Backendless.UserService.CurrentUser();
        String userToken = UserIdStorageFactory.instance().getStorage().get();

        if (userToken != null && !userToken.equals("") ) {                                                // if a user is already loged in first
                                                                                                          // page wont show , it goes directly to main
            Backendless.Data.of( BackendlessUser.class ).findById(userToken, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {

                }

                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                }
            });
            mainModel.setBackendLogedInUser(currentUser);
            Intent goToMain = new Intent(this, MainActivity.class);
            goToMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);                                            // clearing the stack
            goToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(goToMain);

        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        loginAsAGuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstPageActivity.this.finish();
            }
        });




    }
    public void login(){
        Intent loginIntent = new Intent(this,LoginActivity.class);
        startActivity(loginIntent);
    }
    public void signUp(){
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(signUpIntent);


    }

}
