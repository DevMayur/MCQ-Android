package com.srkakadesir.onlineexaminationsystem.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.srkakadesir.onlineexaminationsystem.R;
import com.srkakadesir.onlineexaminationsystem.models.SubjectModel;

import javax.annotation.Nullable;

public class AllTests extends AppCompatActivity {

    private String sub_id;
    private Button bt_create_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tests);
        Intent intent = getIntent();
        sub_id = intent.getStringExtra("sub_id");
        bt_create_test = findViewById(R.id.bt_create_new_test_all_tests);

        bt_create_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTests.this, CreateTest.class);
                intent.putExtra("sub_id",sub_id);
                startActivity(intent);
            }
        });


        fetchTests();

    }

    private void fetchTests() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    }
}
