package com.example.gps_tollcollect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NoFine extends AppCompatActivity {
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_fine);
        t1=findViewById(R.id.nofine);
        t1.setText(ViewWallet3.Veh);
    }
    @Override
    public void onBackPressed() {
        Intent f1=new Intent(getApplicationContext(),ViewWallet3.class);
        startActivity(f1);
        finish();
    }
}