package com.example.checkerslab_edulearning.CompetitivePkg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.system.StructUtsname;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Selected_Test_Data_Model;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Test_Reminder_activity;
import com.example.checkerslab_edulearning.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class CompetetiveAssessmentScreen extends AppCompatActivity implements  View.OnTouchListener  {
    WebView question, option1,option2, option3, option4;
    LinearLayout optLayout1,optLayout2, optLayout3, optLayout4;
    ImageView questionDiagram;
    TextView questionNumber,time,submit;

    String selectedAnswer = "";
    private CountDownTimer countDownTimer;
    Selected_Test_Data_Model model;
    RelativeLayout nextButton, previousButton,previewButton;
    private int currentQuesNO = 0;
    ImageView questionStatusB;
    private DrawerLayout drawerLayout;
    ImageView closeB,bookMarkButton,filledBookMark;
    GridView gridView;
     private questStatusGridAdapter gridAdapter;
    int totalQuestion;
     static  String assessmentID="";
     public  static String AssTimeTaken="";
    private long elapsedTime = 0;
    long totalTimeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competetive_assessment_screen);

        question = findViewById(R.id.questionWebView_id);
        questionDiagram=findViewById(R.id.question_image_id);
        option1 = findViewById(R.id.option1WebView_id);
        option2 =findViewById(R.id.option2WebView_id);
        option3 =findViewById(R.id.option3WebView_id);
        option4 =findViewById(R.id.option4WebView_id);
        optLayout1 =findViewById(R.id.Competitive_option1_layout);
        optLayout2 = findViewById(R.id.Competitive_option2_layout);
        optLayout3 =findViewById(R.id.Competitive_option3_layout);
        optLayout4 = findViewById(R.id.Competitive_option4_layout);
        nextButton = findViewById(R.id.Competitive_Assessment_next_layout);
        previousButton = findViewById(R.id.Competitive_Assessment_previous_layout);
        previewButton=findViewById(R.id.Competitive_Assessment_preview_layout);
        questionNumber=findViewById(R.id.Competitive_Assessment_number_text_id);
        time = findViewById(R.id.Competitive_Assessment_time_text_id);
        bookMarkButton=findViewById(R.id.Competitive_Assessment_bookmark_id);
        filledBookMark=findViewById(R.id.Competitive_Assessment_Filled_bookmark_id);
        submit=findViewById(R.id.Competitive_Assessment_submit_button_id);

        submit.setOnClickListener(v -> SubmitQuize());
        questionStatusB = findViewById(R.id.Competitive_Assessment_view_all_id);

        drawerLayout = findViewById(R.id.drawer_layout_id);
        closeB = findViewById(R.id.close_button_id1212);
        gridView = findViewById(R.id.Grid_view_id);
        totalQuestion = Test_Reminder_activity.testDataList.size();
        gridAdapter = new questStatusGridAdapter(totalQuestion, this);
        gridView.setAdapter(gridAdapter);


        option1.setOnTouchListener(this);
        option2.setOnTouchListener(this);
        option3.setOnTouchListener(this);
        option4.setOnTouchListener(this);
        bookMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setBookmarkStatus(true);
                filledBookMark.setVisibility(View.VISIBLE);
                bookMarkButton.setVisibility(View.GONE);
                Log.d("Bookmark",String.valueOf(currentQuesNO)+"is bookmark");
            }
        });
        filledBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setBookmarkStatus(false);
                filledBookMark.setVisibility(View.GONE);
                bookMarkButton.setVisibility(View.VISIBLE);
            }
        });



        questionStatusB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!drawerLayout.isDrawerOpen(GravityCompat.END))) {
                    gridAdapter.notifyDataSetChanged();
                    drawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });
        closeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((drawerLayout.isDrawerOpen(GravityCompat.END)))
                {
                    drawerLayout.closeDrawer(GravityCompat.END);
                }
            }
        });

        loadNewQuestion();
        startTimer();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQuesNO++;
                loadNewQuestion();

//                Log.d("selected Answer",selectedAnswer);

            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentQuesNO>0)
                {
                    currentQuesNO--;
                    loadNewQuestion();
                }

            }
        });
        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setStatus("Preview");
                currentQuesNO++;
                loadNewQuestion();

            }
        });

