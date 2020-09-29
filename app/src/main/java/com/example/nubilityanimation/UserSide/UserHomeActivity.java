package com.example.nubilityanimation.UserSide;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.nubilityanimation.Fragments.CommunityGarageFragment;
import com.example.nubilityanimation.Fragments.HomeFragment;
import com.example.nubilityanimation.Fragments.UserFragment;
import com.example.nubilityanimation.R;
import com.example.nubilityanimation.Registration.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class UserHomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        connection();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container,new HomeFragment()).commit();
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_home :
                    {
                        FragmentManager fm = getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.container,new HomeFragment()).commit();
                        break;
                    }
                    case R.id.action_community_garage:
                    {
                        FragmentManager fm = getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.container,new CommunityGarageFragment()).commit();
                        break;
                    }
                    case R.id.action_profile :
                    {
                        FragmentManager fm = getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.container,new UserFragment()).commit();
                        break;
                    }
                }


                return true;
            }
        });
    }

    private void connection() {
        mAuth=FirebaseAuth.getInstance();
        mBottomNavigationView=findViewById(R.id.nav_bar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_logout:
            {
                mAuth.signOut();
                startActivity(new Intent(UserHomeActivity.this, LoginActivity.class));
                finish();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }
}