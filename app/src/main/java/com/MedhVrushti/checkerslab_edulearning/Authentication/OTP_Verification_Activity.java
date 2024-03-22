package com.MedhVrushti.checkerslab_edulearning.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.MedhVrushti.checkerslab_edulearning.ErrorStatusDialog;
import com.MedhVrushti.checkerslab_edulearning.LoadingDialog;
import com.MedhVrushti.checkerslab_edulearning.Navigation_Drawer_Activity;
import com.MedhVrushti.checkerslab_edulearning.R;
import com.MedhVrushti.checkerslab_edulearning.StaticFile;

import org.json.JSONException;
import org.json.JSONObject;

public class OTP_Verification_Activity extends AppCompatActivity {

    private ImageView back;
    private PinView pinView;
    private Button verify;
    String enteredPin="";
    String mobileNumber="";

    private TextView mobileNumberText,otpTimingText,resendButton;
    private CountDownTimer countDownTimer;
    private LoadingDialog loadingDialog;
    private ErrorStatusDialog errorStatusDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        loadingDialog=new LoadingDialog(OTP_Verification_Activity.this);
        errorStatusDialog=new ErrorStatusDialog(OTP_Verification_Activity.this);

        back=findViewById(R.id.Verify_OTP_arrow_back_id2);
        pinView=findViewById(R.id.PinView_id);
        verify=findViewById(R.id.Verify_button_id2);
        mobileNumberText=findViewById(R.id.verify_otp_mobileNumber_id);
        otpTimingText=findViewById(R.id.otp_timingText_id);
        resendButton=findViewById(R.id.otp_Resend_button_id);

        Intent intent=getIntent();
        mobileNumber=intent.getStringExtra("Mobile_number");
        mobileNumberText.setText("We've sent it on the number: "+mobileNumber);


        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendOTPMethode(mobileNumber);
            }

        });

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
                RegisteredUser();

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
                updateTimerDisplay(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                otpTimingText.setVisibility(View.GONE);
                resendButton.setVisibility(View.VISIBLE);

            }
        };
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
        loadingDialog.startLoadingDialog();

        String userRegisterURL= StaticFile.Url+"/api/v1/cil/user-auth/otp/verify?enter_otp="+enteredPin;

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("role_id", "100001");
            requestData.put("authentication_type", "mobile");
            requestData.put("mobile_number", "91"+mobileNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, userRegisterURL, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadingDialog.dismissDialog();
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
                        loadingDialog.dismissDialog();
                       // errorStatusDialog.showErrorMessage();
                        // Handle error response
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
                            Toast.makeText(OTP_Verification_Activity.this,"OTP Not Verified.Please Try Again", Toast.LENGTH_SHORT).show();
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

    private void resendOTPMethode(String mobileNumber) {
        loadingDialog.startLoadingDialog();

        String url="https://medhvrushti.checkerslab.com/api/v1/cil/user-auth/otp/send?mobile_number=91"+mobileNumber;
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadingDialog.dismissDialog();
                Toast.makeText(OTP_Verification_Activity.this, "OTP Re-Sent TO Entered Mobile Number", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(OTP_Verification_Activity.this,OTP_Verification_Activity.class);
                intent.putExtra("Mobile_number",String.valueOf(mobileNumber));
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismissDialog();
                errorStatusDialog.showErrorMessage();
                if (error.networkResponse != null) {
                    int statusCode = error.networkResponse.statusCode;
                    byte[] errorResponseData = error.networkResponse.data; // Error response data
                    String errorMessage = new String(errorResponseData); // Convert error data to string
                    Toast.makeText(OTP_Verification_Activity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
        requestQueue.add(request);
    }
}