package com.example.nubilityanimation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nubilityanimation.Interface.RecyclarViewInterface;
import com.example.nubilityanimation.Modal.TopicClass;
import com.example.nubilityanimation.R;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicHolder> {
    private Context context;
    private ArrayList<TopicClass> mTpoicArrayList;
    private RecyclarViewInterface mRecyclarViewInterface;

    public TopicAdapter(Context context, ArrayList<TopicClass> mTpoicArrayList, RecyclarViewInterface mRecyclarViewInterface) {
        this.context = context;
        this.mTpoicArrayList = mTpoicArrayList;
        this.mRecyclarViewInterface = mRecyclarViewInterface;
    }

    @NonNull
    @Override
    public TopicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.custom_topic_name,null);

        return new TopicHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicHolder holder, int position) {
       TopicClass topicClass = mTpoicArrayList.get(position);
       holder.topic.setText(topicClass.getTopicName());
    }

    @Override
    public int getItemCount() {
        return mTpoicArrayList.size();
    }

    public class TopicHolder extends RecyclerView.ViewHolder
    {
        private TextView topic;

        public TopicHolder(@NonNull View itemView) {
            super(itemView);
            topic= itemView.findViewById(R.id.custom_topic_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRecyclarViewInterface.onClickListner(getAdapterPosition());
                }
            });
        }
    }
}
