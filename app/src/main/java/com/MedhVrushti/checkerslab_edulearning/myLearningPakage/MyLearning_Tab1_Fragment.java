package com.MedhVrushti.checkerslab_edulearning.myLearningPakage;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.MedhVrushti.checkerslab_edulearning.R;
import com.MedhVrushti.checkerslab_edulearning.storePackage.StoreAllCoursesScreen;

public class MyLearning_Tab1_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager verticalLayout;
    private MyLearningMainAdapter myLearningMainAdapter;
    private CardView emptyMessageCard;
    private RelativeLayout enrollButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_learning__tab1_, container, false);
        emptyMessageCard=view.findViewById(R.id.MyLearning_empty_Message_details2_id);
        enrollButton=view.findViewById(R.id.MyLearning_EnrollButton_id);


        enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), StoreAllCoursesScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        recyclerView=view.findViewById(R.id.MyLearningMainRecyclerView_id);
        verticalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        //set adapter to recycler view
        recyclerView.setLayoutManager(verticalLayout);

        if (MyLearningMainFragment.myLearningCoursesList.size()>0)
        {
            emptyMessageCard.setVisibility(View.GONE);
            myLearningMainAdapter = new MyLearningMainAdapter(MyLearningMainFragment.myLearningCoursesList, getContext());
            recyclerView.setAdapter(myLearningMainAdapter);
        }


        return view;

    }

}