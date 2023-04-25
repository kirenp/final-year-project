package com.example.gps_tollcollect;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    private static EditText user_id, password;
    private static Button loginButton;
    private static TextView Register;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    static String UserProID="";
    String profession_id;
    static String userid="";
    private String getuser_id =  "";
    private String getPassword =  "";
    private String res = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_id =  findViewById(R.id.login_userid);
        password =  findViewById(R.id.login_password);
        loginButton =  findViewById(R.id.loginBtn);
        Register=    findViewById(R.id.Register);
        show_hide_password =  findViewById(R.id.show_hide_password);
        loginLayout =  findViewById(R.id.login_layout);



        try {
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        } catch (Exception e) {

        }


        setListeners();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getuser_id = user_id.getText().toString();
                getPassword = password.getText().toString();

                if (getuser_id.equals("")) {
                    user_id.setError("User_id Required");
                } else if (getPassword.equals(" ")) {
                    password.setError("password Name Requried");
                } else {


                    String url = "http://blackfarm.in/toll_application/login.php";

                    List<NameValuePair> list = new ArrayList<>();
                    list.add(new BasicNameValuePair("user_id", getuser_id));
                    list.add(new BasicNameValuePair("password", getPassword));

                    JSONParser jn = new JSONParser();
                    JSONObject jsonObject = jn.makeHttpRequest(url, "GET", list);;
                    userid = getuser_id;

                    try {
                        res = jsonObject.getString("status");
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                    if (res.equalsIgnoreCase("1")) {

                        Toast.makeText(getApplicationContext(), "User Login Successful", Toast.LENGTH_SHORT).show();
                        Intent userlogin = new Intent(getApplicationContext(), com.example.gps_tollcollect.UserHome.class);
                        startActivity(userlogin);
                        finish();
                    } else if (res.equalsIgnoreCase("0")) {
                        Toast.makeText(getApplicationContext(), "Invalid User_Id Or Password", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, com.example.gps_tollcollect.Register.class);
                startActivity(intent);
            }
        });
    }

    private void setListeners() {

        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {

                if (isChecked) {



                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                } else {


                    password.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());

                }

            }
        });
    }


    @Override
    public void onBackPressed() {
        new android.app.AlertDialog.Builder(this).setMessage("Do you want to Exit?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
                    }
                }).setNegativeButton("No", null).show();
    }

}

