package com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.UploadAnswerpackage.Upload_Answer_Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;


import static com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ChildModel.LayoutOne;
import static com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ChildModel.LayoutTwo;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder>{

    List<ChildModel> subQuestionList;
    Context context;


    public ChildAdapter(List<ChildModel> subQuestionList, Context context) {
        this.subQuestionList = subQuestionList;
        this.context = context;
    }

    public int getItemViewType(int position)
    {
        switch (subQuestionList.get(position).getViewType()) {
            case 0:
                return LayoutOne;
            case 1:
                return LayoutTwo;
            default:
                return -1;
        }
    }
    @NonNull
    @Override
    public ChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        switch (viewType) {
            case LayoutOne:
                View layoutOne
                        = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.th_assessment_mcq_layout, parent,
                                false);
                return new ViewHolder(layoutOne);
            case LayoutTwo:
                View layoutTwo
                        = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.assessment_subqestion_sl_layout, parent,
                                false);
                return new ViewHolder(layoutTwo);
            default:
                return null;
        }

    }








    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.ViewHolder holder, int position) {
        ChildModel childModel=subQuestionList.get(position);


        switch (subQuestionList.get(position).getViewType()) {
            case LayoutOne:

                try {
                    JSONArray jsonArray2 = new JSONArray(childModel.getSubQuestion());
                    String question2="";
                    for (int i = 0; i < jsonArray2.length();i++) {
                        JSONObject typeObject1 = jsonArray2.getJSONObject(i);

                        question2 = question2+typeObject1.getString("text")+"\\\\\n";
                    }

                    // ... your existing JSON parsing code ...

                    JLatexMathDrawable drawable = JLatexMathDrawable.builder("\\textbf{ " + question2 + "}")
                            .textSize(90)
                            .padding(2)
                            .background(0xFFffffff)
                            .align(JLatexMathDrawable.ALIGN_RIGHT)
                            .build();

                    holder.Question.setLatexDrawable(drawable); // Call method on latexMathView

                    JLatexMathDrawable drawable2 = JLatexMathDrawable.builder("\\text{ " + childModel.getOption1() + "}")
                            .textSize(50)
                            .padding(2)
                            .background(0xFFffffff)
                            .align(JLatexMathDrawable.ALIGN_RIGHT)
                            .build();
                    holder.option1.setLatexDrawable(drawable2);

                    JLatexMathDrawable drawable3 = JLatexMathDrawable.builder("\\text{ " + childModel.getOption2() + "}")
                            .textSize(50)
                            .padding(2)
                            .background(0xFFffffff)
                            .align(JLatexMathDrawable.ALIGN_RIGHT)
                            .build();
                    holder.option2.setLatexDrawable(drawable3);

                    JLatexMathDrawable drawable4 = JLatexMathDrawable.builder("\\text{ " + childModel.getOption3() + "}")
                            .textSize(50)
                            .padding(2)
                            .background(0xFFffffff)
                            .align(JLatexMathDrawable.ALIGN_RIGHT)
                            .build();

                    holder.option3.setLatexDrawable(drawable4);
                    JLatexMathDrawable drawable5 = JLatexMathDrawable.builder("\\text{ " + childModel.getOption4() + "}")
                            .textSize(50)
                            .padding(2)
                            .background(0xFFffffff)
                            .align(JLatexMathDrawable.ALIGN_RIGHT)
                            .build();

                    holder.option4.setLatexDrawable(drawable5);
                    String finalQuestion2 = question2;
                    holder.attempt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(context, Upload_Answer_Activity.class);

                            intent.putExtra("questionData", finalQuestion2);
                            intent.putExtra("question_id",childModel.getSubQuestionId());
                            intent.putExtra("marks",childModel.getQuesMarks());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            view.getContext().startActivity(intent);
                        }
                    });


                }catch (Exception ex) {
                    StringWriter stringWriter = new StringWriter();
                    ex.printStackTrace(new PrintWriter(stringWriter));

                }

                break;

            case LayoutTwo:


                try {
                    JSONArray jsonArray = new JSONArray(childModel.getSubQuestion());
                    String question="";
                    for (int i = 0; i < jsonArray.length();i++) {
                        JSONObject typeObject = jsonArray.getJSONObject(i);

                        question = question+typeObject.getString("text")+"\\\\\n";
                    }
                    JLatexMathDrawable drawable = JLatexMathDrawable.builder("\\textbf{ "+question+"}")
                            .textSize(50)
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
                            // Intent intent=new Intent(context, Camera_Testing_activity.class);
                            intent.putExtra("questionData", finalQuestion);
                            intent.putExtra("question_id",childModel.getSubQuestionId());
                            intent.putExtra("marks",childModel.getQuesMarks());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            view.getContext().startActivity(intent);
                        }
                    });

                }catch (Exception ex) {
                    StringWriter stringWriter = new StringWriter();
                    ex.printStackTrace(new PrintWriter(stringWriter));

                }


                break;
            default:
                return;
        }



