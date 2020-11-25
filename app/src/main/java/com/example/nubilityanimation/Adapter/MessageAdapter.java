package com.example.nubilityanimation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Modal.MessageClass;
import com.example.nubilityanimation.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    private List<MessageClass> mMessageClasses;
    private Context mContext;

    public MessageAdapter(List<MessageClass> mMessageClasses, Context mContext) {
        this.mMessageClasses = mMessageClasses;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.message_card_view_layout,null);

        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        MessageClass messageClass = mMessageClasses.get(position);
        holder.sender_name.setText(messageClass.getSender_name());
        holder.sender_text.setText(messageClass.getSender_message());
        holder.sender_time.setText(messageClass.getSend_time());

    }

    @Override
    public int getItemCount() {
        return mMessageClasses.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder
    {
        private TextView sender_name,sender_text,sender_time;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            sender_name=itemView.findViewById(R.id.group_message_sender_name);
            sender_text=itemView.findViewById(R.id.group_message_message_name);
            sender_time=itemView.findViewById(R.id.group_message_time_stamp);
        }
    }
}
