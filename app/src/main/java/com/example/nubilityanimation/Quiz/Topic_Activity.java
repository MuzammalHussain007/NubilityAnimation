package com.example.nubilityanimation.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Adapter.TopicAdapter;
import com.example.nubilityanimation.Chat.GroupMessageActivity;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.TopicClass;
import com.example.nubilityanimation.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Topic_Activity extends AppCompatActivity implements RecyclarViewInterface {
    private RecyclerView quiz_topic_recyclarView;
    private DatabaseReference quiz_topic_database;
    private ArrayList<TopicClass> mArraylist;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
            {
                startActivity(new Intent(this, GroupMessageActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_);
        connection();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quiz Topic");
        quiz_topic_recyclarView.setLayoutManager(new LinearLayoutManager(Topic_Activity.this));
        quiz_topic_database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TopicClass topicClass = snapshot.getValue(TopicClass.class);
                mArraylist.add(topicClass);
                quiz_topic_recyclarView.setAdapter(new TopicAdapter(getApplicationContext(),mArraylist,Topic_Activity.this));
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
        }) ;

    }

    private void connection() {
        mArraylist= new ArrayList<>();
        quiz_topic_database= FirebaseDatabase.getInstance().getReference(ConstantClass.QUIZ_TOPIC);
        quiz_topic_recyclarView = findViewById(R.id.quiz_recyclarView);
    }

    @Override
    public void onClickListner(int position) {
        TopicClass topicClass = mArraylist.get(position);
        String id = topicClass.getTpoicid();
        Intent intent = new Intent(getApplicationContext(),Quiz_QuestionAcitivity.class);
        intent.putExtra("topic__id",id);
        startActivity(intent);
    }
}