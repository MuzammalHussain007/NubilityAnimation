package com.example.nubilityanimation.Chat;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Adapter.MessageAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.MessageClass;
import com.example.nubilityanimation.R;
import com.example.nubilityanimation.UserSide.UserHomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupMessageActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CardView mCardView;
    private EditText mEditText;
    private ImageButton mImageButton;
    private DatabaseReference mReference ,userReference;
    private FirebaseAuth mAuth;
    private List<MessageClass> message_transfer;
    private MessageClass mMessageClass;
    private ScrollView mScrollView;
    private LinearLayoutManager linearLayoutManager;
    String userName,dateString;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
            {
                startActivity(new Intent(GroupMessageActivity.this, UserHomeActivity.class));
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_message);
        init();

        userReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName = snapshot.child("firstname").getValue().toString()+" "+snapshot.child("pastname").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        getSupportActionBar().setTitle("Community");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayoutManager = new LinearLayoutManager(GroupMessageActivity.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "You can not send Message", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DateFormat dateFormat = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        dateFormat = new SimpleDateFormat("hh.mm aa");
                         dateString = dateFormat.format(new Date());
                    }

                    mReference.push().setValue(new MessageClass(userName,mEditText.getText().toString(),dateString));
                    mEditText.setText("");

                }
            }
        });
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MessageClass messageClass = snapshot.getValue(MessageClass.class);
                messageClass.setKey(snapshot.getKey());
                message_transfer.add(messageClass);
                displayMessage(message_transfer);
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
        });  // show Messagae

    }

    private void displayMessage(List<MessageClass> message_transfer) {

        mRecyclerView.setAdapter(new MessageAdapter(message_transfer,getApplicationContext()));
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.scrollToPositionWithOffset(message_transfer.size()-1,message_transfer.size()-2);

    }

    private void init() {
        userReference= FirebaseDatabase.getInstance().getReference(ConstantClass.DATABSENAME);
        mRecyclerView = findViewById(R.id.message_recyclarView);
        mCardView=findViewById(R.id.message_CardView);
        mEditText=findViewById(R.id.user_messagae);
        mImageButton = findViewById(R.id.sender_image_button);
        mAuth=FirebaseAuth.getInstance();
        message_transfer=new ArrayList<>();
        mMessageClass = new MessageClass();
        mScrollView=findViewById(R.id.groupMessage);
        mReference=FirebaseDatabase.getInstance().getReference(ConstantClass.GROUP_MESSAGE);



    }
}