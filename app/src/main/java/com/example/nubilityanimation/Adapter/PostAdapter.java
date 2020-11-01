package com.example.nubilityanimation.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.PrecomputedText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.FanArt.CommentActivity;
import com.example.nubilityanimation.Modal.PostItem;
import com.example.nubilityanimation.Modal.PostReaction;
import com.example.nubilityanimation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder>{
    private List<PostItem> mPostItems;
    private Context mContext;
    private boolean isLike=false;
    private DatabaseReference userPost;
    private FirebaseAuth mAuth;
    private String postid;



    public PostAdapter(List<PostItem> postItems, Context context) {
        mPostItems = postItems;
        mContext = context;
        mAuth=FirebaseAuth.getInstance();
        userPost=FirebaseDatabase.getInstance().getReference(ConstantClass.POSTREACTION);


    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_user_post,null);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostHolder holder, final int position) {

        final PostItem postItem=mPostItems.get(position);
        holder.username.setText(postItem.getUsername());
        holder.post_text.setText(postItem.getPostdescription());
        if (postItem.getUserimage().isEmpty())
        {
            Glide.with(mContext)
                    .load(R.drawable.user)
                    .fitCenter()
                    .into(holder.mCircleImageView);
        }
        else
        {
            Glide.with(mContext)
                    .load(postItem.getUserimage())
                    .fitCenter()
                    .into(holder.mCircleImageView);
        }
        if (postItem.getPostimage().isEmpty())
        {
            Glide.with(mContext)
                    .load(R.drawable.user)
                    .into(holder.mImageView);
        }
        else
        {
            Glide.with(mContext)
                    .load(postItem.getPostimage())
                    .into(holder.mImageView);
        }

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(mContext, CommentActivity.class);
               postid=holder.id=postItem.getPostid();
              intent.putExtra("postid",postid);
                mContext.startActivity(intent);
            }
        });
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask task = new DownloadClass().execute(postItem.getPostimage());
            }
        });

    }



    @Override
    public int getItemCount() {
        return mPostItems.size();
    }


    public class PostHolder extends RecyclerView.ViewHolder
    {
        private ImageView mImageView,like,comment ,download;
        private CircleImageView mCircleImageView;
        private TextView username,post_text;
        private String id,strimage;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.user_post_image_image);
            download=itemView.findViewById(R.id.download_image);
            mCircleImageView=itemView.findViewById(R.id.user_post_image);
            comment=itemView.findViewById(R.id.user_post_commen);
            post_text=itemView.findViewById(R.id.user_post_text);
            username=itemView.findViewById(R.id.user_post_name);
        }
    }

    public class DownloadClass extends AsyncTask<String,String,Bitmap>
    {
        public ProgressDialog mProgressDialog;
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            FileOutputStream fileOutputStream=null;
            if (bitmap!=null)
            {
                mProgressDialog.dismiss();
                File sdcar = Environment.getExternalStorageDirectory();
                File directory = new File(sdcar.getAbsolutePath(),"/NobilityAnimation");
                    directory.mkdir();
                    String filename = String.format("%d.jpg",System.currentTimeMillis());
                    File out = new File (directory,filename);
                try {
                    fileOutputStream = new FileOutputStream(out);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(out));
                mContext.sendBroadcast(intent);

            }
            else
            {
                mProgressDialog.show();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("Plz Wait ...Its Downloading");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bmimg=null;
            try {
                URL url = new URL(strings[0]);
                InputStream inputStream=null;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig= Bitmap.Config.RGB_565;
                 bmimg = BitmapFactory.decodeStream(inputStream,null,options);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmimg;

        }
    }
}
