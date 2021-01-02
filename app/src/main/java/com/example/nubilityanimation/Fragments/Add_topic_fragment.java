package com.example.nubilityanimation.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Adapter.TopicAdapter;
import com.example.nubilityanimation.AdminSide.Add_Question_Activity;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.TopicClass;
import com.example.nubilityanimation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Add_topic_fragment extends Fragment implements RecyclarViewInterface {
    private EditText topic_add_editText;
    private Button ad_btn;
    private RecyclerView addTopic_recyclarView;
    private ArrayList<TopicClass> topic_Arraylist;
    private DatabaseReference mdatabaseReference;
    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_topic_fragment, container, false);
        topic_add_editText = v.findViewById(R.id.admin_topic_edit_text);
        ad_btn = v.findViewById(R.id.admin_add_button);
        topic_Arraylist= new ArrayList<>();
        mdatabaseReference= FirebaseDatabase.getInstance().getReference(ConstantClass.QUIZ_TOPIC);
        addTopic_recyclarView=v.findViewById(R.id.admin_add_show_recyclarViw);
        addTopic_recyclarView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));


        ad_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topic_add_editText.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity().getApplicationContext(),"Empty Field",Toast.LENGTH_SHORT).show();
                }
                else
                {
                   id = mdatabaseReference.push().getKey();
                   mdatabaseReference.child(id).setValue(new TopicClass(id,topic_add_editText.getText().toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful())
                           {
                               topic_add_editText.setText("");
                           }
                       }
                   });
                }
            }
        });
        mdatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TopicClass topicClass = snapshot.getValue(TopicClass.class);
                topic_Arraylist.add(topicClass);
                addTopic_recyclarView.setAdapter(new TopicAdapter(getActivity().getApplicationContext(),topic_Arraylist,Add_topic_fragment.this));
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


        return v ;
    }

    @Override
    public void onClickListner(int position) {
        TopicClass topicClass = topic_Arraylist.get(position);
        Intent intent = new Intent(getActivity().getApplicationContext(), Add_Question_Activity.class);
        intent.putExtra("topic__id",topicClass.getTpoicid());
        startActivity(intent);
    }
}