package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.checkerslab_edulearning.R;

import java.util.List;

public class Ass_mainCourses_adapter extends RecyclerView.Adapter<Ass_mainCourses_adapter.ViewHolder> {

    List<Ass_mainCourses_model> mainCourses_List;
    Context context;

    public Ass_mainCourses_adapter(List<Ass_mainCourses_model> childModelClassList, Context context) {
        this.mainCourses_List = childModelClassList;
        this.context = context;
    }


    @NonNull
    @Override
    public Ass_mainCourses_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.ass_main_courses_single_line_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Ass_mainCourses_adapter.ViewHolder holder, int position) {
        // holder.imageView.setImageResource(childModelClassList.get(position).image);

        Ass_mainCourses_model model=mainCourses_List.get(position);

//
        holder.courseName.setText(model.getBoardName());
        holder.courseDescription.setText(model.getBoardDescription());
       Glide.with(holder.imageView.getContext()).load("http://192.168.29.159:9191/api/v1/cil/board/image/"+model.getBoardImgUrl()).into(holder.imageView);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Ass_Standards_activity.class);
                intent.putExtra("Ass_Board_ID",String.valueOf(model.getBoardId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainCourses_List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView courseName,courseDescription;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.Ass_mainCourse_Image_id);
            courseName=itemView.findViewById(R.id.Ass_mainCourse_name_text_id);
            courseDescription=itemView.findViewById(R.id.Ass_mainCourse_description_id);
            layout=itemView.findViewById(R.id.ass_main_course_layout_id);
        }
    }
}