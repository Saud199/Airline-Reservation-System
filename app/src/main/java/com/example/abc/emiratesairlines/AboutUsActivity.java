package com.example.abc.emiratesairlines;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class AboutUsActivity extends AppCompatActivity {

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);



            mViewPager=(ViewPager)findViewById(R.id.myviewpager);

            ArrayList<Fragment> fragments= new ArrayList<>();

            FirstFragment firFstFragment=new FirstFragment();
            fragments.add(firFstFragment);

            SecondFragment secondFragment=new SecondFragment();
            fragments.add(secondFragment);

            ThirdFragment thirdFragment=new ThirdFragment();
            fragments.add(thirdFragment);


            mypagerAdapter mypagerAdapter=new mypagerAdapter(getSupportFragmentManager(),fragments);
            mViewPager.setAdapter(mypagerAdapter);

        }

    }

