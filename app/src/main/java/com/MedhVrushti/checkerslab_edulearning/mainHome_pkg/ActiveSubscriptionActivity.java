package com.MedhVrushti.checkerslab_edulearning.mainHome_pkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.MedhVrushti.checkerslab_edulearning.R;

public class ActiveSubscriptionActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private  ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_subscription);
        recyclerView=findViewById(R.id.Active_Subscription_recyclerView_id);
        backButton=findViewById(R.id.Active_subscription_back_id);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ActiveSubscriptionAdapter adapter = new ActiveSubscriptionAdapter(Home_sub_screen_fragment.activeSubscriptionList, getApplicationContext());
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }
}