package com.example.gps_tollcollect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AddVehicle extends AppCompatActivity  {
    EditText eGetNumber;
    Button addButton;
    static String VehNo="";
    private LocationManager locationManager;
    private String provider;
    private DrawerLayout drawer;
    private String res="";
    SharedPreferences sp;
    View headerview;
    public static final int REQUEST_CODE_PERMISSIONS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);


        eGetNumber=(EditText)findViewById(R.id.eVehicleNumber);
        addButton = findViewById(R.id.buttonaddreply);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getVehicleNumber = eGetNumber.getText().toString();

                if (getVehicleNumber.equals("")) {
                    eGetNumber.setError("Required");
                }
                else {
                    System.out.println("Vehicle no: "+VehNo);
                    VehNo=getVehicleNumber;

                    sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String url = "http://blackfarm.in/toll_application/add_vehicle.php";
                    System.out.println("user_id:" + Login.userid);
                    System.out.println(" :" + VehNo);



                    List<NameValuePair> list = new ArrayList<>();
                    list.add(new BasicNameValuePair("user_id", Login.userid));
                    list.add(new BasicNameValuePair("vehicle_no", VehNo));


                    JSONParser jn = new JSONParser();
                    JSONObject jsonObject = jn.makeHttpRequest(url, "GET", list);

                    res = null;

                    try {
                        res = jsonObject.getString("status");
                    } catch (JSONException e) {
                        Log.e("JSON Parser", "Error parsing data [" + e.getMessage() + "] ");
                    }
                    if (res.equalsIgnoreCase("2")) {
                        Toast.makeText(getApplicationContext(), " Added ", Toast.LENGTH_SHORT).show();
                        Intent successpay = new Intent(getApplicationContext(), ViewWallet1.class);
                        startActivity(successpay);
                    }else if (res.equalsIgnoreCase("1")) {
                        Toast.makeText(getApplicationContext(), "This Vehicle Already has a Wallet", Toast.LENGTH_SHORT).show();
                        Intent successpay = new Intent(getApplicationContext(), ViewWallet1.class);
                        startActivity(successpay);
                    }
                    else if (res.equalsIgnoreCase("0")) {
                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    @Override
    public void onBackPressed () {
        Intent f1 = new Intent(getApplicationContext(), UserHome.class);
        startActivity(f1);
        finish();

    }
}
