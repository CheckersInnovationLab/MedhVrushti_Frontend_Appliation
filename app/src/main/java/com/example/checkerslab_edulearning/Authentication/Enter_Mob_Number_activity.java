package com.example.checkerslab_edulearning.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkerslab_edulearning.R;
import com.hbb20.CountryCodePicker;

public class Enter_Mob_Number_activity extends AppCompatActivity {

    private ImageView arrowBack;
    private Button mobContinue;
    private TextView emailButton;
    private int code=123456;
    private EditText mobNumberView;
    private String mobileNumber;
    private CountryCodePicker codePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mob_number);

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
                //Toast.makeText(Enter_Mob_Number_activity.this, String.valueOf(mobileNumber.length()), Toast.LENGTH_SHORT).show();
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
                    Intent intent=new Intent(Enter_Mob_Number_activity.this,OTP_Verification_Activity.class);
                    intent.putExtra("Generated_otp",String.valueOf(code));
                    intent.putExtra("Mobile_number",String.valueOf(mobileNumber));
                    startActivity(intent);
                    finish();
                }

            }
        });



    }
}