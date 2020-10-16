package com.example.nubilityanimation.FanArt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
 import androidx.appcompat.widget.Toolbar;


import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Adapter.PostAdapter;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.PostItem;
import com.example.nubilityanimation.R;
import com.example.nubilityanimation.Registration.PictureUpload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CreatePostActivity extends AppCompatActivity {
    private EditText mEditText;
    private Button maction;
    private ImageView mImageView;
    private FirebaseAuth mAuth;
    private DatabaseReference createpostreference,userReference;
    private FloatingActionButton mFloatingActionButton;
    private ProgressDialog mProgressDialog;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String currentPhotoPath;
    private StorageReference mStorageReference;
    private String uname , uuserimage ,postid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        init();
        mFloatingActionButton
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageSelection();
                    }
                });

        maction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePostActivity.this,DisplayPostActivity.class);
                intent.putExtra("postiD",postid);
                startActivity(intent);
                finish();
            }
        });

    }

    private void ImageSelection() {
        AlertDialog.Builder imageSelecter = new AlertDialog.Builder(CreatePostActivity.this);
        String list[] ={"Camera","Gallery"};
        imageSelecter.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0:
                    {
                        selectionFromCamera();
                        break;
                    }
                    case 1:
                    {
                        selectionFromGallery();
                        break;
                    }
                }
            }
        });
        imageSelecter.create().show();

    }

    private void selectionFromCamera() {

        boolean res = checkPermissions();
        if (res==true)
        {
            dispatchTakePictureIntent();

        }
        else
        {
            checkPermissions();
        }


    }

    private boolean checkPermissions() {
        String[] permissions = new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(),p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),ConstantClass.PERMISSIONREQUEST);
            return false;
        }
        return true;
    }

    private void selectionFromGallery() {
        boolean res = checkPermissions();
        if (res==true)
        {

            Intent gallery = new Intent();
            gallery.setType("image/*");
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), ConstantClass.GALLERY_REQUEST_CODE);

        }
        else
        {
            checkPermissions();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ConstantClass.GALLERY_REQUEST_CODE)
        {
            Glide.with(getApplicationContext()).load(data.getData()).into(mImageView);

            SavetoStorage(data.getData());
        }

        if (requestCode==ConstantClass.CAMERA_REQUEST_PERMISSION)
        {
            File f= new File(currentPhotoPath);
            mImageView.setImageURI(Uri.fromFile(f));
            SavetoStorage(Uri.fromFile(f));

        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.nubilityanimation.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, ConstantClass.CAMERA_REQUEST_PERMISSION);
            }
        }
    }
    private void SavetoStorage(final Uri data){
        if (data==null)
        {
            Glide.with(getApplicationContext()).load(R.drawable.user).into(mImageView);
        }
        else
        {

            mProgressDialog.show();
            StorageReference reference = mStorageReference.child(ConstantClass.USERPOSTIMAGE+ UUID.randomUUID().toString());
            reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(final Uri uri) {
                            userReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                     String name = snapshot.child("firstname").getValue().toString()+" "+ snapshot.child("pastname").getValue().toString();
                                     String img =snapshot.child("image").getValue().toString();
                                     postid=createpostreference.push().getKey();
                                     mEditor.putString("post",postid).commit();
                                     createpostreference.child(postid).setValue(new PostItem(postid,mEditText.getText().toString(),uri.toString(),name,img));
                                     mProgressDialog.dismiss();
                                     mEditText.setText("");

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_SHORT).show();

                                }
                            });



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                    mProgressDialog.setMessage("Uploaded "+(int)progress+"%");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),""+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });



        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==ConstantClass.PERMISSIONREQUEST)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"All Permission Granted",Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void init() {
        mEditText=findViewById(R.id.user_post_edit_text);
        mImageView=findViewById(R.id.user_post_image_view);
        maction=findViewById(R.id.usre_action_butn);
        mAuth=FirebaseAuth.getInstance();
        mSharedPreferences=getSharedPreferences(ConstantClass.MYPREFERENCE,Context.MODE_PRIVATE);
        mProgressDialog = new ProgressDialog(CreatePostActivity.this);
        mEditor = mSharedPreferences.edit();
        mFloatingActionButton=findViewById(R.id.user_post_action_button);
        mStorageReference= FirebaseStorage.getInstance().getReference();
        userReference= FirebaseDatabase.getInstance().getReference(ConstantClass.DATABSENAME);
        createpostreference=FirebaseDatabase.getInstance().getReference(ConstantClass.USER_POST);

    }
}