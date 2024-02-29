package com.example.checkerslab_edulearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.checkerslab_edulearning.ProfilePackage.EducationalDetailsActivity;
import com.example.checkerslab_edulearning.myLearningPakage.MyLearningMainFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Course_Enroll_Activity extends AppCompatActivity {

    ImageView imageView;
    Button enroll;
    private String url=StaticFile.Url+"/api/v1/cil/user_subscriptions/add";

    private static String subscription_id,subscription_name,subscription_type,subscription_category,standard_id,subject_id,
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
                  AddUserPayment();
              }
          });
    }

    private void getDetails() {

      String url2=StaticFile.Url+"/api/v1/cil/main_subscriptions/get?";
      url2=url2+"subscription_id="+SubscriptionID;

      RequestQueue requestQueue = Volley.newRequestQueue(this);

      JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url2,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle success response from the server
                        try {
                            subscription_id = response.getString("subscription_id");
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
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    private void AddUserPayment() {
        String paymentUrl=StaticFile.Url+"/api/v1/cil/user_payments/add";

        Random r = new Random();
        int n = r.nextInt(45 - 28) + 28;

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id", StaticFile.userId);
            requestData.put("subscription_id", subscription_id);
            requestData.put("subscription_price", subscription_price);
            requestData.put("payment_amount", subscription_price);
            requestData.put("discount_applied", "100%");
            requestData.put("payment_status", "Completed");
            requestData.put("payment_method", "online");
            requestData.put("transaction_id", "254341sHDS365"+String.valueOf(n));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, paymentUrl,requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Toast.makeText(Course_Enroll_Activity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
                            String paymentIdL = response.getString("payment_id");
                            AddUserSubscription(paymentIdL);
                            Log.d("Payment Status",paymentIdL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Course_Enroll_Activity.this, "Payment Fail", Toast.LENGTH_SHORT).show();
                        // Handle error response
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            Log.d("error:",error.getMessage());
                            Log.d("Payment Status",errorMessage);
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
    private void AddUserSubscription(String paymentId) {

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id", StaticFile.userId);
            requestData.put("subscription_id", SubscriptionID);
            requestData.put("standard_id", standard_id);
            if (!subject_id.equals("null"))
            {
                requestData.put("subject_id", subject_id);
            }
            requestData.put("discount_applied", "20%");
            requestData.put("attempt_allowed", "2");
            requestData.put("payment_id", paymentId);
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

                        Toast.makeText(Course_Enroll_Activity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                        // Handle success response from the server
                        try {
                            String message = response.getString("message");
                            Log.d("User_Subscription_Status",message);
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.Enroll_layout_id, new MyLearningMainFragment());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
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
}