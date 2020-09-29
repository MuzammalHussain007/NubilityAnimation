package com.example.nubilityanimation.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.User;
import com.example.nubilityanimation.R;
import com.example.nubilityanimation.Registration.PictureUpload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import javax.xml.transform.Result;

import de.hdodenhof.circleimageview.CircleImageView;


public class Account_Detail_Fragment extends Fragment {
    private CircleImageView mCircleImageView;
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private StorageReference mFirebaseStorage;
    private FirebaseUser mUser;
    private FloatingActionButton mFloatingActionButton;
    private ProgressDialog mProgressDialog;
    private TextView fname,lname,email;
    private String currentPhotoPath;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_account__detail_, container, false);
        fname=v.findViewById(R.id.user_detail_firstname_text);
        lname=v.findViewById(R.id.user_detail_lastname_text);
        email=v.findViewById(R.id.user_detail_email_text);
        mCircleImageView=v.findViewById(R.id.user_detail_image);
        mCircleImageView.setImageResource(R.drawable.user);
        conection();
       
        



        mReference.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user!=null)
                {
                    fname.setText(user.getFirstname());
                    lname.setText(user.getPastname());
                    email.setText(mAuth.getCurrentUser().getEmail());
                    Glide.with(getActivity().getApplicationContext())
                            .load(Uri.parse(user.getImage()))
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

    private void setUpOption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] option = new String[]{"Edit FirstName","Edit LastName","Edit Picture"};
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case 0 :
                    {

                     break;
                    }
                    case 1 :
                    {

                        break;
                    }
                    case 2 :
                    {

                        break;
                    }
                }

            }
        });
        builder.create().show();
    }

    private void conection() {

        mAuth=FirebaseAuth.getInstance();
        mFirebaseStorage= FirebaseStorage.getInstance().getReference();

        mProgressDialog= new ProgressDialog(getActivity().getApplicationContext());
        mProgressDialog.setMessage("Uploading");
        mUser=mAuth.getCurrentUser();
        mReference= FirebaseDatabase.getInstance().getReference(ConstantClass.DATABSENAME);
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
            result = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),ConstantClass.PERMISSIONREQUEST);
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
           startActivityForResult(Intent.createChooser(gallery, "Select Picture"), ConstantClass.GALLERY_REQUEST_CODE_USERDETAIL);

        }
        else
        {
            checkPermissions();
        }

    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity().getApplicationContext(),
                        "com.example.nubilityanimation.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, ConstantClass.CAMERA_REQUEST_PERMISSION_USERDETAIL);
            }
        }
    }
    private void SavetoStorage(final Uri data){
        if (data.toString().isEmpty())
        {
            Glide.with(getActivity().getApplicationContext()).load(R.drawable.user).into(mCircleImageView);
        }
        else
        {

              mProgressDialog.show();
//            StorageReference reference = mFirebaseStorage.child(ConstantClass.IMAGEPATH+ UUID.randomUUID().toString());
//            reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//
//                            mReference.child(mAuth.getCurrentUser().getUid()).child("image").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful())
//                                    {
//                                        mProgressDialog.dismiss();
//                                    }
//                                }
//                            });
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                        }
//                    });
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                    double progress = (100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
//                    mProgressDialog.setMessage("Uploaded "+(int)progress+"%");
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    mProgressDialog.dismiss();
//                    Toast.makeText(getActivity().getApplicationContext(),""+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
//                }
//            });
//
//

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==ConstantClass.PERMISSIONREQUEST)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity().getApplicationContext(),"All Permission Granted",Toast.LENGTH_SHORT).show();
            }

        }

    }
    private void ImageSelection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] item = new String[]{"Camera","Gallery"};
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               switch (which)
               {
                   case 0 :
                   {
                       selectionFromCamera();
                       break;
                   }
                   case 1 :
                   {
                       selectionFromGallery();
                       break;
                   }
               }
            }
        });
        builder.create().show();

    }
}