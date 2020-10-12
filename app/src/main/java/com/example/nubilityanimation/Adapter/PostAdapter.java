package com.example.nubilityanimation.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Constant.ConstantClass;
import com.example.nubilityanimation.Modal.PostItem;
import com.example.nubilityanimation.Modal.User;
import com.example.nubilityanimation.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder>{
    private List<PostItem> mPostItems;
    private Context mContext;
    private boolean isLike=false;
    private DatabaseReference mReference,userPost;
    private SharedPreferences mSharedPreferences;
    private String id;


    public PostAdapter(List<PostItem> postItems, Context context) {
        mPostItems = postItems;
        mContext = context;

        mSharedPreferences = context.getSharedPreferences(ConstantClass.MYPREFERENCE,Context.MODE_PRIVATE);
        userPost=FirebaseDatabase.getInstance().getReference(ConstantClass.USER_POST);
        mReference=FirebaseDatabase.getInstance().getReference(ConstantClass.USER_POST);
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
                    .fitCenter()
                    .into(holder.mImageView);
        }
        else
        {
            Glide.with(mContext)
                    .load(postItem.getPostimage())
                    .fitCenter()
                    .into(holder.mImageView);
        }
        isLike=true;
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLike==true)
                {
                    holder.like.setImageResource(R.drawable.user_unlike_image);
                    isLike=false;




                }
                else if (isLike==false)
                {
                    holder.like.setImageResource(R.drawable.user_like_image);
                    isLike=true;
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return mPostItems.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder
    {
        private ImageView mImageView,like,comment;
        private CircleImageView mCircleImageView;
        private TextView username,post_text;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.user_post_image_image);
            mCircleImageView=itemView.findViewById(R.id.user_post_image);
            like=itemView.findViewById(R.id.user_post_like_button);
            comment=itemView.findViewById(R.id.user_post_commen);
            post_text=itemView.findViewById(R.id.user_post_text);
            username=itemView.findViewById(R.id.user_post_name);
        }
    }
}
