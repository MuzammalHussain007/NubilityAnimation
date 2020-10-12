package com.example.nubilityanimation.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nubilityanimation.Chat.GroupMessageActivity;
import com.example.nubilityanimation.FanArt.DisplayPostActivity;
import com.example.nubilityanimation.R;


public class CommunityGarageFragment extends Fragment {
       private TextView estore,fanart , disscussion;

       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_community_garage, container, false);
        disscussion=v.findViewById(R.id.community_garage_text_view);
        fanart=v.findViewById(R.id.fan_art_text_view);
        estore=v.findViewById(R.id.e_store_text_view);

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
        return  v;
    }
}