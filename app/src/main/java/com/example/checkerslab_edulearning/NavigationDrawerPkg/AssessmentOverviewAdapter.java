package com.example.checkerslab_edulearning.NavigationDrawerPkg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.UploadAnswerpackage.Upload_Answer_Activity;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ChildModel;
import com.example.checkerslab_edulearning.commonActivityPackage.AllAssessmentAdapter;
import com.example.checkerslab_edulearning.commonActivityPackage.AllAssessmentModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;

public class AssessmentOverviewAdapter extends RecyclerView.Adapter<AssessmentOverviewAdapter.ViewHolder>{

    List<AssessmentOverviewModel> list;
    Context context;

    public AssessmentOverviewAdapter(List<AssessmentOverviewModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentOverviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.assessment_overview_sl_layout, null, false);
        return new AssessmentOverviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentOverviewAdapter.ViewHolder holder, int position) {


        AssessmentOverviewModel childModel=list.get(position);

        try {
            JSONArray jsonArray = new JSONArray(childModel.getQuestionLatex());
            String question="";
            for (int i = 0; i < jsonArray.length();i++) {
                JSONObject typeObject = jsonArray.getJSONObject(i);

                question = question+typeObject.getString("text")+"\\\\\n";
            }

            String colorString = "#E7EAED";
            int colorInt = Color.parseColor(colorString);
            JLatexMathDrawable drawable = JLatexMathDrawable.builder("\\textbf{ "+question+"}")
                    .textSize(50)
                    .padding(2)
                    .background(colorInt)
                    .align(JLatexMathDrawable.ALIGN_RIGHT)
                    .build();

            holder.question.setLatexDrawable(drawable);
            holder.quesNo.setText(childModel.getQuestionNumber()+")");
            holder.obtainedMarks.setText(childModel.getObtainedMarks());
            holder.totalMarks.setText(childModel.getTotalMarks());
            holder.overView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.descriptiontext.setText(childModel.getAnswerOverview());
            holder.overView.setOnClickListener(new View.OnClickListener() {
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
            String finalQuestion = question;

        }catch (Exception ex) {
            StringWriter stringWriter = new StringWriter();
            ex.printStackTrace(new PrintWriter(stringWriter));

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        JLatexMathView question;
        TextView quesNo,obtainedMarks,totalMarks;
        Button yourAns,overView;
        TextView descriptiontext;
        LinearLayout descLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //courseImage = itemView.findViewById(R.id.Home_popular_courses_image_id);
            question=itemView.findViewById(R.id.assessment_overview_question_id);
            quesNo=itemView.findViewById(R.id.assessment_overview_questionNumber_id);
            yourAns=itemView.findViewById(R.id.assessment_yourAns_check_button_id);
            overView=itemView.findViewById(R.id.assessment_overview_check_button_id);

            descriptiontext=itemView.findViewById(R.id.assessment_overView_text_id_text_id);
            descLayout=itemView.findViewById(R.id.assessment_overview_layout_id);
            obtainedMarks=itemView.findViewById(R.id.obtained_marks_text_id2323);
            totalMarks=itemView.findViewById(R.id.total_marks_text_id2323);

        }
    }
}
