package com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.Upload_Answer_Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder>{


    List<ChildModel> subQuestionList;
    Context context;

    public ChildAdapter(List<ChildModel> subQuestionList, Context context) {
        this.subQuestionList = subQuestionList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.assessment_subqestion_sl_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.ViewHolder holder, int position) {
        ChildModel childModel=subQuestionList.get(position);

        try {

            JSONArray jsonArray = new JSONArray(childModel.getSubQuestion());
            String question="";
            for (int i = 0; i < jsonArray.length();i++) {
                JSONObject typeObject = jsonArray.getJSONObject(i);

                 question = question+typeObject.getString("text")+"\\\\\n";

            }
            JLatexMathDrawable drawable = JLatexMathDrawable.builder("\\text{ "+question+"}")
                    .textSize(150)
                    .padding(2)
                    .background(0xFFffffff)
                    .align(JLatexMathDrawable.ALIGN_RIGHT)
                    .build();


            holder.latexMathView.setLatexDrawable(drawable);
            String finalQuestion = question;
            holder.attempt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, Upload_Answer_Activity.class);
                    intent.putExtra("questionData", finalQuestion);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     view.getContext().startActivity(intent);
                }
            });

        }catch (Exception ex) {
            StringWriter stringWriter = new StringWriter();
            ex.printStackTrace(new PrintWriter(stringWriter));

        }


    }

    @Override
    public int getItemCount() {
        return subQuestionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView subQuestion;
        Button attempt;
        JLatexMathView latexMathView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            latexMathView=itemView.findViewById(R.id.assessment_sub_question_id);
            attempt=itemView.findViewById(R.id.question_Attempt_button_id);

        }
    }
}
