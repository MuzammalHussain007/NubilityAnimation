package com.example.nubilityanimation.AdminSide;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Adapter.QuestionAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Fragments.Add_topic_fragment;
import com.example.nubilityanimation.Modal.Quiz_Question;
import com.example.nubilityanimation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Add_Question_Activity extends AppCompatActivity {
    private String id,question_statement,question_option1,question_option2,question_option3,question_corect_Answer;
    private EditText question,option1,option2,option3,answer;
    private DatabaseReference questionDatabase;
    private ArrayList<Quiz_Question> questionArrayList;
    private Button quiz_Add_button;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__question_);
        init();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        quiz_Add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question_statement=question.getText().toString();
                question_option1=option1.getText().toString();
                question_option2=option2.getText().toString();
                question_option3=option3.getText().toString();
                question_corect_Answer=answer.getText().toString();
                if (question_statement.isEmpty() && question_option1.isEmpty() && question_option2.isEmpty() && question_option3.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Fill all field",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String quiz_id=questionDatabase.push().getKey();

                    Quiz_Question quiz_question = new Quiz_Question(quiz_id,question_statement,question_option1,question_option2,question_option3,question_corect_Answer);
                    questionDatabase.child(id).setValue(quiz_question).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful())
                         {
                             question.setText("");
                             option1.setText("");
                             option2.setText("");
                             option3.setText("");
                             answer.setText("");
                         }
                        }
                    });

                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionDatabase.addChildEventListener(new ChildEventListener() {
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId())
    {
        case android.R.id.home:
        {
            startActivity(new Intent(getApplicationContext(), Add_topic_fragment.class));
            finish();
            break;
        }
    }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        Bundle intent= getIntent().getExtras();
        id = intent.getString("topic__id");
        question=findViewById(R.id.quiz_question_statement);
        option1=findViewById(R.id.quiz_option1);
        option2=findViewById(R.id.quiz_option2);
        option3=findViewById(R.id.quiz_option3);
        answer=findViewById(R.id.quiz_corect_Answer);
        questionArrayList=new ArrayList<>();
        quiz_Add_button=findViewById(R.id.quiz_buttion_add);
        recyclerView=findViewById(R.id.quiz_question_recyclarView);

        questionDatabase = FirebaseDatabase.getInstance().getReference(ConstantClass.QUIZ_QUESTION);
    }
}