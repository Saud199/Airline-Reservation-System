package com.example.abc.emiratesairlines;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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


import java.util.ArrayList;



public class ScheduleFlightsActivity extends AppCompatActivity {

    Button f_schedule;
    EditText f_num, f_day,f_month,f_year, f_time;
    Spinner f_from, f_to, f_timespin;
    TextView finfo;


    static int checker = 1;

    String number, day,month,year, time, from, to, t_spin, finaltime;

    ArrayList<String> FLarraylist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_flights);


        f_schedule = (Button) findViewById(R.id.btnschedule);
        f_num = (EditText) findViewById(R.id.flightnum);
        f_day = (EditText) findViewById(R.id.flightday);
        f_month = (EditText) findViewById(R.id.flightmonth);
        f_year = (EditText) findViewById(R.id.flightyear);
        f_time = (EditText) findViewById(R.id.flighttiming);
        f_from = (Spinner) findViewById(R.id.flightfrom);
        f_to = (Spinner) findViewById(R.id.flightto);
        f_timespin = (Spinner) findViewById(R.id.timeSpiner);
        finfo = (TextView) findViewById(R.id.info);

        f_schedule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                checker = 1;
                schedule();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(checker==1){
                            hand();
                        }else if(checker==3){
                            Toast.makeText(ScheduleFlightsActivity.this, "This Number of Flight Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 6000);



            }
        });


    }
    public void schedule() {

        if (f_num.getText().toString().length() != 0 && f_day.getText().toString().length() != 0 && f_time.getText().toString().length() != 0
                && f_month.getText().toString().length() != 0   && f_year.getText().toString().length() != 0) {


            number = f_num.getText().toString();
            day= f_day.getText().toString();
            month= f_month.getText().toString();
            year= f_year.getText().toString();

            time = f_time.getText().toString();
            String from = f_from.getSelectedItem().toString();
            String TO = f_to.getSelectedItem().toString();
            t_spin = f_timespin.getSelectedItem().toString();



            Double nt = Double.parseDouble(time);
            Double nd = Double.parseDouble(day);
            Double nm = Double.parseDouble(month);
            Double ny = Double.parseDouble(year);

            if (nt <= 0 || nt >= 13) {

                Toast.makeText(ScheduleFlightsActivity.this, "Enter Correct Time", Toast.LENGTH_SHORT).show();
                checker=2;
                //finfo.setText("ERROR :  Enter Correct Time");

            } else if (TO.equals(from)) {
                Toast.makeText(ScheduleFlightsActivity.this, "Select Correct Destintion", Toast.LENGTH_SHORT).show();
                checker=2;
                // finfo.setText("ERROR :  Select Correct Destintion");

            } else if (number.length() < 3 || number.length() > 3) {

                Toast.makeText(ScheduleFlightsActivity.this, "Flight number should equal to 3 digits", Toast.LENGTH_SHORT).show();
                checker=2;
                //finfo.setText("ERROR  :  Flight number should equal to 3 digits");

            } else if (nd <=0 || nd>=32 || nm<=0 || nm>=13 || ny!=2017 ||  (nm==2 && nd>=29)    ||(nm==4 && nd>=31 )  ||  (nm==6 && nd>=31 )   ||  (nm==9 && nd>=31 )   ||  (nm==11 && nd>=31 ) ) {
                //

                Toast.makeText(ScheduleFlightsActivity.this, "Enter Correct Date", Toast.LENGTH_SHORT).show();
                checker=2;
            }



            else {

                final FirebaseDatabase db = FirebaseDatabase.getInstance();
                final DatabaseReference ref = db.getReference("Flights");

                Query queryRef = ref.orderByChild("flight_Number").equalTo(number);


                queryRef.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        checker=3;
                        //   Toast.makeText(ScheduleFlightsActivity.this, "This Number of Flight Already Exists", Toast.LENGTH_SHORT).show();
                        //  finfo.setText("ERROR  : This Number of Flight Already Exists");
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
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }

        } else {

            Toast.makeText(ScheduleFlightsActivity.this, "Please fill out the field", Toast.LENGTH_SHORT).show();
            checker=2;
            //  finfo.setText("ERROR  : Please fill out All fields ");
        }
    }

    public void hand() {


        if (checker==1) {

            number = f_num.getText().toString();
            day = f_day.getText().toString();
            month = f_month.getText().toString();
            year = f_year.getText().toString();
            time = f_time.getText().toString();
            from = f_from.getSelectedItem().toString();
            to = f_to.getSelectedItem().toString();
            t_spin = f_timespin.getSelectedItem().toString();

            finaltime = time + t_spin;
            final String date=day + " - " + month + " - " + year;


            final FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference ref = db.getReference("Flights");


            ScheduleDetails scheduleDetails=new ScheduleDetails(number,date,finaltime,from,to);
            String key = ref.push().getKey();
            scheduleDetails.setKey(key);
            ref.child(key).setValue(scheduleDetails);


            //  finfo.setText("Flight Has Been Scheduled");
            Toast.makeText(ScheduleFlightsActivity.this, "Flight Has Been Scheduled", Toast.LENGTH_SHORT).show();



                 /*   final FirebaseDatabase db = FirebaseDatabase.getInstance();
                    final DatabaseReference ref = db.getReference("Flights");

                    String key = ref.push().getKey();

                    ref.child(key).child("Flight Number").setValue(number);
                    ref.child(key).child("Flight Time").setValue(finaltime);
                    ref.child(key).child("Flight Date").setValue(date);
                    ref.child(key).child("Flight From").setValue(from);
                    ref.child(key).child("Flight To").setValue(to);
                    ref.child(key).child("Flight Key").setValue(key);

                    Toast.makeText(ScheduleFlightsActivity.this, "Flight Has Been Scheduled", Toast.LENGTH_SHORT).show();
*/
        }
    }


}






                /*
               // Query queryRef = ref.orderByChild("Flight Number").equalTo(number);
               Query queryRef = ref.orderByChild("Flight Number");



                queryRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            int i=0;
                            FLarraylist.add(i,childSnapshot.child("Flight Number").getValue().toString());
                            i++;
                        }

                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                            if(!FLarraylist.contains(number)){

                                Fvalue="2";
                            }
                                else{

                                    Toast.makeText(getContext(), "Flight already been Scheduled", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

        }



               queryRef.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {

                        Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();

                          Fnum = String.valueOf(value.get("Flight Number"));


                         if (!Fnum.equals(number)){

                             Fvalue = "2";
                             Toast.makeText(getContext(), "This number of flight has already been scheduled", Toast.LENGTH_SHORT).show();

                        }
                        else {
                             Fvalue = "2";
                             Toast.makeText(getContext(), "This number of flight has already been scheduled", Toast.LENGTH_SHORT).show();
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
            }*/






 /*   void res(){



            //String container[] = new String[FLarraylist.size()];
           // container= FLarraylist.toArray(container);





            final FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference ref = db.getReference("Flights");


            final String date = f_date.getText().toString();
            String time = f_time.getText().toString();
            final String from = f_from.getSelectedItem().toString();
            final String TO = f_to.getSelectedItem().toString();
            String t_spin = f_timespin.getSelectedItem().toString();
            final String number = f_num.getText().toString();

            final String finaltime = time + t_spin;

            String key = ref.push().getKey();

            ref.child(key).child("Key").setValue(key);
            ref.child(key).child("Flight Number").setValue(number);
            ref.child(key).child("Flight Date").setValue(date);
            ref.child(key).child("Flight Time").setValue(finaltime);
            ref.child(key).child("Flight From").setValue(from);
            ref.child(key).child("Flight To").setValue(TO);


            Toast.makeText(getContext(), "Flight has been Scheduled", Toast.LENGTH_SHORT).show();
        }
    }


*/