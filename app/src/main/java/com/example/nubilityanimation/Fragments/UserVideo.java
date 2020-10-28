package com.example.nubilityanimation.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.UserVideoThumbnail;
import com.example.nubilityanimation.R;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


public class UserVideo extends Fragment {
    private DatabaseReference mReference;
    private ImageView mImageView;
    private StorageReference mStorageReference;
    private EditText mText;
    private ProgressBar mProgressBar;
    private Button mButton;
    private String imgURL;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user_video, container, false);
        mImageView = v.findViewById(R.id.admin_side_video_image);
         mReference=FirebaseDatabase.getInstance().getReference(ConstantClass.USERVIDEO);
        mText=v.findViewById(R.id.admin_video_name);
        mButton=v.findViewById(R.id.admin_upload_btn);
        mProgressBar=v.findViewById(R.id.admin_progressbar);
        mStorageReference= FirebaseStorage.getInstance().getReference();
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), ConstantClass.GALLERY_REQUEST_CODE);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mText.getText().toString()==" ")
                {
                Toast.makeText(getActivity().getApplicationContext(),"Can not upload",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String thumnailID=mReference.push().getKey();
                    mReference.push().setValue(new UserVideoThumbnail(thumnailID,mText.getText().toString(),imgURL));
                    mText.setText(" ");


                }

            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ConstantClass.GALLERY_REQUEST_CODE)
        {
            mProgressBar.setVisibility(View.VISIBLE);
            Glide.with(getActivity().getApplicationContext()).load(data.getData()).into(mImageView);
            StorageReference reference = mStorageReference.child(ConstantClass.USERVIDEOFOLDER+ UUID.randomUUID());
            reference.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                          imgURL=uri.toString();
                          mProgressBar.setVisibility(View.GONE);
                        }
                    });

                }
            });

        }
    }
}