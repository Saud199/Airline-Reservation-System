package com.example.abc.emiratesairlines;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Abdul Ahad on 8/22/2017.
 */

public class mypagerAdapter extends FragmentStatePagerAdapter {


    ArrayList<Fragment>  mFragments;

    public mypagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){

            case 0:
                return "About Us";
            case 1:
                return "Emirates Video";
            case 2:
                return "About Developers";
            default:
                return "Unknown";

        }
    }
}