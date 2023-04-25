package com.example.gps_tollcollect;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.Fade;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    TextView naname, consumerno;
    SharedPreferences sp;
    private String res="";

    FloatingActionButton button;

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Fade fade = new Fade();
        fade.setDuration(400);
        getWindow().setAllowEnterTransitionOverlap(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(fade);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("User Home");
        drawer = findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name = com.example.gps_tollcollect.Login.userid;

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerview = navigationView.getHeaderView(0);
        naname = headerview.findViewById(R.id.navname);
        consumerno = headerview.findViewById(R.id.consumerno_text);
        consumerno.setText(name);

        String url = "http://blackfarm.in/calorie_calculator/delete_item.php";
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("user_id", Login.userid ));
        JSONParser jn = new JSONParser();
        JSONObject jsonObject = jn.makeHttpRequest(url, "GET", list);
        String cal_consumed;
        res = null;

        try {
            res = jsonObject.getString("status");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data [" + e.getMessage() + "] ");
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewhome:
                Intent viewCom = new Intent(getApplicationContext(), UserHome.class);//Viewing Farm
                startActivity(viewCom);
                finish();
                break;
            case R.id.viewdistrict:
                Intent viewCom1= new Intent(getApplicationContext(), ViewDistrict.class);//Viewing Farm
                startActivity(viewCom1);
                finish();
                break;
            case R.id.viewtollamount:
                Intent viewp= new Intent(getApplicationContext(), ViewDistrict1.class);//Viewing Farm
                startActivity(viewp);
                finish();
                break;
            case R.id.viewpaidtoll:
                Intent viewCom5= new Intent(getApplicationContext(), ViewWallet2.class);//Viewing Farm
                startActivity(viewCom5);
                finish();
                break;
            case R.id.viewfine:
                Intent viewCom53= new Intent(getApplicationContext(), ViewWallet3.class);//Viewing Farm
                startActivity(viewCom53);
                finish();
                break;
            case R.id.viewpaidfine:
                Intent viewCom51= new Intent(getApplicationContext(), ViewUserFine1.class);//Viewing Farm
                startActivity(viewCom51);
                finish();
                break;
            case R.id.sendfeedback:
                Intent viewCom3= new Intent(getApplicationContext(), SendFeedback.class);//Viewing Farm
                startActivity(viewCom3);
                finish();
                break;
            case R.id.viewfeedback:
                Intent viewCom4= new Intent(getApplicationContext(), ViewFeedback.class);//Viewing Farm
                startActivity(viewCom4);
                finish();
                break;
            case R.id.addwallet:
                Intent viewCom5sw= new Intent(getApplicationContext(), AddVehicle.class);//Viewing Farm
                startActivity(viewCom5sw);
                finish();
                break;
            case R.id.viewwallet:
                Intent viewCom5s= new Intent(getApplicationContext(), ViewWallet1.class);//Viewing Farm
                startActivity(viewCom5s);
                finish();
                break;

            case R.id.nav_logout:
                new AlertDialog.Builder(this).setMessage("Do you want to Logout?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent za = new Intent(getApplicationContext(), com.example.gps_tollcollect.Login.class);
                                startActivity(za);

                            }
                        }).setNegativeButton("No", null).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override

    public void onBackPressed() {
        new android.app.AlertDialog.Builder(this).setMessage("Do you want to Logout?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent za = new Intent(getApplicationContext(), com.example.gps_tollcollect.Login.class);
                        startActivity(za);

                    }
                }).setNegativeButton("No", null).show();
    }

}