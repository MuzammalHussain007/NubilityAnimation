package com.example.nubilityanimation.FragmentImplementation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Adapter.FavouriteAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.User_Favourite;
import com.example.nubilityanimation.R;
import com.example.nubilityanimation.UserSide.UserHomeActivity;
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
    private List<User_Favourite> mUserFavourites;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
            {
                startActivity(new Intent(FavouriteUserActivity.this, UserHomeActivity.class));
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_user);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setTitle("Favourite");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        init();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               User_Favourite user_favourite = snapshot.getValue(User_Favourite.class);
               mUserFavourites.add(user_favourite);
               mRecyclerView.setAdapter(new FavouriteAdapter(mUserFavourites,getApplicationContext(),FavouriteUserActivity.this));

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
        mUserFavourites= new ArrayList<>();

        mRecyclerView = findViewById(R.id.favouriteRecyclarView);
        mDatabaseReference= FirebaseDatabase.getInstance().getReference(ConstantClass.USERFAVOURITE);
        

    }

    @Override
    public void onClickListner(int position) {
        User_Favourite user_favourite = mUserFavourites.get(position);
        String videoid,videoURL,pictureURL;

        videoid=user_favourite.getVideoId();
        videoURL=user_favourite.getVideoURL();
        pictureURL=user_favourite.getPictureURL();

        Intent intent = new Intent(getApplicationContext(),UserVideoActivity.class);
        intent.putExtra("user_video",videoid);
        intent.putExtra("vidURL",videoURL);
        startActivity(intent);
    }
}