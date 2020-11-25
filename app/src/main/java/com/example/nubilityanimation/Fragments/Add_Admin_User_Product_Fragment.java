package com.example.nubilityanimation.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.ProductForUser;
import com.example.nubilityanimation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Add_Admin_User_Product_Fragment extends Fragment {
    private ImageView imageView;
    private Button upload_button;
    private DatabaseReference mReference;
    private StorageReference mStorageReference;
    private EditText product_name,product_description,product_author,product_stock,product_price;
    private ProgressBar progressBar;
    private String imageUrl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add__admin__user__product_, container, false);
        imageView=v.findViewById(R.id.product_image_view);
        product_name=v.findViewById(R.id.product_name_edit_text);
        product_description=v.findViewById(R.id.product_desciption_edit_text);
        product_stock=v.findViewById(R.id.product_stock_edit_text);
        product_price=v.findViewById(R.id.product_price_edit_text);
        upload_button=v.findViewById(R.id.product_add_button);
        product_author=v.findViewById(R.id.product_author_edit_text);
        mStorageReference= FirebaseStorage.getInstance().getReference();
        mReference= FirebaseDatabase.getInstance().getReference(ConstantClass.PRODUCTFORUSER);
        progressBar = v.findViewById(R.id.product_progress_bar);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), ConstantClass.GALLERY_REQUEST_CODE);
                return true;
            }
        });

        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product_name.getText().toString().isEmpty() && product_description.getText().toString().isEmpty() && product_price.getText().toString().isEmpty() && product_stock.getText().toString().isEmpty() && imageUrl.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Fill all Spece",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String id = mReference.push().getKey();
                    mReference.child(id).setValue(new ProductForUser(id,product_name.getText().toString(),imageUrl,product_description.getText().toString(),product_author.getText().toString(),product_price.getText().toString(),product_stock.getText().toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful())
                           {
                               product_name.setText("");
                               product_description.setText("");
                               product_price.setText("");
                               product_stock.setText("");
                               product_author.setText("");
                           }
                        }
                    });
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
            Glide.with(getApplicationContext()).load(data.getData()).into(imageView);

            SavetoStorage(data.getData());
        }
    }

    private void SavetoStorage(Uri data) {
        progressBar.setVisibility(View.VISIBLE);

        StorageReference storageReference = mStorageReference.child(ConstantClass.PRODUCTPICTURE+ UUID.randomUUID());
        storageReference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageUrl=uri.toString();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }
}