package com.example.nubilityanimation.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.AdminSide.AdminHomeActivity;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.R;
import com.example.nubilityanimation.Registration.LoginActivity;
import com.example.nubilityanimation.SplashScreen;
import com.example.nubilityanimation.UserSide.UserHomeActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserFragment extends Fragment {
    private CircleImageView mCircleImageView;
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private FirebaseUser mUser;
    private String fname,lname;
    private TextView user_account,user_notification,user_setting,user_help,user_logout,user_name;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_user, container, false);
        user_account = v.findViewById(R.id.user_fragment_account);
        user_notification=v.findViewById(R.id.user_fragment_notification);
        user_help=v.findViewById(R.id.user_fragment_help);
        user_name=v.findViewById(R.id.user_fragment_name);
        user_setting=v.findViewById(R.id.user_fragment_setting);
        user_logout=v.findViewById(R.id.user_fragment_logout);
        mCircleImageView=v.findViewById(R.id.profile_image);
        conection();
        user_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.user_fragment_master,new Account_Detail_Fragment()).commit();
                mCircleImageView.setVisibility(v.GONE);
                user_name.setVisibility(v.GONE);
                user_account.setVisibility(v.GONE);
                user_help.setVisibility(v.GONE);
                user_notification.setVisibility(v.GONE);
                user_setting.setVisibility(v.GONE);
                user_logout.setVisibility(v.GONE);
            }
        });
        user_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                getActivity().finish();
            }
        });
        mReference.child(mAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        fname=snapshot.child("firstname").getValue().toString();
                        lname=snapshot.child("pastname").getValue().toString();

                        String image = snapshot.child("image").getValue().toString();
                        if (image.isEmpty())
                        {

                            Glide.with(getActivity().getApplicationContext())
                                    .load(R.drawable.user)
                                    .fitCenter()
                                    .centerCrop()
                                    .into(mCircleImageView);
                        }
                        else
                        {
                            user_name.setText(fname+" "+lname);
                            Glide.with(getActivity().getApplicationContext())
                                    .load(image)
                                    .fitCenter()
                                    .centerCrop()
                                    .into(mCircleImageView);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity().getApplicationContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });


        return v;
    }

    private void conection() {

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mReference=FirebaseDatabase.getInstance().getReference(ConstantClass.DATABSENAME);
    }
}