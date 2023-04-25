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
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SendFeedback extends AppCompatActivity {
    EditText Efeedback;
    Button Bfeedback;

    private DrawerLayout drawer;
    private String res="";
    SharedPreferences sp;
    View headerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Efeedback=findViewById(R.id.eFeedback);
        Bfeedback=findViewById(R.id.bFeedback);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Bfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getFeed=Efeedback.getText().toString();

                if (getFeed.equals("")){
                    Efeedback.setError("Requried");
                }
                else {


                    sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String userid= Login.userid;
                    String url = "http://blackfarm.in/toll_application/send_feedback.php";

                    System.out.println("user_id:"+userid);
                    System.out.println("name:"+getFeed);



                    List<NameValuePair> list = new ArrayList<>();
                    list.add(new BasicNameValuePair("user_id", userid));
                    list.add(new BasicNameValuePair("feedback", getFeed));

                    JSONParser jn = new JSONParser();
                    JSONObject jsonObject = jn.makeHttpRequest(url, "GET", list);

                    res = null;

                    try {
                        res = jsonObject.getString("status");
                    } catch (JSONException e) {
                        Log.e("JSON Parser", "Error parsing data [" + e.getMessage() + "] ");
                    }
                    if (res.equalsIgnoreCase("2")) {

                        Toast.makeText(getApplicationContext(), " Feedback Submitted Successfully ...", Toast.LENGTH_SHORT).show();

                        Intent k = new Intent(getApplicationContext(),UserHome.class);
                        startActivity(k);
                        finish();

                    }
                    else if (res.equalsIgnoreCase("0")) {
                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                        Intent k = new Intent(getApplicationContext(), SendFeedback.class);
                        startActivity(k);
                        finish();
                    }
                }

            }

        });
    }




    @Override
    public void onBackPressed() {
        Intent f1=new Intent(getApplicationContext(),UserHome.class);
        startActivity(f1);
        finish();
    }

}
