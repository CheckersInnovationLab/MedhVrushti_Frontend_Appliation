package com.example.checkerslab_edulearning.commonActivityPackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.AssessmentSection_pkg.Test_Reminder_activity;
import com.example.checkerslab_edulearning.R;

import java.util.List;

public class AllAssessmentAdapter extends RecyclerView.Adapter<AllAssessmentAdapter.ViewHolder>{

    List<AllAssessmentModel> list;
    Context context;

    public AllAssessmentAdapter(List<AllAssessmentModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public AllAssessmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_assessment_sl_layout, null, false);
        return new AllAssessmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAssessmentAdapter.ViewHolder holder, int position) {
        final AllAssessmentModel allAssessmentModel = list.get(position);


        holder.assessmentName.setText(allAssessmentModel.getAssName());
        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Test_Reminder_activity.class);
                intent.putExtra("assessment_id",allAssessmentModel.getAssId());
                intent.putExtra("assessment_marks",allAssessmentModel.getAssMarks());
                intent.putExtra("assessment_Name",allAssessmentModel.getAssName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView assessmentName;
        Button start;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //courseImage = itemView.findViewById(R.id.Home_popular_courses_image_id);
            assessmentName = itemView.findViewById(R.id.Assessment_name_id);
            start = itemView.findViewById(R.id.Assessment_start_button_id);
        }
    }
}
