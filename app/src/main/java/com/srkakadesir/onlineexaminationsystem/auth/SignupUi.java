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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.srkakadesir.onlineexaminationsystem.R;
import com.srkakadesir.onlineexaminationsystem.Test.Dashboard;

import java.util.HashMap;
import java.util.Map;

public class SignupUi extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private EditText et_email,et_password,et_confirm_password,et_username;
    private Button bt_signup;
    private TextView tv_already_registered;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser!=null) {
            startActivity(new Intent(SignupUi.this, Dashboard.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_ui);

        et_email = findViewById(R.id.et_email_address_signup);
        et_password = findViewById(R.id.et_password_signup);
        et_confirm_password = findViewById(R.id.et_confirm_password_signup);
        bt_signup = findViewById(R.id.bt_signup_signup);
        tv_already_registered = findViewById(R.id.tv_already_member_signup);
        et_username = findViewById(R.id.et_username_signup);

        tv_already_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupUi.this,LoginUi .class));
            }
        });

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_email.getText())) {
                    if (!TextUtils.isEmpty(et_password.getText())) {
                        if (!et_password.equals(et_confirm_password)){
                            if (!TextUtils.isEmpty(et_username.getText())){
                                createUserByEmailAndPassword(et_email.getText().toString(),et_password.getText().toString());
                            }else {
                                Toast.makeText(SignupUi.this, "Please Enter an Username", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignupUi.this, "Passwords Do not match!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SignupUi.this, "Please Enter a Password!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SignupUi.this, "Please Enter a Email Address!", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void createUserByEmailAndPassword(final String email_address, final String password) {
        mAuth.createUserWithEmailAndPassword(email_address,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    addUserDetails(email_address,task.getResult().getUser().getUid());
                }else {
                    Toast.makeText(SignupUi.this, "ERROR : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void addUserDetails(String email_address, String uid) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String, Object> params = new HashMap<>();
        params.put("username",et_username.getText().toString());
        params.put("email",email_address);
        params.put("uid",uid);
        firebaseFirestore.collection("users").document(uid).set(params).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignupUi.this, "Account Created Successfully !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupUi.this, Dashboard.class));
                } else {
                    Toast.makeText(SignupUi.this, "ERROR : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
