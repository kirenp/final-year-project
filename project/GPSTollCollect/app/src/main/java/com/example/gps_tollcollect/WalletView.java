package com.example.gps_tollcollect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WalletView extends AppCompatActivity {
    Button special,boating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_view);

        special=findViewById(R.id.idadd);
        boating=findViewById(R.id.idview);

        special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f1 = new Intent(getApplicationContext(), AddVehicle.class);//addwallet
                startActivity(f1);
                finish();
            }
        });

        boating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f1 = new Intent(getApplicationContext(), ViewWallet1.class);//viewwallet
                startActivity(f1);
                finish();
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