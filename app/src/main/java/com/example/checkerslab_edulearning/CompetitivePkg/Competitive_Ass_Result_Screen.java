package com.example.checkerslab_edulearning.CompetitivePkg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.daimajia.numberprogressbar.OnProgressBarListener;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Selected_Test_Data_Model;
import com.example.checkerslab_edulearning.Navigation_Drawer_Activity;
import com.example.checkerslab_edulearning.ProfilePackage.PersonalProfileActivity;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.commonActivityPackage.Assessment_Solution_Screen;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Test_Reminder_activity;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.commonActivityPackage.assessmentHome.Assessment_home_Screen;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Competitive_Ass_Result_Screen extends AppCompatActivity implements OnProgressBarListener {

    private Timer timer;

    private NumberProgressBar bnp;
    private TextView currentScore1,studentRank,currentScore2,totalMarks2,studName;
    RelativeLayout solutionButton,reTestButton;
    CircularProgressBar circularProgressBar;
    int obtainedMarks=0,totalMarks=0;
    CircleImageView profileImage;
    TextView correctCountT,wrongCountT,unAnsweredT;
    Dialog dialog;
    Button cancelButton;
    int correctQuestion=0,unAttempt=0,wrong=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitive_ass_result_screen);

        solutionButton=findViewById(R.id.Ass_solutionButton_id);

        ///////////////vertical progress bar//////////

        bnp = (NumberProgressBar)findViewById(R.id.numberbar1);
        currentScore1=findViewById(R.id.Current_score_id1);
        circularProgressBar=findViewById(R.id.circularProgressBar_id);
        studentRank=findViewById(R.id.Competitive_assessment_ranking_text_id);
        currentScore2=findViewById(R.id.Current_score_id2);
        totalMarks2=findViewById(R.id.total_Marks_id);
        profileImage=findViewById(R.id.profile_image_id2001);
        studName=findViewById(R.id.Profile_Student_Name_id2001);
        correctCountT=findViewById(R.id.total_correct_question_id);
        wrongCountT=findViewById(R.id.total_wrong_question_id);
        unAnsweredT=findViewById(R.id.total_UnAnswered_question_id);
        reTestButton=findViewById(R.id.Ass_ReTestButton_id);
