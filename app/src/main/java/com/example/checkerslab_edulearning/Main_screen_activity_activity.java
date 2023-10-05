package com.example.checkerslab_edulearning;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.Manifest;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main_screen_activity_activity extends AppCompatActivity {

    // Button camera,wallpaper;
    ImageView image;
    Bitmap bitmap;
    TextView camera;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_activity);

        camera = findViewById(R.id.click);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkPermission= ContextCompat.checkSelfPermission(Main_screen_activity_activity.this, Manifest.permission.CAMERA);
                if (checkPermission==RESULT_OK)
                {
                    OpenCameraMethod();
                }
                else
                {
                    ActivityCompat.requestPermissions(Main_screen_activity_activity.this,new String[]{Manifest.permission.CAMERA},1);

                }
            }
        });



    }
    private void OpenCameraMethod() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Create a file to store the image
        File photoFile = createImageFile();

        if (photoFile != null) {
            imageUri = FileProvider.getUriForFile(this,
                    "com.example.cameraview.fileprovider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, 1);
        }
    }
    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    OpenCameraMethod();
                } else {
                    Toast.makeText(Main_screen_activity_activity.this, "You Don't have Permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

              //  image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}
