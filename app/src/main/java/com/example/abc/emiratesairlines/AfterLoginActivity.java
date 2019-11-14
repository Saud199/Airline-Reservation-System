package com.example.abc.emiratesairlines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AfterLoginActivity extends AppCompatActivity {

    Button bookflightt,flightstatus,flightschedule,givefeedback,cotactUs,aboutUs,logoutcus;
    TextView loginID;


    public void onBackPressed() {
        Toast.makeText(AfterLoginActivity.this,"Please Log out to go back",Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        bookflightt = (Button) findViewById(R.id.bookflight_button);
        flightstatus = (Button) findViewById(R.id.flightstatus_btn);
        flightschedule = (Button) findViewById(R.id.flightschedule_btn);
        givefeedback = (Button) findViewById(R.id.feedback_btn);
        cotactUs = (Button) findViewById(R.id.contactus_btn);
        aboutUs = (Button) findViewById(R.id.aboutus_btn);
        logoutcus = (Button) findViewById(R.id.logoutcs_btn);
        loginID = (TextView) findViewById(R.id.login_id_customer);


        loginID.setText("Logged In As : "+CustomerInfo.name);


        bookflightt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AfterLoginActivity.this,BookFlightActivity.class);
                startActivity(i);
            }
        });

        flightstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AfterLoginActivity.this,FlightStatusActivity.class);
                startActivity(i);
            }
        });

        flightschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AfterLoginActivity.this,FlightScheduleActivity.class);
                startActivity(i);
            }
        });

        givefeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AfterLoginActivity.this,GiveFeedbackActivity.class);
                startActivity(i);
            }
        });

        cotactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AfterLoginActivity.this,ContactusActivity.class);
                startActivity(i);
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AfterLoginActivity.this,AboutUsActivity.class);
                startActivity(i);
            }
        });

        logoutcus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AfterLoginActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
