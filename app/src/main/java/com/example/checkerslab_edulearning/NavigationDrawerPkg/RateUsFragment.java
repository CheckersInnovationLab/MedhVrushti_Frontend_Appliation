package com.example.checkerslab_edulearning.NavigationDrawerPkg;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RateUsFragment extends Fragment {


    private RatingBar ratingBar;
    private Button rateNowButton;
    private Dialog dialog;
    private Button dialogButton;
    private TextView dialogTitle,dialogMessage,rateMayBeLater;
    private ImageView dialogImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rate_us, container, false);

        ratingBar=view.findViewById(R.id.ratingBar_id);
        rateNowButton=view.findViewById(R.id.rateNowButton_id);
        rateMayBeLater=view.findViewById(R.id.rateMayBeLater_id);


        rateNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rating=String.valueOf(ratingBar.getRating());

                if (rating.isEmpty())
                {
                    Toast.makeText(getContext(), "Rating can't be empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    submitRating(rating);
                }

            }
        });
        rateMayBeLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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


    private void submitRating( String rating) {

        String url= StaticFile.Url+"/api/v1/cil/user_ratings/add";

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id", StaticFile.userId);
            requestData.put("rating", rating);
            requestData.put("created_by", "MedhVrushti App");
            requestData.put("status", "Active");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showSuccessMessage();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        showErrorMessage();

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
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


    private void showSuccessMessage() {
        dialogTitle.setText("Submitted");
        dialogMessage.setText("Thank for Rating Us! ");
        dialogImage.setImageResource(R.drawable.successfull_icon_3);
        dialogButton.setText("cancel");

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();

    }
    private void showErrorMessage() {
        dialogTitle.setText("Sorry!");
        dialogMessage.setText("There is some issue while submitting your data,Please try again and provide correct details");
        dialogImage.setImageResource(R.drawable.wrong_icon);
        dialogButton.setText("cancel");
        dialogTitle.setTextColor(Color.parseColor("#FF0000"));
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}