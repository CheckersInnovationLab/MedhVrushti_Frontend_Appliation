package com.example.checkerslab_edulearning.CompetitivePkg;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.checkerslab_edulearning.AssessmentSection_pkg.Test_Reminder_activity;
import com.example.checkerslab_edulearning.R;

public class questStatusGridAdapter extends BaseAdapter {

    private int noOfQues;
    Context context;


    public questStatusGridAdapter(int noOfQues, Context context) {
        this.noOfQues = noOfQues;
        this.context = context;
    }

    @Override
    public int getCount() {
        return noOfQues;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View myView;

        myView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ques_status_sl_grid_layout,viewGroup,false);

//        if (view == null)
//        {
//            }
//        else
//        {
//          myView=view;
//        }

        TextView quesNo=myView.findViewById(R.id.ques_status_grid_quesNo_id);
       quesNo.setText(String.valueOf(i+1));
       // quesNo.setText(Test_Reminder_activity.testDataList.get(i).getStatus());


        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof CompetetiveAssessmentScreen) {
                    ((CompetetiveAssessmentScreen) context).gotoQuestion(i);
                }
            }
        });

        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

        switch (Test_Reminder_activity.testDataList.get(i).getStatus()){
            case "Not_Visited":
            {
                quesNo.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.Gray)));
                break;
            }
            case "UnAnswered":
            {
                quesNo.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.Red)));
                break;
            }
            case "Answered":
            {
                quesNo.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.Green)));
                break;
            }

            case "Preview":
            {
                quesNo.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myView.getContext(), R.color.Pink)));
                break;
            }
        }


        return myView;
    }
}
