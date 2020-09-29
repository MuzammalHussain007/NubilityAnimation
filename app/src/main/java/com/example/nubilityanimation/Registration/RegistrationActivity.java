package com.example.nubilityanimation.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.User;
import com.example.nubilityanimation.R;
import com.example.nubilityanimation.UserSide.UserHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    private EditText fname,lname,email,password;
    private Button login_btn;
    private FirebaseAuth mAuth ;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        connection();
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInformation();
            }
        });
    }

    private void setInformation() {

        if ((email.getText().toString()!="") && (password.getText().toString()!=""))
        {
            authentication();
        }
    }

    private void authentication() {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            User user = new User(fname.getText().toString(),lname.getText().toString(),"user","");
                            mReference.child(mAuth.getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                           if (task.isSuccessful())
                                           {
                                               startActivity(new Intent(RegistrationActivity.this, PictureUpload.class));
                                               finish();
                                           }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                  Toast.makeText(getApplicationContext(),""+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),""+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void connection() {
        login_btn=findViewById(R.id.btn_registration);
        fname=findViewById(R.id.registration_first_name_edit_text);
        lname=findViewById(R.id.registration_last_name_edit_text);
        email=findViewById(R.id.registration_email_edit_text);
        password=findViewById(R.id.registration_password_edit_text);
        mAuth= FirebaseAuth.getInstance();
        mReference=FirebaseDatabase.getInstance().getReference(ConstantClass.DATABSENAME);

    }
}