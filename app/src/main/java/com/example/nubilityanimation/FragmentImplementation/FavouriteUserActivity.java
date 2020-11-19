package com.example.nubilityanimation.FragmentImplementation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Adapter.UserWatchLaterAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.User_Watch_Later;
import com.example.nubilityanimation.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FavouriteUserActivity extends AppCompatActivity implements RecyclarViewInterface {
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseReference;
    private List<User_Watch_Later> mUserWatchLater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_user);
        getSupportActionBar().setTitle("Favourite");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        init();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User_Watch_Later user_watch_later = snapshot.getValue(User_Watch_Later.class);
                mUserWatchLater.add(user_watch_later);
                mRecyclerView.setAdapter(new UserWatchLaterAdapter(mUserWatchLater,getApplicationContext(),FavouriteUserActivity.this));

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
        mUserWatchLater=new ArrayList<>();
        mRecyclerView = findViewById(R.id.favouriteRecyclarView);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference(ConstantClass.USERFAVOURITE);
        

    }

    @Override
    public void onClickListner(int position) {
        User_Watch_Later user_watch_later = mUserWatchLater.get(position);
        String videoid,videoURL,pictureURL;

        videoid=user_watch_later.getVideoId();
        videoURL=user_watch_later.getVideoURL();
        pictureURL=user_watch_later.getPictureURL();

        Intent intent = new Intent(getApplicationContext(),UserVideoActivity.class);
        intent.putExtra("user_video",videoid);
        intent.putExtra("vidURL",videoURL);
        startActivity(intent);
    }
}