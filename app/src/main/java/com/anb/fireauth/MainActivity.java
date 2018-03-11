package com.anb.fireauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText input_email, input_password;
    TextView btnSignup,btnForgotPass;

    RelativeLayout activity_login;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //View
        btnLogin = (Button)findViewById(R.id.login_btn_login);
        input_email = (EditText)findViewById(R.id.login_email);
        input_password = (EditText)findViewById(R.id.login_password);
        btnSignup = (TextView)findViewById(R.id.login_btn_signup);
        btnForgotPass = (TextView)findViewById(R.id.login_btn_forgot_password);
        activity_login = (RelativeLayout) findViewById(R.id.activity_login);

        btnSignup.setOnClickListener(this);
        btnForgotPass.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        //init Firebase Auth
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this,DashBoard.class));
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn_forgot_password)
        {
            startActivity(new Intent(MainActivity.this,ForgotPassword.class));
            finish();
        }
        else if (v.getId() == R.id.login_btn_signup)
        {
            startActivity(new Intent(MainActivity.this,SignUp.class));
            finish();
        }
        else if (v.getId() == R.id.login_btn_login)
        {
            if (!input_email.getText().toString().equals("") && !input_password.getText().toString().equals("")){
                loginUser(input_email.getText().toString(),input_password.getText().toString());
            }
            else
            {
                Snackbar.make(activity_login,"Fill your email and password", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void loginUser(String email, final String password){
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                        {
                            if (password.length() < 6)
                            {
                                Snackbar.make(activity_login,"Passowrd length must be over 6", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            startActivity(new Intent(MainActivity.this,DashBoard.class));
                        }
                    }
                });
    }
}
