package com.example.humorsdatacollection;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class ActivityLogin extends AppCompatActivity {

    private TextView registerNowButton;
    private TextView forgotPasswordButton;
    private EditText userEmailTextView, userPasswordTextView;
    private Button loginButton;

    private boolean loginStatus=false;

    String userEmail, userPassword;

    ViewDialog viewDialog;

    String TAG ="com.example.humorsdatacollection";

    public static final String MyPREFERENCES = "user_session_data" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
       mainn();


    }

    private void mainn(){

        declare_variables();
        statusbarcolorchange();
        userSignIn();





    }

    @Override
    protected void onStart() {
        super.onStart();


// We can then use the data


    }

    private void declare_variables(){

        forgotPasswordButton = findViewById(R.id.forgot_password_button);
        userEmailTextView = findViewById(R.id.user_email_edittext_login);
        userPasswordTextView = findViewById(R.id.user_password_edittext_login);
        loginButton = findViewById(R.id.login_button);




        viewDialog = new ViewDialog(ActivityLogin.this);

    }
    private void statusbarcolorchange() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.gradientDarkColor));
        }
    }
    private void userSignIn() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userEmail = userEmailTextView.getText().toString();
                userPassword = userPasswordTextView.getText().toString();

                String loginUrl ="https://fitspine.in/humors_data_collection/user_login.php?" +
                        "email="+userEmail +
                        "&password="+userPassword;


                AsyncHttpClient client = new AsyncHttpClient();
                client.get(loginUrl, new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started
                        viewDialog.startLoadingDialog();
                        hideKeyboard(ActivityLogin.this);
                        Log.i("response", "loginResponse");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        viewDialog.dismisDialog();

                        String loginResponse = new String(responseBody);
                        Log.i("response", loginResponse);
                        if (loginResponse.equals("\n\n\nsucess")){

                            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("useremail", userEmail);
                            editor.putBoolean("login_status", true);
                            editor.commit();
                            Toasty.success(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT, true).show();

                            moveToDashboard();
                        }else{
                            Toasty.error(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT, true).show();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }


                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });




            }
        });
    }

    private void moveToDashboard() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }




}