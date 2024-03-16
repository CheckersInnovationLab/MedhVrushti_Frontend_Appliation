package com.example.checkerslab_edulearning.NavigationDrawerPkg;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContactUsFragment extends Fragment {



    private EditText userName,contactNumber,emailId,queryMessage;
    private Button submitButton;
    private Dialog dialog;
    private Button dialogButton;
    private TextView dialogTitle,dialogMessage;
    private ImageView dialogImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_contact_us, container, false);

        userName=view.findViewById(R.id.ContactUs_UserName_id);
        contactNumber=view.findViewById(R.id.ContactUs_ContactNumber_id);
        queryMessage=view.findViewById(R.id.ContactUs_UserQuery_id);
        submitButton=view.findViewById(R.id.ContactUs_SubmitButton_id);
        emailId=view.findViewById(R.id.ContactUs_EmailId_id);


        //===================================== Dialog Box =======================================//

        dialog= new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_message_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        dialogTitle=dialog.findViewById(R.id.successful_layout_Title_id);
        dialogMessage=dialog.findViewById(R.id.successful_layout_message_id);
        dialogImage=dialog.findViewById(R.id.successful_layout_icon_id);

        dialogButton=dialog.findViewById(R.id.successful_layout_OkButton_id);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        //========================================================================================//


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameS=userName.getText().toString();
                String contactNumberS=contactNumber.getText().toString();
                String queryMessageS=queryMessage.getText().toString();
                String emailIdS=emailId.getText().toString();

                if (userNameS.isEmpty())
                {
                    userName.setError("Please Enter  User Name");
                    userName.requestFocus();
                    return;                 }
                else if (contactNumberS.isEmpty())
                {
                    userName.setError("Please Enter Contact Number");
                    userName.requestFocus();
                    return;
                }
                else if (emailIdS.isEmpty())
                {
                    emailId.setError("Please Enter Contact Number");
                    emailId.requestFocus();
                    return;
                }

                else if (queryMessageS.isEmpty())
                {
                    queryMessage.setError("Please Enter Message");
                    queryMessage.requestFocus();
                    return;
                }
                else
                {
                    submitQueryMessage(userNameS,contactNumberS,emailIdS,queryMessageS);
                }
            }
        });

        return view;
    }

    private void submitQueryMessage( String userNameS, String contactNumberS,String emailIdS, String queryMessageS) {
        Log.d("submitQueryMessage","submitQueryMessage1");

        String url= StaticFile.Url+"/api/v1/cil/user_issues/add";

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id", StaticFile.userId);
            requestData.put("user_name", userNameS);
            requestData.put("user_message", queryMessageS);
            requestData.put("email_id", emailIdS);
            requestData.put("created_by", "MedhVrushti App");
            requestData.put("status", "Active");
            requestData.put("attribute1", contactNumberS);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showSuccessMessage();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        showErrorMessage();

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
//                            Log.d("error:",error.getMessage());
                            Log.d("submitQueryMessage",errorMessage);
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

    private void showSuccessMessage() {
        dialogTitle.setText("Submitted");
        dialogMessage.setText("Thank for contact Us! We are trying to reaching at you shortly.");
        dialogImage.setImageResource(R.drawable.successfull_icon_3);
        dialogButton.setText("Ok");

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();

    }
    private void showErrorMessage() {
        dialogTitle.setText("Sorry!");
        dialogMessage.setText("There is some issue while submitting your data,Please try again and provide correct details");
        dialogImage.setImageResource(R.drawable.wrong_icon);
        dialogButton.setText("cancel");
        dialogTitle.setTextColor(Color.parseColor("#FF0000"));
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

}