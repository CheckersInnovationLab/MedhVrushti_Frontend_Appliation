package com.MedhVrushti.checkerslab_edulearning.commonActivityPackage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.MedhVrushti.checkerslab_edulearning.AssessmentSection_pkg.Selected_Test_Data_Model;
import com.MedhVrushti.checkerslab_edulearning.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

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
        holder.questionDiagram.setVisibility(View.GONE);

        holder.questionNumber.setText(String.valueOf(position+1)+")");

        String finalQuestion="";
        String imgUrl="";
        String UpdatedQuestion= model.getQuestion();
        UpdatedQuestion = UpdatedQuestion.replace("\\", "\\\\");
        try {
            JSONArray jsonArray = new JSONArray(UpdatedQuestion);

            // Iterate through the array
            for (int i = 0; i < jsonArray.length(); i++) {
                // Access the "text" value for each element
                JSONObject item = jsonArray.getJSONObject(i);

                String textValue="";
                if (item.getString("type").equals("text"))
                {
                    textValue = item.getString("text");
                }


                if (item.getString("type").equals("chart"))
                {

                    JSONArray jsonArray2 = new JSONArray(model.getQuestionDiagrams());
                    for (int j = 0; j < 1; j++)
                    {

                        String questionSt = jsonArray2.getString(j);
                        String diagram=questionSt.replace("\\/","/");
                        holder.questionDiagram.setVisibility(View.VISIBLE);
                        imgUrl= diagram;
                        Log.d("diagram==",finalQuestion+"----"+imgUrl);
                        Glide.with(context)
                                .load(imgUrl)
                                .fitCenter()
                                .into(holder.questionDiagram);

                    }
                }
                finalQuestion=finalQuestion+textValue;

            }
        }
        catch (Exception e)
        {
            Log.d("latexQuestion",e.getMessage());
        }
        holder.descDiagram.setVisibility(View.GONE);
        String finalDesc="";
        String descImgUrl="";
        String UpdatedDesc= model.getAnswerDescription().replace("\\n","<br>");
        //String UpdatedQuestion="[{\"type\": \"text\", \"text\": \"The region shaded horizontally is represented by the\"}, {\"type\": \"text\",  \"text\": \" inequations\"}, {\"type\": \"chart\", \"texdt\": \"line\"}]";
        UpdatedDesc = UpdatedDesc.replace("\\", "\\\\");
        try {
            JSONArray jsonArray = new JSONArray(UpdatedDesc);
            for (int i = 0; i < jsonArray.length(); i++) {
                // Access the "text" value for each element
                JSONObject item = jsonArray.getJSONObject(i);

                String textValue="";
                if (item.getString("type").equals("text") || item.getString("type").equals("math"))
                {
                    textValue = item.getString("text");
                }
                if (item.getString("type").equals("chart") || item.getString("type").equals("diagram"))
                {

                    JSONArray jsonArray2 = new JSONArray(model.getDescriptionDiagrams());
                    for (int j = 0; j < 1; j++)
                    {
                        String questionSt = jsonArray2.getString(j);
                        String diagram=questionSt.replace("\\/","/");
                        holder.descDiagram.setVisibility(View.VISIBLE);
                        descImgUrl= diagram;
                       // Log.d("diagram==",finalQuestion+"----"+imgUrl);
                        Glide.with(context)
                                .load(descImgUrl)
                                .fitCenter()
                                .into(holder.descDiagram);
                    }
                }
                finalDesc=finalDesc+textValue;

            }
        }
        catch (Exception e)
        {
            Log.d("latexQuestion",e.getMessage());
        }
        String finalUpdatedQuestion=finalQuestion.replace("\\n","<br>");
        setupWebView(holder.ques,finalUpdatedQuestion);
        setupWebView(holder.opt1, model.getOption1());
        setupWebView(holder.opt2, model.getOption2());
        setupWebView(holder.opt3, model.getOption3());
        setupWebView(holder.opt4, model.getOption4());
        setupWebView(holder.ans, model.getAnswer());
        setupWebView(holder.descText, finalDesc);



        //////////////////////////////////////////////////////////////////////////


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

        int obtainedMarks=Integer.valueOf(model.getObtainedMarks());
        int actualMarks=model.getMarks();

       if (obtainedMarks==actualMarks)
       {
           holder.questionCheckingStatus.setImageResource(R.drawable.correct_icon);
       }
       else
       {
           holder.questionCheckingStatus.setImageResource(R.drawable.wrong_icon);
       }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

      //  WebView   opt1,opt2,opt3,opt4,descText;
        WebView ques,ans,descText,opt1,opt2,opt3,opt4;
        Button descriptionButton;
        LinearLayout descLayout;
        TextView questionNumber;
        ImageView questionDiagram,descDiagram,questionCheckingStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ques=itemView.findViewById(R.id.Competitive_Solution_question_webView);
            ans=itemView.findViewById(R.id.assessment_question_Answer_WebView_id);
            opt1=itemView.findViewById(R.id.Competitive_Solution_option1_webView_id);
            opt2=itemView.findViewById(R.id.Competitive_Solution_option2_webView_id);
            opt3=itemView.findViewById(R.id.Competitive_Solution_option3_webView_id);
            opt4=itemView.findViewById(R.id.Competitive_Solution_option4_webView_id);
            descriptionButton=itemView.findViewById(R.id.assessment_description_button_id);
            descLayout=itemView.findViewById(R.id.assessment_description_layout_id);
            descText=itemView.findViewById(R.id.assessment_description_text_id);
            questionNumber=itemView.findViewById(R.id.Competitive_Solution_questionNumber_id);
            questionDiagram=itemView.findViewById(R.id.Solution_question_image_id);
            descDiagram=itemView.findViewById(R.id.Solution_description_image_id);
            questionCheckingStatus=itemView.findViewById(R.id.QuestionCheckingStatus_id);
        }
    }
    private void setupWebView(WebView webView, String content) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        String htmlData = "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/style.css\">" +
                "<script type=\"text/x-mathjax-config\">" +
                "MathJax.Hub.Config({" +
                "  messageStyle: 'none'," +
                "  tex2jax: {preview: 'none'}," +
                "  showMathMenu: false" + // This line disables the MathJax context menu
                "});" +
                "</script>" +
                "<script type=\"text/javascript\" src=\"file:///android_asset/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script>" +
                "</head><body>" + content + "</body></html>";

        webView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
    }
}
