package com.example.nubilityanimation.Quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Adapter.QuestionAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.Quiz_Question;
import com.example.nubilityanimation.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Quiz_QuestionAcitivity extends AppCompatActivity {
    private String topic_id;
    private DatabaseReference quizReference;
    private ArrayList<Quiz_Question> questionArrayList;
    private RecyclerView recyclerView;
    private Button show_result_button;
    private SharedPreferences sharedPreferences;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
            {
                startActivity(new Intent(getApplicationContext(),Topic_Activity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz__question_acitivity);
        getSupportActionBar().setTitle("Quiz Question");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        init();
        show_result_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//             Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
//             intent.putExtra("topic__id",topic_id);
                String marks = sharedPreferences.getString("mark","mark");
                Toast.makeText(getApplicationContext(),""+marks,Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        quizReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Quiz_Question quiz_question = snapshot.getValue(Quiz_Question.class);
                questionArrayList.add(quiz_question);
                recyclerView.setAdapter(new QuestionAdapter(questionArrayList,getApplicationContext()));


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void init() {
        Bundle intent= getIntent().getExtras();
        topic_id = intent.getString("topic__id");
        questionArrayList= new ArrayList<>();
        show_result_button=findViewById(R.id.show_res_exam);
        recyclerView = findViewById(R.id.user_quiz_recyclarView);
        sharedPreferences = getSharedPreferences("Preferonces",MODE_MULTI_PROCESS);

        quizReference = FirebaseDatabase.getInstance().getReference(ConstantClass.QUIZ_QUESTION);
    }
}