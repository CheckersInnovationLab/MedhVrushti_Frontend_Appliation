package com.example.checkerslab_edulearning.mainHome_pkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.commonActivityPackage.CourseSubjectAdapter;
import com.example.checkerslab_edulearning.commonActivityPackage.CourseSubjectModel;
import com.example.checkerslab_edulearning.commonActivityPackage.CourseSubjectsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TopCategoriesScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<TopCategoriesModel> catItemsList;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_categories_screen);
        recyclerView=findViewById(R.id.TopCategories_recyclerView_id);
        backButton=findViewById(R.id.TopCategories_back_button_id);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        catItemsList=new ArrayList<>();

        getAllTopCategories();
    }

    private void getAllTopCategories() {

        TopCategoriesAdapter topCategoriesAdapter=new TopCategoriesAdapter(Home_sub_screen_fragment.catItemsList,getApplicationContext());
        recyclerView.setAdapter(topCategoriesAdapter);

    }


}