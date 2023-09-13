package com.example.checkerslab_edulearning.mainHome_pkg;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.checkerslab_edulearning.R;

import java.util.List;


public class PopularCoursesAdapter extends RecyclerView.Adapter<PopularCoursesAdapter.ViewHolder> {


    private final List<popularCoursesModel> popularCoursesList;
    Context context;

    public PopularCoursesAdapter(List<popularCoursesModel> popularCoursesList, Context context) {
        this.popularCoursesList = popularCoursesList;
        this.context = context;
    }

    @NonNull
    @Override
    public PopularCoursesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_courses_sl_layout, null, false);
        return new PopularCoursesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularCoursesAdapter.ViewHolder holder, int position) {
        final popularCoursesModel popularCoursesModel = popularCoursesList.get(position);

        int[] cardColors = {R.drawable.card_color_1_back, R.drawable.card_color_2_back,R.drawable.card_color_4_back,R.drawable.card_color_3_back };
        int colorIndex = position % cardColors.length;


        // Set the background color of the CardView
        holder.layout.setBackgroundResource(cardColors[colorIndex]);

        holder.discount.setText(popularCoursesModel.getCourseDiscount());
        holder.courseName.setText(popularCoursesModel.getCourseName());
        Glide.with(holder.itemView)
                .load(popularCoursesModel.getCourseImage())
                .fitCenter()
                .into(holder.courseImage);
    }

    @Override
    public int getItemCount() {
        return popularCoursesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        TextView courseName,discount;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.Home_popular_courses_image_id);
            courseName = itemView.findViewById(R.id.Home_popular_courses_name_id);
            discount = itemView.findViewById(R.id.Home_popular_courses_discount_id);
            layout = itemView.findViewById(R.id.Home_popular_courses_card_layout_id);
        }
    }
}