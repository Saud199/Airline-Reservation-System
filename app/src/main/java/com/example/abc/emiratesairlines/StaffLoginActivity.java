package com.example.abc.emiratesairlines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StaffLoginActivity extends AppCompatActivity {

    Button scheduleFlights,checkTickets,chkfdb,logoutstaff,cancelFlights,viewFlights,sendNotificationBtn;
    TextView loginIDstaff;


    public void onBackPressed() {
        Toast.makeText(StaffLoginActivity.this,"Please Log out to go back",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);


        scheduleFlights = (Button) findViewById(R.id.scheduleflight_btn);
        cancelFlights = (Button) findViewById(R.id.cancelflight_btn);
        checkTickets = (Button) findViewById(R.id.detailtick_btn);
        logoutstaff = (Button) findViewById(R.id.logoutst_btn);
        chkfdb = (Button) findViewById(R.id.checkfeeback_btn);
        viewFlights = (Button) findViewById(R.id.view_flights_btn);
        //sendNotificationBtn = (Button) findViewById(R.id.send_notification_btn);
        loginIDstaff = (TextView) findViewById(R.id.login_id_staff);

        loginIDstaff.setText("Logged In As : "+CustomerInfo.name);


        scheduleFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StaffLoginActivity.this , ScheduleFlightsActivity.class);
                startActivity(i);
            }
        });

        checkTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StaffLoginActivity.this , CheckTicketsActivity.class);
                startActivity(i);
            }
        });

        chkfdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StaffLoginActivity.this , CheckFeedBackActivity.class);
                startActivity(i);
            }
        });

        cancelFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StaffLoginActivity.this , CancelFlightsActivity.class);
                startActivity(i);
            }
        });

        viewFlights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StaffLoginActivity.this , ViewFlightsActivity.class);
                startActivity(i);
            }
        });

        /*sendNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StaffLoginActivity.this , SendNotificationActivity.class);
                startActivity(i);
            }
        });*/

        logoutstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StaffLoginActivity.this , LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
