package com.MedhVrushti.checkerslab_edulearning;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.MedhVrushti.checkerslab_edulearning.Authentication.Enter_Mob_Number_activity;

public class Welcome_Screen_Activity extends AppCompatActivity {

    private Button getStarted;
    private Dialog dialog;
    private LoadingDialog loadingDialog;
    private ErrorStatusDialog errorStatusDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        loadingDialog=new LoadingDialog(Welcome_Screen_Activity.this);
        errorStatusDialog=new ErrorStatusDialog(Welcome_Screen_Activity.this);
        getStarted=findViewById(R.id.welcome_screen_getStarted_button_id);
        dialog=new Dialog(this);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("userId", "");
                String token = sharedPreferences.getString("token", "");
                if (!userId.isEmpty() && !token.isEmpty()) {
                    StaticFile.bearToken=token;
                    StaticFile.userId=userId;
                    Intent intent=new Intent(getApplicationContext(), Navigation_Drawer_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent=new Intent(Welcome_Screen_Activity.this, Enter_Mob_Number_activity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
    }

}