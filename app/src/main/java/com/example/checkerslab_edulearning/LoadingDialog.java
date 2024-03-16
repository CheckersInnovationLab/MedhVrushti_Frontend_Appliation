package com.example.checkerslab_edulearning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadingDialog {

    Activity activity;
    AlertDialog dialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public LoadingDialog() {
    }

    public void startLoadingDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        View view=inflater.inflate(R.layout.custom_loading_bar_layout,null);
        builder.setView(view);
        view.setBackgroundColor(Color.TRANSPARENT);
        builder.setCancelable(true);
        dialog=builder.create();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.show();
    }
    public void dismissDialog()
    {
       dialog.dismiss();
    }

}
