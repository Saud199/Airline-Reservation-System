package com.example.abc.emiratesairlines;



        import android.app.NotificationManager;
        import android.content.Context;
        import android.support.v4.app.NotificationCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import java.util.ArrayList;

public class CancelFlightsActivity extends AppCompatActivity  {


    ListView listView;

    TextView nof;

    final FirebaseDatabase db = FirebaseDatabase.getInstance();
    final DatabaseReference ref = db.getReference("Flights");





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_flights);

        listView = (ListView)findViewById(R.id.messagelist);
        nof = (TextView) findViewById(R.id.no_flights_cancel);

        abc();

    }

    public void abc(){


        final ArrayList<ScheduleDetails> amessage = new ArrayList<>();

        final CustomAdapter adp=new CustomAdapter(this, amessage, new ScheduleDelListner() {


            @Override
            public void onDel(ScheduleDetails sd) {

                ref.child(sd.getKey()).removeValue();

            }
        });

        listView.setAdapter(adp);


        nof.setText("No Flights Available");

    ref.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {


            ScheduleDetails mes=dataSnapshot.getValue(ScheduleDetails.class);
            amessage.add(mes);
            adp.notifyDataSetChanged();
            nof.setText("");



        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {




        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

            ScheduleDetails ms=dataSnapshot.getValue(ScheduleDetails.class);
            amessage.remove(ms);
            adp.notifyDataSetChanged();


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


