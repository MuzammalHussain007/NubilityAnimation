package com.example.nubilityanimation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.UserVideoThumbnail;
import com.example.nubilityanimation.R;

import java.util.List;

public class UserVideoAdapter extends RecyclerView.Adapter<UserVideoAdapter.UserVideoHolder>  {
    private List<UserVideoThumbnail> mThumbnails ;
    private Context mContext;
    private RecyclarViewInterface mRecyclarViewInterface;

    public UserVideoAdapter(List<UserVideoThumbnail> thumbnails, Context context, RecyclarViewInterface recyclarViewInterface) {
        mThumbnails = thumbnails;
        mContext = context;
        mRecyclarViewInterface = recyclarViewInterface;
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
        if (userVideoThumbnail.getPictureURL().isEmpty())
        {
            Glide.with(mContext)
                    .load(R.drawable.user).into(holder.mImageView);
        }
        else
        {
            Glide.with(mContext)
                    .load(userVideoThumbnail.getPictureURL()).into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mThumbnails.size();
    }

    public class UserVideoHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public UserVideoHolder(@NonNull View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.video_thumbnail_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclarViewInterface.onClickListner(getAdapterPosition());
                }
            });
        }
    }
}
