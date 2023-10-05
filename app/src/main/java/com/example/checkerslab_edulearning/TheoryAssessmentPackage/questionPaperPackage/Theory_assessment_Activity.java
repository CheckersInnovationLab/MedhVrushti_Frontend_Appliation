package com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Ass_Standards_Model;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Ass_standards_adapter;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ChildAdapter;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ChildModel;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ParentAdapter;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ParentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Theory_assessment_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ParentModel> mainQuestionList;
    ArrayList<ChildModel> subQuestionList;
    private String Url="http://flask-medhvrushti.checkerslab.com/generate_question_paper";

    ParentAdapter parentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory_assessment);

        recyclerView=findViewById(R.id.Theory_assessment_Main_recyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mainQuestionList=new ArrayList<>();
        subQuestionList=new ArrayList<>();

        getAssessmentData();
    }
    private void getAssessmentData() {
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET,Url , null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i=0;i<=response.length();i++)
//                {
//                    try {
//                        JSONObject object=response.getJSONObject(i);
//                        Ass_Standards_Model model=new Ass_Standards_Model(object.getInt("stdId"),
//                                object.getInt("totalSubjects"),
//                                object.getString("stdName"),
//                                object.getString("stdName"));
//
////                        standardsList.add(model);
//                    }catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
////                Ass_standards_adapter adapter=new Ass_standards_adapter(standardsList,getApplicationContext());
////                recyclerView.setAdapter(adapter);
////
////                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
////
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
////        {
////            @Override
////            protected Map<String, String> getParams() {
////                // below line we are creating a map for
////                // storing our values in key and value pair.
////                Map<String, String> params = new HashMap<String, String>();
////
////                // on below line we are passing our key
////                // and value pair to our parameters.
////                params.put("boardId","100002");
////
////                // at last we are
////                // returning our params.
////                return params;
////            }
////        };
//        // below line is to make
//        // a json object request.
//
//        requestQueue.add(arrayRequest);

        /////////////////////////////////////////////////////////

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("subject_id", "100004");
            //     requestData.put("lastName", "100002");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Handle success response from the server
                        try {
                            JSONArray assQuestionArray = response.getJSONArray("questions");
                            JSONArray assQueTypeArray = response.getJSONArray("format");
                            mainQuestionList.clear();

                            int initialCount = 0;
                            for (int i = 0; i < assQueTypeArray.length(); i++) {
                                JSONObject typeObject = assQueTypeArray.getJSONObject(i);
                                String questionType = typeObject.getString("question_type_name");
                                String questionCount = typeObject.getString("total_question");

                                int sublistSize = Integer.parseInt(questionCount);
                                List<ChildModel> currentSubQuestionList = new ArrayList<>(); // Create a new list for each main question

                                for (int j = initialCount; j < initialCount + sublistSize; j++) {
                                    JSONObject questionsObject = assQuestionArray.getJSONObject(j);
                                    String questionNumber = questionsObject.getString("question_latex_by_line");
                                    currentSubQuestionList.add(new ChildModel(questionNumber));
                                }

                                initialCount += sublistSize;

                                mainQuestionList.add(new ParentModel(questionType, currentSubQuestionList));
                            }

                            parentAdapter = new ParentAdapter(mainQuestionList, getApplicationContext());
                            recyclerView.setAdapter(parentAdapter);

                           Log.d("count", String.valueOf(mainQuestionList.size()) + " and question count" + subQuestionList.size());


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
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
                        }
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

}
