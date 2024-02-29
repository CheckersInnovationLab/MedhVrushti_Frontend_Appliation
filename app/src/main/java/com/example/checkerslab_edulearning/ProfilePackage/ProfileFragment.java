package com.example.checkerslab_edulearning.ProfilePackage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.BoardModelClass;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.commonActivityPackage.Contact_Us_Screen;
import com.example.checkerslab_edulearning.commonActivityPackage.Rate_Us_Screen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {


    private ImageView editPersonalProfile,editEducationalDetails;
    private TextView userName,userEmailID,userMobileNumber,userCourseName,userAddress,userCollegeName;
    private LinearLayout contactUsButton,rateUsButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        editPersonalProfile=view.findViewById(R.id.edit_personal_details_button_id);
        editEducationalDetails=view.findViewById(R.id.edit_education_details_button_id);

        contactUsButton=view.findViewById(R.id.Contact_Us_button_id);

        userName=view.findViewById(R.id.your_detail_name_id);
        userEmailID=view.findViewById(R.id.your_detail_email_id);
        userMobileNumber=view.findViewById(R.id.your_detail_mobile_id);
        userCollegeName=view.findViewById(R.id.education_detail_college_name_id);
        userCourseName=view.findViewById(R.id.education_detail_department_name_id);
        userAddress=view.findViewById(R.id.education_detail_location_name_id);
        rateUsButton=view.findViewById(R.id.Rate_Us_button_id);


        contactUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Contact_Us_Screen.class);
                startActivity(intent);
            }
        });
        rateUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Rate_Us_Screen.class);
                startActivity(intent);
            }
        });

        getProfileDetails();
        getEducationDetails();
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

        String url2= StaticFile.Url+"/api/v1/cil/users/get?";
        url2=url2+"user_id="+StaticFile.userId;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url2,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle success response from the server
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
    private void getEducationDetails() {

        String courseUrl=StaticFile.Url+"/api/v1/cil/board/get/all";
        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonObjectRequest2 = new JsonArrayRequest(Request.Method.GET, courseUrl,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle success response from the server
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);

                                object.getString("board_name");
                                object.getString("board_id");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
        requestQueue2.add(jsonObjectRequest2);
    }
}