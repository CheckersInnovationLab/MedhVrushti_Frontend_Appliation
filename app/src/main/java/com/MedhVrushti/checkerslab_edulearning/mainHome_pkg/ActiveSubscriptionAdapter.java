package com.MedhVrushti.checkerslab_edulearning.mainHome_pkg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MedhVrushti.checkerslab_edulearning.R;
import com.MedhVrushti.checkerslab_edulearning.commonActivityPackage.CourseSubjectsActivity;
import com.MedhVrushti.checkerslab_edulearning.myLearningPakage.MyLeaningMainModel;

import java.util.List;


public class ActiveSubscriptionAdapter extends RecyclerView.Adapter<ActiveSubscriptionAdapter.ViewHolder>{

    private final List<MyLeaningMainModel> activeSubList;
    Context context;

    public ActiveSubscriptionAdapter(List<MyLeaningMainModel> activeSubList, Context context) {
        this.activeSubList = activeSubList;
        this.context = context;
    }

    @NonNull
    @Override
    public ActiveSubscriptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.active_subscription_sl_layout, null, false);
        return new ActiveSubscriptionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveSubscriptionAdapter.ViewHolder holder, int position) {
        final MyLeaningMainModel categoryItemModel = activeSubList.get(position);


        holder.courseName.setText(categoryItemModel.getSubscription_name());
        holder.enrollmentDate.setText(categoryItemModel.getSubscription_date());
        holder.subType.setText(categoryItemModel.getSubscription_type());
        holder.endDate.setText(categoryItemModel.getAccess_end_date());

        holder.startLearningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CourseSubjectsActivity.class);
                intent.putExtra("Subscription_id",categoryItemModel.getSubscription_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
//        Glide.with(holder.itemView)
//                .load(categoryItemModel.getCatImgUrl())
//                .fitCenter()
//                .into(holder.catImage);

    }

    @Override
    public int getItemCount() {
        return activeSubList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseName,enrollmentDate,endDate,subType;
        RelativeLayout startLearningButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.Active_subscription_sl_course_name_id);
            enrollmentDate = itemView.findViewById(R.id.Active_subscription_sl_Enrollment_date_id);
            endDate = itemView.findViewById(R.id.Active_subscription_sl_endDate_id);
            subType = itemView.findViewById(R.id.Active_subscription_sl_type_id);
            startLearningButton=itemView.findViewById(R.id.Active_subscription_StartLearning_id);

        }
    }
}
