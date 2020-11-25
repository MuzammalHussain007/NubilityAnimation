package com.example.nubilityanimation.Fragments;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Adapter.UserVideoAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.FragmentImplementation.UserVideoActivity;
import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.UserVideoThumbnail;
import com.example.nubilityanimation.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements RecyclarViewInterface {
    private RecyclerView mRecyclerView;
    private DatabaseReference mReference;
    private UserVideoAdapter mUserVideoAdapter;
    private List<UserVideoThumbnail> mThumbnails;
    private String id;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        View v= inflater.inflate(R.layout.fragment_home, container, false);
        mReference= FirebaseDatabase.getInstance().getReference(ConstantClass.USERVIDEO);
        mRecyclerView= v.findViewById(R.id.user_home_recyclarView);
        setHasOptionsMenu(true);
        mThumbnails = new ArrayList<>();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),3));
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
         id = userVideoThumbnail.getThumbnailid();
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
        getActivity().getMenuInflater().inflate(R.menu.movie_search_view,menu);
        MenuItem item = menu.findItem(R.id.user_movie_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search Here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                firbaseSearch(s);


                return true;

            }
        });



    }

    private void firbaseSearch(String s) {
        List<UserVideoThumbnail> searchItem = new ArrayList<>();
        for (UserVideoThumbnail search : mThumbnails)
        {
            if (search.getThumbnailName().toLowerCase().contains(s.toLowerCase()))
            {
                searchItem.add(search);
            }
            mUserVideoAdapter = new UserVideoAdapter(searchItem,getContext().getApplicationContext(),HomeFragment.this);
            mRecyclerView.setAdapter(mUserVideoAdapter);
            mUserVideoAdapter.notifyDataSetChanged();
        }
    }
}