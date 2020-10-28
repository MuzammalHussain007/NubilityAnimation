package com.example.nubilityanimation.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nubilityanimation.Adapter.PostAdapter;
import com.example.nubilityanimation.Adapter.UserVideoAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.FanArt.DisplayPostActivity;
import com.example.nubilityanimation.Modal.PostItem;
import com.example.nubilityanimation.Modal.UserVideoThumbnail;
import com.example.nubilityanimation.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private DatabaseReference mReference;
    private UserVideoAdapter mUserVideoAdapter;
    private List<UserVideoThumbnail> mThumbnails;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_home, container, false);
        mReference= FirebaseDatabase.getInstance().getReference(ConstantClass.USERVIDEO);
        mRecyclerView= v.findViewById(R.id.user_home_recyclarView);
        mThumbnails = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                UserVideoThumbnail userVideoThumbnail = snapshot.getValue(UserVideoThumbnail.class);
                mThumbnails.add(userVideoThumbnail);
                mRecyclerView.setAdapter(new UserVideoAdapter(mThumbnails,getActivity()));

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



        return v;
    }
}