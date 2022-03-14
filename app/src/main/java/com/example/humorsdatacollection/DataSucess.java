package com.example.humorsdatacollection;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataSucess extends AppCompatActivity {

        Button btnMoveToMain;


    private String name=null, age=null, gender=null, acetone=null, ammonia=null , benz=null, tolu=null, h2s=null, ethn=null, hydr=null,buta=null, meth=null;
     private String   co=null, hum=null, temp=null, blow=null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sucess);

        btnMoveToMain = findViewById(R.id.btn_move_to_main);

        Bundle extras = getIntent().getExtras();



        diplayData(extras.getString("received_data"));


        TextView name1 = findViewById(R.id.name);
        TextView ageGender = findViewById(R.id.age_gender);
        TextView acetone1 = findViewById(R.id.ace);
        TextView ammonia1 = findViewById(R.id.ammonia);
        TextView benzene = findViewById(R.id.benz);
        TextView tolune = findViewById(R.id.tolune);
        TextView h2S = findViewById(R.id.h2s);
        TextView ethanol = findViewById(R.id.ethanol);
        TextView hydrogen = findViewById(R.id.hydrozen);
        TextView butane = findViewById(R.id.butane);
        TextView methane = findViewById(R.id.methane);
        TextView co1 = findViewById(R.id.co);
        TextView temp1 = findViewById(R.id.temp);
        TextView hum1 = findViewById(R.id.hum);
        TextView blow1 = findViewById(R.id.blow);



        name1.setText(name);
        ageGender.setText(age +"," + gender);
        acetone1.setText(acetone);
        ammonia1.setText(ammonia);
        benzene.setText(benz);
        tolune.setText(tolu);
        h2S.setText(h2s);
        ethanol.setText(ethn);
        hydrogen.setText(hydr);
        butane.setText(buta);
        methane.setText(meth);
        co1.setText(co);
        temp1.setText("Temperature"+temp);
        hum1.setText("Humidity"+hum);
        blow1.setText("Blow "+blow);









        statusbarcolorchange();
        btnMoveToMain = findViewById(R.id.btn_move_to_main);
        btnMoveToMain.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

            finish();
        });


    }

    private void statusbarcolorchange() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.gradientDarkColor));
    }

    private void diplayData(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                acetone = jsonObject1.getString("ace");
                ammonia = jsonObject1.getString("nh3");
                benz = jsonObject1.getString("benzi");
                tolu = jsonObject1.getString("tolune");
                h2s = jsonObject1.getString("h2s");
                ethn = jsonObject1.getString("ethanol");
                hydr = jsonObject1.getString("H2");
                co = jsonObject1.getString("CO");
                buta = jsonObject1.getString("butane");
                meth = jsonObject1.getString("CH4");
                temp = jsonObject1.getString("temp");
                hum = jsonObject1.getString("humid");
                blow = jsonObject1.getString("blow");
                name = jsonObject1.getString("name");
                gender = jsonObject1.getString("gender");
                age = jsonObject1.getString("age");



            }




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
