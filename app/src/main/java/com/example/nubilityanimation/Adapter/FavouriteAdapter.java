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
import com.example.nubilityanimation.Modal.User_Favourite;
import com.example.nubilityanimation.R;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteHolder> {
    private List<User_Favourite> mUserFavourites;
    private Context mContext;
    private RecyclarViewInterface mRecyclarViewInterface;

    public FavouriteAdapter(List<User_Favourite> mUserFavourites, Context mContext, RecyclarViewInterface mRecyclarViewInterface) {
        this.mUserFavourites = mUserFavourites;
        this.mContext = mContext;
        this.mRecyclarViewInterface = mRecyclarViewInterface;
    }

    @NonNull
    @Override
    public FavouriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_video_thumbnail,null);
        return new FavouriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteHolder holder, int position) {
        User_Favourite user_favourite= mUserFavourites.get(position);
        if (user_favourite.getPictureURL().isEmpty())
        {
            Glide.with(mContext).load(R.drawable.user).into(holder.mImageView);
        }
        else
        {
            Glide.with(mContext).load(user_favourite.getPictureURL()).into(holder.mImageView);
        }

    }

    @Override
    public int getItemCount() {
        return mUserFavourites.size();
    }

    public class FavouriteHolder extends RecyclerView.ViewHolder
    {
        private ImageView mImageView;

        public FavouriteHolder(@NonNull View itemView) {
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
