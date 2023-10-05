package com.example.checkerslab_edulearning.storePackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.checkerslab_edulearning.Course_Enroll_Activity;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.mainHome_pkg.TopCategoriesAdapter;
import com.example.checkerslab_edulearning.mainHome_pkg.popularCoursesModel;

import java.util.List;


public class StoreCoursesAdapter extends RecyclerView.Adapter<StoreCoursesAdapter.ViewHolder> {


    private final List<StoreCoursesModel> storeCoursesList;
    Context context;

    public StoreCoursesAdapter(List<StoreCoursesModel> storeCoursesList, Context context) {
        this.storeCoursesList = storeCoursesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store_courses_sl_layout, null, false);
        return new StoreCoursesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final StoreCoursesModel CoursesModel = storeCoursesList.get(position);

        int[] cardColors = {R.drawable.card_color_1_back, R.drawable.card_color_2_back, R.drawable.card_color_4_back, R.drawable.card_color_3_back};
        int colorIndex = position % cardColors.length;


        // Set the background color of the CardView

        holder.price.setText(CoursesModel.getCoursePrice());
        holder.courseName.setText(CoursesModel.getCourseName());
        Glide.with(holder.itemView)
                .load(CoursesModel.getCourseImgUrl())
                .fitCenter()
                .into(holder.courseImage);
        holder.enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Course_Enroll_Activity.class);
                Toast.makeText(context, "fff", Toast.LENGTH_SHORT).show();
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return storeCoursesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        TextView courseName, price;
        Button enrollButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.Store_course_image_id);
            courseName = itemView.findViewById(R.id.Store_course_name_id);
            price = itemView.findViewById(R.id.Store_course_price_id);
            enrollButton=itemView.findViewById(R.id.Store_course_enroll_button_id);
        }
    }
}

