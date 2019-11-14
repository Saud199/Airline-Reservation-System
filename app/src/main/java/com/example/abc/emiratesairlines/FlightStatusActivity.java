package com.example.abc.emiratesairlines;

 import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;

        import java.util.ArrayList;

public class FlightStatusActivity extends AppCompatActivity {

    Button submit;
   // EditText ph_no;
    TextView textView;
    ListView showbooklist;

    String flightno,from,to;

    private ArrayList<String> bookflights = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_status);

    //    submit=(Button)findViewById(R.id.submitbtn);
      //  ph_no=(EditText)findViewById(R.id.ticket_detail);
        textView=(TextView)findViewById(R.id.text);
        showbooklist=(ListView)findViewById(R.id.showmyflight);


        viewFlights();


    }


    public void viewFlights() {

        textView.setText("No Flights Available");



            final FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference ref = db.getReference("Booked Flights");

            Query queryRef = ref.orderByChild("Phone No").equalTo(CustomerInfo.no);

            queryRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(FlightStatusActivity.this, android.R.layout.simple_list_item_1, bookflights);
                    showbooklist.setAdapter(adapter);


                    String name = String.valueOf(dataSnapshot.child("Name").getValue());
                    String cnic = String.valueOf(dataSnapshot.child("Cnic").getValue());
                    String cus_class = String.valueOf(dataSnapshot.child("Class").getValue());
                    String flightno1 = String.valueOf(dataSnapshot.child("Flight No").getValue());
                    String phno = String.valueOf(dataSnapshot.child("Phone No").getValue());

                    flightno=flightno1;

                //    flightdetails();  From : " + from + "\n To : " + to + "\n";


                    String flightresult = "Name : " + name + "\n CNIC No : " + cnic + "\n Class : " + cus_class + "\n Flight No : " + flightno1 + "\n Phone No : " + phno + "\n";

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




    void flightdetails(){


        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference("Flights");

        Query queryRef = ref.orderByChild("flight_Number").equalTo(flightno);

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {



               String sto = String.valueOf(dataSnapshot.child("to").getValue());
              String  sfrom = String.valueOf(dataSnapshot.child("from").getValue());

                to=sto;
                from=sfrom;

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





