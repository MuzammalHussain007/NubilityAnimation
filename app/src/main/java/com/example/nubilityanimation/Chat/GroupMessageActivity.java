package com.example.nubilityanimation.Chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.nubilityanimation.Adapter.MessageAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.MessageClass;
import com.example.nubilityanimation.Modal.User;
import com.example.nubilityanimation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GroupMessageActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CardView mCardView;
    private EditText mEditText;
    private ImageButton mImageButton;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    private List<MessageClass> message_transfer;
    private MessageClass mMessageClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_message);
        init();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(GroupMessageActivity.this));
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "You can not send Message", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mReference.push().setValue(new MessageClass(mAuth.getCurrentUser().getEmail(),mEditText.getText().toString(),String.valueOf(Calendar.getInstance().getTime())));
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(GroupMessageActivity.this));
        mRecyclerView.setAdapter(new MessageAdapter(message_transfer));


    }

    private void init() {
        mRecyclerView = findViewById(R.id.message_recyclarView);
        mCardView=findViewById(R.id.message_CardView);
        mEditText=findViewById(R.id.user_messagae);
        mImageButton = findViewById(R.id.sender_image_button);
        mAuth=FirebaseAuth.getInstance();
        message_transfer=new ArrayList<>();
        mMessageClass = new MessageClass();
        mReference=FirebaseDatabase.getInstance().getReference(ConstantClass.GROUP_MESSAGE);



    }
}