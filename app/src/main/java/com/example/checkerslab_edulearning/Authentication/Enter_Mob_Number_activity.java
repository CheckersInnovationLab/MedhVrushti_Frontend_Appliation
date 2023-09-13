package com.example.checkerslab_edulearning.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.checkerslab_edulearning.R;

public class Enter_Mob_Number_activity extends AppCompatActivity {

    private ImageView arrowBack;
    private Button mobContinue;
    private TextView emailButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mob_number);

        emailButton=findViewById(R.id.email_login_text_id);
        mobContinue=findViewById(R.id.Auth_MobNo_Continue_button_id);
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
                Intent intent=new Intent(Enter_Mob_Number_activity.this,OTP_Verification_Activity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}