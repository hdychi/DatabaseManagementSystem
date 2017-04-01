package com.example.administrator.databasemanagementsystem.Models;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/10/17.
 */
public class fragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList = new ArrayList<Fragment>();
    public fragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList){
        super((fragmentManager));
        this.fragmentList = fragmentList;
    }
    @Override
    public Fragment getItem(int position){
        return  fragmentList.get(position);
    }
    @Override
    public  int getCount(){
        return  fragmentList.size();
    }
}