//        option1.setOnClickListener(this);
//        option2.setOnClickListener(this);
//        option3.setOnClickListener(this);
//        option4.setOnClickListener(this);

    }
    private void loadNewQuestion() {

        optLayout1.setBackground(null);
        optLayout2.setBackground(null);
        optLayout3.setBackground(null);
        optLayout4.setBackground(null);
        filledBookMark.setVisibility(View.GONE);
        bookMarkButton.setVisibility(View.VISIBLE);

        if(currentQuesNO == totalQuestion ){
            SubmitQuize();
            return;
        }
        selectedAnswer="";
        model= Test_Reminder_activity.testDataList.get(currentQuesNO);
        questionDiagram.setVisibility(View.GONE);


        if (model.getStatus().equals("Not_Visited"))
        {
            model.setStatus("UnAnswered");
        }
        Log.d("getBookmarkStatus",String.valueOf(model.getBookmarkStatus()));


        if (model.getBookmarkStatus()==(true))
        {
            filledBookMark.setVisibility(View.VISIBLE);
            bookMarkButton.setVisibility(View.GONE);
        }

        if (!model.getSelectedAnswer().isEmpty())
        {
//            Log.d("selected Answer",String.valueOf(currentQuesNO)+"---"+model.getSelectedAnswer());

            getQuestionStatus(currentQuesNO);

        }
        questionNumber.setText(String.valueOf(currentQuesNO+1));

        String finalQuestion="";
        String imgUrl="";
        String UpdatedQuestion= model.getQuestion();
        //String UpdatedQuestion="[{\"type\": \"text\", \"text\": \"The region shaded horizontally is represented by the\"}, {\"type\": \"text\",  \"text\": \" inequations\"}, {\"type\": \"chart\", \"texdt\": \"line\"}]";
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
                        questionDiagram.setVisibility(View.VISIBLE);
                        imgUrl= diagram;
                        Log.d("diagram==",finalQuestion+"----"+imgUrl);
                        Glide.with(getApplicationContext())
                                .load(imgUrl)
                                .fitCenter()
                                .into(questionDiagram);

                    }
                }
                finalQuestion=finalQuestion+textValue;

        }
    }
        catch (Exception e)
        {
            Log.d("latexQuestion",e.getMessage());
        }

//        question.getSettings().setJavaScriptEnabled(true);
//        question.setWebChromeClient(new WebChromeClient());
//

//
//        String htmlData = "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/style.css\">" +
//                "<script type=\"text/x-mathjax-config\">" +
//                "MathJax.Hub.Config({" +
//                "  messageStyle: 'none'," +
//                "  tex2jax: {preview: 'none'}," +
//                "  showMathMenu: false" + // This line disables the MathJax context menu
//                "});" +
//                "</script>" +
//                "<script type=\"text/javascript\" src=\"file:///android_asset/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script>" +
//                "</head><body>" +finalUpdatedQuestion + "</body></html>";
//
//        Log.d("New Log",finalUpdatedQuestion);
//
//
//        question.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);


