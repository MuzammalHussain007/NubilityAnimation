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
import com.example.nubilityanimation.Modal.User_Watch_Later;
import com.example.nubilityanimation.R;

import java.util.List;

public class UserWatchLaterAdapter extends RecyclerView.Adapter<UserWatchLaterAdapter.UserWatchHolder> {
     private List<User_Watch_Later> mUserWatchLaterList;
     private Context mContext;
     private RecyclarViewInterface mRecyclarViewInterface;

    public UserWatchLaterAdapter(List<User_Watch_Later> mUserWatchLaterList, Context mContext, RecyclarViewInterface mRecyclarViewInterface) {
        this.mUserWatchLaterList = mUserWatchLaterList;
        this.mContext = mContext;
        this.mRecyclarViewInterface = mRecyclarViewInterface;
    }

    @NonNull
    @Override
    public UserWatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_video_thumbnail,null);
        return new UserWatchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserWatchHolder holder, int position) {
        User_Watch_Later user_watch_later= mUserWatchLaterList.get(position);

        if (user_watch_later.getPictureURL().isEmpty())
        {
            Glide.with(mContext)
                    .load(R.drawable.user).into(holder.mImageView);
        }
        else
        {
            Glide.with(mContext)
                    .load(user_watch_later.getPictureURL()).into(holder.mImageView);
        }


    }

    @Override
    public int getItemCount() {
        return mUserWatchLaterList.size();
    }

    public class UserWatchHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public UserWatchHolder(@NonNull View itemView) {
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
