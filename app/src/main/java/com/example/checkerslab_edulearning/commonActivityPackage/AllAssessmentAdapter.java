package com.example.checkerslab_edulearning.commonActivityPackage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.AssessmentSection_pkg.Test_Reminder_activity;
import com.example.checkerslab_edulearning.NavigationDrawerPkg.AssessmentResultDetailsScreen;
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
        holder.assessmentStatus.setText(allAssessmentModel.getAssStatus());
        holder.totalTime.setText(String.valueOf(allAssessmentModel.getTotalTime())+"min");
        holder.totalMarks.setText(allAssessmentModel.getTotalMarks()+"marks");
        holder.totalQuestion.setText(allAssessmentModel.getTotalQuestion()+"Qs");

        if (allAssessmentModel.getAssStatus().equals("Completed"))
        {
          holder.assessmentStatus.setTextColor(Color.parseColor("#026C02"));
          holder.checkResult.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.start.setVisibility(View.VISIBLE);
        }
        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Test_Reminder_activity.class);
                intent.putExtra("assessment_id",allAssessmentModel.getAssId());
//                intent.putExtra("assessment_marks",allAssessmentModel.getAssMarks());
//                intent.putExtra("assessment_Name",allAssessmentModel.getAssName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);


            }
        });
        holder.checkResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AssessmentResultDetailsScreen.class);
                intent.putExtra("User_assessment_name",allAssessmentModel.getAssName());
                intent.putExtra("assessment_id",allAssessmentModel.getAssId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);



            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView assessmentName,assessmentStatus,totalMarks,totalTime,totalQuestion;
        Button start,checkResult;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //courseImage = itemView.findViewById(R.id.Home_popular_courses_image_id);
            assessmentName = itemView.findViewById(R.id.Assessment_name_id);
            start = itemView.findViewById(R.id.Assessment_start_button_id);
            assessmentStatus=itemView.findViewById(R.id.Assessment_status_id);
            checkResult=itemView.findViewById(R.id.Assessment_Result_button_id);
            totalMarks=itemView.findViewById(R.id.AssessmentTotalMarks_id);
            totalQuestion=itemView.findViewById(R.id.AssessmentTotalQuestion_id);
            totalTime=itemView.findViewById(R.id.AssessmentTotalTime_id);
        }
    }
}
