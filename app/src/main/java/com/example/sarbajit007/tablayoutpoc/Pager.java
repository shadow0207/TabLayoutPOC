package com.example.sarbajit007.tablayoutpoc;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by sarbajit007 on 12/2/2017.
 */

public class Pager extends FragmentStatePagerAdapter {
    int tab_count;
    FragmentManager fm;
    public Pager(FragmentManager fm,int tab_count) {
        super(fm);
       this.fm=fm;
        this.tab_count=tab_count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Tab1 tab1=new Tab1();


                return tab1;
            case 1:
               Tab2 tab2=new Tab2();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tab_count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0: return "MAIN";
            case 1: return "SEARCH";

        }
        return null;
    }
}
