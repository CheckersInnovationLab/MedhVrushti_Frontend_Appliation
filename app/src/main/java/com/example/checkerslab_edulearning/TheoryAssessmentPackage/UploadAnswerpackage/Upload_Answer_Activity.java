package com.example.checkerslab_edulearning.TheoryAssessmentPackage.UploadAnswerpackage;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.checkerslab_edulearning.Course_Enroll_Activity;
import com.example.checkerslab_edulearning.NavigationDrawerPkg.AssessmentOverviewAdapter;
import com.example.checkerslab_edulearning.NavigationDrawerPkg.AssessmentOverviewModel;
import com.example.checkerslab_edulearning.Navigation_Drawer_Activity;
import com.example.checkerslab_edulearning.R;


import ru.noties.jlatexmath.JLatexMathDrawable;
import ru.noties.jlatexmath.JLatexMathView;



/////////
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.commonActivityPackage.Assessment_Instruction_Activity;
import com.example.checkerslab_edulearning.commonActivityPackage.assessmentHome.Assessment_home_Screen;
import com.example.checkerslab_edulearning.myLearningPakage.MyLeaningMainModel;
import com.example.checkerslab_edulearning.myLearningPakage.MyLearningMainAdapter;
import com.example.checkerslab_edulearning.myLearningPakage.MyLearningMainFragment;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class Upload_Answer_Activity extends AppCompatActivity {

    JLatexMathView latexMathView;
    LinearLayout AddAnswerImage;
    ///////////selecting and uploading answer///////////////////
    ImageView answerImage;
    private static final int GalleryPick = 1;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGE_PICKCAMERA_REQUEST = 500;
    String cameraPermission[];
    String storagePermission[];
    Uri imageuri;
    TextView click;
    Bitmap bitmap;
    RelativeLayout uploadButton,submitTest;
    static String questionId="",questionmarks="";

    private RecyclerView recyclerView;
    ArrayList<AnswerImagedModel> ansImageList;
    LinearLayoutManager horizontalLayout;
    //////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_answer);
        latexMathView=findViewById(R.id.assessment_selected_question_id);

        Intent intent=getIntent();
        String question=intent.getStringExtra("questionData");
         questionId=intent.getStringExtra("question_id");
        questionmarks=intent.getStringExtra("marks");


        JLatexMathDrawable drawable = JLatexMathDrawable.builder("\\text{ "+question+"}")
                .textSize(150)
                .padding(2)
                .background(0xFFffffff)
                .align(JLatexMathDrawable.ALIGN_RIGHT)
                .build();
        latexMathView.setLatexDrawable(drawable);

/////////////////////////////////////////answer captured and upload///////////////////////////
        AddAnswerImage=findViewById(R.id.Add_answer_image_id);
          answerImage=findViewById(R.id.add_More_image_id);

        // allowing permissions of gallery and camera
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        AddAnswerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePicDialog();
            }
        });


        recyclerView=findViewById(R.id.Answer_images_recycler_id);
        horizontalLayout
                = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        recyclerView.setLayoutManager(horizontalLayout);
        ansImageList=new ArrayList<>();
        uploadButton=findViewById(R.id.Theory_Answer_Upload_button_id);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadData();
            }
        });
        submitTest=findViewById(R.id.Theory_Answer_Submit_Test_button_id);
        submitTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDailog();
            }

        });



