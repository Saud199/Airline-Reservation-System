package com.example.abc.emiratesairlines;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SendNotificationActivity extends AppCompatActivity {

    EditText flightNo,myMessage;
    Button notybutton;

    String finalMessage;
    String flightNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        flightNo = (EditText) findViewById(R.id.cancel_flight_no);
        myMessage = (EditText) findViewById(R.id.cancel_msg);
        notybutton = (Button) findViewById(R.id.send_noty);


        finalMessage = myMessage.getText().toString();
        flightNum = flightNo.getText().toString();


        notybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //flightdetails();
                sendNotification();
            }
        });



    }


    public void sendNotification() {
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Emirates Airlines")
                .setContentText(""+myMessage.getText().toString());

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0,mBuilder.build());

    }


    void flightdetails(){


        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference("Booked Flights");

        String finalflight="EK-"+flightNum;

        Query queryRef = ref.orderByChild("Flight No").equalTo(finalflight);

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                sendNotification();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }



}
