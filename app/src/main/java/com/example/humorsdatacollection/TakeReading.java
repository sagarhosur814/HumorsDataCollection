package com.example.humorsdatacollection;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import es.dmoral.toasty.Toasty;
import rx.functions.Action1;

public class TakeReading extends AppCompatActivity {

    private Button firstStepButton;
    LinearLayout firstLayout, secondLayout, thirdLayout, sendAtTheRateLayout;

    private  RadioGroup rgGender,rgAlcohol, rgSmoking,  rgExercise, rgMedicine, rgFoodWater;
    private String userGender, userAlcohol, userSmoking, userExercise, userMedicine, userFoodWater;

    private  TextView breathTimer;

    private CheckBox chkNone, chkDiabetes, chkKidney, chkLiver, chkLungs;

    private Button btnSendSignal;

    private TextView txtResult;

    private EditText etAge, etHeight , etWeight, etDiseaseDescription, etMedicineDescription, etDailyWater;
    private String userAge, userHeight, userWeight, userDiseaseDescription, userMedicineDescription, userDailyWater;
    public static final String MyPREFERENCES = "user_session_data" ;
    private String userLoginName="Null";
    private String DEVICE_NAME="NONE";

    private StringBuffer recivedData=null;

    private TextView result_data;

    private String todaysDate=null;

    /// Ble class
    RxBle rxBle ;

    String msg="";

    EditText etUserName;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_reading);
        ////////////// ble class call
        rxBle = RxBle.getInstance();
        /////////////////////-----------------all steps


        init();
    }


    @Override
    protected void onStart() {
        super.onStart();
        DEVICE_NAME = rxBle.getDEVICE_MAME();
       // setInfo(DEVICE_NAME);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init(){
        /// inits vars
        init_vars();

        // making all vars null
        allnulls();

        //user form fill
        formFill();

        //firstStep
        firstStep();

        ///send signal to humors device
        sendSignal();

        /// get login name
        getLoginName();

        // start bar color change
        statusbarcolorchange();

        // get current date and time
        getDateAndTime();

    }

    private void allnulls(){

        userGender = "Null";
        userAlcohol = "Null";
        userSmoking = "Null";
        userExercise = "Null";
        userMedicine = "Null";
        userAge = "Null";
        userHeight = "Null";
        userWeight = "Null";
        userDiseaseDescription = "Null";
        userMedicineDescription = "Null";
        userDailyWater = "Null";
        userFoodWater = "Null";

    }
    private void init_vars(){
        firstStepButton = findViewById(R.id.add_data_next_button);

        // layouts
        firstLayout = findViewById(R.id.firstLayout);
        secondLayout = findViewById(R.id.secondLayout);
        thirdLayout = findViewById(R.id.thirdLayout);
        breathTimer = findViewById(R.id.timer_breath);
        sendAtTheRateLayout = findViewById(R.id.atTheRate);


        /// radio groups
        rgGender = findViewById(R.id.radioGroupGender);
        rgAlcohol = findViewById(R.id.radioGroupAlcohol);
        rgSmoking = findViewById(R.id.radioGroupSmoke);
        rgExercise = findViewById(R.id.radioGroupExcr);
        rgMedicine = findViewById(R.id.radioGroupMedicine);
        rgFoodWater = findViewById(R.id.radioGroupFoodWater);

        /// checkboxes
        chkNone = findViewById(R.id.none);
        chkDiabetes = findViewById(R.id.diabates);
        chkKidney = findViewById(R.id.kidney);
        chkLiver = findViewById(R.id.liver);
        chkLungs = findViewById(R.id.lungs);

        /// Buttons
        btnSendSignal = findViewById(R.id.btn_send_signal);

        /// TextView
     //   txtResult = findViewById(R.id.txtResult);
        result_data = findViewById(R.id.myresult);
      //  txtResult.setText("");
        result_data.setText("");


        ///EditTexts
        etAge = findViewById(R.id.et_age);
        etHeight = findViewById(R.id.et_height);
        etWeight = findViewById(R.id.et_weight);
        etDiseaseDescription = findViewById(R.id.et_disease_desc);
        etMedicineDescription = findViewById(R.id.et_medicine_description);
        etDailyWater = findViewById(R.id.et_daily_water);
        etUserName = findViewById(R.id.et_userName);

    }
    private void formFill(){

//        <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<gender>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbGender=(RadioButton)findViewById(checkedId);
                if (rbGender.getText().toString().equals("Male"))
                {
                    userGender = rbGender.getText().toString();
                    setInfo(userGender + " Selected");

                }else if (rbGender.getText().toString().equals("Female")){
                    userGender = rbGender.getText().toString();
                    setInfo(userGender + " Selected");
                }
            }
        });
//        <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Alcohol>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        rgAlcohol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbAlcohol=(RadioButton)findViewById(checkedId);
                if (rbAlcohol.getText().toString().equals("Regularly"))
                {
                    userAlcohol = rbAlcohol.getText().toString();
                    setInfo(userAlcohol+ " Selected");
                }else if (rbAlcohol.getText().toString().equals("Occasionally")){
                    userAlcohol = rbAlcohol.getText().toString();
                    setInfo(userAlcohol+ " Selected");
                }else if (rbAlcohol.getText().toString().equals("Never")){
                    userAlcohol = rbAlcohol.getText().toString();
                    setInfo(userAlcohol+ " Selected");
                }
            }
        });
