package com.example.nubilityanimation.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.nubilityanimation.Adapter.PostAdapter;
import com.example.nubilityanimation.Adapter.UserVideoAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.FanArt.DisplayPostActivity;
import com.example.nubilityanimation.FragmentImplementation.UserVideoActivity;
import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.PostItem;
import com.example.nubilityanimation.Modal.UserVideoThumbnail;
import com.example.nubilityanimation.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements RecyclarViewInterface {
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
        setHasOptionsMenu(true);
        mThumbnails = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                UserVideoThumbnail userVideoThumbnail = snapshot.getValue(UserVideoThumbnail.class);
                mThumbnails.add(userVideoThumbnail);
                mRecyclerView.setAdapter(new UserVideoAdapter(mThumbnails,getActivity(),HomeFragment.this));

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

    @Override
    public void onClickListner(int position) {

        UserVideoThumbnail userVideoThumbnail = mThumbnails.get(position);
        String id = userVideoThumbnail.getThumbnailid();
        String url = userVideoThumbnail.getVideoURL();
        String img = userVideoThumbnail.getPictureURL();
        Intent intent = new Intent(getActivity().getApplicationContext(), UserVideoActivity.class);
        intent.putExtra("user_video",id);
        intent.putExtra("vidURL",url);
        intent.putExtra("picURL",img);
        getActivity().startActivity(intent);


    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movie_search_view,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.user_movie_search :
            {
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        firebaseSearch(newText);
                        return true;
                    }
                });
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void firebaseSearch(String text)
    {
      List<UserVideoThumbnail> thumbnails = new ArrayList<>();
        for (UserVideoThumbnail userVideoThumbnail : mThumbnails)
        {
           if (userVideoThumbnail.getThumbnailName().toLowerCase().contains(text.toLowerCase()))
           {
             thumbnails.add(userVideoThumbnail);
           }
        }
         mUserVideoAdapter=new UserVideoAdapter(thumbnails,getActivity().getApplicationContext(),HomeFragment.this);
        mRecyclerView.setAdapter(mUserVideoAdapter);

    }
}