package com.example.checkerslab_edulearning.commonActivityPackage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.AssessmentSection_pkg.Selected_Test_Data_Model;
import com.example.checkerslab_edulearning.R;

import java.util.List;

import io.github.kexanie.library.MathView;

public class Assessment_solution_adapter extends RecyclerView.Adapter<Assessment_solution_adapter.ViewHolder>{
    List<Selected_Test_Data_Model> list;
    Context context;

    public Assessment_solution_adapter(List<Selected_Test_Data_Model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ass_solution_sl_layout,null,false);
        return new Assessment_solution_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Selected_Test_Data_Model model=list.get(position);

        holder.ques.setText("Question:"+model.getQuestion());


        holder.opt1.setText("1)"+model.getOption1());
        holder.opt2.setText("2)"+model.getOption2());
        holder.opt3.setText("2)"+model.getOption3());
        holder.opt4.setText("4)"+model.getOption4());
//        if (model.getAnswer().equals(model.getOption1()))
//        {
//            holder.opt1.setTextColor(Color.parseColor("#009900"));
//        }
//        else if (model.getAnswer().equals(model.getOption2()))
//        {
//            holder.opt2.setTextColor(Color.parseColor("#009900"));
//        }
//        else if (model.getAnswer().equals(model.getOption3()))
//        {
//            holder.opt3.setTextColor(Color.parseColor("#009900"));
//        }
//        else if (model.getAnswer().equals(model.getOption4()))
//        {
//            holder.opt4.setTextColor(Color.parseColor("#009900"));
//        }


//         String updatedOption1 = model.getAnswerDescription().replace("\n", "<br>");
//         String styledOpt1 = "<font size='3' color='#4c4c4c'>" + updatedOption1 + "</font>";
        holder.descText.setText(model.getAnswerDescription().replace("\n", "<br>"));
        holder.descriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibilityStatus=holder.descLayout.getVisibility();
                if (visibilityStatus==View.VISIBLE)
                {
                    holder.descLayout.setVisibility(View.GONE);
                }
                else
                {
                    holder.descLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MathView ques;
        MathView opt1,opt2,opt3,opt4,descText;
        Button descriptionButton;
        LinearLayout descLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ques=itemView.findViewById(R.id.Solution_question_id3001);
            opt1=itemView.findViewById(R.id.Option1_id3001);
            opt2=itemView.findViewById(R.id.Option2_id3001);
            opt3=itemView.findViewById(R.id.Option3_id3001);
            opt4=itemView.findViewById(R.id.Option4_id3001);
            descriptionButton=itemView.findViewById(R.id.assessment_description_button_id);
            descLayout=itemView.findViewById(R.id.assessment_description_layout_id);
            descText=itemView.findViewById(R.id.assessment_description_text_id);
        }
    }
}
