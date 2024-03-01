package com.example.checkerslab_edulearning.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.example.checkerslab_edulearning.Navigation_Drawer_Activity;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.commonActivityPackage.assessmentHome.Assessment_home_Screen;
import com.example.checkerslab_edulearning.storePackage.StoreCoursesModel;
import com.example.checkerslab_edulearning.storePackage.storeCoursesParentAdapter;
import com.example.checkerslab_edulearning.storePackage.storeCoursesParentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OTP_Verification_Activity extends AppCompatActivity {

    private ImageView back;
    private PinView pinView;
    private Button verify;
    String enteredPin="";
    String generated_otp,mobileNumber="";
    private  String userRegisterURL= StaticFile.Url+"/api/v1/cil/user-auth/authenticate";
    private TextView mobileNumberText,otpTimingText,resendButton;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        back=findViewById(R.id.Verify_OTP_arrow_back_id2);
        pinView=findViewById(R.id.PinView_id);
        verify=findViewById(R.id.Verify_button_id2);
        mobileNumberText=findViewById(R.id.verify_otp_mobileNumber_id);
        otpTimingText=findViewById(R.id.otp_timingText_id);
        resendButton=findViewById(R.id.otp_Resend_button_id);

        Intent intent=getIntent();
        generated_otp=intent.getStringExtra("Generated_otp").toString();
        mobileNumber=intent.getStringExtra("Mobile_number");
        mobileNumberText.setText("We've sent it on the number: "+mobileNumber);

        setOTPTime();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pin="";
                pin=pin+s;
                enteredPin=pin;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (generated_otp.equals(enteredPin)){
                    RegisteredUser();
                }
                else {
                    Toast.makeText(OTP_Verification_Activity.this, "OTP doesn't matched", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setOTPTime() {

        otpTimingText.setVisibility(View.VISIBLE);
        resendButton.setVisibility(View.GONE);
        long totalTimeInMillis = 1 * 60 * 1000;

        countDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display on each tick
                updateTimerDisplay(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                // Handle the timer finish event (e.g., quiz submission)
                //otpTimingText.setText("Timer: 00:00");
                // Add any actions to perform when the timer finishes
                otpTimingText.setVisibility(View.GONE);
                resendButton.setVisibility(View.VISIBLE);

            }
        };

        // Start the countdown timer
        countDownTimer.start();
    }

    private void updateTimerDisplay(long millisUntilFinished) {
        // Convert milliseconds to minutes and seconds
        int minutes = (int) (millisUntilFinished / 1000) / 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;

        // Format the time and update the TextView
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        otpTimingText.setText("Resend OTP in " + timeLeftFormatted+"s");
    }
    private void RegisteredUser() {

        Log.d("mobile number",mobileNumber);

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("role_id", "100001");
            requestData.put("authentication_type", "mobile");
            requestData.put("mobile_number", "+91"+mobileNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, userRegisterURL, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Handle success response from the server
                        try {
                            String token = response.getString("jwt_token");
                            String userId=response.getString("user_id");
                            StaticFile.bearToken=token;
                            StaticFile.userId=userId;

                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userId", userId);
                            editor.putString("token", token);
                            editor.apply();

                            Toast.makeText(OTP_Verification_Activity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(OTP_Verification_Activity.this, Navigation_Drawer_Activity.class);
                            startActivity(intent);
                             finish();

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