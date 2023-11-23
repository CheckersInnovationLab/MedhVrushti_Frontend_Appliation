package com.example.checkerslab_edulearning.commonActivityPackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.R;

import java.util.List;

public class CourseChapterAdapter  extends RecyclerView.Adapter<CourseChapterAdapter.ViewHolder> {

    List<CourseChapterModel> list;
    Context context;

    public CourseChapterAdapter(List<CourseChapterModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseChapterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_chapter_sl_layout, null, false);
        return new CourseChapterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseChapterAdapter.ViewHolder holder, int position) {
        final CourseChapterModel courseChapterModel = list.get(position);


        holder.chapterName.setText(courseChapterModel.getChapter_name());
        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,All_Assessment_Activity.class);
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
        TextView chapterName;
         ImageView next;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //courseImage = itemView.findViewById(R.id.Home_popular_courses_image_id);
            chapterName = itemView.findViewById(R.id.Course_chapter_name_id);
            next = itemView.findViewById(R.id.CourseChapter_next_button_id);
        }
    }
}
