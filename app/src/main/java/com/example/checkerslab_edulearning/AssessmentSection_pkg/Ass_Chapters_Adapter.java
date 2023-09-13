package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.R;

import java.util.List;

public class Ass_Chapters_Adapter  extends RecyclerView.Adapter<Ass_Chapters_Adapter.ViewHolder>{

    List<Ass_Chapters_model> list;
    Context context;

    public Ass_Chapters_Adapter(List<Ass_Chapters_model> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ass_chapters_sl_layout,null,false);
        return new Ass_Chapters_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ass_Chapters_model model=list.get(position);

        holder.ChapterName.setText(model.getChapterName());

        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Ass_Topic_activity.class);
                intent.putExtra("Ass_chapter_id",String.valueOf(model.getChapterId()));
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

        TextView ChapterName,progressPercentage;
        ProgressBar progressBar;
        ImageView next;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ChapterName=itemView.findViewById(R.id.Assessment_subject_topic_name_id);
            next=itemView.findViewById(R.id.Topic_next_button_id000);
            progressPercentage=itemView.findViewById(R.id.Assessment_ProgressPerc_text_id);
            progressBar=itemView.findViewById(R.id.Assessment_topic_progress_bar_id);
        }
    }
}
