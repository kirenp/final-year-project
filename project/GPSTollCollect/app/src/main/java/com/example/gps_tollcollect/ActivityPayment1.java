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

public class ActivityPayment1 extends AppCompatActivity implements View.OnClickListener {
    TextView  cardExpiry;
    EditText cardNumber, cardHolderName, cardCvv,eAmount;
    Button paybutton, selectbtn;
    private int mYear, mMonth, mDay;
    private String res = "";
    SharedPreferences sp;
    static String orderId="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_payment1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        paybutton = findViewById(R.id.bPay);
        selectbtn = findViewById(R.id.bSelectdate);
        cardCvv = findViewById(R.id.eCardCvv);
        cardNumber = findViewById(R.id.eCardNumber);
        cardExpiry = findViewById(R.id.tCardExpiry);
        eAmount = findViewById(R.id.eAmount);
        cardHolderName = findViewById(R.id.eCardHolderName);
        selectbtn.setOnClickListener(this);


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
                String getAmount = eAmount.getText().toString();
                if (getCardHolder.equals("")) {
                    cardHolderName.setError(" Requried");
                } else if (getCardNumber.equals("")) {
                    cardNumber.setError(" Requried");
                } else if (getCardExpiry.equals("")) {
                    cardExpiry.setError("Requried");
                } else if (getCardCvv.equals("")) {
                    cardCvv.setError("Requried");
                } else if (getAmount.equals("")) {
                    eAmount.setError("Requried");
                } else {
                    sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String userid = Login.userid;
                    String url = "http://blackfarm.in/toll_application/add_activity_payment1.php";

                    System.out.println("user_id:" + userid);
                    System.out.println("card_number:" + getCardNumber);
                    System.out.println("card_cvv:" + getCardCvv);
                    System.out.println("card_expiry_date:" + getCardExpiry);
                    System.out.println("card_holder_name:" + getCardHolder);
                    System.out.println("Amount:" + getAmount);
                    System.out.println("Amount:" + ViewWallet.WalletAmt);


                    List<NameValuePair> list = new ArrayList<>();
                    list.add(new BasicNameValuePair("idwallet", ViewWallet1.WalletID));
                    list.add(new BasicNameValuePair("amount", getAmount));
                    list.add(new BasicNameValuePair("walletamount", ViewWallet.WalletAmt));

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
                    if (res.equalsIgnoreCase("1")) {
                        Toast.makeText(getApplicationContext(), "Wallet Updated", Toast.LENGTH_SHORT).show();
                        Intent successpay = new Intent(getApplicationContext(), ViewWallet.class);
                        startActivity(successpay);
                    }
                   else if (res.equalsIgnoreCase("0")) {
                        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                        Intent successpay1 = new Intent(getApplicationContext(), ActivityPayment1.class);
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
            Intent f1 = new Intent(getApplicationContext(), ViewWallet.class);
            startActivity(f1);
            finish();

        }
    }
