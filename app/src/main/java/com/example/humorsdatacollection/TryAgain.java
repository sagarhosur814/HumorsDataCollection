package com.example.humorsdatacollection;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;

public class TryAgain extends AppCompatActivity {

    Button btnMoveToMain, copyBtn;
    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_again);

        btnMoveToMain = findViewById(R.id.btn_move_to_main);
        copyBtn = findViewById(R.id.copyData);
        data = findViewById(R.id.data);

        Bundle extras = getIntent().getExtras();
        data.setText(extras.getString("received_data"));




        statusbarcolorchange();
        btnMoveToMain = findViewById(R.id.btn_move_to_main);
        btnMoveToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                finish();
            }
        });

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Data", data.getText().toString());
                Toasty.success(getApplicationContext(), "Copied", Toasty.LENGTH_SHORT).show();
                clipboard.setPrimaryClip(clip);

            }
        });
    }

    private void statusbarcolorchange() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.gradientDarkColor));
    }
}