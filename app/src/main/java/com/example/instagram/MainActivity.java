package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText emailInput;
    private Button loginBtn;
    private Button btnSignup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.etUserName);
        passwordInput = findViewById(R.id.etPassWord);
        emailInput = findViewById(R.id.etEmail);
        loginBtn = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignUp);

        if (ParseUser.getCurrentUser() != null) {
            final Intent intent = new Intent(MainActivity.this, HomeActivity.class );
            startActivity(intent);
            //this is so that the user cannot just back up and see password and username
            finish();
        } else {


            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String username = usernameInput.getText().toString();
                    final String password = passwordInput.getText().toString();

                    //pass these inputs into the login method
                    login(username, password);
                }
            });

            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String username = usernameInput.getText().toString();
                    final String password = passwordInput.getText().toString();
                    final String email = emailInput.getText().toString();
                    signUp(username, password, email);
                }
            });


        }

    }

    private void login(String username, String password) {
        if (ParseUser.getCurrentUser() != null) {
            final Intent intent = new Intent(MainActivity.this, HomeActivity.class );
            startActivity(intent);
            //this is so that the user cannot just back up and see password and username
            finish();
        } else {
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        Log.d("LoginActivity", "Login successful!");
                        final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        //this is so that the user cannot just back up and see password and username
                        finish();

                    } else {
                        Log.e("LoginActivity", "Login failure");
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void signUp(String username, String password, String email) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("SignUpActivity", "Sign up successful");
                    final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //sign up didn't succeed
                    Log.e("SignUpActivity", "SignUp Failure");
                }

            }
        });

    }


}
