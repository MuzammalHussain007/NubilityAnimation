package com.example.nubilityanimation.Registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.nubilityanimation.AdminSide.AdminHomeActivity;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.User;
import com.example.nubilityanimation.R;
import com.example.nubilityanimation.UserSide.UserHomeActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG ="LoginActivity".getClass().getName() ;
    private EditText email,password;
    private Button btn_login;
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private CallbackManager mCallbackManager;
    private LoginButton mLoginButton;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connection();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setloginactivity();
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                      //  Toast.makeText(getApplicationContext(),""+loginResult.toString(),Toast.LENGTH_SHORT).show();
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);

                    }
                });



            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }



    private void setloginactivity() {
        if (email.getText().toString()!="" && password.getText().toString()!="")
        {
            enterIntologin();
        }
    }

    private void enterIntologin() {
        mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful())
                          {
                              mReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                                      String type = snapshot.child("type").getValue().toString();
                                      if (type.contains("admin"))
                                      {
                                          startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                                          finish();
                                      }
                                      else if (type.contains("user"))
                                      {
                                          startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
                                          finish();

                                      }


                                  }

                                  @Override
                                  public void onCancelled(@NonNull DatabaseError error) {
                                      Toast.makeText(getApplicationContext(),""+error.getMessage(),Toast.LENGTH_SHORT).show();

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
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "signInWithCredential:success");
                            User user = new User(mAuth.getCurrentUser().getDisplayName(),"","user",mAuth.getCurrentUser().getPhotoUrl().toString());
                            mReference.child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        startActivity(new Intent(LoginActivity.this,UserHomeActivity.class));

                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else {
                            Toast.makeText(getApplicationContext(),""+task.getException(),Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "signInWithCredential:failure", task.getException());


                        }


                    }
                });
    }


    private void connection() {
        email=findViewById(R.id.email_login_EditText);
        password=findViewById(R.id.password_login_EditText);
        mReference= FirebaseDatabase.getInstance().getReference(ConstantClass.DATABSENAME);
        mAuth=FirebaseAuth.getInstance();
        btn_login=findViewById(R.id.login_button);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager=CallbackManager.Factory.create();
        mLoginButton=findViewById(R.id.login_socails_links);
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setMessage("Uploaded");
    }


    public void shift(View view) {
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
        finish();
    }

    public void setForget(View view) {
      startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
      finish();
    }
}