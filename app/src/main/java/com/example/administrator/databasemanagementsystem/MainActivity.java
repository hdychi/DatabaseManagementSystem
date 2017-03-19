package com.example.administrator.databasemanagementsystem;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private String databasePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = new File(Environment.getExternalStorageDirectory()+"/databaseManagement");
        if(!file.exists()){
            try{
                if(!file.mkdirs()){
                    System.out.println("创建失败");
                }

            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println("创建文件异常");
            }
        }
        databasePath = Environment.getExternalStorageDirectory()+"/databaseManagement/"+"data.db";

        database = SQLiteDatabase.openOrCreateDatabase(databasePath,null);
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
                "                 constraint FKCourId foreign key(courId) references Course(courId) on delete cascade\n" +
                "                 )";
        database.execSQL(str1);
        database.execSQL(str2);
        database.execSQL(str3);

    }
}
