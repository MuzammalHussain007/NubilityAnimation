package com.example.nubilityanimation.AdminSide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.nubilityanimation.Adapter.AdminFragmentAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Fragments.UserVideo;
import com.example.nubilityanimation.R;
import com.example.nubilityanimation.Registration.LoginActivity;
import com.example.nubilityanimation.UserSide.UserHomeActivity;
import com.facebook.login.LoginManager;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminHomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth ;
    private TabLayout mTabLayout ;
    private ViewPager mViewPager;
    private DatabaseReference mDatabaseReference;
    private AdminFragmentAdapter mAdminFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        connection();
        mAdminFragmentAdapter.addFragment(new UserVideo(),"UserVideo");
        mViewPager.setAdapter(mAdminFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void connection() {
        mAuth=FirebaseAuth.getInstance();
        mDatabaseReference= FirebaseDatabase.getInstance().getReference(ConstantClass.USERVIDEO);
        mTabLayout=findViewById(R.id.admin_activity_tab_layout);
        mViewPager= findViewById(R.id.admin_activity_viewPager);
        mAdminFragmentAdapter = new AdminFragmentAdapter(getSupportFragmentManager());
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
                LoginManager.getInstance().logOut();
                startActivity(new Intent(AdminHomeActivity.this, LoginActivity.class));
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}