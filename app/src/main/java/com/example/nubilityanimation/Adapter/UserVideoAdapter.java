package com.example.nubilityanimation.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Modal.UserVideoThumbnail;
import com.example.nubilityanimation.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserVideoAdapter extends RecyclerView.Adapter<UserVideoAdapter.UserVideoHolder>  {
    private List<UserVideoThumbnail> mThumbnails ;
    private Context mContext;

    public UserVideoAdapter(List<UserVideoThumbnail> thumbnails, Context context) {
        mThumbnails = thumbnails;
        mContext = context;
    }

    @NonNull
    @Override
    public UserVideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_video_thumbnail,null);

        return new UserVideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserVideoHolder holder, int position) {
        UserVideoThumbnail userVideoThumbnail = mThumbnails.get(position);
        holder.mTextView.setText(userVideoThumbnail.getName());

        if (userVideoThumbnail.getPicURL().isEmpty())
        {
            Glide.with(mContext)
                    .load(R.drawable.user).into(holder.mCircleImageView);
        }
        else
        {
            Glide.with(mContext)
                    .load(userVideoThumbnail.getPicURL()).into(holder.mCircleImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mThumbnails.size();
    }

    public class UserVideoHolder extends RecyclerView.ViewHolder {
        private CircleImageView mCircleImageView;
        private TextView mTextView;

        public UserVideoHolder(@NonNull View itemView) {
            super(itemView);
            mTextView=itemView.findViewById(R.id.user_video_thumnail_view);
            mCircleImageView=itemView.findViewById(R.id.video_thumbnail_image);
        }
    }
}
