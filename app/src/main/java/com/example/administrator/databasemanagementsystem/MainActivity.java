package com.example.administrator.databasemanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.administrator.databasemanagementsystem.Models.fragmentAdapter;
import com.example.administrator.databasemanagementsystem.UI.CourseFragment;
import com.example.administrator.databasemanagementsystem.UI.InsertChooseAcrivity;
import com.example.administrator.databasemanagementsystem.UI.InsertCourseActivity;
import com.example.administrator.databasemanagementsystem.UI.InsertStudentActivity;
import com.example.administrator.databasemanagementsystem.UI.StudentFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private String databasePath;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button addButton;
    private fragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private List<String> tabs;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.lightBlue));

        setContentView(R.layout.activity_main);
        tabs = new ArrayList<>();
        initialViews();
        initialDatabase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载菜单文件
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    public void initialViews() {
        /*addButton = (Button)findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this,v);
                final MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.main_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent();
                        if(item.getItemId()==R.id.menu_insert_student){
                            intent.setClass(MainActivity.this, InsertStudentActivity.class);
                            startActivity(intent);
                        }
                        else if(item.getItemId()==R.id.menu_insert_course){
                            intent.setClass(MainActivity.this, InsertCourseActivity.class);
                            startActivity(intent);
                        }
                        else{
                            intent.setClass(MainActivity.this, InsertChooseAcrivity.class);
                            startActivity(intent);
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });*/
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("学生选课信息管理系统");

        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.add) {
                    PopupMenu popupMenu = new PopupMenu(MainActivity.this, findViewById(R.id.add));


                    final MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.main_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Intent intent = new Intent();
                            if (item.getItemId() == R.id.menu_insert_student) {
                                intent.setClass(MainActivity.this, InsertStudentActivity.class);
                                startActivity(intent);
                            } else if (item.getItemId() == R.id.menu_insert_course) {
                                intent.setClass(MainActivity.this, InsertCourseActivity.class);
                                startActivity(intent);
                            } else {
                                intent.setClass(MainActivity.this, InsertChooseAcrivity.class);
                                startActivity(intent);
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
                return true;
            }
        });


        tabs.add("学生查询");
        tabs.add("课程查询");
        fragmentList = new ArrayList<Fragment>();
        StudentFragment studentFragment = new StudentFragment();
        CourseFragment courseFragment = new CourseFragment();
        fragmentList.add(studentFragment);
        fragmentList.add(courseFragment);
        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new fragmentAdapter(fm, fragmentList);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < 2; i++) {
            tabLayout.getTabAt(i).setText(tabs.get(i));
        }


    }

    public void initialDatabase() {
        File file = new File(Environment.getExternalStorageDirectory() + "/databaseManagement");
        if (!file.exists()) {
            try {
                if (!file.mkdirs()) {
                    System.out.println("创建失败");
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("创建文件异常");
            }
        }
        databasePath = Environment.getExternalStorageDirectory() + "/databaseManagement/" + "data.db";

        database = SQLiteDatabase.openOrCreateDatabase(databasePath, null);

        String str1 = "create table if not exists Student(stdId char(10),stdName varchar(30),\n" +
                "                stdGender varchar(10),stdAge integer,\n" +
                "                stdInYear integer,stdClass varchar(10),\n" +
                "                primary key(stdId),\n" +
                "                constraint validGender check(stdGender in ('男','女')),\n" +
                "\t\t\t\tconstraint validAge check(stdAge>=10 and stdAge<=50))";
        String str2 = "create table if not exists Course(courId char(7),courName varchar(20),courTeacherName varchar(30),\n" +
                "                 courCredit integer,courMinGrade integer,courCancelYear integer,\n" +
                "                 primary key(courId))";
        String str3 = "create table if not exists ChooseCourse(stdId char(10),courId char(7),\n" +
                "\t\t\t\t chooseYear integer,grade integer,\n" +
                "                 \n" +
                "                 constraint PKChooseCourse primary key(stdId,courId,chooseYear),\n" +
                "\t\t\t\t constraint FKStdId foreign key(stdId) references Student(stdId) on delete cascade on update cascade,\n" +
                "                 constraint FKCourId foreign key(courId) references Course(courId) on delete cascade on update cascade\n" +
                "                 )";

        database.execSQL(str1);
        database.execSQL(str2);
        database.execSQL(str3);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
