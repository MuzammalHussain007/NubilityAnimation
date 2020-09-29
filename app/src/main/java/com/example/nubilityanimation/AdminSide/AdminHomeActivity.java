package com.example.nubilityanimation.AdminSide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.nubilityanimation.R;
import com.example.nubilityanimation.Registration.LoginActivity;
import com.example.nubilityanimation.UserSide.UserHomeActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        connection();
    }

    private void connection() {
        mAuth=FirebaseAuth.getInstance();
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