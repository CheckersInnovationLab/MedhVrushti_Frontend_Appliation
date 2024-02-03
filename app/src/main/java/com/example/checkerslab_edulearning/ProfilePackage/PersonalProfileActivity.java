package com.example.checkerslab_edulearning.ProfilePackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.UploadAnswerpackage.AnswerImagedModel;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.UploadAnswerpackage.AnswerImagesAdapter;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.UploadAnswerpackage.Upload_Answer_Activity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalProfileActivity extends AppCompatActivity {

    EditText userName,userMobNo,userWhNo,userEmailId,userGender,userAddress,parentMobNo,userProfileImg;
    CircleImageView profileImageView;
    TextView update,userBirthDate;
    LinearLayout userBirthDateLayout;
    RadioGroup radioGroupGender;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private static final int IMAGE_PICKCAMERA_REQUEST = 500;
    String cameraPermission[];
    String storagePermission[];
    Uri imageuri;
    Bitmap bitmap;
    String selectedImgUrl="",selectedBirthDate="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);

        //initialization
        userName=findViewById(R.id.edit_studName_id);
        userMobNo=findViewById(R.id.edit_studMobile_id);
        userWhNo=findViewById(R.id.edit_studWhatsAppNo_id);
        userEmailId=findViewById(R.id.edit_studEmail_id);
        userBirthDate=findViewById(R.id.edit_BirthDate_id);
        userAddress=findViewById(R.id.edit_address_id);
        parentMobNo=findViewById(R.id.edit_parentMob_id);
        update=findViewById(R.id.edit_personal_details_update_id);
        profileImageView=findViewById(R.id.User_profile_image_id);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        userBirthDateLayout=findViewById(R.id.edit_BirthDate_layout_id);

        ///////////////
        userName.setEnabled(true);
        userMobNo.setEnabled(true);
        userEmailId.setEnabled(true);
        userWhNo.setEnabled(true);
        parentMobNo.setEnabled(true);
        userAddress.setEnabled(true);
        ///////////////

       //fetch Profile Data from database and show on layout
        FetchProfileData();

        //set on click listen on profile image view
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // allowing permissions of gallery and camera
                cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

                //get profile Image
                showImagePicDialog();
            }
        });

        //select date of birth
        userBirthDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("select Dated ddddddddddddddddd","date");
                final Calendar c = Calendar.getInstance();

// on below line we are getting
// our day, month, and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

// on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        PersonalProfileActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                // create a Calendar instance and set it to the selected date
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(year, monthOfYear, dayOfMonth);

                                // create a SimpleDateFormat for the desired format
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                                // format the selected date and set it to your text view.
                                selectedBirthDate = dateFormat.format(selectedDate.getTime());
                                userBirthDate.setText(selectedBirthDate);
                            }
                        },
                        // on below line we are passing year,
                        // month and day for the selected date in our date picker.
                        year, month, day);

