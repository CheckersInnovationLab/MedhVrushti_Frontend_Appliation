package com.example.checkerslab_edulearning.Authentication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.ErrorStatusDialog;
import com.example.checkerslab_edulearning.LoadingDialog;
import com.example.checkerslab_edulearning.R;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class Enter_Mob_Number_activity extends AppCompatActivity {

    private ImageView arrowBack;
    private Button mobContinue;
    private TextView emailButton;
    private EditText mobNumberView;
    private String mobileNumber;
    private CountryCodePicker codePicker;
    private LoadingDialog loadingDialog;
    private ErrorStatusDialog errorStatusDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mob_number);

        loadingDialog=new LoadingDialog(Enter_Mob_Number_activity.this);
        errorStatusDialog=new ErrorStatusDialog(Enter_Mob_Number_activity.this);

        emailButton=findViewById(R.id.email_login_text_id);
        mobContinue=findViewById(R.id.Auth_MobNo_Continue_button_id);
        mobNumberView=findViewById(R.id.Mobile_number_editeText_id);
        codePicker=findViewById(R.id.country_code_picker);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Enter_Mob_Number_activity.this,Enter_Email_Id_activity.class);
                startActivity(intent);
            }
        });
        arrowBack=findViewById(R.id.Enter_number_arrow_back_id);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mobContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber=mobNumberView.getText().toString();
                int numberSize = mobileNumber.length();
                if ((mobileNumber.trim().equals("")))
                {
                    Toast.makeText(Enter_Mob_Number_activity.this, "Mobile Number can't be Empty", Toast.LENGTH_SHORT).show();

                }
                 else if (!(numberSize==10))
                {
                    Toast.makeText(Enter_Mob_Number_activity.this, "Please Enter valid Number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SendOTP(mobileNumber);
                }

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
                Toast.makeText(Enter_Mob_Number_activity.this, "OTP Sent TO Entered Mobile Number", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Enter_Mob_Number_activity.this,OTP_Verification_Activity.class);
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
                    Toast.makeText(Enter_Mob_Number_activity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
        requestQueue.add(request);
    }

}