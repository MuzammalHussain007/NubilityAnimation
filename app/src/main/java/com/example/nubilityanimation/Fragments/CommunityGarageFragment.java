package com.example.nubilityanimation.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.nubilityanimation.Chat.GroupMessageActivity;
import com.example.nubilityanimation.E_Store.ProductViewActivity;
import com.example.nubilityanimation.FanArt.DisplayPostActivity;
import com.example.nubilityanimation.FragmentImplementation.FavouriteUserActivity;
import com.example.nubilityanimation.FragmentImplementation.WatchLaterActivity;
import com.example.nubilityanimation.R;


public class CommunityGarageFragment extends Fragment {
       private ImageView estore,fanart ,disscussion ,watch_Later,favorite;
       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_community_garage, container, false);
        favorite= v.findViewById(R.id.item_favourite);
        estore=v.findViewById(R.id.e_store);
        fanart=v.findViewById(R.id.fan_art);
        disscussion=v.findViewById(R.id.e_discussion);
        watch_Later=v.findViewById(R.id.watch_later);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), FavouriteUserActivity.class));
                getActivity().finish();
            }
        });
        watch_Later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), WatchLaterActivity.class));
                getActivity().finish();
            }
        });

        disscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), GroupMessageActivity.class));
                getActivity().finish();
            }
        });
        fanart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), DisplayPostActivity.class));
                getActivity().finish();
            }
        });
        estore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), ProductViewActivity.class));
                getActivity().finish();
            }
        });
        return  v;
    }
}