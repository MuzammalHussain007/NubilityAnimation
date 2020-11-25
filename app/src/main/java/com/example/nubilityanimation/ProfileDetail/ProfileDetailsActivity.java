package com.example.nubilityanimation.ProfileDetail;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.User;
import com.example.nubilityanimation.R;
import com.example.nubilityanimation.UserSide.UserHomeActivity;
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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDetailsActivity extends AppCompatActivity {
    private FloatingActionButton mFloatingActionButton;
    private DatabaseReference mReference;
    private FirebaseAuth mAuth;
    private StorageReference mFirebaseStorage;
    private CircleImageView mCircleImageView;
    private ProgressDialog mProgressDialog;
    private TextView first_name,last_name,email_address;
    private String currentPhotoPath;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
            {
                startActivity(new Intent(ProfileDetailsActivity.this, UserHomeActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        connection();
        getSupportActionBar().setTitle("Profile Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mReference.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                first_name.setText(user.getFirstname());
                last_name.setText(user.getPastname());
                email_address.setText(mAuth.getCurrentUser().getEmail());
                if (user.getImage().isEmpty())
                {
                    Glide.with(ProfileDetailsActivity.this)
                            .load(R.drawable.user).centerCrop().fitCenter()
                            .into(mCircleImageView);
                }
                else
                {
                    Glide.with(ProfileDetailsActivity.this)
                            .load(user.getImage()).centerCrop().fitCenter()
                            .into(mCircleImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOption();
            }
        });
    }

    private void createOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileDetailsActivity.this);
        String option[] = new String[]{"Update First Name","Update Last Name", "Update Image"};
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0 :
                    {
                        mReference.child(mAuth.getCurrentUser().getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ProfileDetailsActivity.this);
                                        View view = getLayoutInflater().inflate(R.layout.custom_edit_firstname_field,null);
                                        final EditText firsname;
                                        Button update ,cancel;
                                        firsname=view.findViewById(R.id.editabe_firstname);
                                        update=view.findViewById(R.id.btn_editable_firstname);
                                        cancel=view.findViewById(R.id.btn_editable_cancel_firstname);
                                        builder1.setView(view);
                                        final AlertDialog alertDialog = builder1.create();
                                        alertDialog.show();

                                        update.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                HashMap<String,Object> map = new HashMap<>();
                                                map.put("firstname",firsname.getText().toString());
                                                mReference.child(mAuth.getCurrentUser().getUid()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        alertDialog.dismiss();
                                                    }
                                                });
                                            }
                                        });

                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertDialog.dismiss();
                                            }
                                        });

                                     }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_LONG).show();
                                    }
                                });
                        break;
                    }
                    case 1 :
                    {
                        mReference.child(mAuth.getCurrentUser().getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ProfileDetailsActivity.this);
                                        View view = getLayoutInflater().inflate(R.layout.custom_edit_lastname,null);
                                        final EditText firsname;
                                        Button update ,cancel;
                                        firsname=view.findViewById(R.id.editabe_lastname);
                                        update=view.findViewById(R.id.btn_editable_lastname);
                                        cancel=view.findViewById(R.id.btn_editable_cancel_lastname);
                                        builder1.setView(view);
                                        final AlertDialog alertDialog = builder1.create();
                                        alertDialog.show();

                                        update.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                HashMap<String,Object> map = new HashMap<>();
                                                map.put("pastname",firsname.getText().toString());
                                                mReference.child(mAuth.getCurrentUser().getUid()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        alertDialog.dismiss();
                                                    }
                                                });
                                            }
                                        });

                                        cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertDialog.dismiss();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_LONG).show();
                                    }
                                });
                        break;
                    }

                    case 2 :
                    {
                       ImageSelection();
                        break;
                    }
                }
            }
        });
        builder.create().show();
    }

    private void connection() {
        mAuth=FirebaseAuth.getInstance();
        mReference= FirebaseDatabase.getInstance().getReference(ConstantClass.DATABSENAME);
        mFirebaseStorage=FirebaseStorage.getInstance().getReference();
        mFloatingActionButton=findViewById(R.id.feb_Action_button);
        first_name=findViewById(R.id.user_detail_first_name);
        last_name=findViewById(R.id.user_detail_last_name);
        email_address=findViewById(R.id.user_detail_email);
        mCircleImageView=findViewById(R.id.user_detail_image);
        mProgressDialog = new ProgressDialog(ProfileDetailsActivity.this);
    }

    private void ImageSelection() {
        AlertDialog.Builder imageSelecter = new AlertDialog.Builder(ProfileDetailsActivity.this);
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

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ConstantClass.GALLERY_REQUEST_CODE)
        {
            Glide.with(getApplicationContext()).load(data.getData()).into(mCircleImageView);

            SavetoStorage(data.getData());
        }

        if (requestCode==ConstantClass.CAMERA_REQUEST_PERMISSION)
        {
            File f= new File(currentPhotoPath);
            mCircleImageView.setImageURI(Uri.fromFile(f));
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
            Glide.with(getApplicationContext()).load(R.drawable.user).into(mCircleImageView);
        }
        else
        {

            mProgressDialog.show();
            StorageReference reference = mFirebaseStorage.child(ConstantClass.IMAGEPATH+ UUID.randomUUID().toString());
            reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            mReference.child(mAuth.getCurrentUser().getUid()).child("image").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        mProgressDialog.dismiss();
                                    }
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
}