package com.example.abc.emiratesairlines;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static android.R.attr.key;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {


    VideoView mVideoView;
    Button sbtn,pbtn,rbtn;


    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        mVideoView = (VideoView) view.findViewById(R.id.video_view);
        sbtn=(Button)view.findViewById(R.id.btnstart);
        pbtn=(Button)view.findViewById(R.id.btnpause);



        sbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        String path = "android.resource://com.example.abc.emiratesairlines/" + R.raw.emirates;
                                        Uri uri2 = Uri.parse(path);
                                        mVideoView.setVideoURI(uri2);
                                        mVideoView.start();


                                    }
                                }
        );


        pbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        String path = "android.resource://com.example.abc.emiratesairlines/" + R.raw.emirates;
                                        Uri uri2 = Uri.parse(path);
                                        mVideoView.setVideoURI(uri2);
                                        mVideoView.pause();


                                    }
                                }
        );



        return view;
    }


}