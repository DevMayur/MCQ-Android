package com.srkakadesir.onlineexaminationsystem.Test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.srkakadesir.onlineexaminationsystem.R;

import java.util.HashMap;
import java.util.Map;

public class AddSubject extends AppCompatActivity {

    private EditText et_subject_name;
    private Button bt_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        et_subject_name = findViewById(R.id.et_subject_name_add_subject);
        bt_add = findViewById(R.id.bt_add_add_subject);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(et_subject_name.getText())) {
                    addSubject();
                }else {
                    Toast.makeText(AddSubject.this, "Please Enter a Subject Title !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void addSubject() {
        Map<String , Object> params = new HashMap<>();
        params.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        params.put("title",et_subject_name.getText().toString());

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("subjects").add(params).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddSubject.this, "Subject Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddSubject.this,Dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddSubject.this, "ERROR : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
