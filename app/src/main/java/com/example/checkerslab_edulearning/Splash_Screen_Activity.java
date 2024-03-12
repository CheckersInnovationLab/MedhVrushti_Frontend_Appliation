package com.example.checkerslab_edulearning;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import java.util.HashMap;
import java.util.Map;

public class Splash_Screen_Activity extends AppCompatActivity {

    private TextView appName;
    private ImageView appImage;
    private Animation fromBottom,fromTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Thread thread=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(4000);
                }catch(Exception e)
                {
                    Toast.makeText(Splash_Screen_Activity.this, "e.getMessage()", Toast.LENGTH_SHORT).show();
                }
                finally {
                   // getOtp();
                    Intent intent=new Intent(Splash_Screen_Activity.this,Welcome_Screen_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }



        };
        thread.start();

        appImage=findViewById(R.id.splash_screen_image_id);
        appName=findViewById(R.id.splash_screen_app_name_id);
        fromBottom= AnimationUtils.loadAnimation(this,R.anim.splash_from_bottom);
        fromTop=AnimationUtils.loadAnimation(this,R.anim.splash_from_top);
        appImage.setAnimation(fromTop);
        appName.setAnimation(fromBottom);
    }
    private void getOtp() {
        String paymentUrl="https://api.kaleyra.io/v1/HXIN1792863738IN/messages";


        final String to = "919307257088";
        final String type = "OTP";
        final String body = "OTP for Login:15655-Checkers Innovation Lab";
        final String sender="CILABS";
        final String template_id="1107170970692943346";


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, paymentUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(Splash_Screen_Activity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
                            // Process the response if needed
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Splash_Screen_Activity.this, "Payment Fail", Toast.LENGTH_SHORT).show();
                        // Handle error response
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                           // Log.d("error:", error.getMessage());
                            Log.d("Payment Status", errorMessage);
                        }
                    }
                }) {

            @Override
            public byte[] getBody() {
                // Set the raw body content
                String rawBody = "to=" + to + "&type=" + type + "&body=" + body + "&sender=" + sender;
                return rawBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("api-key", "A15111196e3deb33b24fc272bc");
                return headers;
            }
        };

        requestQueue.add(stringRequest);

    }
}