//        <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Smoking>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        rgSmoking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbSmoking=(RadioButton)findViewById(checkedId);
                if (rbSmoking.getText().toString().equals("Regularly"))
                {
                    userSmoking = rbSmoking.getText().toString();
                    setInfo(userSmoking+ " Selected");
                }else if (rbSmoking.getText().toString().equals("Occasionally")){
                    userSmoking = rbSmoking.getText().toString();
                    setInfo(userSmoking+ " Selected");
                }else if (rbSmoking.getText().toString().equals("Never")){
                    userSmoking = rbSmoking.getText().toString();
                    setInfo(userSmoking+ " Selected");
                }
            }
        });

  //        <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Exercise>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        rgExercise.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbExcr=(RadioButton)findViewById(checkedId);
                if (rbExcr.getText().toString().equals("Regular Workout (5-7 times a week)"))
                {
                    userExercise = rbExcr.getText().toString();
                    setInfo(userExercise+ " Selected");
                }else if (rbExcr.getText().toString().equals("Moderate Workout (2-3 times a week)")){
                    userExercise = rbExcr.getText().toString();
                    setInfo(userExercise+ " Selected");
                }else if (rbExcr.getText().toString().equals("Never")){
                    userExercise = rbExcr.getText().toString();
                    setInfo(userExercise+ " Selected");
                }
            }
        });


