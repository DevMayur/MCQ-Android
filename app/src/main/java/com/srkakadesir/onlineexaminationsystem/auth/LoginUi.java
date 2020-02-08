package com.srkakadesir.onlineexaminationsystem.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.srkakadesir.onlineexaminationsystem.R;
import com.srkakadesir.onlineexaminationsystem.Test.AddSubject;
import com.srkakadesir.onlineexaminationsystem.Test.Dashboard;

public class LoginUi extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private EditText et_email,et_password;
    private Button bt_login;
    private TextView tv_not_member_yet;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser!=null) {
            startActivity(new Intent(LoginUi.this, Dashboard.class));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ui);

        et_email = findViewById(R.id.et_email_address_login);
        et_password = findViewById(R.id.et_password_login);
        bt_login = findViewById(R.id.bt_login_login);
        tv_not_member_yet = findViewById(R.id.tv_not_member_login);


        tv_not_member_yet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginUi.this,SignupUi.class));
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_email.getText())) {
                    if (!TextUtils.isEmpty(et_password.getText())) {

                        loginByEmailAndPassword(et_email.getText().toString(),et_password.getText().toString());

                    }else {
                        Toast.makeText(LoginUi.this, "Please Enter a Password!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginUi.this, "Please Enter a Email Address!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void loginByEmailAndPassword(String email_address, String password) {
        mAuth.signInWithEmailAndPassword(email_address,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginUi.this, "Login Success !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginUi.this, Dashboard.class));
                }else {
                    Toast.makeText(LoginUi.this, "ERROR : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
