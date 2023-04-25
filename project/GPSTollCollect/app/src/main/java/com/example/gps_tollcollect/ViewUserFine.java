package com.example.gps_tollcollect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ViewUserFine extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener,RecyclerView.OnItemTouchListener{
private ViewUserFine.OnItemClickListener mListener;

public interface OnItemClickListener {
    public void onItemClick(View view, int position);

}
    static String  FineID="";
    static String  FineAmt="";
    static String  FineName="";
    static String  FineTypeID="";
    private DrawerLayout drawer;
    TextView naname,consumerno;
    String[] amount,fine_type,fine_date,iduser_fine,idfine_type,current_speed;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView recyclerView;
    private ViewUserFineAdapter viewMaterialAdapter;
    View headerview;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_fine);

        new ViewUserFine.AsyncFetch().execute();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vehicle No : "+ViewWallet3.Veh);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String nameheader = Login.userid;
        View headerview = navigationView.getHeaderView(0);
        naname = headerview.findViewById(R.id.navname);
        consumerno = headerview.findViewById(R.id.consumerno_text);
        consumerno.setText(nameheader);



    }

    @Override
    public void onClick(View view) {

        Intent i = new Intent(getApplicationContext(), UserHome.class);
        startActivity(i);

    }


    GestureDetector mGestureDetector;
    public ViewUserFine(){

    }
    public ViewUserFine(Context context, final RecyclerView recyclerView, ViewUserFine.OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

private class AsyncFetch extends AsyncTask<String, String, String> {


    ProgressDialog pdLoading = new ProgressDialog(ViewUserFine.this);
    HttpURLConnection conn;
    URL url = null;

    @Override
    protected void onPreExecute () {
        super.onPreExecute();

        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

    }

    @Override
    protected String doInBackground (String...params){

        try {

            // Enter URL address where your json file resides
            // Even you can make call to php file which returns json data
            url = new URL("http://blackfarm.in/toll_application/ViewUserFine.php");

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.toString();
        }

        try {

            // Setup HttpURLConnection class to send and receive data from php and mysql
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("GET");

            // setDoOutput to true as we recieve data from json file
            conn.setDoOutput(true);

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return e1.toString();
        }

        try {

            int response_code = conn.getResponseCode();

            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {

                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Pass data to onPostExecute method
                return (result.toString());

            } else {

                return ("unsuccessful");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            conn.disconnect();
        }


    }
    @Override
    protected void onPostExecute (String result){
        pdLoading.dismiss();
        List<UserFine> material = new ArrayList<>();

        pdLoading.dismiss();
        try {
            List<NameValuePair> list = new ArrayList<>();
            list.add(new BasicNameValuePair("vehicle_no", ViewWallet3.Veh));
            JSONParser jn = new JSONParser();
            JSONObject jsonObject = jn.makeHttpRequest(String.valueOf(url),
                    "GET",
                    list);
            String res = jsonObject.getString("status");
            Log.d("name==", res);
            if (res.equalsIgnoreCase("1")) {
                JSONArray ja;
                ja = jsonObject.getJSONArray("data");
                iduser_fine=new String[ja.length()];
                fine_type=new String[ja.length()];
                amount=new String[ja.length()];
                fine_date=new String[ja.length()];
                idfine_type=new String[ja.length()];
                current_speed=new String[ja.length()];
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);



                    UserFine data = new UserFine();
                    data.fine_type = jo.getString("fine_type");
                    data.amount = jo.getString("amount");
                    data.fine_date = jo.getString("fine_date");
                    data.current_speed = jo.getString("current_speed");
                    amount[i] = jo.getString("amount");
                    fine_type[i] = jo.getString("fine_type");
                    iduser_fine[i] = jo.getString("iduser_fine");
                    idfine_type[i] = jo.getString("idfine_type");
                    material.add(data);

                }

                viewMaterialAdapter = new ViewUserFineAdapter(ViewUserFine.this,material );
                recyclerView.setAdapter(viewMaterialAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ViewUserFine.this));

            } else {
                Intent l = new Intent(getApplicationContext(),NoFine.class);//Viewtoll amount
                startActivity(l);
            }

        } catch (JSONException e) {
            Toast.makeText(ViewUserFine.this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        recyclerView.addOnItemTouchListener(
                new ViewUserFine(getApplicationContext(), recyclerView, new ViewUserFine.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        FineID=iduser_fine[position];
                        FineName=fine_type[position];
                        FineAmt=amount[position];
                        FineTypeID=idfine_type[position];
                        Intent l = new Intent(getApplicationContext(),ActivityPayment.class);//Viewtoll amount
                        startActivity(l);
                    }
                })
        );

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
                                Intent za = new Intent(getApplicationContext(), Login.class);
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
        Intent f1=new Intent(getApplicationContext(),ViewWallet3.class);
        startActivity(f1);
        finish();
    }
}
