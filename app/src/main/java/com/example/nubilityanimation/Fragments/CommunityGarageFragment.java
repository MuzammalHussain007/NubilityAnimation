package com.example.nubilityanimation.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.nubilityanimation.Chat.GroupMessageActivity;
import com.example.nubilityanimation.E_Store.ProductViewActivity;
import com.example.nubilityanimation.E_Store.UserCartActivity;
import com.example.nubilityanimation.FanArt.DisplayPostActivity;
import com.example.nubilityanimation.FragmentImplementation.FavouriteUserActivity;
import com.example.nubilityanimation.FragmentImplementation.WatchLaterActivity;
import com.example.nubilityanimation.R;


public class CommunityGarageFragment extends Fragment {
       private TextView estore,fanart ,disscussion ,add_tocart,watch_Later,favorite;
       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_community_garage, container, false);
        disscussion=v.findViewById(R.id.community_garage_text_view);
        fanart=v.findViewById(R.id.fan_art_text_view);
        estore=v.findViewById(R.id.e_store_text_view);
        favorite=v.findViewById(R.id.favourite_text_view);
        add_tocart=v.findViewById(R.id.cart_text_view);
        watch_Later=v.findViewById(R.id.watch_later_text_view);
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
        add_tocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getActivity().getApplicationContext(), UserCartActivity.class));
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