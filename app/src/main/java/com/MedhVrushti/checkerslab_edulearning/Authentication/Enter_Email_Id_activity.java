package com.MedhVrushti.checkerslab_edulearning.Authentication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.MedhVrushti.checkerslab_edulearning.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Enter_Email_Id_activity extends AppCompatActivity {


    private ImageView arrowBack;
    private TextView mobileButton;
    private Button emailContinue;
    int code;
    private EditText emailId;
    String Url="http://192.168.50.67:9191/api/v1/cil/email/send-otp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_email_id);


        mobileButton=findViewById(R.id.mobile_login_text_id);
        emailContinue=findViewById(R.id.Auth_mailId_Continue_button_id2);
        emailId=findViewById(R.id.Auth_Email_editeText_id);
        mobileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Enter_Email_Id_activity.this,Enter_Mob_Number_activity.class);
                startActivity(intent);
                finish();
            }
        });
        arrowBack=findViewById(R.id.Enter_number_arrow_back_id2);
        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        emailContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailOTP();

            }

            private void sendEmailOTP() {

                Random random=new Random();
                code=random.nextInt(899966)+1000;


                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                StringRequest request=new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Intent intent=new Intent(MainActivity.this,VerifyOTP.class);
//                        intent.putExtra("Otp",String.valueOf(code));
//                        startActivity(intent);
                        Toast.makeText(Enter_Email_Id_activity.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Enter_Email_Id_activity.this,OTP_Verification_Activity.class);
                        intent.putExtra("Generated_otp",String.valueOf(code));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Enter_Email_Id_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> map=new HashMap<>();
                        map.put("email",emailId.getText().toString());
                        map.put("otp",String.valueOf(code));
                        return map;
                    }
                };
                requestQueue.add(request);
            }
        });

    }
}