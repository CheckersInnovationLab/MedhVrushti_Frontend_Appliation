package com.example.checkerslab_edulearning.NavigationDrawerPkg;

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

import com.bumptech.glide.Glide;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.commonActivityPackage.CourseSubjectsActivity;
import com.example.checkerslab_edulearning.myLearningPakage.MyLeaningMainModel;
import com.example.checkerslab_edulearning.myLearningPakage.MyLearningMainAdapter;

import java.util.List;

public class myAssessmentAdapter  extends RecyclerView.Adapter<myAssessmentAdapter.ViewHolder> {


    private final List<myAssessmentModel> myAssessmentModelListList;
    Context context;

    public myAssessmentAdapter(List<myAssessmentModel> myAssessmentModelListList, Context context) {
        this.myAssessmentModelListList = myAssessmentModelListList;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_assessment_sl_layout, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final myAssessmentModel myAssessmentModel = myAssessmentModelListList.get(position);

//
        holder.assessmentName.setText(myAssessmentModel.getAssessmentName());
        holder.checkingStatus.setText(myAssessmentModel.getCheckingStatus());

        if (myAssessmentModel.getCheckingStatus().equals("Completed") ||myAssessmentModel.getCheckingStatus().equals("Completed"))
        {
            holder.viewResult.setVisibility(View.VISIBLE);
        }

        holder.viewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(context, AssessmentResult.class);
//                intent.putExtra("User_assessment_id",myAssessmentModel.getUser_assessmentID());
//                v.getContext().startActivity(intent);


            }
        });

    }
    @Override
    public int getItemCount() {
        return myAssessmentModelListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView assessmentName, checkingStatus;
        Button viewResult;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            assessmentName=itemView.findViewById(R.id.myAssessment_name_id);
            checkingStatus=itemView.findViewById(R.id.myAssessment_checking_status_id);
            viewResult=itemView.findViewById(R.id.myAssessment_view_result_button_id);

        }
    }

}
