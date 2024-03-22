package com.MedhVrushti.checkerslab_edulearning;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.MedhVrushti.checkerslab_edulearning.myLearningPakage.MyLearningMainFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SubscriptionPaymentScreen extends AppCompatActivity {

    private EditText couponCodeEditText;
    private Button submitButton;
    private Dialog dialog;
    private Button StartLearningButton;
    private LoadingDialog loadingDialog;
    private ErrorStatusDialog errorStatusDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_payment_screen);
        couponCodeEditText=findViewById(R.id.CouponCode_EditText_Id);
        submitButton=findViewById(R.id.CouponCode_submitButton_Id);
        loadingDialog=new LoadingDialog(SubscriptionPaymentScreen.this);
        errorStatusDialog=new ErrorStatusDialog(SubscriptionPaymentScreen.this);

        //===================================== Dialog Box =======================================//
        dialog= new Dialog(SubscriptionPaymentScreen.this);
        dialog.setContentView(R.layout.dialog_message_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        StartLearningButton=dialog.findViewById(R.id.successful_layout_OkButton_id);
        StartLearningButton.setText("Start Learning");
        StartLearningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.payment_layout_id, new MyLearningMainFragment());
                fragmentTransaction.disallowAddToBackStack();
                fragmentTransaction.commit();
            }
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        //========================================================================================//
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String couponCode=couponCodeEditText.getText().toString();
                if (!couponCode.isEmpty())
                {
                    checkCouponCodeValidity(couponCode);
                }
                else
                {
                    couponCodeEditText.setError("Coupon Code Can't be Empty");
                    couponCodeEditText.requestFocus();
                    return;
                }
            }
        });
    }

    private void checkCouponCodeValidity(String couponCode)
    {
        loadingDialog.startLoadingDialog();
        String url=StaticFile.Url+"/api/v1/cil/special_discounts/check?coupon_code="+couponCode+"&subscription_id="+Course_Enroll_Activity.SubscriptionID;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        loadingDialog.dismissDialog();
                            try {
                                String couponStatus=response.getString("status");
                                String discountId=response.getString("discount_id");
                                String message=response.getString("message");

                                if (couponStatus=="Active"|| couponStatus.equals("Active"))
                                {
                                    AddUserSubscription(discountId);
                                }
                                else
                                {
                                    couponCodeEditText.setError("Coupon Code Expired!");
                                    couponCodeEditText.requestFocus();

                                }
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                                Toast.makeText(SubscriptionPaymentScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismissDialog();
                        if (error.networkResponse != null) {
                            couponCodeEditText.setError("Please Enter valid Code ");
                            couponCodeEditText.requestFocus();
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
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

    private void AddUserSubscription(String discountId) {
        String url=StaticFile.Url+"/api/v1/cil/user_subscriptions/add";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id", StaticFile.userId);
            requestData.put("subscription_id", Course_Enroll_Activity.SubscriptionID);
            requestData.put("standard_id", Course_Enroll_Activity.standard_id);
            if (!Course_Enroll_Activity.subject_id.equals("null"))
            {
                requestData.put("subject_id", Course_Enroll_Activity.subject_id);
            }
            requestData.put("discount_applied", "20%");
            requestData.put("attempt_allowed", "2");
            requestData.put("payment_id", StaticFile.samplePaymentId);
            requestData.put("total_validity", "90");
            requestData.put("auto_renewal", "false");
            requestData.put("status", "Active");
            requestData.put("subscription_date", date);
            requestData.put("access_end_date", StaticFile.todayDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String message = response.getString("message");
                            updatingCouponDetails(discountId);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismissDialog();
                        errorStatusDialog.showErrorMessage();
                        if (error.networkResponse != null) {

                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
                            Toast.makeText(SubscriptionPaymentScreen.this, errorMessage, Toast.LENGTH_SHORT).show();
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
    private void updatingCouponDetails(String discountId) {
        String url=StaticFile.Url+"/api/v1/cil/special_discounts/update?discount_id="+discountId;

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("status", "Used");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url,requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadingDialog.dismissDialog();
                        dialog.show();
                        Toast.makeText(SubscriptionPaymentScreen.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismissDialog();
                        errorStatusDialog.showErrorMessage();
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
                            Toast.makeText(SubscriptionPaymentScreen.this, errorMessage, Toast.LENGTH_SHORT).show();

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