//
//        option1.getSettings().setJavaScriptEnabled(true);
//        option1.setWebChromeClient(new WebChromeClient());
//        String htmlDat1 = "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/style.css\">" +
//                "<script type=\"text/x-mathjax-config\">" +
//                "MathJax.Hub.Config({" +
//                "  messageStyle: 'none'," +
//                "  tex2jax: {preview: 'none'}," +
//                "  showMathMenu: false" + // This line disables the MathJax context menu
//                "});" +
//                "</script>" +
//                "<script type=\"text/javascript\" src=\"file:///android_asset/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script>" +
//                "</head><body>" + model.getOption1() + "</body></html>";
//
//        option1.loadDataWithBaseURL(null, htmlDat1, "text/html", "UTF-8", null);
///////////////////////////////////////////////////////////////////////////////////////////////////
//
/////////////////////////////
//        option2.getSettings().setJavaScriptEnabled(true);
//        option2.setWebChromeClient(new WebChromeClient());
//        String htmlDat2 = "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/style.css\">" +
//                "<script type=\"text/x-mathjax-config\">" +
//                "MathJax.Hub.Config({" +
//                "  messageStyle: 'none'," +
//                "  tex2jax: {preview: 'none'}," +
//                "  showMathMenu: false" + // This line disables the MathJax context menu
//                "});" +
//                "</script>" +
//                "<script type=\"text/javascript\" src=\"file:///android_asset/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script>" +
//                "</head><body>" + model.getOption2() + "</body></html>";
//
//        option2.loadDataWithBaseURL(null, htmlDat2, "text/html", "UTF-8", null);
///////////////////////////////////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////   option 3    ////////////////////////////////////
//        option3.getSettings().setJavaScriptEnabled(true);
//        option3.setWebViewClient(new WebViewClient());
//        String htmlData3 = "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/style.css\">" +
//                "<script type=\"text/x-mathjax-config\">" +
//                "MathJax.Hub.Config({" +
//                "  messageStyle: 'none'," +
//                "  tex2jax: {preview: 'none'}," +
//                "  showMathMenu: false" + // This line disables the MathJax context menu
//                "});" +
//                "</script>" +
//                "<script type=\"text/javascript\" src=\"file:///android_asset/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script>" +
//                "</head><body>" + model.getOption3() + "</body></html>";
//
//        option3.loadDataWithBaseURL(null, htmlData3, "text/html", "UTF-8", null);
///////////////////////////////////////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////   option 4    ////////////////////////////////////
//        option4.getSettings().setJavaScriptEnabled(true);
//        option4.setWebViewClient(new WebViewClient());
//        String htmlData4 = "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/style.css\">" +
//                "<script type=\"text/x-mathjax-config\">" +
//                "MathJax.Hub.Config({" +
//                "  messageStyle: 'none'," +
//                "  tex2jax: {preview: 'none'}," +
//                "  showMathMenu: false" + // This line disables the MathJax context menu
//                "});" +
//                "</script>" +
//                "<script type=\"text/javascript\" src=\"file:///android_asset/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML\"></script>" +
//                "</head><body>" + model.getOption4() + "</body></html>";
//
//        option4.loadDataWithBaseURL(null, htmlData4, "text/html", "UTF-8", null);
        String finalUpdatedQuestion=finalQuestion.replace("\\n","<br>");
        setupWebView(question,finalUpdatedQuestion);
        setupWebView(option1, model.getOption1());
        setupWebView(option2, model.getOption2());
        setupWebView(option3, model.getOption3());
        setupWebView(option4, model.getOption4());
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

    private void getQuestionStatus(int currentQuesNO) {

        Log.d("checking1",model.getSelectedAnswer()+"=="+model.getOption1());

        if (model.getSelectedAnswer().equals(model.getOption1()))
        {
            Toast.makeText(this, "Already submitted Question in system", Toast.LENGTH_SHORT).show();
            optLayout1.setBackground(getResources().getDrawable(R.drawable.button_border));
        }
        else if (model.getSelectedAnswer()==model.getOption2())
        {
            optLayout2.setBackground(getResources().getDrawable(R.drawable.button_border));
        }
        else if (model.getSelectedAnswer()==model.getOption3())
        {
            optLayout3.setBackground(getResources().getDrawable(R.drawable.button_border));
        }
        else if (model.getSelectedAnswer()==model.getOption4())
        {
            optLayout4.setBackground(getResources().getDrawable(R.drawable.button_border));
        }
    }

    //    @Override
//    public void onClick(View view) {
//
//        option1.setBackgroundColor(Color.WHITE);
//        option2.setBackgroundColor(Color.WHITE);
//        option3.setBackgroundColor(Color.WHITE);
//        option4.setBackgroundColor(Color.WHITE);
//
//        WebView clickedButton=(WebView) view;
//        if (clickedButton.getId()==R.id.option1WebView_id){
//            selectedAnswer  =model.getOption1() ;
//            option1.getSettings();
//            option1.setBackgroundColor(Color.BLUE);
//
//        }
//        if (clickedButton.getId()==R.id.option2WebView_id){
//            selectedAnswer  =model.getOption2() ;
//                option2.setBackgroundColor(Color.GRAY);
//        }
//        if (clickedButton.getId()==R.id.option3WebView_id){
//            selectedAnswer  =model.getOption3() ;
//            option4.setBackgroundColor(Color.GRAY);
//        }
//        if (clickedButton.getId()==R.id.option4WebView_id){
//            selectedAnswer  =model.getOption4() ;
//                option4.setBackgroundColor(Color.GRAY);
//        }
//       // option4.setBackgroundColor(Color.GRAY);
//         clickedButton.setBackgroundColor(Color.GRAY);
//        model.setSelectedAnswer(selectedAnswer);
//    }
    private void startTimer() {

//        long totalTimeInMillis = 10 * 60 * 1000;
//
//        countDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                // Update the timer display on each tick
//                updateTimerDisplay(millisUntilFinished);
//            }
//
//            @Override
//            public void onFinish() {
//                // Handle the timer finish event (e.g., quiz submission)
//                time.setText("Timer: 00:00");
//                // Add any actions to perform when the timer finishes
//            }
//        };
//
//        // Start the countdown timer
//        countDownTimer.start();

         totalTimeInMillis =  10 * 60 * 1000; // Example: 10 minutes

        // Create a countdown timer
        countDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display on each tick
                updateTimerDisplay(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                // Handle the timer finish event (e.g., quiz submission)
                time.setText("Time: 00:00");
              //  elapsedTimeTextView.setText("Elapsed Time: " + getElapsedTimeFormatted());
                // Add any actions to perform when the timer finishes
            }
        };

        // Start the countdown timer
        countDownTimer.start();


    }
