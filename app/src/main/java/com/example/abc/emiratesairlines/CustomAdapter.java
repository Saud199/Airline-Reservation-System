package com.example.abc.emiratesairlines;


        import android.content.Context;
        import android.content.DialogInterface;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AlertDialog;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.util.ArrayList;

/**
 * Created by Abdul Ahad on 10/29/2017.
 */

public class CustomAdapter extends ArrayAdapter<ScheduleDetails>  {

    ArrayList<ScheduleDetails> messages;
    ScheduleDelListner scheduleDelListner;
    public CustomAdapter(Context context,  ArrayList<ScheduleDetails> sdl, ScheduleDelListner mdl) {
        super(context, 0, sdl);
        this.scheduleDelListner=mdl;
        this.messages=sdl;
    }





    @NonNull
    @Override
    public View getView(int pos, @NonNull View view, @NonNull ViewGroup viewGroup) {

        if(view==null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.customlayout, viewGroup, false);
        }


        TextView name=(TextView)view.findViewById(R.id.tname);
        TextView msg=(TextView)view.findViewById(R.id.tmsg);
        Button del=(Button)view.findViewById(R.id.btndel);

        final ScheduleDetails sds=messages.get(pos);

        name.setText("Flight Number :  EK-"+sds.getFlight_Number());
        msg.setText("Date : "+sds.getFlight_date());

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                builder.setTitle("Cancel Flight");
                builder.setMessage("Are you sure you want to cancel this flight ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scheduleDelListner.onDel(sds);
                        Toast.makeText(getContext(),"Flight has been canceled",Toast.LENGTH_SHORT).show();
                        
                    }
                });

                builder.setNegativeButton("No" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.create().show();

            }
        });


        return view;
    }
}
