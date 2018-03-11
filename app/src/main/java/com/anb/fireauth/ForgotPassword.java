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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText input_email;
    private Button btnResetpPass;
    private TextView btnBack;
    private RelativeLayout activity_forgot;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //View
        input_email = (EditText)findViewById(R.id.forgot_email);
        btnResetpPass = (Button)findViewById(R.id.forgot_btn_reset);
        btnBack = (Button)findViewById(R.id.forgot_btn_back);
        activity_forgot = (RelativeLayout)findViewById(R.id.activity_forgot_password);

        btnResetpPass.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        //Init Firebase
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.forgot_btn_back)
        {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        else if (v.getId() == R.id.forgot_btn_reset)
        {
            resetPassword(input_email.getText().toString());
        }
    }

    private void resetPassword(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Snackbar.make(activity_forgot, "we have sent password to email: "+email,Snackbar.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Snackbar.make(activity_forgot, "Failed to send password" ,Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
