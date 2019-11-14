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

import java.util.ArrayList;

public class ViewFlightsActivity extends AppCompatActivity {

    ListView flightList;
    TextView counter;


    private ArrayList<String> flightnames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flights);


        flightList = (ListView) findViewById(R.id.scheduledflight_stafflist);
        counter = (TextView) findViewById(R.id.flight_info_detail);


        viewFlights();

    }



    public void viewFlights() {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Flights");

        counter.setText("No Flights Available");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewFlightsActivity.this, android.R.layout.simple_list_item_1, flightnames);
                flightList.setAdapter(adapter);


                String date = String.valueOf(dataSnapshot.child("flight_date").getValue());
                String from = String.valueOf(dataSnapshot.child("from").getValue());
                String no = String.valueOf(dataSnapshot.child("flight_Number").getValue());
                String time = String.valueOf(dataSnapshot.child("flight_Time").getValue());
                String to = String.valueOf(dataSnapshot.child("to").getValue());

                String flightresult="From : "+from+" ----> " + "To : " + to + "\n" + "Flight Number : EK-" + no + "\n" + "Time : "+time + "\n" + "Date : "+date;

                if(date.length() > 0) {

                    adapter.notifyDataSetChanged();
                    flightnames.add(flightresult);
                    counter.setText("");
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
