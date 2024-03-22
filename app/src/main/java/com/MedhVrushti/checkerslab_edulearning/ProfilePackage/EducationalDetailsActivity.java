package com.MedhVrushti.checkerslab_edulearning.ProfilePackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.MedhVrushti.checkerslab_edulearning.BoardModelClass;
import com.MedhVrushti.checkerslab_edulearning.ErrorStatusDialog;
import com.MedhVrushti.checkerslab_edulearning.LoadingDialog;
import com.MedhVrushti.checkerslab_edulearning.R;
import com.MedhVrushti.checkerslab_edulearning.StaticFile;
import com.MedhVrushti.checkerslab_edulearning.standardModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EducationalDetailsActivity extends AppCompatActivity {

    private EditText userSchoolName,userDepartment;
    private AutoCompleteTextView courseAutoCompleteTxt,standardAutoCompleteTxt;
    private TextView saveButton;
    private String academicId="";
    private String selectedCourseID="",selectedStandardID="";
    private List<BoardModelClass> coursesList;
    private List<String> courseNames,standardName;
    private List<standardModelClass> standardList;
    private ArrayAdapter<BoardModelClass> courseAdapterItems,standardAdapterItems;
    private LoadingDialog loadingDialog;
    private ErrorStatusDialog errorStatusDialog;
    private ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educational_details);
        loadingDialog=new LoadingDialog(EducationalDetailsActivity.this);
        errorStatusDialog=new ErrorStatusDialog(EducationalDetailsActivity.this);
        backButton=findViewById(R.id.edit_education_detailsToolbar_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        userSchoolName=findViewById(R.id.edit_schoolName_id);
        courseAutoCompleteTxt=findViewById(R.id.edit_courseName_id);
        standardAutoCompleteTxt=findViewById(R.id.edit_standard_id);
        userDepartment=findViewById(R.id.edit_department_id);
        saveButton=findViewById(R.id.edit_education_details_save_id);

        coursesList=new ArrayList<>();
        standardList=new ArrayList<>();
        courseNames=new ArrayList<>();
        standardName=new ArrayList<>();
        getUserEducationData();
        getCourseDetails();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userSchoolNameL=userSchoolName.getText().toString();
                String userDepartmentL=userDepartment.getText().toString();
                UpdateDetails(userSchoolNameL,selectedCourseID,selectedCourseID,userDepartmentL);
            }
        });

    }

    private void getUserEducationData() {
        loadingDialog.startLoadingDialog();

        String url2= StaticFile.Url+"/api/v1/cil/user-academic-details/get/by/user?";
        url2=url2+"user_id="+StaticFile.userId;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url2,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadingDialog.dismissDialog();
                        try {
                            String schoolName  = response.getString("school_name");
                            String boardId  = response.getString("board_id");
                            String stdId  = response.getString("std_id");
                            academicId  = response.getString("academic_id");
                            String departmentName=response.getString("attribute1");

                            showUserAcademicDetails(schoolName,boardId,stdId,academicId,departmentName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismissDialog();
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            Toast.makeText(EducationalDetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
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


    private void showUserAcademicDetails(String schoolName, String boardId, String stdId, String academicId, String departmentName) {

        if (!(schoolName=="null"))
        {
            userSchoolName.setText(schoolName);
        }
//        if (!(boardId=="null"))
//        {
//            userCourseName.setText(boardId);
//        }
//        if (!(stdId=="null"))
//        {
//            userStandard.setText(stdId);
//        }
        if (!(departmentName=="null"))
        {
            userDepartment.setText(departmentName);
        }
        if (!(academicId =="null"))
        {
            //parentMobNo.setText(academicId);
        }
    }


    private void UpdateDetails(String userSchoolNameL, String userCourseNameL, String userStandardL, String userDepartmentL) {
       loadingDialog.startLoadingDialog();
        String Url=StaticFile.Url+"/api/v1/cil/user-academic-details/update?academic_id="+academicId;

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id", StaticFile.userId);
            requestData.put("school_name", userSchoolNameL);
////            if (!subject_id.equals("null"))
////            {
////                requestData.put("subject_id", subject_id);
////            }
            requestData.put("board_id", selectedCourseID);
            requestData.put("std_id", selectedStandardID);
            requestData.put("attribute1",userDepartmentL);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Url,requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadingDialog.dismissDialog();
                        try {
                            String message = response.getString("message");
                            Toast.makeText(EducationalDetailsActivity.this, message, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                            loadingDialog.dismissDialog();
                            errorStatusDialog.showErrorMessage();
                        // Handle error response
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            Toast.makeText(EducationalDetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
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

    private void getCourseDetails()
    {
        String courseUrl=StaticFile.Url+"/api/v1/cil/board/get/all";
        coursesList.clear();
        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonObjectRequest2 = new JsonArrayRequest(Request.Method.GET, courseUrl,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle success response from the server
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);

                                BoardModelClass modelClass=new BoardModelClass(object.getString("board_name"),object.getString("board_id"));
                                coursesList.add(modelClass);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        setCourseList();
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
                            Toast.makeText(EducationalDetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        requestQueue2.add(jsonObjectRequest2);
    }
    private void setCourseList() {

        ArrayAdapter<BoardModelClass> courseAdapterItems = new ArrayAdapter<BoardModelClass>(getApplicationContext(), R.layout.courses_code_list_layout, coursesList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // Customize the view to display only the name
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setText(coursesList.get(position).getName());
                return textView;
            }

            @NonNull
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // Customize the dropdown view to display only the name
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setText(coursesList.get(position).getName());
                return textView;
            }
        };
        courseAutoCompleteTxt.setAdapter(courseAdapterItems);

        // get selected Course
        courseAutoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected BoardModelClass object
                BoardModelClass selectedBoard = (BoardModelClass) parent.getItemAtPosition(position);
                selectedCourseID= selectedBoard.getId();
                getStandardDetails(selectedCourseID);

            }
        });

    }

    private void getStandardDetails(String selectedCourseIDL) {

        String standardUrl=StaticFile.Url+"/api/v1/cil/standard/get/all/by/board_id?board_id="+ selectedCourseIDL;
        standardList.clear();
        RequestQueue requestQueue3 = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonObjectRequest3 = new JsonArrayRequest(Request.Method.GET, standardUrl,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);


                                standardModelClass modelClass=new standardModelClass(object.getString("std_name"),object.getString("std_id"));

                                //     String standardName=object.getString("std_name");

                                standardList.add(modelClass);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        setStandardList();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {

                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
                            Toast.makeText(EducationalDetailsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        requestQueue3.add(jsonObjectRequest3);
    }

    private void setStandardList() {
        ArrayAdapter<standardModelClass> courseAdapterItems = new ArrayAdapter<standardModelClass>(getApplicationContext(), R.layout.courses_code_list_layout, standardList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setText(standardList.get(position).getName());
                return textView;
            }

            @NonNull
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // Customize the dropdown view to display only the name
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setText(standardList.get(position).getName());
                return textView;
            }
        };
        standardAutoCompleteTxt.setAdapter(courseAdapterItems);

        // get selected Course
        standardAutoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected BoardModelClass object
                standardModelClass selectedStandard = (standardModelClass) parent.getItemAtPosition(position);

                // Extract the id
                selectedStandardID= selectedStandard.getId();
            }
        });

    }
}