//        previewT=findViewById(R.id.total_Preview_question_id);

        if (!(Navigation_Drawer_Activity.studProfileImage == "null"))
        {
            Glide.with(getApplicationContext())
                    .load(Navigation_Drawer_Activity.studProfileImage)
                    .fitCenter()
                    .into(profileImage);
        }
        if (!(Navigation_Drawer_Activity.studName == "null"))
        {
            studName.setText(Navigation_Drawer_Activity.studName);
        }
        bnp.setOnProgressBarListener(this);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bnp.incrementProgressBy(1);
                    }
                });
            }
        }, 1000, 100);

    /////////////////////////////////////////////////////////

        CalculateResult();

        solutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Competitive_Ass_Result_Screen.this, Assessment_Solution_Screen.class);
                startActivity(intent);
            }
        });
    }

    private void CalculateResult() {

        for (int i=0;i< Test_Reminder_activity.testDataList.size();i++)
        {


            Log.d("Result","rear answer="+Test_Reminder_activity.testDataList.get(i).getAnswer()+" Selected answer is="+Test_Reminder_activity.testDataList.get(i).getSelectedAnswer());
            if (Test_Reminder_activity.testDataList.get(i).getSelectedAnswer()=="")
            {
                unAttempt++;
            }
            else
            {
                if (Test_Reminder_activity.testDataList.get(i).getSelectedAnswer().equals(Test_Reminder_activity.testDataList.get(i).getAnswer()))
                {
                    correctQuestion++;
                }
                else {
                    wrong++;
                }
            }
        }

        obtainedMarks=correctQuestion;
        totalMarks=Integer.valueOf(Test_Reminder_activity.totalMarksS);
        currentScore1.setText(String.valueOf(obtainedMarks));
        currentScore2.setText(String.valueOf(obtainedMarks));
        totalMarks2.setText(String.valueOf(totalMarks));
        circularProgressBar.setProgressMax(totalMarks);
        circularProgressBar.setProgress(obtainedMarks);
        correctCountT.setText(String.valueOf(obtainedMarks));
        wrongCountT.setText(String.valueOf(wrong));
        unAnsweredT.setText(String.valueOf(unAttempt));
//        previewT.setText(String.valueOf(correctQuestion));

        calculateRanking();

        uploadAssessmentDetails();


        //successful message Dialog box
        dialog= new Dialog(Competitive_Ass_Result_Screen.this);
        dialog.setContentView(R.layout.retest_unavailable_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        cancelButton=dialog.findViewById(R.id.retest_unavailable_layout_cancelButton_id);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        reTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });




        // Log.d("Result=","correct ="+correctQuestion+"\n wrong ="+wrong+"\n unattempt="+unAttempt);

    }


    private void uploadAssessmentDetails() {

        String userAssessmentUrl= StaticFile.Url+"/api/v1/cil/user_assessments/update";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());

        JSONObject requestData = new JSONObject();
        try {

            requestData.put("user_id",StaticFile.userId);
            requestData.put("assessment_id",Test_Reminder_activity.assessmentID);
            requestData.put("obtained_marks", obtainedMarks);
            requestData.put("ass_end_date", date);
            requestData.put("result_status", "Completed");
            requestData.put("status", "Active");
            requestData.put("result_date", date);
            requestData.put("time_taken", CompetetiveAssessmentScreen.AssTimeTaken);
            requestData.put("remarks", "Completed");
            requestData.put("created_by", "MedhVrushti App");
            requestData.put("last_updated_by", "MedhVrushti App");
            requestData.put("attribute10", String.valueOf(correctQuestion));
            requestData.put("attribute9", String.valueOf(unAttempt));
            requestData.put("attribute8", String.valueOf(wrong));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, userAssessmentUrl,requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        // Handle success response from the server
                        try {
//                            Intent intent=new Intent(Competitive_Ass_Result_Screen.this, Competitive_Assessment_Main_Screen2.class);
//                            startActivity(intent);

                            uploadAssessmentAnswer();
                            String message = response.getString("message");
                            Toast.makeText(getApplicationContext(), "Assessment Completed", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            Log.d("userSubscription :",errorMessage);
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
                        }
                    }
                }
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", getBodyContentType());
                headers.put("Authorization", "Bearer " +StaticFile.bearToken);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
    @Override
    public void onProgressChange(int current, int max) {
        if(current == max) {
           // Toast.makeText(getApplicationContext(), "ddddd", Toast.LENGTH_SHORT).show();
            bnp.setProgress(0);
        }
    }

    private void calculateRanking() {
        String userRankingUrl= "http://89.116.33.21:5000/get/CET/Ranking";


        JSONObject requestData = new JSONObject();
        try {
            // requestData.put("user_id", Navigation_Drawer_Activity.userId);
            requestData.put("user_id",StaticFile.userId);
            requestData.put("assessment_id",Test_Reminder_activity.assessmentID);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, userRankingUrl,requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        // Handle success response from the server
                        try {
//                            Intent intent=new Intent(Competitive_Ass_Result_Screen.this, Competitive_Assessment_Main_Screen2.class);
//                            startActivity(intent);
                            String message = response.getString("message");
                            Toast.makeText(getApplicationContext(), "Assessment Completed", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            Log.d("userSubscription :",errorMessage);
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
                        }
                    }
                }
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", getBodyContentType());
                headers.put("Authorization", "Bearer " +StaticFile.bearToken);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

    private void uploadAssessmentAnswer() {

        String Url="https://medhvrushti.checkerslab.com/api/v1/cil/assessment_answers_data/add/all/assessment_answers";

        JSONArray answersArray = new JSONArray();

         Log.d("status","upload Answer");

        for (int i=0;i<Test_Reminder_activity.testDataList.size();i++)
        {
            Selected_Test_Data_Model ansModel=Test_Reminder_activity.testDataList.get(i);
            try {
                JSONObject answerObject = new JSONObject();
                answerObject.put("user_id", StaticFile.userId);
                answerObject.put("assessment_id", Test_Reminder_activity.assessmentID);
                answerObject.put("question_id", ansModel.getQuestionId());
                answerObject.put("question_type_id",ansModel.getQuestionTypeId() );
                answerObject.put("answer_latex", ansModel.getSelectedAnswer());
                answerObject.put("assessment_type", ansModel.getQuestionType());
                answerObject.put("checking_status", "Checked");
                answerObject.put("total_marks", ansModel.getMarks());
                if (ansModel.getSelectedAnswer().equals(ansModel.getAnswer()))
                {
                    answerObject.put("obtained_marks", ansModel.getMarks());
                }
                else
                {
                    answerObject.put("obtained_marks", 0);
                }

                answerObject.put("created_by", "MedhVrushti APP");
                answerObject.put("last_updated_by", "MedhVrushti APP");
                answerObject.put("status", "Active");
                answerObject.put("bookmark", ansModel.getBookmarkStatus());

                answersArray.put(answerObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, Url, answersArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("QuestionPaperGenerator","Success");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            Log.d("QuestionPaperGenerator",errorMessage);
                        }
                    }
                }
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", getBodyContentType());
                headers.put("Authorization", "Bearer " + StaticFile.bearToken);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent =new Intent(Competitive_Ass_Result_Screen.this,Assessment_home_Screen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();

    }
}