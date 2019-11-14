package com.example.abc.emiratesairlines;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookFlightActivity extends AppCompatActivity {

    EditText et_name,et_cnic;
    Spinner  s_class,flightAvailable;
    Button bookbtn;
    TextView ticketPrice;

    static int cnt1=0;


    ArrayList<String> booklist = new ArrayList<String>();

    String bookflight[] = new String[booklist.size()];

    String name,cnic;

    String v_name,v_cnic,v_flight;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_flight);

        et_name = (EditText) findViewById(R.id.id_name);
        et_cnic = (EditText) findViewById(R.id.id_cnic);

        s_class = (Spinner) findViewById(R.id.id_class);
        flightAvailable = (Spinner) findViewById(R.id.id_flight_available);
        bookbtn = (Button) findViewById(R.id.buy_ticket);

        ticketPrice = (TextView) findViewById(R.id.ticket_price);

        getFlight();


        String clas = s_class.getSelectedItem().toString();
        //String flightAvailablestring = flightAvailable.getSelectedItem().toString();

        s_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = (String) adapterView.getItemAtPosition(i);

                if (value.equalsIgnoreCase( "FIRST CLASS")) {
                    ticketPrice.setText("Ticket Price : AED 4000");
                }

                else if (value.equalsIgnoreCase("BUSINESS CLASS")) {
                    ticketPrice.setText("Ticket Price : AED 3000");
                }

                else if (value.equalsIgnoreCase("ECONOMY CLASS")) {
                    ticketPrice.setText("Ticket Price : AED 2000");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ticketPrice.setText("");
            }
        });

        bookbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cnt1=0;
                        viewFlights();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //    if ((v_name.equalsIgnoreCase(name) && v_cnic.equals(cnic) && v_flight.equalsIgnoreCase(flightAvailable.getSelectedItem().toString())) || v_cnic.equals(cnic)){

                                if(cnt1==1){

                                    Toast.makeText(BookFlightActivity.this, "Your flight has Already been Scheduled", Toast.LENGTH_SHORT).show();

                                }else{

                                    booking();
                                }
                            }
                        }, 5000);

                    }
                });
    }



    public void booking(){
        name=et_name.getText().toString();
        cnic=et_cnic.getText().toString();

        String no=flightAvailable.getSelectedItem().toString();

        if(no.equalsIgnoreCase("Flights") ){
            Toast.makeText(this,"No Flights Available",Toast.LENGTH_SHORT).show();

        }

        else if(name.length()==0 || cnic.length()==0 || cnic.length()<13  ){
            Toast.makeText(this,"Fill Data Correctly",Toast.LENGTH_SHORT).show();

        }
        else{

            final FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference ref = db.getReference("Booked Flights");


            String key=ref.push().getKey();
            String flno=flightAvailable.getSelectedItem().toString();
            String flclass=s_class.getSelectedItem().toString();


            ref.child(key).child("Name").setValue(name);
            ref.child(key).child("Cnic").setValue(cnic);
            ref.child(key).child("Phone No").setValue(CustomerInfo.no);
            ref.child(key).child("Flight No").setValue(flno);
            ref.child(key).child("Class").setValue(flclass);
            ref.child(key).child("Key").setValue(key);

            Toast.makeText(BookFlightActivity.this,"Your flight has been booked",Toast.LENGTH_SHORT).show();


        }
    }



    public void getFlight () {



        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference("Flights");

        Query queryRef = ref.orderByChild("flight_Number");



        booklist.add(0, "Flights");

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    int i = 0;

                    String s=childSnapshot.child("flight_Number").getValue().toString();

                    booklist.add(i, "EK-"+s);

                    i++;
                }


                bookflight= booklist.toArray(bookflight);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(BookFlightActivity.this,android.R.layout.simple_spinner_dropdown_item,bookflight);
                flightAvailable.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }


    public void viewFlights() {

        name=et_name.getText().toString();
        cnic=et_cnic.getText().toString();


        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference("Booked Flights");

        Query queryRef = ref.orderByChild("Cnic").equalTo(cnic);

        cnt1=0;

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String name = String.valueOf(dataSnapshot.child("Name").getValue());
                String cnic = String.valueOf(dataSnapshot.child("Cnic").getValue());
                String flightno1 = String.valueOf(dataSnapshot.child("Flight No").getValue());

                v_name=name;
                v_cnic=cnic;
                v_flight=flightno1;

                cnt1=1;

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