//        <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Medicine>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        rgMedicine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbMedicine=(RadioButton)findViewById(checkedId);
                if (rbMedicine.getText().toString().equals("Yes"))
                {
                    userMedicine = rbMedicine.getText().toString();
                    setInfo(userMedicine+ " Selected");
                    etMedicineDescription.setVisibility(View.VISIBLE);
                }else if (rbMedicine.getText().toString().equals("No")){
                    userMedicine = rbMedicine.getText().toString();
                    setInfo(userMedicine+ " Selected");
                    etMedicineDescription.setVisibility(View.GONE);
                }
            }
        });

        //        <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Medicine>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        rgMedicine.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbMedicine=(RadioButton)findViewById(checkedId);
                if (rbMedicine.getText().toString().equals("Yes"))
                {
                    userMedicine = rbMedicine.getText().toString();
                    setInfo(userMedicine+ " Selected");
                    etMedicineDescription.setVisibility(View.VISIBLE);
                }else if (rbMedicine.getText().toString().equals("No")){
                    userMedicine = rbMedicine.getText().toString();
                    setInfo(userMedicine+ " Selected");
                    etMedicineDescription.setVisibility(View.GONE);
                }
            }
        });

        //        <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Last hour food water >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        rgFoodWater.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbFoodWater=(RadioButton)findViewById(checkedId);
                if (rbFoodWater.getText().toString().equals("Yes"))
                {
                    userFoodWater = rbFoodWater.getText().toString();
                    setInfo(userFoodWater+ " Selected");
                }else if (rbFoodWater.getText().toString().equals("No")){
                    userFoodWater = rbFoodWater.getText().toString();
                    setInfo(userFoodWater+ " Selected");
                }
            }
        });








    }

    private void firstStep(){
        firstStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userAge = etAge.getText().toString();
                userHeight = etHeight.getText().toString();
                userWeight = etWeight.getText().toString();
                userMedicineDescription = etMedicineDescription.getText().toString();
                userDiseaseDescription = etDiseaseDescription.getText().toString();
                userDailyWater = etDailyWater.getText().toString();

                Check(v);

                if (rxBle.getConnectionStatus())
                {

                    firstLayout.setVisibility(View.GONE);
                    sendAtTheRateLayout.setVisibility(View.VISIBLE);
//                    addDataToServer("NIL", userAge, "NIL", "NIl", userHeight,userWeight,
//                                      userDailyWater, userAlcohol, userSmoking, userExercise, userMedicine,
//                                      userMedicineDescription, "test", userLoginName,
//                            "NIl" ,"NIl", userLoginName, "NIL",
//                            "5152", "HUMORS_01", userGender);

                }else
                    setError("Device is not connected !! \n please connect and try again");

            }
        });
    }

    private void sendSignal()
    {
        btnSendSignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxBle.sendData("@");

                setSucess("Device is activated");
                sendAtTheRateLayout.setVisibility(View.GONE);
                secondLayout.setVisibility(View.VISIBLE);

                secondStep();

            }
        });
    }

    private void secondStep() {
        new CountDownTimer(8000, 1000) {

         @SuppressLint("SetTextI18n")
         public void onTick(long millisUntilFinished) {
                        breathTimer.setText(String.valueOf(millisUntilFinished / 1000));
                        //here you can have your logic to set text to edittext
          }

         @SuppressLint("SetTextI18n")
         public void onFinish() {
             secondLayout.setVisibility(View.GONE);
             thirdLayout.setVisibility(View.VISIBLE);
             dataRecive();


//             Handler handler = new Handler();
//             handler.postDelayed(new Runnable() {
//                 @Override
//                 public void run() {
//
//                     StartIntent(DataSucess.class);
//                 }
//             },4000);

         }
        }.start();

    }

    private void dataRecive() {
        rxBle.receiveData().subscribe(new Action1<String>() {
            @Override
            public void call(String receiveData) {
                result_data.setVisibility(View.GONE);
                result_data.append(receiveData);
                if (receiveData.contains("*"))
                {


                    addDataToServer(etUserName.getText().toString(), userAge, "NIL", "NIl", userHeight,userWeight,
                                      userDailyWater, userAlcohol, userSmoking, userExercise, userMedicine,
                                     userMedicineDescription,msg , userDiseaseDescription,
                            userFoodWater ,userLoginName, userLoginName, todaysDate,
                            result_data.getText().toString(), DEVICE_NAME, userGender);


                   // setSucess(result_data.getText().toString());
                   // StartIntent(DataSucess.class);


                }else if (receiveData.contains("#"))
                {
                    setError("Not blown !! Please try again");
                    StartIntent(MainActivity.class);
                }
            }
        });


    }

    public void Check(View v)
    {


        // Concatenation of the checked options in if

        // isChecked() is used to check whether
        // the CheckBox is in true state or not.

        if(chkNone.isChecked())
            msg = msg + "None";
        else
            msg = msg + "";
        if(chkDiabetes.isChecked())
            msg = msg + ",Diabetes";
        else
            msg = msg + "";
        if(chkKidney.isChecked())
            msg = msg + ",Kidney";
        else
            msg = msg + "";
        if(chkLiver.isChecked())
            msg = msg + ",Liver";
        else
            msg = msg + "";
        if(chkLungs.isChecked())
            msg = msg + ",Lungs ";
        else
            msg = msg + "";
        // Toast is created to display the
        // message using show() method.
      //  Toast.makeText(this, msg + "are selected",
           //    Toast.LENGTH_LONG).show();
    }



    ////////////////////////////////////////Toasty Messages
    private void setError(String error) { Toasty.error(getApplicationContext(), error, Toasty.LENGTH_SHORT).show();}
    private void setWarning(String warning) {Toasty.warning(getApplicationContext(), warning, Toasty.LENGTH_SHORT).show(); }
    private void setSucess(String sucess) {Toasty.success(getApplicationContext(), sucess, Toasty.LENGTH_SHORT).show(); }
    private void setInfo(String info) {Toasty.info(getApplicationContext(), info, Toasty.LENGTH_SHORT).show(); }
    /*-----------------------------------------------------------------------------*/


    private void getLoginName(){
        SharedPreferences sh = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        // The value will be default as empty string because for
        // the very first time when the app is opened, there is nothing to show
         userLoginName = sh.getString("useremail", "null");
    }

    private void addDataToServer(String name, String age, String email, String ph_no, String height,String weight,
                                 String daily_water, String alcohol, String smoking, String excer, String medicine,
                                 String medi_name, String disease_his, String disea_disp, String last_hr_wat_food,
                                 String tester_id, String login, String dttm,String testdata, String hwid, String gender) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url ="https://humorstech.com/humorscalculation/patient_data_detail.php?name="+name+"&age="+age+
                "&email="+email+
                "&ph_no="+ph_no+
                "&height="+height+
                "&weight="+weight+
                "&daily_water="+daily_water+
                "&alcohol="+alcohol+
                "&smoking="+smoking+
                "&excer="+excer+
                "&medicine="+medicine+
                "&medi_name="+medi_name+
                "&disease_his="+disease_his+
                "&disea_disp="+disea_disp+
                "&last_hr_wat_food="+last_hr_wat_food+
                "&tester_id="+tester_id+
                "&login="+login+
                "&dttm_app="+dttm+
                "&gender="+gender+
                "&testdata="+testdata+
                "&hwid="+hwid;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

                String res = new String(responseBody);
                Log.d("resmyres", res);

                /// response from the server
                if (res.equals("retest")){
;
                    Intent intent = new Intent(getApplicationContext(), TryAgain.class);
                    intent.putExtra("received_data" , result_data.getText().toString());
                    startActivity(intent);
                    finish();

                }else if (res.contains("data")){
                    Intent intent = new Intent(getApplicationContext(), DataSucess.class);
                    intent.putExtra("received_data" ,res);
                    startActivity(intent);
                    finish();
                }



            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

             //   String str = new String(responseBody);
              //  setError(str);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
                setError(String.valueOf(retryNo));
            }
        });
    }

    private void StartIntent(Class moveTo){
        Intent intent = new Intent(getApplicationContext() , moveTo);
        startActivity(intent);
    }

    private void statusbarcolorchange() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.gradientDarkColor));
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        rxBle.closeBle();
//    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getDateAndTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM_dd_hh:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        // System.out.println();
        todaysDate=dtf.format(now);
    }
}