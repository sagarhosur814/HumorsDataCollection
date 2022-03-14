package com.example.humorsdatacollection;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import es.dmoral.toasty.Toasty;


public class MainActivity extends AppCompatActivity {

    boolean connection_status;
    RxBle rxBle ;
    private String todaysDate;
    private String DEVICE_NAME=null;
    ViewDialog viewDialog = new ViewDialog(MainActivity.this);

    private  Button btnConnect , btnDisconnect, takeReading;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rxBle = RxBle.getInstance();
        init();

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init() {
        statusbarcolorchange();
        init_vars();


        // connect
        tryToConnect();
        /// disconnect
        disConnect();
        /// get Current date and time
        getDateAndTime();

        // start reading
        startReading();

    }

    @Override
    protected void onStart() {
        super.onStart();
        // content
        content();
    }

    private void init_vars(){
        btnConnect = findViewById(R.id.btn_connect);
        btnDisconnect = findViewById(R.id.btn_disconnect);
        takeReading = findViewById(R.id.take_reading);
    }

    private void startReading(){
        takeReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent(TakeReading.class);
            }
        });
    }

    private void tryToConnect(){
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntent(Connect.class);
            }
        });
    }

    private void disConnect(){
        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connection_status)
                { viewDialog.startLoadingDialog();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            viewDialog.dismisDialog();
                            rxBle.closeBle();
                        }
                    },3000);
                }
                else
                    setWarning("Device is not connected");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void content() {
        connection_status = rxBle.getConnectionStatus();
        DEVICE_NAME = rxBle.getDEVICE_MAME();
        Log.d("connection status", String.valueOf(connection_status));
        if (connection_status)
            btnConnect.setText("Connected to " +DEVICE_NAME);
        else
            btnConnect.setText("Connect");
        refresh(1000);
    }

    private void refresh(int mils) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };
        handler.postDelayed(runnable,mils);
    }

    private void statusbarcolorchange() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.gradientDarkColor));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getDateAndTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM_dd_hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        // System.out.println();
        todaysDate=dtf.format(now);
    }

    private void startIntent(Class activity)
    {
        Intent intent = new Intent(getApplicationContext(), activity);
        startActivity(intent);
    }


    ////////////////////////////////////////Toasty Messages
    private void setError(String error)
    {
        Toasty.error(getApplicationContext(), error, Toasty.LENGTH_SHORT).show();
    }
    private void setWarning(String warning)
    {
        Toasty.warning(getApplicationContext(), warning, Toasty.LENGTH_SHORT).show();
    }
    private void setSucess(String sucess)
    {
        Toasty.success(getApplicationContext(), sucess, Toasty.LENGTH_SHORT).show();
    }
    /*-----------------------------------------------------------------------------*/






}