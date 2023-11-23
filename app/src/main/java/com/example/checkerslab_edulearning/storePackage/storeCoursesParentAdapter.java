package com.example.checkerslab_edulearning.storePackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.R;

import java.util.List;

public class storeCoursesParentAdapter extends RecyclerView.Adapter<storeCoursesParentAdapter.ViewHolder>{


    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();
    private final List<storeCoursesParentModel> storeCoursesTypeList;
    Context context;

    public storeCoursesParentAdapter(List<storeCoursesParentModel> storeCoursesTypeList, Context context) {
        this.storeCoursesTypeList = storeCoursesTypeList;
        this.context = context;
    }

    @NonNull
    @Override
    public storeCoursesParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.main_courses_parent_layout, null, false);
        return new storeCoursesParentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull storeCoursesParentAdapter.ViewHolder holder, int position) {
        final storeCoursesParentModel CoursesModel = storeCoursesTypeList.get(position);

//        int[] cardColors = {R.drawable.card_color_1_back, R.drawable.card_color_2_back, R.drawable.card_color_4_back, R.drawable.card_color_3_back};
//        int colorIndex = position % cardColors.length;


        // Set the background color of the CardView

        holder.coursesType.setText(CoursesModel.getCourseType());

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                holder.ChildRecyclerView.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        layoutManager
                .setInitialPrefetchItemCount(
                        CoursesModel.getCoursesItemList()
                                .size());


        StoreCoursesAdapter storeCoursesAdapter
                = new StoreCoursesAdapter(CoursesModel.getCoursesItemList(),context);

        holder.ChildRecyclerView.setLayoutManager(layoutManager);
        holder.ChildRecyclerView
                .setAdapter(storeCoursesAdapter);
        holder.ChildRecyclerView
                .setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return storeCoursesTypeList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView coursesType;
        private RecyclerView ChildRecyclerView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coursesType = itemView.findViewById(R.id.courses_item_type_id);
            ChildRecyclerView = itemView.findViewById(R.id.child_recyclerview);

        }
    }
}
