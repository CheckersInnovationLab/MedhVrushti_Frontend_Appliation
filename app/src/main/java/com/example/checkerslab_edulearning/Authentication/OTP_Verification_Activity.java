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

import com.chaos.view.PinView;
import com.example.checkerslab_edulearning.Navigation_Drawer_Activity;
import com.example.checkerslab_edulearning.R;

public class OTP_Verification_Activity extends AppCompatActivity {

    private ImageView back;
    private PinView pinView;
    private Button verify;
    String enteredPin="";
    String generated_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        back=findViewById(R.id.Verify_OTP_arrow_back_id2);
        pinView=findViewById(R.id.PinView_id);
        verify=findViewById(R.id.Verify_button_id2);

        Intent intent=getIntent();
        generated_otp=intent.getStringExtra("Generated_otp").toString();

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
                   Intent intent=new Intent(OTP_Verification_Activity.this, Navigation_Drawer_Activity.class);
                   startActivity(intent);
                   finish();
                }
                else {
                    Toast.makeText(OTP_Verification_Activity.this, "OTP doesn't matched", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}