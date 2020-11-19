package com.example.nubilityanimation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Modal.UserCommentVideo;
import com.example.nubilityanimation.R;

import java.util.List;

public class UserCommentAdapter extends RecyclerView.Adapter<UserCommentAdapter.UserCommentHolder> {
    private Context mContext;
    private List<UserCommentVideo> mUserComments;

    public UserCommentAdapter(Context mContext, List<UserCommentVideo> mUserComments) {
        this.mContext = mContext;
        this.mUserComments = mUserComments;
    }

    @NonNull
    @Override
    public UserCommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater inflater = LayoutInflater.from(mContext);
       View v=inflater.inflate(R.layout.custom_comment_viewver,null);

       return new UserCommentHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCommentHolder holder, int position) {
        UserCommentVideo userCommentVideo = mUserComments.get(position);
        holder.usertextView.setText(userCommentVideo.getUserText());  //get user text refer as username
        holder.commentText.setText(userCommentVideo.getUsername());
    }

    @Override
    public int getItemCount() {
        return mUserComments.size();
    }

    public class UserCommentHolder extends RecyclerView.ViewHolder
    {
        private TextView usertextView,commentText;
        private RatingBar mRatingBar;
        String id;

        public UserCommentHolder(@NonNull View itemView) {
            super(itemView);
            mRatingBar=itemView.findViewById(R.id.rating_id);
            usertextView=itemView.findViewById(R.id.custom_comment_username);
            commentText=itemView.findViewById(R.id.user_custom_comment);
        }
    }
}
