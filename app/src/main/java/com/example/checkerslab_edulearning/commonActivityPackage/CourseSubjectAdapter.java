package com.example.checkerslab_edulearning.commonActivityPackage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.AssessmentSection_pkg.Ass_Chapters_activity;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Ass_Subjects_Model;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.commonActivityPackage.assessmentHome.Assessment_home_Screen;
import com.example.checkerslab_edulearning.mainHome_pkg.PopularCoursesAdapter;
import com.example.checkerslab_edulearning.mainHome_pkg.popularCoursesModel;

import java.util.List;

public class CourseSubjectAdapter extends RecyclerView.Adapter<CourseSubjectAdapter.ViewHolder>  {
    List<CourseSubjectModel> list;
    Context context;

    public CourseSubjectAdapter(List<CourseSubjectModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseSubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_subject_single_line, null, false);
        return new CourseSubjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseSubjectAdapter.ViewHolder holder, int position) {
        final CourseSubjectModel courseSubjectModel = list.get(position);

        holder.subjectName.setText(courseSubjectModel.getSubject_name());
       // holder.totalChapter.setText("TotalChapter: "+courseSubjectModel.getTotal_chapters());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent=new Intent(context, Assessment_home_Screen.class);
               intent.putExtra("Subject_id",courseSubjectModel.getSubject_id());
               intent.putExtra("subject_Name",courseSubjectModel.getSubject_name());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
             v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        TextView subjectName,totalChapter;
        RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //courseImage = itemView.findViewById(R.id.Home_popular_courses_image_id);
            subjectName = itemView.findViewById(R.id.CourseSubject_name_id);
            totalChapter = itemView.findViewById(R.id.total_chapter_id);
            layout = itemView.findViewById(R.id.CourseSubject_layout_id);
        }
    }
}






