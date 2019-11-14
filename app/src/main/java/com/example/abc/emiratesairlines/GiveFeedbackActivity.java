package com.example.abc.emiratesairlines;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GiveFeedbackActivity extends AppCompatActivity {

    EditText feedback;
    Button submit_btn;

    final FirebaseDatabase db = FirebaseDatabase.getInstance();
    final DatabaseReference ref = db.getReference("Feedbacks");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_feedback);

        feedback = (EditText) findViewById(R.id.write_feedback);
        submit_btn = (Button) findViewById(R.id.submit_feedback);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String sfeedback = feedback.getText().toString();

                if(sfeedback.length()==0){

                    Toast.makeText(GiveFeedbackActivity.this,"Please write something",Toast.LENGTH_SHORT).show();

                }else {

                    String cname = CustomerInfo.name;
                    String cno = CustomerInfo.no;



                    String key = ref.push().getKey();

                    ref.child(key).child("name").setValue(cname);
                    ref.child(key).child("phone").setValue(cno);
                    ref.child(key).child("feedback").setValue(sfeedback);

                    Toast.makeText(GiveFeedbackActivity.this, "Feedback Sent", Toast.LENGTH_SHORT).show();

                }
            }
        });






    }
}