// show the date picker dialog.
                datePickerDialog.show();
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //gender radio button
                int selectedRadioButtonId = radioGroupGender.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String selectedGender = selectedRadioButton.getText().toString();


                String usernameL=userName.getText().toString();
                String userMobNoL=userMobNo.getText().toString();
                String userEmailIdL=userEmailId.getText().toString();
                String userWhNoL=userWhNo.getText().toString();
                String parentMobNoL=parentMobNo.getText().toString();
                String userAddressL=userAddress.getText().toString();

                updateProfile(selectedImgUrl,usernameL,userMobNoL,userEmailIdL,userWhNoL,parentMobNoL,selectedBirthDate,selectedGender,userAddressL);
            }

        });
    }

    private void FetchProfileData() {

        String url2= StaticFile.Url+"/api/v1/cil/users/get?";
        url2=url2+"user_id="+StaticFile.userId;

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url2,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle success response from the server
                        try {
                           String mobileNumber  = response.getString("mobile_no");
                            String name  = response.getString("first_name");
                            String profileImage  = response.getString("profile_image_url");
                            String whatsappNo  = response.getString("user_whatsapp_no");
                            String emailID  = response.getString("email_id");
                            String birthDate  = response.getString("date_of_birth");
                            String gender  = response.getString("gender");
                            String address  = response.getString("address");
                            String parentMobNumber  = response.getString("parents_contact_no");


                            showUserDetails(mobileNumber,name,profileImage,whatsappNo,emailID,birthDate,gender,address,parentMobNumber);

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
                headers.put("Authorization", "Bearer " + StaticFile.bearToken);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void showUserDetails(String mobileNumber, String name, String profileImage, String whatsappNo, String emailID, String birthDate, String gender, String address, String parentMobNumber)
    {

        if (!(name=="null"))
        {
            userName.setText(name);
        }
        if (!(mobileNumber=="null"))
        {
            userMobNo.setText(mobileNumber);
        }
        if (!(emailID=="null"))
        {
            userEmailId.setText(emailID);
        }
        if (!(whatsappNo=="null"))
        {
            userWhNo.setText(whatsappNo);
        }
        if (!(parentMobNumber =="null"))
        {
            parentMobNo.setText(parentMobNumber);
        }
        if (!(address =="null"))
        {
            userAddress.setText(address);
        }
        if (!(birthDate =="null"))
        {
            userBirthDate.setText(birthDate);
        }
//        if (!(gender =="null"))
//        {
//            radio1.setChecked(true);
//        }
        if (!(profileImage== "null"))
        {
            Glide.with(getApplicationContext())
                    .load(profileImage)
                    .fitCenter()
                    .into(profileImageView);
        }
    }
    private void updateProfile(String selectedImgUrl, String usernameL, String userMobNoL, String userEmailIdL, String userWhNoL, String parentMobNoL, String selectedBirthDate, String selectedGender, String userAddressL) {

        String updateProfileUrl=StaticFile.Url+"/api/v1/cil/users/update?userId="+StaticFile.userId+"&roleId=100001";


        Map<String, String> params = new HashMap<>();
        params.put("firstName",usernameL );
        params.put("mobileNo",userMobNoL );
        params.put("userWhatsappNo",userWhNoL );
        params.put("emailId",userEmailIdL );
        params.put("dateOfBirth", selectedBirthDate );
        params.put("gender",selectedGender );
        params.put("address",userAddressL );
        params.put("parentsContactNo",parentMobNoL );
        params.put("createdBy","Medhvrushti Mobile App" );
        //params.put("createdBy","Medhvrushti Mobile App" );profile_status
        //params.put("roleId","100001" );

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String boundary = "boundary_" + UUID.randomUUID().toString();

        Bitmap finalBitmap = bitmap;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, updateProfileUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        Toast.makeText(PersonalProfileActivity.this, "Response: " + response, Toast.LENGTH_LONG).show();
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



                Uri imageUri = Uri.parse(selectedImgUrl); // Assuming AnswerImagedModel has a method named getUriString
                Bitmap imageBitmap = null;
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(PersonalProfileActivity.this.getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                // Add each image file to the request body
                writer.append("--").append(boundary).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"profileImgUrl\"; filename=\"image_" + 1 + ".jpg\"\r\n");
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


                writer.append("--").append(boundary).append("--").append("\r\n");
                writer.flush();

                return outputStream.toByteArray();
            }



            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", getBodyContentType());
                headers.put("Authorization", "Bearer " + StaticFile.bearToken);
                return headers;
            }
        };
        requestQueue.add(stringRequest);


//        int selected=rGroup.getCheckedRadioButtonId();
//        Log.d("selected",String.valueOf(selected));
//        RadioButton radio=(RadioButton) findViewById(selected);
//        Toast.makeText(PersonalProfileActivity.this,"You selected : "+radio.getText(),Toast.LENGTH_LONG).show();
//
    }


    //////////////////////profile image functionality///////////////////////////////////////////////////

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
                        ActivityCompat.requestPermissions(PersonalProfileActivity.this,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST);
                        ActivityCompat.requestPermissions(PersonalProfileActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_REQUEST);
                    }
                } else if (which == 1) {
                    if (checkStoragePermission())
                    {
                        pickFromGallery();
                    }
                    else {
                        ActivityCompat.requestPermissions(PersonalProfileActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_REQUEST);
                    }
                }
            }
        });
        builder.create().show();
    }

    private Boolean checkCameraPermission() {
        int result= ContextCompat.checkSelfPermission(PersonalProfileActivity.this, Manifest.permission.CAMERA);
        int result1= ContextCompat.checkSelfPermission(PersonalProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result==RESULT_OK && result1==RESULT_OK;
    }
    private Boolean checkStoragePermission() {
        int result= ContextCompat.checkSelfPermission(PersonalProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result==RESULT_OK;
    }
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
        CropImage.activity().start(PersonalProfileActivity.this);
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
                selectedImgUrl=resultUri.toString();
                Log.d("selected Image",resultUri.toString());
                  Glide.with(getApplicationContext())
                    .load(selectedImgUrl)
                    .fitCenter()
                    .into(profileImageView);


                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /////////////////////profile Image End////////////////////////////////////////////////////////////




}