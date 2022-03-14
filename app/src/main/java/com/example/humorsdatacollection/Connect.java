package com.example.humorsdatacollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class Connect extends AppCompatActivity {

    Button btnD1, btnD2, btnD3;


    ///ble class initialise
    private RxBle mRxBle;

    ViewDialog viewDialog = new ViewDialog(Connect.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        init();
    }
    private void init(){
        init_vars();

        // d1 connect
        d1();

        // send data
        d2();

        d3();

        // status bar color change
        statusbarcolorchange();
    }
    private void init_vars(){
        btnD1 = findViewById(R.id.d1);
        btnD2 = findViewById(R.id.d2);
        btnD3 = findViewById(R.id.d3);


    }
    private void d1(){
        btnD1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDialog.startLoadingDialog();
                mRxBle = RxBle.getInstance().setTargetDevice("HUMORS_01");
                mRxBle.openBle(Connect.this);
                mRxBle.scanBleDevices(true);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewDialog.dismisDialog();
                        if (mRxBle.getConnectionStatus())
                        {
                            setSucess("Connected");
                            startIntent(MainActivity.class);
                        } else if (!mRxBle.getConnectionStatus()){
                            startIntent(MainActivity.class);
                        }
                    }
                },7000);

            }
        });
    }
    private void d2(){
        btnD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDialog.startLoadingDialog();
                mRxBle = RxBle.getInstance().setTargetDevice("HUMORS_03");
                mRxBle.openBle(Connect.this);
                mRxBle.scanBleDevices(true);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewDialog.dismisDialog();
                        if (mRxBle.getConnectionStatus())
                        {
                            setSucess("Connected");
                            startIntent(MainActivity.class);
                        } else if (!mRxBle.getConnectionStatus()){
                            startIntent(MainActivity.class);
                        }
                    }
                },7000);

            }
        });
    }
    private void d3(){
        btnD3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDialog.startLoadingDialog();
                mRxBle = RxBle.getInstance().setTargetDevice("HUMORS_02");
                mRxBle.openBle(Connect.this);
                mRxBle.scanBleDevices(true);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewDialog.dismisDialog();
                        if (mRxBle.getConnectionStatus())
                        {
                            setSucess("Connected");
                            startIntent(MainActivity.class);
                        } else if (!mRxBle.getConnectionStatus()){
                            startIntent(MainActivity.class);
                        }
                    }
                },7000);

            }
        });
    }



    private void startIntent(Class moveTO){
        Intent intent = new Intent(getApplicationContext(), moveTO);
        startActivity(intent);
        finish();
    }
    private void setError(String error){
        Toasty.error(getApplicationContext(), error, Toasty.LENGTH_SHORT).show();
    }
    private void setSucess(String sucess){
        Toasty.success(getApplicationContext(), sucess, Toasty.LENGTH_SHORT).show();
    }

    private void statusbarcolorchange() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.gradientDarkColor));
    }
}