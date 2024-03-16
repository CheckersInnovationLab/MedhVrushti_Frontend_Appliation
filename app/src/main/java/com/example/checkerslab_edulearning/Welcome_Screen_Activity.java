package com.example.checkerslab_edulearning;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.Authentication.Enter_Mob_Number_activity;
import com.example.checkerslab_edulearning.Authentication.OTP_Verification_Activity;

public class Welcome_Screen_Activity extends AppCompatActivity {

    private Button getStarted;
    private Dialog dialog;
    private String mobileNumber;
    private LoadingDialog loadingDialog;
    private ErrorStatusDialog errorStatusDialog;

//    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        loadingDialog=new LoadingDialog(Welcome_Screen_Activity.this);
        errorStatusDialog=new ErrorStatusDialog(Welcome_Screen_Activity.this);
        getStarted=findViewById(R.id.welcome_screen_getStarted_button_id);
        dialog=new Dialog(this);

        String[] permission={
                android.Manifest.permission.READ_PHONE_NUMBERS
        };

        requestPermissions(permission,102);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("userId", "");
                String token = sharedPreferences.getString("token", "");
                if (!userId.isEmpty() && !token.isEmpty()) {
                    StaticFile.bearToken=token;
                    StaticFile.userId=userId;

                    Toast.makeText(Welcome_Screen_Activity.this, "Already Login", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(), Navigation_Drawer_Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    getAuthenticationDetails();
                }
            }
        });
    }
    private void getAuthenticationDetails() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(Welcome_Screen_Activity.this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Welcome_Screen_Activity.this,android.Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Welcome_Screen_Activity.this,android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mobileNumber=telephonyManager.getLine1Number();
        if (mobileNumber.startsWith("+91")) {
            mobileNumber = mobileNumber.substring(3);
        } else if (mobileNumber.length() > 10) {
            mobileNumber = mobileNumber.substring(mobileNumber.length() - 10);
        }


        String country = telephonyManager.getSimCountryIso();
        showAuthenticationWindow();
    }
    private void showAuthenticationWindow() {
        dialog.setContentView(R.layout.access_mobile_number);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        Button button=dialog.findViewById(R.id.verify_number_button);
        TextView useAnMeth=dialog.findViewById(R.id.use_another_methode_text_id);
        button.setText("Continue with"+" "+mobileNumber);
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendOTP(mobileNumber);
            }
        });
        useAnMeth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Welcome_Screen_Activity.this, Enter_Mob_Number_activity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

    }
    private void SendOTP(String mobileNumber) {
        loadingDialog.startLoadingDialog();

        String url="https://medhvrushti.checkerslab.com/api/v1/cil/user-auth/otp/send?mobile_number=91"+mobileNumber;
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               loadingDialog.dismissDialog();
                Toast.makeText(Welcome_Screen_Activity.this, "OTP Sent TO Entered Mobile Number", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Welcome_Screen_Activity.this,OTP_Verification_Activity.class);
                intent.putExtra("Mobile_number",String.valueOf(mobileNumber));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismissDialog();
                errorStatusDialog.showErrorMessage();
                Toast.makeText(Welcome_Screen_Activity.this, "Error! Please Check Internet Connection Or Restart Activity", Toast.LENGTH_SHORT).show();
                if (error.networkResponse != null) {
                    int statusCode = error.networkResponse.statusCode;
                    byte[] errorResponseData = error.networkResponse.data; // Error response data
                    String errorMessage = new String(errorResponseData); // Convert error data to string
                }
            }
        });
        requestQueue.add(request);
    }
}