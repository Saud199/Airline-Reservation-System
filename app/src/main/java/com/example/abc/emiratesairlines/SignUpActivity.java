package com.example.abc.emiratesairlines;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "PhoneAuth";


    Button getcode,resend,verify;
    EditText gname,gphoneno,gcode,adminpass;
    Spinner acc_type;

    String adminKey="12345";

    String mykey;

    static int a=0;

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private FirebaseAuth fbAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        getcode = (Button) findViewById(R.id.get_ver_code);
        resend = (Button) findViewById(R.id.get_resend);
        verify = (Button) findViewById(R.id.verify_code_btn);
        gname = (EditText) findViewById(R.id.getname);
        gphoneno = (EditText) findViewById(R.id.getphoneno);
        gcode = (EditText) findViewById(R.id.ver_code);
        adminpass = (EditText) findViewById(R.id.admin_key);
        acc_type = (Spinner) findViewById(R.id.select_acc_type);


        getcode.setEnabled(true);
        verify.setEnabled(false);
        resend.setEnabled(false);

        fbAuth = FirebaseAuth.getInstance();

        acc_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = (String) adapterView.getItemAtPosition(i);

                if (value.equalsIgnoreCase( "Customer")) {
                    adminpass.setEnabled(false);
                    adminpass.setHint("");
                }

                else if (value.equalsIgnoreCase("Staff")) {
                    adminpass.setEnabled(true);
                    adminpass.setHint("Enter Admin Key");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adminpass.setHint("");
            }
        });


        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a=0;
                if (!TextUtils.isEmpty(gphoneno.getText().toString()) && gphoneno.getText().toString().length() != 13) {
                    gphoneno.setError("Invalid Phone Number");
                    a=1;
                }

               else if (acc_type.getSelectedItem().toString().equalsIgnoreCase("Staff") && adminpass.getText().toString().isEmpty()) {
                    adminpass.setError("Enter Admin Key");
                    a=1;
                }

               else if (acc_type.getSelectedItem().toString().equalsIgnoreCase("Staff") && !adminpass.getText().toString().equals("12345")) {
                    adminpass.setError("Invalid Key");
                    a=1;
                }

                else {
                    search();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(a==0){
                                sendCode();
                            }
                            else if(a==2){

                                Toast.makeText(SignUpActivity.this, "Phone Number Already Exists", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }, 6000);
                }
            }
        });


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendCode();
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode();
            }
        });


    }




    public void sendCode() {

        String phoneNumber = gphoneno.getText().toString().trim();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);
    }


    public void resendCode() {

        String phoneNumber = gphoneno.getText().toString().trim();

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }



    public void verifyCode() {

        String code = gcode.getText().toString();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }


    private void setUpVerificatonCallbacks() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                resend.setEnabled(false);
                verify.setEnabled(true);
                gcode.setText("" + credential.getSmsCode());
                signInWithPhoneAuthCredential(credential);



            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.d(TAG, "Invalid credential: "
                            + e.getLocalizedMessage());
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // SMS quota exceeded
                    Log.d(TAG, "SMS Quota exceeded.");
                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                phoneVerificationId = verificationId;
                resendToken = token;
                verify.setEnabled(true);
                getcode.setEnabled(false);
                resend.setEnabled(true);
            }
        };
    }


    private void signInWithPhoneAuthCredential(  final  PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            gcode.setText("" + credential.getSmsCode());
                            resend.setEnabled(false);
                            verify.setEnabled(true);

                            String name=gname.getText().toString();
                            String phone=gphoneno.getText().toString();
                            String type=acc_type.getSelectedItem().toString();

                            Information info=new Information(name,phone,type);

                            final FirebaseDatabase db = FirebaseDatabase.getInstance();
                            final DatabaseReference ref = db.getReference("Message");

                            String skey =  ref.push().getKey();
                            ref.child(skey).setValue(info);


                            Toast.makeText(SignUpActivity.this,"Account Created",Toast.LENGTH_SHORT).show();

                            Intent i=new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(i);


                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(SignUpActivity.this, "Enter Valid Code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }


    public void search() {

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference ref = db.getReference("Message");

        final String Findnum = gphoneno.getText().toString().trim();

        a=0;

        Query queryRef = ref.orderByChild("number").equalTo(Findnum);

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {

                //Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                //String num = String.valueOf(value.get("number"));

                a = 2;


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