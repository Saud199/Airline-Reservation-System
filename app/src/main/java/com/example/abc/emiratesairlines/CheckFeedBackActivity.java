package com.example.abc.emiratesairlines;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.id;

public class CheckFeedBackActivity extends AppCompatActivity {


    Button deleteFeedbacks;
    ListView feedbackList;
    TextView textView,emptyFeedback;




    private ArrayList<String> musername = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_feed_back);

        deleteFeedbacks = (Button) findViewById(R.id.deletefeedback_btn);
        feedbackList = (ListView) findViewById(R.id.feedback_list);
        textView=(TextView) findViewById(R.id.text);
        emptyFeedback=(TextView) findViewById(R.id.emptyfeedbaks);

        view();

        deleteFeedbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(CheckFeedBackActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Delete Feedbacks");
                builder.setMessage("Are you sure you want to delete all feedbacks ?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
                        ref.child("Feedbacks").removeValue();
                        Toast.makeText(CheckFeedBackActivity.this,"All Feedbacks are Deleted",Toast.LENGTH_SHORT).show();
                        musername.add("");
                        //finish();


                    }
                });

                builder.setNegativeButton("Cancel" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(CheckFeedBackActivity.this,"Feedbacks not deleted",Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                });

                builder.create().show();

            }

        });


    }


    public void view() {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Feedbacks");

        emptyFeedback.setText("No Feedbacks yet");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                final  ArrayAdapter<String> adapter = new ArrayAdapter<String>(CheckFeedBackActivity.this, android.R.layout.simple_list_item_1, musername);
                feedbackList.setAdapter(adapter);


                String name = String.valueOf(dataSnapshot.child("name").getValue());
                String no = String.valueOf(dataSnapshot.child("phone").getValue());
                String feeds = String.valueOf(dataSnapshot.child("feedback").getValue());

               String result="Name : "+name+"\n" + "Phone No : " + no + "\n" + "Feedback : " + feeds;


                if(name.length() > 0) {

                    musername.add(result);
                    emptyFeedback.setText("");
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

