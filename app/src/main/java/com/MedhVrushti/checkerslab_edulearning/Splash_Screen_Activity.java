package com.MedhVrushti.checkerslab_edulearning;

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
}