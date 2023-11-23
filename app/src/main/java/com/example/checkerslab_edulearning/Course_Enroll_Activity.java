package com.example.checkerslab_edulearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.bumptech.glide.Glide;
import com.example.checkerslab_edulearning.myLearningPakage.MyLearningMainFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Course_Enroll_Activity extends AppCompatActivity {

    ImageView imageView;
    Button enroll;
    private String url="http://apis-medhvrushti.checkerslab.com/api/v1/cil/user_subscriptions/add";

    private static String subscription_code,subscription_name,subscription_type,subscription_category,standard_id,subject_id,
            access_id,subscription_price,description,default_discount,subscription_img_url;

    private TextView subscriptionNameT,subscriptionPriceT;
    static String SubscriptionID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_enroll);

        imageView=findViewById(R.id.Enroll_Course_Image_id);
        enroll=findViewById(R.id.Enroll_Course_button_id);
        subscriptionNameT=findViewById(R.id.Enroll_Subscription_Name_id);
        subscriptionPriceT=findViewById(R.id.Enroll_Subscription_price_id);

        Intent intent=getIntent();
        SubscriptionID=intent.getStringExtra("subscription_id");

        getDetails();



        TextView text1 = findViewById(R.id.text1);
        text1.setText("Important Due to Bug #69477, redo log writes for large, externally stored BLOB fields could overwrite the most recent checkpoint. To address this bug, a patch introduced in MySQL 5.6.20 limits the size of redo log BLOB writes to 10% of the redo log file size. As a result of this limit, innodb_log_file_size should be set to a value greater than 10 times the largest BLOB data size found in the rows of your tables plus the length of other variable");

          enroll.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  UpdateData();
              }
          });

    }

    private void getDetails() {



        String url2="http://apis-medhvrushti.checkerslab.com/api/v1/cil/main_subscriptions/get?";
      url2=url2+"subscription_id="+SubscriptionID;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url2,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle success response from the server
                        try {
                            subscription_code = response.getString("subscription_code");
                            subscription_name=response.getString("subscription_name");
                            subscription_type=response.getString("subscription_type");
                            subscription_category=response.getString("subscription_category");
                            standard_id=response.getString("standard_id");
                            subject_id=response.getString("subject_id");
                            access_id=response.getString("access_id");
                            subscription_price=response.getString("subscription_price");
                            description=response.getString("description");

                            default_discount=response.getString("default_discount");
                            subscription_img_url=response.getString("subscription_img_url");



                            subscriptionNameT.setText(subscription_name);
                            subscriptionPriceT.setText("₹"+subscription_price);

                            Glide.with(getApplicationContext())
                                    .load(subscription_img_url)
                                    .fitCenter()
                                    .into(imageView);
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
            //    headers.put("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaHViaGFtaGFkYXdhbGVAZ21haWwuY29tIiwiZXhwIjoxNzAwNjMyNDQ0fQ.-SEhCjYYLEf4dGW3e-PKv1da2KJd16ujIQu_vCcdNP4");
                return headers;
            }
        };


        requestQueue.add(jsonObjectRequest);



    }

    private void UpdateData() {

        Toast.makeText(Course_Enroll_Activity.this, "standard_id"+standard_id, Toast.LENGTH_SHORT).show();

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id", Navigation_Drawer_Activity.userId);
            requestData.put("subscription_id", SubscriptionID);
            requestData.put("standard_id", standard_id);
            if (!subject_id.equals("null"))
            {
                requestData.put("subject_id", subject_id);
            }
            requestData.put("discount_applied", "20%");
            requestData.put("attempt_allowed", "2");
            requestData.put("payment_id", "100001");
            requestData.put("total_validity", "2  months");
            requestData.put("auto_renewal", "true");
            requestData.put("status", "Active");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle success response from the server
                        try {
                            String message = response.getString("message");
                            //     Toast.makeText(MainActivity.this, "dddddddddd", Toast.LENGTH_SHORT).show();
                            System.out.println("Error Status Code: " + "Successfully added");
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

// Replace the container with the new fragment
                            fragmentTransaction.replace(R.id.Enroll_layout_id, new MyLearningMainFragment());



// Optionally, add the transaction to the back stack
                            fragmentTransaction.addToBackStack(null);

                            fragmentTransaction.commit();

                            // Display or handle the message as needed
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
                headers.put("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaHViaGFtaGFkYXdhbGVAZ21haWwuY29tIiwiZXhwIjoxNjk5NjIxMDc2fQ.dSB36WdBg6_oR3WUq7PMDyHHAvm7CCwV6wyUo-SsFo0");
                return headers;
            }
        };



        requestQueue.add(jsonObjectRequest);




    }
}