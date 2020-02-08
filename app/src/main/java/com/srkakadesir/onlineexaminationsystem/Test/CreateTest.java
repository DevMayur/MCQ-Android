package com.srkakadesir.onlineexaminationsystem.Test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.srkakadesir.onlineexaminationsystem.R;

import java.util.HashMap;
import java.util.Map;

public class CreateTest extends AppCompatActivity {

    private EditText et_title;
    private Button bt_add_questions;
    private String sub_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        Intent intent = getIntent();
        sub_id = intent.getStringExtra("sub_id");

        et_title = findViewById(R.id.et_test_title_create_test);
        bt_add_questions = findViewById(R.id.bt_add_questions_create_test);

        bt_add_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTest(sub_id);
            }
        });

    }

    private void createTest(final String sub_id) {
        Log.d("test1logm","in createtest");
        Map<String, Object> params = new HashMap<>();
        params.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        params.put("title",et_title.getText().toString());

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("subjects/"+sub_id+"/tests").document(et_title.getText().toString()).set(params)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("test1logm","in task success");
                            String collection = "subjects/"+sub_id+"/tests";
                            String document = et_title.getText().toString();
                            Intent intent = new Intent(CreateTest.this,AddQuestions.class);
                            intent.putExtra("collection",collection);
                            intent.putExtra("document",document);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else {
                            Toast.makeText(CreateTest.this, "ERROR : ", Toast.LENGTH_SHORT).show();
                            Log.d("test1logm","in task failed");

                        }
                    }
                });
    }
}
