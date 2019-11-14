package com.example.abc.emiratesairlines;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class CheckTicketsActivity extends AppCompatActivity {

    ListView showlist;
    TextView textView;
    private ArrayList<String> bookflights = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_tickets);

        showlist=(ListView)findViewById(R.id.checkticket);
        textView=(TextView)findViewById(R.id.text1);

        viewTicket();

    }

    public void viewTicket() {

        textView.setText("No Ticket Booked");


        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference("Booked Flights");

        // Query queryRef = ref.orderByChild("Phone No");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(CheckTicketsActivity.this, android.R.layout.simple_list_item_1, bookflights);
                showlist.setAdapter(adapter);


                String name = String.valueOf(dataSnapshot.child("Name").getValue());
                String cnic = String.valueOf(dataSnapshot.child("Cnic").getValue());
                String cus_class = String.valueOf(dataSnapshot.child("Class").getValue());
                String flightno1 = String.valueOf(dataSnapshot.child("Flight No").getValue());
                String phno = String.valueOf(dataSnapshot.child("Phone No").getValue());





                String flightresult = "Name : " + name + "\n Cnic No : " + cnic + "\n Class : " + cus_class + "\n Flight No : " + flightno1 + "\n Phone No : " + phno + "\n";

                if (name.length() > 0) {

                    adapter.notifyDataSetChanged();
                    bookflights.add(flightresult);
                    textView.setText("");
                    adapter.notifyDataSetChanged();
                }

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
