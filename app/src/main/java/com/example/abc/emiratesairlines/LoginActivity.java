package com.example.abc.emiratesairlines;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth fbAuth;

    Button mlogin,msignup;
    EditText username,phoneNumber;

    int cnt=1;

    String newphoneNumber;

    final FirebaseDatabase db = FirebaseDatabase.getInstance();
    final DatabaseReference ref = db.getReference("Message");




    public void onBackPressed() {
        Toast.makeText(LoginActivity.this,"Press Home Button To Go Back",Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        fbAuth = FirebaseAuth.getInstance();



        mlogin=(Button)findViewById(R.id.xlogin);
        msignup=(Button)findViewById(R.id.xsignup);
        username = (EditText) findViewById(R.id.login_name);
        phoneNumber = (EditText) findViewById(R.id.login_phone_number);




        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkConnected()==true) {
                    Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(i);
                }else{

                    Toast.makeText(LoginActivity.this,"Connect with Internet to continue",Toast.LENGTH_SHORT).show();
                }
            }
        });


        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cnt=1;

                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(phoneNumber.getText().toString())) {
                    Toast.makeText(LoginActivity.this,"Please fill out the fields",Toast.LENGTH_SHORT).show();
                    cnt=0;
                }
                else if(isNetworkConnected()==true) {
                    search();
                }else{
                    Toast.makeText(LoginActivity.this,"Connect with Internet to continue",Toast.LENGTH_SHORT).show();
                    cnt=0;
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(cnt==1){
                            Toast.makeText(LoginActivity.this,"Invalid name and number ",Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 3000);


            }
        });


    }


    public void search() {

        final String Findname1 = username.getText().toString().trim();
        final String Findnum1 = phoneNumber.getText().toString().trim();

        final StringBuilder userphoneNumber =new StringBuilder(Findnum1);

        if (!Findnum1.contains("+")) {
            userphoneNumber.deleteCharAt(0);
            userphoneNumber.insert(0,"+92");
            newphoneNumber = userphoneNumber.toString();

            cnt=1;

            Query queryRef = ref.orderByChild("number").equalTo(newphoneNumber);

            queryRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {

                    Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                    String name = String.valueOf(value.get("name"));
                    String num=String.valueOf(value.get("number"));
                    String accounttype=String.valueOf(value.get("accountType"));


                    if (num.equals(newphoneNumber) && name.equalsIgnoreCase(Findname1)) {

                        if (accounttype.equals("Customer")) {
                            CustomerInfo cusinfo = new CustomerInfo(name , num);
                            Intent ci = new Intent(LoginActivity.this,AfterLoginActivity.class);
                            startActivity(ci);
                            cnt=0;

                        }

                        else  {
                            CustomerInfo cusinfo = new CustomerInfo(name , num);
                            Intent si = new Intent(LoginActivity.this,StaffLoginActivity.class);
                            startActivity(si);
                            cnt=0;

                        }

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

        else {

            cnt=1;

            Query queryRef = ref.orderByChild("number").equalTo(Findnum1);

            queryRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {

                    Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                    String name = String.valueOf(value.get("name"));
                    String num=String.valueOf(value.get("number"));
                    String accounttype=String.valueOf(value.get("accountType"));


                    if (num.equals(Findnum1) && name.equalsIgnoreCase(Findname1)) {

                        if (accounttype.equals("Customer")) {
                            CustomerInfo cusinfo = new CustomerInfo(name , num);
                            Intent ci = new Intent(LoginActivity.this,AfterLoginActivity.class);
                            startActivity(ci);
                            cnt=0;
                        }

                        else {
                            CustomerInfo cusinfo = new CustomerInfo(name , num);
                            Intent si = new Intent(LoginActivity.this,StaffLoginActivity.class);
                            startActivity(si);
                            cnt=0;
                        }

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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }



}