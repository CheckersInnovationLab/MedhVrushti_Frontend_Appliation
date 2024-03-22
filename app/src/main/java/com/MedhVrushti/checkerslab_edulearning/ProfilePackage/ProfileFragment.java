package com.MedhVrushti.checkerslab_edulearning.ProfilePackage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.MedhVrushti.checkerslab_edulearning.ErrorStatusDialog;
import com.MedhVrushti.checkerslab_edulearning.LoadingDialog;
import com.MedhVrushti.checkerslab_edulearning.R;
import com.MedhVrushti.checkerslab_edulearning.StaticFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {


    private ImageView editPersonalProfile,editEducationalDetails;
    private TextView userName,userEmailID,userMobileNumber,userCourseName,userAddress,userCollegeName,department;
    private LoadingDialog loadingDialog;
    private ErrorStatusDialog errorStatusDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        loadingDialog=new LoadingDialog(getActivity());
        errorStatusDialog=new ErrorStatusDialog(getActivity());

        editPersonalProfile=view.findViewById(R.id.edit_personal_details_button_id);
        editEducationalDetails=view.findViewById(R.id.edit_education_details_button_id);
        department=view.findViewById(R.id.education_detail_department_name_id);
        userName=view.findViewById(R.id.your_detail_name_id);
        userEmailID=view.findViewById(R.id.your_detail_email_id);
        userMobileNumber=view.findViewById(R.id.your_detail_mobile_id);
        userCollegeName=view.findViewById(R.id.education_detail_college_name_id);
        userCourseName=view.findViewById(R.id.education_detail_department_name_id);
        userAddress=view.findViewById(R.id.education_detail_location_name_id);
        getProfileDetails();
        editPersonalProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),PersonalProfileActivity.class);
                startActivity(intent);
            }
        });
        editEducationalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),EducationalDetailsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void getProfileDetails() {
        loadingDialog.startLoadingDialog();
        String url2= StaticFile.Url+"/api/v1/cil/users/get?";
        url2=url2+"user_id="+StaticFile.userId;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url2,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String mobileNumber  = response.getString("mobile_no");
                            String name  = response.getString("first_name");
                            String profileImage  = response.getString("profile_image_url");
                            String whatsappNo  = response.getString("user_whatsapp_no");
                            String emailID  = response.getString("email_id");
                            String birthDate  = response.getString("date_of_birth");
                            String gender  = response.getString("gender");
                            String address  = response.getString("address");
                            String parentMobNumber  = response.getString("parents_contact_no");

                            setUserPersonalDetails(name,mobileNumber,profileImage,emailID,address,parentMobNumber,gender,birthDate,whatsappNo);
                            getUserEducationData();

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
                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
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

    private void setUserPersonalDetails(String name, String mobileNumber, String profileImage, String emailID, String address, String parentMobNumber, String gender, String birthDate, String whatsappNo) {
        if (!(name =="null"))
        {
            userName.setText(name);
        }
        if (!(mobileNumber =="null"))
        {
            userMobileNumber.setText(mobileNumber);
        }
        if (!(emailID =="null"))
        {
            userEmailID.setText(emailID);
        }
    }
    private void getUserEducationData() {
        String url2= StaticFile.Url+"/api/v1/cil/user-academic-details/get/by/user?";
        url2=url2+"user_id="+StaticFile.userId;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url2,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadingDialog.dismissDialog();
                        try {
                            String schoolName  = response.getString("school_name");
                            String boardId  = response.getString("board_id");
                            String stdId  = response.getString("std_id");
//                            academicId  = response.getString("academic_id");
                            String departmentName=response.getString("attribute1");
                            userCollegeName.setText(schoolName);
                            department.setText(departmentName);
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
                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
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
}