/////////////////////////////////////////////////////

    }


    /////////////////////////////////////////start of answer captured and upload///////////////////////////
    ////show image pic diaglog
    private void showImagePicDialog() {
        String options[] = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {

                    if (checkCameraPermission())
                    {
                        pickFromCamera();
                    }
                    else {
                        ActivityCompat.requestPermissions(Upload_Answer_Activity.this,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST);
                        ActivityCompat.requestPermissions(Upload_Answer_Activity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_REQUEST);
                    }
                } else if (which == 1) {
                    if (checkStoragePermission())
                    {
                        pickFromGallery();
                    }
                    else {
                        ActivityCompat.requestPermissions(Upload_Answer_Activity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_REQUEST);
                    }
                }
            }
        });
        builder.create().show();
    }

    private Boolean checkStoragePermission() {
        int result= ContextCompat.checkSelfPermission(Upload_Answer_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result==RESULT_OK;
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void requestStoragePermission() {
//        requestPermissions(storagePermission, STORAGE_REQUEST);
//    }

    private Boolean checkCameraPermission() {
        int result= ContextCompat.checkSelfPermission(Upload_Answer_Activity.this, Manifest.permission.CAMERA);
        int result1= ContextCompat.checkSelfPermission(Upload_Answer_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result==RESULT_OK && result1==RESULT_OK;
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void requestCameraPermission() {
//        requestPermissions(cameraPermission, CAMERA_REQUEST);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0) {
                    boolean camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (camera_accepted && writeStorageaccepted) {
                        pickFromCamera();
                    } else {
                        Toast.makeText(this, "Please Enable Camera and Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST: {
                if (grantResults.length > 0) {
                    boolean writeStorageaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageaccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Please Enable Storage Permissions", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }
    }

    private void pickFromCamera() {
        openCamera();
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        imageuri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(cameraIntent, IMAGE_PICKCAMERA_REQUEST);
    }

    private void pickFromGallery() {
        CropImage.activity().start(Upload_Answer_Activity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICKCAMERA_REQUEST && resultCode == RESULT_OK) {
            CropImage.activity(imageuri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
//                    Picasso.get().load(resultUri).into(answerImage);
                ansImageList.add(new AnswerImagedModel(resultUri.toString()));
                AnswerImagesAdapter answerImagesAdapter=new AnswerImagesAdapter(ansImageList,getApplicationContext());
                recyclerView.setAdapter(answerImagesAdapter);

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void UploadData() {

        //String url="http://apis-medhvrushti.checkerslab.com/api/v1/cil/assessment_answers_data/add/multiple_images";
        String url="http://flask-medhvrushti.checkerslab.com/add-student-answer";
        Map<String, String> params = new HashMap<>();
        params.put("userId","100006" );//Navigation_Drawer_Activity.userId
        params.put("questionId", questionId);
        params.put("assessmentType", "TH");
        params.put("totalMarks", questionmarks);
        params.put("createdBy", "MedhVrshti_App");
        params.put("status", "active");
        params.put("checkingStatus", "active");
        params.put("timeTaken", "15 min");
        params.put("questionTypeId", "100027");
        params.put("assessmentId", "100005");
        params.put("bearerToken", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaHViaGFtaGFkYXdhbGVAZ21haWwuY29tIiwiZXhwIjoxNzAwNjc4MzYwfQ.nUuo8mD8RAMT5hOEMctzId-eakkIf3d-ijUvYr3cVwY");


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String boundary = "boundary_" + UUID.randomUUID().toString();

        Bitmap finalBitmap = bitmap;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        Toast.makeText(Upload_Answer_Activity.this, "Response: " + response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data;
                            String errorMessage = new String(errorResponseData);
                            Log.e("Error Status Code", String.valueOf(statusCode));
                            Log.e("Error Response Data", errorMessage);
                        }
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "multipart/form-data; boundary=" + boundary;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);

                // Add parameters to the request body
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    writer.append("--").append(boundary).append("\r\n");
                    writer.append("Content-Disposition: form-data; name=\"").append(entry.getKey()).append("\"\r\n");
                    writer.append("\r\n").append(entry.getValue()).append("\r\n");
                }

                // Loop over each image in the ansImageList and append it to the request body
                for (int i = 0; i < ansImageList.size(); i++) {
                    AnswerImagedModel imageModel = ansImageList.get(i);
                    Uri imageUri = Uri.parse(imageModel.getAnswerImgUrl()); // Assuming AnswerImagedModel has a method named getUriString
                    Bitmap imageBitmap;
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(Upload_Answer_Activity.this.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                        continue;  // Skip this image on error
                    }

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();

                    // Add each image file to the request body
                    writer.append("--").append(boundary).append("\r\n");
                    writer.append("Content-Disposition: form-data; name=\"answerImageUrls\"; filename=\"image_" + i + ".jpg\"\r\n");
                    writer.append("Content-Type: image/jpeg\r\n\r\n");
                    writer.flush(); // Important!

                    try {
                        outputStream.write(imageBytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    writer.append("\r\n"); // New line after image data
                }

                writer.append("--").append(boundary).append("--").append("\r\n");
                writer.flush();

                return outputStream.toByteArray();
            }



            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", getBodyContentType());
                //headers.put("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaHViaGFtaGFkYXdhbGVAZ21haWwuY29tIiwiZXhwIjoxNjk4MjI4MjQyfQ.vV_gS6QC8TKnyRMnGW556WnN0St1I3I6diCOtCNmsK4");
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void showDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Upload_Answer_Activity.this);
        builder.setMessage("Do you want to Submit Exam?");
        builder.setTitle("Confirmation !");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            finish();
            submitExamMethod();

        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

    private void submitExamMethod() {
        String url="http://apis-medhvrushti.checkerslab.com/api/v1/cil/user_assessments/update";


     //   Toast.makeText(Course_Enroll_Activity.this, "standard_id"+standard_id, Toast.LENGTH_SHORT).show();

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id","100006");//  Navigation_Drawer_Activity.userId
            requestData.put("assessment_id", "100005");
            requestData.put("time_taken", "1Hour");
            requestData.put("remarks", "Submitted");
            //requestData.put("ass_end_date", "2");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url,requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle success response from the server
                        try {
                            String message = response.getString("message");
                            System.out.println("Error Status Code: " + "Successfully Submitted");
                            sendForChecking();

                            // Display or handle the message as needed
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
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
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
                headers.put("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaHViaGFtaGFkYXdhbGVAZ21haWwuY29tIiwiZXhwIjoxNjk5NjIxMDc2fQ.dSB36WdBg6_oR3WUq7PMDyHHAvm7CCwV6wyUo-SsFo0");
                return headers;
            }
        };



        requestQueue.add(jsonObjectRequest);

    }




    private void sendForChecking() {

        String Url = "http://flask-medhvrushti.checkerslab.com/check-paper-with-chatgpt";
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id", "100006");
            requestData.put("assessment_id", "100005");
            requestData.put("bearer_token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWNoaW5AZ21haWwuY29tIiwiZXhwIjoxNzAwNzI0NTc2fQ.vsoRzq7dYzdxwqM-z5EJ4bippatbpYZm_O-1bZX1_Rc");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       // String message = response.getString("message");
                        System.out.println("Message: " + "Send for checking");
                        Toast.makeText(Upload_Answer_Activity.this, "Send for checking", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Upload_Answer_Activity.this, Assessment_home_Screen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
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
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
                            Intent intent = new Intent(Upload_Answer_Activity.this, Assessment_home_Screen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
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

///////////////////////////////////////end of captured and upload answer//////////////////////////
}