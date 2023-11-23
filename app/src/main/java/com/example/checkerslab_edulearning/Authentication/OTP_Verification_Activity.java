package com.example.checkerslab_edulearning.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    private  String registerURL="http://apis-medhvrushti.checkerslab.com/api/v1/cil/auth/authenticate/by/mobile_no?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        back=findViewById(R.id.Verify_OTP_arrow_back_id2);
        pinView=findViewById(R.id.PinView_id);
        verify=findViewById(R.id.Verify_button_id2);

        Intent intent=getIntent();
        generated_otp=intent.getStringExtra("Generated_otp").toString();
        mobileNumber=intent.getStringExtra("Mobile_number");

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
             //    Toast.makeText(OTP_Verification_Activity.this, pin, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OTP_Verification_Activity.this, enteredPin, Toast.LENGTH_SHORT).show();
                if (generated_otp.equals(enteredPin)){
                    RegisteredUser();
//                   Intent intent=new Intent(OTP_Verification_Activity.this, Navigation_Drawer_Activity.class);
//                   startActivity(intent);
//                   finish();
                }
                else {
                    Toast.makeText(OTP_Verification_Activity.this, "OTP doesn't matched", Toast.LENGTH_SHORT).show();
                }
            }


        });

    }

    private void RegisteredUser() {
        registerURL=registerURL+"mobile_number="+mobileNumber+"&role_id=100002";



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, registerURL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Retrieve the token from the JSON response
                            String token = response.getString("token");
                        //    Toast.makeText(OTP_Verification_Activity.this, token, Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(OTP_Verification_Activity.this, Navigation_Drawer_Activity.class);
                            intent.putExtra("Bearer_token",token);
                   startActivity(intent);
                   finish();
                            // Use the token as needed for your application logic
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle the JSON Exception here
                        }
                    }
                },new Response.ErrorListener() {
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