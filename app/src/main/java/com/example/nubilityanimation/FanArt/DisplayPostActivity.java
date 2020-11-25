package com.example.nubilityanimation.FanArt;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Adapter.PostAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.PostItem;
import com.example.nubilityanimation.R;
import com.example.nubilityanimation.UserSide.UserHomeActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DisplayPostActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CardView mCardView;
    private List<PostItem> mPostItems;
    private DatabaseReference mReference;
    private List<String> postidList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_post);
        init();
        getSupportActionBar().setTitle("Fan-Art");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(DisplayPostActivity.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

               PostItem postItem = snapshot.getValue(PostItem.class);
               mPostItems.add(postItem);
               mRecyclerView.setAdapter(new PostAdapter(mPostItems,DisplayPostActivity.this));



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
        mPostItems=new ArrayList<>();
        mRecyclerView = findViewById(R.id.display_post_recyclarView);
        mCardView=findViewById(R.id.user_post_cardView);
        mReference= FirebaseDatabase.getInstance().getReference(ConstantClass.USER_POST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_post_layout,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.create_post:
            {
             startActivity(new Intent(getApplicationContext(),CreatePostActivity.class));
                finish();
                break;
            }
            case android.R.id.home :
            {
                startActivity(new Intent(DisplayPostActivity.this, UserHomeActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}