//    private void updateTimerDisplay(long millisUntilFinished) {
//        // Convert milliseconds to minutes and seconds
//        int minutes = (int) (millisUntilFinished / 1000) / 60;
//        int seconds = (int) (millisUntilFinished / 1000) % 60;
//
//        // Format the time and update the TextView
//        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
//        time.setText("Timer: " + timeLeftFormatted);
//    }
private void updateTimerDisplay(long millisUntilFinished) {
    // Convert milliseconds to minutes and seconds
    int minutes = (int) (millisUntilFinished / 1000) / 60;
    int seconds = (int) (millisUntilFinished / 1000) % 60;

    // Format the time and update the remaining time TextView
    String remainingTimeFormatted = String.format("%02d:%02d", minutes, seconds);
    time.setText("Time: " + remainingTimeFormatted);

    // Update elapsed time
    elapsedTime = totalTimeInMillis - millisUntilFinished;

    // Format the elapsed time and update the elapsed time TextView
    AssTimeTaken=getElapsedTimeFormatted();
    Log.d("AssTimeTaken",AssTimeTaken);
//   elapsedTimeTextView.setText("Elapsed Time: " + getElapsedTimeFormatted());
}

    private String getElapsedTimeFormatted() {
        // Convert elapsed time to minutes and seconds
        int elapsedMinutes = (int) (elapsedTime / 1000) / 60;
        int elapsedSeconds = (int) (elapsedTime / 1000) % 60;

        // Format the elapsed time
        return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
    }
    public void gotoQuestion(int position) {
        currentQuesNO=position;
        loadNewQuestion();

        if ((drawerLayout.isDrawerOpen(GravityCompat.END)))
        {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        Log.d("Debug", "Selected position: " + position);

        // Notify the adapter about the dataset change
        gridAdapter.notifyDataSetChanged();

        if ((drawerLayout.isDrawerOpen(GravityCompat.END))) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();

        if (action == MotionEvent.ACTION_UP) {
            optLayout1.setBackground(null);
            optLayout2.setBackground(null);
            optLayout3.setBackground(null);
            optLayout4.setBackground(null);

            WebView clickedButton = (WebView) view;

            if (clickedButton.getId() == R.id.option1WebView_id) {
                selectedAnswer = model.getOption1();
                optLayout1.setBackground(getResources().getDrawable(R.drawable.button_border));
            } else if (clickedButton.getId() == R.id.option2WebView_id) {
                selectedAnswer = model.getOption2();
                optLayout2.setBackground(getResources().getDrawable(R.drawable.button_border));
            } else if (clickedButton.getId() == R.id.option3WebView_id) {
                selectedAnswer = model.getOption3();
                optLayout3.setBackground(getResources().getDrawable(R.drawable.button_border));
            } else if (clickedButton.getId() == R.id.option4WebView_id) {
                selectedAnswer = model.getOption4();
                optLayout4.setBackground(getResources().getDrawable(R.drawable.button_border));
            }
            model.setSelectedAnswer(selectedAnswer);
            model.setStatus("Answered");
//            Log.d("Answer", selectedAnswer);
        }
        return false;
    }

    private void SubmitQuize() {
        finishQuiz();

    }
    void finishQuiz(){

        Log.d("Submitted","Submitted");
        ////////////////////
        AlertDialog.Builder builder = new AlertDialog.Builder(CompetetiveAssessmentScreen.this);
        builder.setMessage("Do you want to Submit the Test?");
        builder.setTitle("Submit Test");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            finish();
            countDownTimer.cancel();
            Intent intent=new Intent(CompetetiveAssessmentScreen.this, Competitive_Ass_Result_Screen.class);
           startActivity(intent);
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.dismiss();

        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {
        finishQuiz();
//        Intent intent=new Intent(CompetetiveAssessmentScreen.this, Competitive_Ass_Result_Screen.class);
//        startActivity(intent);
       // super.onBackPressed();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            finishQuiz(); // Show confirmation dialog
            // Handle Home button press
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_APP_SWITCH) { // For App Overview or Recent Apps button
            finishQuiz(); // Show confirmation dialog
            // Handle App Overview button press
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
//    @Override
//    protected void onPause() {
//
//        finishQuiz(); // Show confirmation dialog or perform necessary actions
//        super.onPause();
//    }
    @Override
    protected void onUserLeaveHint() {
        finishQuiz();
       // super.onUserLeaveHint();

    }

}