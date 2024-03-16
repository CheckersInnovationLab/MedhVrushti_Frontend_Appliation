package com.example.checkerslab_edulearning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ErrorStatusDialog {


    Activity activity;
    AlertDialog dialog;

    public ErrorStatusDialog(Activity activity) {
        this.activity = activity;
    }

    public ErrorStatusDialog() {
    }

    public void showErrorMessage()
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.error_message_layout,null));
        builder.setCancelable(true);
        dialog=builder.create();
        dialog.show();

        Button cancelButton=dialog.findViewById(R.id.ErrorMessage_layout_OkButton_id);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




    }

}
