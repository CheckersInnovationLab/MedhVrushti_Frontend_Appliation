package com.example.checkerslab_edulearning.CompetitivePkg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkerslab_edulearning.AssessmentSection_pkg.Selected_Test_Data_Model;
import com.example.checkerslab_edulearning.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import io.github.kexanie.library.MathView;


public class competitiveAssAdapter extends RecyclerView.Adapter<competitiveAssAdapter.ViewHolder> {

    private final List<Selected_Test_Data_Model> assDataList;
    private RelativeLayout previousSelectB; // Declaration added
    Context context;

    public competitiveAssAdapter(List<Selected_Test_Data_Model> assDataList, Context context) {
        this.assDataList = assDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public competitiveAssAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.compettive_ass_sl_layout, parent, false);
        return new competitiveAssAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull competitiveAssAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return assDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MathView question, option1, option2, option3, option4;
        RelativeLayout optLayout1, optLayout2, optLayout3, optLayout4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.Competitive_Assessment_question_id);
            option1 = itemView.findViewById(R.id.Competitive_option_1_id);
            option2 = itemView.findViewById(R.id.Competitive_option_2_id);
            option3 = itemView.findViewById(R.id.Competitive_option_3_id);
            option4 = itemView.findViewById(R.id.Competitive_option_4_id);
            optLayout1 = itemView.findViewById(R.id.Competitive_option1_layout);
            optLayout2 = itemView.findViewById(R.id.Competitive_option2_layout);
            optLayout3 = itemView.findViewById(R.id.Competitive_option3_layout);
            optLayout4 = itemView.findViewById(R.id.Competitive_option4_layout);
        }




        private void setData(final int position) {
            final Selected_Test_Data_Model assDataModel = assDataList.get(position);



            if (assDataModel.getStatus()=="Not_Visited")
            {
                assDataModel.setStatus("UnAnswered");
            }


            //get question and convert from \n to <br>
            //String updatedQuestion = assDataModel.getQuestion().replace("\n", "<br>");
            //set style to mathView
           // String styledQues = "<font size='5' color='#252525'>" + updatedQuestion+ "</font>";
            //set latex to mathView

            question.setNestedScrollingEnabled(true);


            //////////////tested code latex
//            question.setText(assDataModel.getQuestion());
            /////////////////////////////////////////////
            String finalQuestion="";
            String UpdatedQuestion=assDataModel.getQuestion();

            UpdatedQuestion = UpdatedQuestion.replace("\\", "\\\\");
            try {
                JSONArray jsonArray = new JSONArray(UpdatedQuestion);


                // Iterate through the array
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Access the "text" value for each element
                    JSONObject item = jsonArray.getJSONObject(i);

                    String textValue = item.getString("text");

                    finalQuestion=finalQuestion+textValue;

                    // Now you can use the text value as needed
                    Log.d("MainActivity", "Text value: " + textValue);

                }
            }
            catch (Exception e)
            {
                Log.d("latexQuestion",e.getMessage());
            }
            Log.d("latexQuestionOverall:",UpdatedQuestion);
            Log.d("latexQuestionFinal", finalQuestion);

            question.setText(finalQuestion);
            Log.d("questionId",String.valueOf(assDataModel.getQuestionId()));





           // String updatedOption1 = assDataModel.getOption1().replace("\n", "<br>");
           // String styledOpt1 = "<font size='5' color='#4c4c4c'>" + updatedOption1 + "</font>";
            option1.setText(assDataModel.getOption1());

           // String updatedOption2 = assDataModel.getOption2().replace("\n", "<br>");
           // String styledOpt2 = "<font size='5' color='#4c4c4c'>" + updatedOption2 + "</font>";
            option2.setText(assDataModel.getOption2());

           // String updatedOption3 = assDataModel.getOption3().replace("\n", "<br>");
           // String styledOpt3 = "<font size='5' color='#4c4c4c'>" + updatedOption3 + "</font>";
            option3.setText(assDataModel.getOption3());

          //  String updatedOption4 = assDataModel.getOption4().replace("\n", "<br>");
          //  String styledOpt4 = "<font size='5' color='#4c4c4c'>" + updatedOption4 + "</font>";
            option4.setText(assDataModel.getOption4());

            optLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectOption(optLayout1, assDataModel.getOption1(), position);
                }
            });

            optLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectOption(optLayout2, assDataModel.getOption2(), position);
                }
            });

            optLayout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectOption(optLayout3, assDataModel.getOption3(), position);
                }
            });

            optLayout4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectOption(optLayout4, assDataModel.getOption4(), position);
                }
            });

            updateOptionUI(optLayout1, assDataModel.getSelectedAnswer() == assDataModel.getOption1());
            updateOptionUI(optLayout2, assDataModel.getSelectedAnswer() == assDataModel.getOption2());
            updateOptionUI(optLayout3, assDataModel.getSelectedAnswer() == assDataModel.getOption3());
            updateOptionUI(optLayout4, assDataModel.getSelectedAnswer() == assDataModel.getOption4());

        }

        private void selectOption(RelativeLayout optLayout, String optionNumber, int position) {
            // Update the model
            Selected_Test_Data_Model model = assDataList.get(position);
            model.setSelectedAnswer(optionNumber);
            model.setStatus("Answered");

            updateOptionUI(optLayout1, optionNumber == model.getOption1());
            updateOptionUI(optLayout2, optionNumber == model.getOption2());
            updateOptionUI(optLayout3, optionNumber == model.getOption3());
            updateOptionUI(optLayout4, optionNumber == model.getOption4());
            // Notify the adapter to refresh this item
            //notifyItemChanged(position);
        }

        private void updateOptionUI(RelativeLayout optLayout, boolean isSelected) {
            optLayout.setBackgroundResource(isSelected ? R.color.Green : R.color.white);
        }
    }
}
