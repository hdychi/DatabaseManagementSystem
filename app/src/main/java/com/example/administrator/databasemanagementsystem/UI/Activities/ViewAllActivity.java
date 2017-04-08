package com.example.administrator.databasemanagementsystem.UI.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.administrator.databasemanagementsystem.Models.fragmentAdapter;
import com.example.administrator.databasemanagementsystem.R;
import com.example.administrator.databasemanagementsystem.UI.Fragments.AllChooseFragment;
import com.example.administrator.databasemanagementsystem.UI.Fragments.AllCourseFragment;
import com.example.administrator.databasemanagementsystem.UI.Fragments.AllStudentFragment;
import com.example.administrator.databasemanagementsystem.UI.Fragments.CourseFragment;
import com.example.administrator.databasemanagementsystem.UI.Fragments.StudentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/8.
 */

public class ViewAllActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private com.example.administrator.databasemanagementsystem.Models.fragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private List<String> tabs;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savadInstance){
        super.onCreate(savadInstance);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.lightBlue));
        setContentView(R.layout.view_all_activity);
        mToolbar = (Toolbar)findViewById(R.id.viewAlltoolbar);
        tabLayout = (TabLayout)findViewById(R.id.viewAlltablayout);
        viewPager = (ViewPager)findViewById(R.id.viewAllviewPager);

        mToolbar.setTitle("查看所有记录");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initialFragments();
    }
    public void initialFragments(){
        tabs = new ArrayList<>();
        tabs.add("所有学生");
        tabs.add("所有课程");
        tabs.add("所有选课");
        fragmentList = new ArrayList<Fragment>();
        AllStudentFragment studentFragment = new AllStudentFragment();
        AllCourseFragment courseFragment = new AllCourseFragment();
        AllChooseFragment chooseFragment = new AllChooseFragment();
        fragmentList.add(studentFragment);
        fragmentList.add(courseFragment);
        fragmentList.add(chooseFragment);
        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new fragmentAdapter(fm, fragmentList);

        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < 3; i++) {
            tabLayout.getTabAt(i).setText(tabs.get(i));
        }

    }
}
