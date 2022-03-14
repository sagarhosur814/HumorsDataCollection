package com.example.humorsdatacollection;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

class ViewDialog {

    private Activity activity;
    private AlertDialog alertDialog;

    ViewDialog(Activity myactivity)
    {
        activity= myactivity;
    }

    void startLoadingDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_dialog,null));


        builder.setCancelable(false);


        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dailogstyle);

        alertDialog.show();
        alertDialog.getWindow().setLayout(200, 200);
    }


    void dismisDialog()
    {
        alertDialog.dismiss();
    }
}

