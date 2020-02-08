package com.srkakadesir.onlineexaminationsystem.Test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.srkakadesir.onlineexaminationsystem.R;

import java.util.HashMap;
import java.util.Map;

public class AddQuestions extends AppCompatActivity {

    private String collection,document;
    private String new_collection;
    private TextView tv_question_number;
    private EditText question,option1,option2,option3,option4;
    private Button correct1,correct2,correct3,correct4,next;
    private int selected_option = 0;
    int no;

    @Override
    protected void onStart() {
        super.onStart();
        no = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        tv_question_number = findViewById(R.id.tv_question_number_add_questions);
        question = findViewById(R.id.et_question_add_question);
        option1 = findViewById(R.id.et_option_1_add_question);
        option2 = findViewById(R.id.et_option_2_add_question);
        option3 = findViewById(R.id.et_option_3_add_question);
        option4 = findViewById(R.id.et_option_4_add_question);

        correct1 = findViewById(R.id.bt_correct_1_add_question);
        correct2 = findViewById(R.id.bt_correct_2_add_question);
        correct3 = findViewById(R.id.bt_correct_3_add_question);
        correct4 = findViewById(R.id.bt_correct_4_add_question);

        next = findViewById(R.id.bt_next_add_question);

        correct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct1.setBackgroundColor(getResources().getColor(R.color.colorCorrectAnswer));
                correct2.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
                correct3.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
                correct4.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
                selected_option = 1;
            }
        });

        correct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct1.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
                correct2.setBackgroundColor(getResources().getColor(R.color.colorCorrectAnswer));
                correct3.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
                correct4.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
                selected_option = 2;
            }
        });

        correct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct1.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
                correct2.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
                correct3.setBackgroundColor(getResources().getColor(R.color.colorCorrectAnswer));
                correct4.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
                selected_option = 3;
            }
        });

        correct4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correct1.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
                correct2.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
                correct3.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
                correct4.setBackgroundColor(getResources().getColor(R.color.colorCorrectAnswer));
                selected_option = 4;
            }
        });

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(option1.getText()) && !TextUtils.isEmpty(option2.getText()) &&
                        !TextUtils.isEmpty(option3.getText()) && !TextUtils.isEmpty(option4.getText())) {

                    if (!TextUtils.isEmpty(question.getText())){

                        if (selected_option != 0) {
                            setQuestions(no);

                        } else {
                            Toast.makeText(AddQuestions.this, "Please Select an Option ", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(AddQuestions.this, "Please Enter Question...", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(AddQuestions.this, "Please fill four options", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intent = getIntent();
        collection = intent.getStringExtra("collection");
        document = intent.getStringExtra("document");
        new_collection = collection + "/" + document + "/questions";
    }

    public void setQuestions(int no) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        if (no <= 10) {
            Map<String, Object> que_params = new HashMap<>();
            que_params.put("question_number",no);
            que_params.put("option1",option1.getText().toString());
            que_params.put("option2",option2.getText().toString());
            que_params.put("option3",option3.getText().toString());
            que_params.put("option4",option4.getText().toString());
            que_params.put("correct_option",selected_option);

            firebaseFirestore.collection(new_collection).document("question_"+no).set(que_params);
            this.no++;

            option1.setText("");
            option2.setText("");
            option3.setText("");
            option4.setText("");

            correct1.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
            correct2.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
            correct3.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
            correct4.setBackgroundColor(getResources().getColor(R.color.colorNormalAnswer));
            selected_option = 0;

        }else {
            Toast.makeText(this, "10 Question Completed", Toast.LENGTH_SHORT).show();
        }
    }
}
