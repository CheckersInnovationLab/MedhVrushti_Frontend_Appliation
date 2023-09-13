package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.R;

import java.util.List;

public class Ass_Topic_Adapter extends RecyclerView.Adapter<Ass_Topic_Adapter.ViewHolder>{

    List<Ass_Topic_model> list;
    Context context;

    public Ass_Topic_Adapter(List<Ass_Topic_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ass_topic_sl_layout,null,false);
        return new Ass_Topic_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ass_Topic_model model=list.get(position);

        holder.TopicName.setText(model.getTopicName());
//
        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Test_Reminder_activity.class);
                intent.putExtra("Ass_topic_id",String.valueOf(model.getTopicId()));
//                intent.putExtra("SubjectTopicBookUrl",model.getSubjectTopicBookUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView TopicName,status;
        Button start;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TopicName=itemView.findViewById(R.id.Assessment_sub_topic_name_id);
            start=itemView.findViewById(R.id.Assessment_subTopic_start_id);
        }
    }
}
