package com.example.gps_tollcollect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    EditText Ename,Eemail,Euserid,Epassword,Eaddress,Ephone;
    Button register;
    private String res="";
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Ename=findViewById(R.id.name);
        Eemail = findViewById(R.id.email);
        Eaddress = findViewById(R.id.address);
        Ephone = findViewById(R.id.phone);
        Euserid = findViewById(R.id.userid);
        Epassword = findViewById(R.id.password);

        register = findViewById(R.id.regbtn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getname=Ename.getText().toString();
                String getemail=Eemail.getText().toString();
                String getaddress=Eaddress.getText().toString();
                String getphone=Ephone.getText().toString();
                String getuserid=Euserid.getText().toString();
                String getpassword=Epassword.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (getname.equals("")){
                    Ename.setError("Required");
                } else if (getemail.equals("")){
                    Eemail.setError("Requried");
                }else if (getaddress.equals("")){
                    Eaddress.setError("Requried");
                } else if (getphone.equals("") ){
                    Ephone.setError("Requried");
                }else if (getuserid.equals("")){
                    Euserid.setError("Requried");
                }else if (getpassword.equals("")){
                    Epassword.setError("Requried");
                }else if (getphone.length() !=10){
                    Ephone.setError("Check Number");
                }else if (getemail.matches(emailPattern)){
                    Eemail.setError("Check Email");
                }
                else {


                    sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


                    String url = "http://blackfarm.in/toll_application/user_register.php";

                    List<NameValuePair> list = new ArrayList<>();
                    list.add(new BasicNameValuePair("username", getuserid));
                    list.add(new BasicNameValuePair("name", getname));
                    list.add(new BasicNameValuePair("email", getemail));
                    list.add(new BasicNameValuePair("address", getaddress));
                    list.add(new BasicNameValuePair("phone", getphone));
                    list.add(new BasicNameValuePair("password", getpassword));
                    JSONParser jn = new JSONParser();
                    JSONObject jsonObject = jn.makeHttpRequest(url, "GET", list);

                    res = null;

                    try {
                        res = jsonObject.getString("status");
                    } catch (JSONException e) {
                        Log.e("JSON Parser", "Error parsing data [" + e.getMessage() + "] ");
                    }
                    if (res.equalsIgnoreCase("1")) {

                        Toast.makeText(getApplicationContext(), "This User_Id is Exist", Toast.LENGTH_SHORT).show();

                    }
                    if (res.equalsIgnoreCase("2")) {

                        Toast.makeText(getApplicationContext(), " Registration completed...", Toast.LENGTH_SHORT).show();

                        Intent servdetails = new Intent(getApplicationContext(), com.example.gps_tollcollect.Login.class);
                        startActivity(servdetails);
                        finish();

                    }
                    else if (res.equalsIgnoreCase("0")) {
                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });
    }
    @Override
    public void onBackPressed() {
        Intent regback = new Intent(getApplicationContext(), Login.class);
        startActivity(regback);
        finish();
    }
    }
