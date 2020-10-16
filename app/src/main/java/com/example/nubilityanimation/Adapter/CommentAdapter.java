package com.example.nubilityanimation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nubilityanimation.Modal.UserComment;
import com.example.nubilityanimation.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private List<UserComment> mUserComments ;

    private Context mContext;

    public CommentAdapter(List<UserComment> userComments,Context context) {
        mUserComments = userComments;
        mContext = context;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_user_comment,null);
        return new CommentHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        UserComment userComment = mUserComments.get(position);
        holder.username.setText(userComment.getUsername());




    }

    @Override
    public int getItemCount() {
        return mUserComments.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        private CircleImageView mCircleImageView;
        private TextView username,user_text;
        private String post;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            mCircleImageView=itemView.findViewById(R.id.user_comment_id);
            username=itemView.findViewById(R.id.user_comment_user_name);
            user_text=itemView.findViewById(R.id.comment_user_text);

        }
    }
}