//////////////////////////////////////////////
//        switch (holder.getItemViewType()) {
//            case VIEW_TYPE_MCQ:
//                // Bind your MCQ view here
//
//
//
//
//                try {
//                    JSONArray jsonArray2 = new JSONArray(childModel.getSubQuestion());
//                    String question2="";
//                    for (int i = 0; i < jsonArray2.length();i++) {
//                        JSONObject typeObject1 = jsonArray2.getJSONObject(i);
//
//                        question2 = question2+typeObject1.getString("text")+"\\\\\n";
//                    }
//                    JLatexMathDrawable drawable1 = JLatexMathDrawable.builder("\\text{ "+question2+"}")
//                            .textSize(150)
//                            .padding(2)
//                            .background(0xFFffffff)
//                            .align(JLatexMathDrawable.ALIGN_RIGHT)
//                            .build();
//
//                    holder.latexMathView.setLatexDrawable(drawable1);
//                    String finalQuestion2 = question2;
//                    holder.attempt.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent=new Intent(context, Upload_Answer_Activity.class);
//                            // Intent intent=new Intent(context, Camera_Testing_activity.class);
//                            intent.putExtra("questionData", finalQuestion2);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            view.getContext().startActivity(intent);
//                        }
//                    });
//
//                }catch (Exception ex) {
//                    StringWriter stringWriter = new StringWriter();
//                    ex.printStackTrace(new PrintWriter(stringWriter));
//
//                }
//
//
//
//
//                break;
//            case VIEW_TYPE_Normal:
//                // Bind your normal view here
//
//                try {
//                    JSONArray jsonArray = new JSONArray(childModel.getSubQuestion());
//                    String question="";
//                    for (int i = 0; i < jsonArray.length();i++) {
//                        JSONObject typeObject = jsonArray.getJSONObject(i);
//
//                        question = question+typeObject.getString("text")+"\\\\\n";
//                    }
//                    JLatexMathDrawable drawable = JLatexMathDrawable.builder("\\text{ "+question+"}")
//                            .textSize(150)
//                            .padding(2)
//                            .background(0xFFffffff)
//                            .align(JLatexMathDrawable.ALIGN_RIGHT)
//                            .build();
//
//                    holder.latexMathView.setLatexDrawable(drawable);
//                    String finalQuestion = question;
//                    holder.attempt.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent=new Intent(context, Upload_Answer_Activity.class);
//                            // Intent intent=new Intent(context, Camera_Testing_activity.class);
//                            intent.putExtra("questionData", finalQuestion);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            view.getContext().startActivity(intent);
//                        }
//                    });
//
//                }catch (Exception ex) {
//                    StringWriter stringWriter = new StringWriter();
//                    ex.printStackTrace(new PrintWriter(stringWriter));
//
//                }
//
//
//
//                break;
            // Handle other view types
  //      }
///////////////////////////////////////////

//        try {
//            JSONArray jsonArray = new JSONArray(childModel.getSubQuestion());
//            String question="";
//            for (int i = 0; i < jsonArray.length();i++) {
//                JSONObject typeObject = jsonArray.getJSONObject(i);
//
//                 question = question+typeObject.getString("text")+"\\\\\n";
//            }
//            JLatexMathDrawable drawable = JLatexMathDrawable.builder("\\text{ "+question+"}")
//                    .textSize(150)
//                    .padding(2)
//                    .background(0xFFffffff)
//                    .align(JLatexMathDrawable.ALIGN_RIGHT)
//                    .build();
//
//            holder.latexMathView.setLatexDrawable(drawable);
//            String finalQuestion = question;
//            holder.attempt.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                   Intent intent=new Intent(context, Upload_Answer_Activity.class);
//                   // Intent intent=new Intent(context, Camera_Testing_activity.class);
//                    intent.putExtra("questionData", finalQuestion);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                     view.getContext().startActivity(intent);
//                }
//            });
//
//        }catch (Exception ex) {
//            StringWriter stringWriter = new StringWriter();
//            ex.printStackTrace(new PrintWriter(stringWriter));
//
//        }
    }

    @Override
    public int getItemCount() {
        return subQuestionList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView subQuestion;
        Button attempt;
        JLatexMathView latexMathView;

        JLatexMathView Question,option1,option2,option3,option4;
        Button attempt2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            latexMathView=itemView.findViewById(R.id.assessment_sub_question_id);
            attempt=itemView.findViewById(R.id.question_Attempt_button_id);

            Question=itemView.findViewById(R.id.Theory_assessment_Main_Que_id);
            option1=itemView.findViewById(R.id.Theory_assessment_Main_Que_id1);
            option2=itemView.findViewById(R.id.Theory_assessment_Main_Que_id2);
            option3=itemView.findViewById(R.id.Theory_assessment_Main_Que_id3);
            option4=itemView.findViewById(R.id.Theory_assessment_Main_Que_id4);
            attempt2=itemView.findViewById(R.id.question_Attempt_button_id2);

        }
    }
}
