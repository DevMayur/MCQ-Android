package com.srkakadesir.onlineexaminationsystem.Test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.srkakadesir.onlineexaminationsystem.adapters.SubjectAdapter;
import com.srkakadesir.onlineexaminationsystem.models.SubjectModel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Dashboard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button add_subject;
    private SubjectAdapter adapter;
    private List<SubjectModel> sList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        add_subject = findViewById(R.id.bt_add_subject_dashboard);
        recyclerView = findViewById(R.id.recycler_view_subjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sList = new ArrayList<>();
        adapter = new SubjectAdapter(this,sList);
        recyclerView.setAdapter(adapter);

        add_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,AddSubject.class));
            }
        });

        fetchSubjects();

    }

    private void fetchSubjects() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("subjects").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (!queryDocumentSnapshots.isEmpty()){
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        SubjectModel model = doc.getDocument().toObject(SubjectModel.class).withId(doc.getDocument().getId());
                        sList.add(model);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(Dashboard.this, "No Subjects Status ! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
