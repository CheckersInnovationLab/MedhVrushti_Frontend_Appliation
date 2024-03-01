package com.example.checkerslab_edulearning.myLearningPakage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.checkerslab_edulearning.Course_Enroll_Activity;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.commonActivityPackage.CourseChaptersActivity;
import com.example.checkerslab_edulearning.commonActivityPackage.CourseSubjectsActivity;
import com.example.checkerslab_edulearning.mainHome_pkg.PopularCoursesAdapter;
import com.example.checkerslab_edulearning.storePackage.StoreCoursesAdapter;
import com.example.checkerslab_edulearning.storePackage.StoreCoursesModel;

import java.util.List;

public class MyLearningMainAdapter extends RecyclerView.Adapter<MyLearningMainAdapter.ViewHolder> {


    private final List<MyLeaningMainModel> myLearningList;
    Context context;

    public MyLearningMainAdapter(List<MyLeaningMainModel> myLearningList, Context context) {
        this.myLearningList = myLearningList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mylearning_sl_layout, null, false);
        return new MyLearningMainAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final MyLeaningMainModel CoursesModel = myLearningList.get(position);

        holder.courseName.setText(CoursesModel.getSubscription_name());
//        if (!CoursesModel.getSubscription_image().isEmpty())
//        {
//            Log.d("image",CoursesModel.getSubscription_image());
//            Glide.with(holder.itemView)
//                    .load(CoursesModel.getSubscription_image())
//                    .fitCenter()
//                    .into(holder.courseImage);
//        }

        holder.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CourseSubjectsActivity.class);
                intent.putExtra("Subscription_id",CoursesModel.getSubscription_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return myLearningList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        TextView courseName, price;
        Button startButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.myLearning_course_image_id);
            courseName = itemView.findViewById(R.id.myLearning_course_name_id);
          //  price = itemView.findViewById(R.id.myLearning_course_price_id);
            startButton=itemView.findViewById(R.id.myLearning_course_button_id);
        }
    }
}
