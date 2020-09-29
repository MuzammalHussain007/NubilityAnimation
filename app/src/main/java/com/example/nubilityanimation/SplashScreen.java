package com.example.nubilityanimation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.nubilityanimation.AdminSide.AdminHomeActivity;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Registration.LoginActivity;
import com.example.nubilityanimation.UserSide.UserHomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        connection();
        getSupportActionBar().hide();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
               if (mAuth.getCurrentUser()!=null)
               {
                   mReference.child(mAuth.getCurrentUser().getUid())
                           .addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   String type = snapshot.child("type").getValue().toString();
                                   if (type.contains("admin"))
                                   {
                                       startActivity(new Intent(SplashScreen.this, AdminHomeActivity.class));
                                       finish();
                                   }
                                   else if (type.contains("user"))

                                   {
                                       startActivity(new Intent(SplashScreen.this, UserHomeActivity.class));
                                       finish();
                                   }

                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {
                                   Toast.makeText(getApplicationContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();

                               }
                           });

               }
               else
               {
                   startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                   finish();
               }

            }
        },ConstantClass.TIME);

    }

    private void connection() {
        mHandler = new Handler();
        mAuth=FirebaseAuth.getInstance();
        mReference= FirebaseDatabase.getInstance().getReference(ConstantClass.DATABSENAME);
    }
}