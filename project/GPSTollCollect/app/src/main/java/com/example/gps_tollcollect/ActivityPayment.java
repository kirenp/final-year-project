package com.example.gps_tollcollect;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityPayment extends AppCompatActivity implements View.OnClickListener {
    TextView amt, cardExpiry;
    EditText cardNumber, cardHolderName, cardCvv;
    Button paybutton, selectbtn;
    private int mYear, mMonth, mDay;
    private String res = "";
    SharedPreferences sp;
    static String orderId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        amt = findViewById(R.id.tAmount);
        paybutton = findViewById(R.id.bPay);
        selectbtn = findViewById(R.id.bSelectdate);
        cardCvv = findViewById(R.id.eCardCvv);
        cardNumber = findViewById(R.id.eCardNumber);
        cardExpiry = findViewById(R.id.tCardExpiry);
        cardHolderName = findViewById(R.id.eCardHolderName);
        selectbtn.setOnClickListener(this);

        String displayamount =ViewUserFine.FineAmt;
        amt.setText(displayamount);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        paybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getCardHolder = cardHolderName.getText().toString();
                String getCardNumber = cardNumber.getText().toString();
                String getCardExpiry = cardExpiry.getText().toString();
                String getCardCvv = cardCvv.getText().toString();
                if (getCardHolder.equals("")) {
                    cardHolderName.setError("Card Holder Name is Requried");
                } else if (getCardNumber.equals("")) {
                    cardNumber.setError("Card Number is Requried");
                } else if (getCardExpiry.equals("")) {
                    cardExpiry.setError("Expiry Date is Requried");
                } else if (getCardCvv.equals("")) {
                    cardCvv.setError("Expiry Date is Requried");
                } else {
                    sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String userid = Login.userid;
                    String url = "http://blackfarm.in/toll_application/add_activity_payment.php";

                    System.out.println("user_id:" + userid);
                    System.out.println("card_number:" + getCardNumber);
                    System.out.println("card_cvv:" + getCardCvv);
                    System.out.println("card_expiry_date:" + getCardExpiry);
                    System.out.println("card_holder_name:" + getCardHolder);


                    List<NameValuePair> list = new ArrayList<>();
                    list.add(new BasicNameValuePair("user_id", Login.userid));
                    list.add(new BasicNameValuePair("iduser_fine", ViewUserFine.FineID));
                    list.add(new BasicNameValuePair("amount", ViewUserFine.FineAmt));
                    list.add(new BasicNameValuePair("idfine", ViewUserFine.FineTypeID));
                    list.add(new BasicNameValuePair("vehicle_no", ViewWallet3.Veh));
                    list.add(new BasicNameValuePair("card_cvv", getCardCvv));
                    list.add(new BasicNameValuePair("card_number", getCardNumber));
                    list.add(new BasicNameValuePair("card_expiry", getCardExpiry));
                    list.add(new BasicNameValuePair("card_holder_name", getCardHolder));

                    JSONParser jn = new JSONParser();
                    JSONObject jsonObject = jn.makeHttpRequest(url, "GET", list);

                    res = null;

                    try {
                        res = jsonObject.getString("status");
                    } catch (JSONException e) {
                        Log.e("JSON Parser", "Error parsing data [" + e.getMessage() + "] ");
                    }
                    if (res.equalsIgnoreCase("2")) {
                        Toast.makeText(getApplicationContext(), "Payment Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent successpay = new Intent(getApplicationContext(), UserHome.class);
                        startActivity(successpay);
                    }
                   else if (res.equalsIgnoreCase("0")) {
                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                        Intent successpay1 = new Intent(getApplicationContext(), ActivityPayment.class);
                        startActivity(successpay1);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == selectbtn) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            cardExpiry.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

        @Override
        public void onBackPressed () {
            Intent f1 = new Intent(getApplicationContext(), ViewUserFine.class);
            startActivity(f1);
            finish();

        }
    }
