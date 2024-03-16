package com.example.checkerslab_edulearning.myLearningPakage;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.ErrorStatusDialog;
import com.example.checkerslab_edulearning.LoadingDialog;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.SubscriptionPaymentScreen;
import com.example.checkerslab_edulearning.mainHome_pkg.Home_sub_screen_fragment;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyLearningMainFragment extends Fragment {

    static public ArrayList<MyLeaningMainModel> myLearningCoursesList;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ProgressBar pb;
    private String userSubscriptionUrl= StaticFile.Url+"/api/v1/cil/user_subscriptions/get/all/by/user_id?";
    private Dialog dialog;
    private Button dialogButton;
    private TextView dialogTitle,dialogMessage;
    private ImageView dialogImage;
    LoadingDialog loadingDialog;
    ErrorStatusDialog errorStatusDialog;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_my_learning_main, container, false);
       tabLayout = view.findViewById(R.id.tabLayout);
       viewPager = view.findViewById(R.id.viewPager);

       loadingDialog=new LoadingDialog(getActivity());
       errorStatusDialog=new ErrorStatusDialog(getActivity());

       myLearningCoursesList=new ArrayList<>();
       AddItemsToTopCatRecyclerView();



       //===================================== Dialog Box =======================================//

       dialog= new Dialog(getContext());
       dialog.setContentView(R.layout.dialog_message_layout);
       dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       dialog.setCancelable(false);

       dialogTitle=dialog.findViewById(R.id.successful_layout_Title_id);
       dialogMessage=dialog.findViewById(R.id.successful_layout_message_id);
       dialogImage=dialog.findViewById(R.id.successful_layout_icon_id);

       dialogButton=dialog.findViewById(R.id.successful_layout_OkButton_id);
       dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

       //========================================================================================//







       return view;
    }

    private void AddItemsToTopCatRecyclerView() {
        loadingDialog.startLoadingDialog();


       Log.d("AddItemsToTopCatRecyclerView","IN");

        userSubscriptionUrl=userSubscriptionUrl+"user_id="+ StaticFile.userId;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, userSubscriptionUrl,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // Handle success response from the server
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                MyLeaningMainModel model=new MyLeaningMainModel(object.getString("user_subscription_id"),
                                        object.getString("user_id"),
                                        object.getString("subscription_id"),
                                        object.getString("standard_id"),
                                        object.getString("subscription_name"),
                                        object.getString("subscription_type"),
                                        object.getString("subscription_category"),
                                        object.getString("description"),
                                        object.getString("subscription_image"),
                                        object.getString("subscription_date"),
                                        object.getString("access_end_date"),
                                        object.getString("attribute1")
                                );
                                myLearningCoursesList.add(model);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                      //  pb.setVisibility(ProgressBar.GONE);
                        loadingDialog.dismissDialog();
                        setTabLayout();
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

                            //log for error
                            Log.d("userSubscriptionUrl Error Status Code: ", String.valueOf(statusCode));
                            Log.d(" userSubscriptionUrl Error Response Data: ", errorMessage);
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void setTabLayout() {
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getFragmentManager(), 0);
        viewPager.setAdapter(tabsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

//    private void showErrorMessage() {
//        dialogTitle.setText("Sorry!");
//        dialogMessage.setText("There is some issue while fetching your Data,Please try after some time");
//        dialogImage.setImageResource(R.drawable.wrong_icon);
//        dialogButton.setText("cancel");
//        dialogButton.setVisibility(View.GONE);
//        dialogTitle.setTextColor(Color.parseColor("#FF0000"));
//        dialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.cancel();
//            }
//        });
//        dialog.show();
//    }


}