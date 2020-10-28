package com.example.nubilityanimation.FanArt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nubilityanimation.Adapter.CommentAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.UserComment;
import com.example.nubilityanimation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private DatabaseReference userReference ,postReference , commentReference;
    private FirebaseAuth mAuth;
    private ImageView mImageView;
    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private List<UserComment> mUserComments=new ArrayList<>();
    String name ,image ,id,commentid;

   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        init();
        commentReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserComment userComment = snapshot.getValue(UserComment.class);
                mUserComments.add(userComment);
                mRecyclerView.setAdapter(new CommentAdapter(mUserComments,CommentActivity.this));
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
        userReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name= snapshot.child("firstname").getValue().toString();
                image = snapshot . child("image").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText.getText().toString()=="")
                {
                    Toast.makeText(getApplicationContext(),"You can not send comment",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Bundle intent= getIntent().getExtras();
                    id = intent.getString("postid");
                    commentid = commentReference.push().getKey();
                    commentReference.child(commentid).setValue(new UserComment(commentid,name,image,mEditText.getText().toString()));

                    mEditText.setText("");

                }

            }
        });
    }

    private void init() {
        Bundle intent= getIntent().getExtras();
        id = intent.getString("postid");

            userReference= FirebaseDatabase.getInstance().getReference(ConstantClass.DATABSENAME);
            postReference=FirebaseDatabase.getInstance().getReference(ConstantClass.USER_POST);
            commentReference = FirebaseDatabase.getInstance().getReference(ConstantClass.USERCOMMENT).child(id);
            mAuth= FirebaseAuth.getInstance();
            mImageView =findViewById(R.id.comment_send_btn);
            mEditText=findViewById(R.id.comment_text);
            mRecyclerView = findViewById(R.id.comment_activity_recyclarView);


    }
}