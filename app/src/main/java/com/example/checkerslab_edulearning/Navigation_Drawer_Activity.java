package com.example.checkerslab_edulearning;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.example.checkerslab_edulearning.NavigationDrawerPkg.PerformanceFragment;
import com.example.checkerslab_edulearning.ProfilePackage.ProfileFragment;
import com.example.checkerslab_edulearning.commonActivityPackage.PrivacyPolicyScreen;
import com.example.checkerslab_edulearning.databinding.ActivityNavigationDrawerBinding;
import com.example.checkerslab_edulearning.subscription.User_Subscription_screen;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Navigation_Drawer_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ActivityNavigationDrawerBinding binding;

    private RelativeLayout editButton;
    private RelativeLayout laterButton;
    Dialog dialog;
   public static  String BearerToken="";
    private TextView userName,emailId,mobileNo,courseName;
    private ImageView userProfileImage;
    public static  String studName="",studEmailId="",studMobileNo="",studProfileImage="";
    public static String studCourseName="";
   // public static String userId="100001";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BearerToken=StaticFile.bearToken;

        //**************** edit profile Dialog box

        dialog = new Dialog(Navigation_Drawer_Activity.this);
        dialog.setContentView(R.layout.add_profile_dailog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.show();

        editButton=dialog.findViewById(R.id.edit_profile_pop_up_edit_id);
        laterButton=dialog.findViewById(R.id.edit_profile_pop_up_later_id);

        laterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

     //********************************dialog end***********************************************


        if (savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id,new Home_Screen_Fragment()).commit();
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

///////////////  Nav Header View  /////////////////////////////////////////////////
        View hView = navigationView.getHeaderView(0);
        userName=(TextView) hView.findViewById(R.id.nav_student_name_id);
        emailId=hView.findViewById(R.id.nav_student_email_id);
        mobileNo=hView.findViewById(R.id.nav_student_mobileNo_id);
        courseName=hView.findViewById(R.id.nav_student_CourseNo_id);
        userProfileImage=hView.findViewById(R.id.User_profile_image_id);



//

        getUserDetails();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation__drawer_, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:

                Home_Screen_Fragment homeFragment=new Home_Screen_Fragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_id,homeFragment);
                transaction.commit();
                break;

            case R.id.nav_subscription_id:

                User_Subscription_screen subscriptionFragment=new User_Subscription_screen();
                FragmentTransaction transaction1=getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.content_id,subscriptionFragment);
                transaction1.commit();
                break;


            case R.id.nav_profile:

                ProfileFragment profileFragment=new ProfileFragment();
                FragmentTransaction transaction2=getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.content_id,profileFragment);
                transaction2.commit();
                break;

            case R.id.nav_performance:

                PerformanceFragment performanceFragment=new PerformanceFragment();
                FragmentTransaction transaction3=getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.content_id,performanceFragment);
                transaction3.commit();
                break;

            case R.id.nav_log_out:

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Create an Intent to navigate to the LoginActivity
                Intent intent = new Intent(Navigation_Drawer_Activity.this, Welcome_Screen_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_privacy:
                PrivacyPolicyScreen privacyPolicyScreen=new PrivacyPolicyScreen();
                FragmentTransaction privacyPolicyTransaction=getSupportFragmentManager().beginTransaction();
                privacyPolicyTransaction.replace(R.id.content_id,privacyPolicyScreen);
                privacyPolicyTransaction.commit();
                break;


        }
        DrawerLayout layout=findViewById(R.id.drawer_layout);
        layout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getUserDetails() {
        String url=StaticFile.Url+"/api/v1/cil/users/get?"+"user_id="+StaticFile.userId;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ProfileStatus","success");
                        // Handle success response from the server
                        try {
                            studName = response.getString("first_name");
                            studEmailId=response.getString("email_id");
                            studMobileNo=response.getString("mobile_no");
//                            studCourseName=response.getString("standard_id");
                            studProfileImage=response.getString("profile_image_url");
                            // Display or handle the message as needed
                            setUserDetails(studName,studEmailId,studCourseName,studMobileNo,studProfileImage);
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
                            Log.d("ProfileStatus",errorMessage);
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

    private void setUserDetails(String studName, String studEmailId, String studCourseName, String studMobileNo, String studProfileImage)
    {


        Log.d("ProfileStatus",studName+","+studMobileNo);
        if (!(studName =="null"))
        {
            userName.setText(studName);
        }
        if (!(studMobileNo =="null"))
        {
            mobileNo.setText(studMobileNo);
        }
        if (!(studCourseName =="null"))
        {
            courseName.setText(studCourseName);
        }
        if (!(studProfileImage== "null"))
        {
            Glide.with(getApplicationContext())
                    .load(studProfileImage)
                    .fitCenter()
                    .into(userProfileImage);
        }
    }
}