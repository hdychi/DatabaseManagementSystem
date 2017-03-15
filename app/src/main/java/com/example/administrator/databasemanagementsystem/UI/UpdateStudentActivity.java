package com.example.administrator.databasemanagementsystem.UI;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.Models.ChooseCourse;
import com.example.administrator.databasemanagementsystem.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public class UpdateStudentActivity extends Activity{
    private Button comfirmButton;
    private EditText stdId;
    private EditText stdName;
    private  EditText stdGender;
    private EditText stdAge;
    private EditText stdYear;
    private EditText stdClass;

    private String originID;
    private String originName;
    private  String originGender;
    private int originAge;
    private  int originYear;
    private String originClass;

    private String databasePath;
    private SQLiteDatabase db;
    private DataBaseHelper helper;
    @Override
    protected void onCreate(Bundle savadInstance){
        super.onCreate(savadInstance);
        setContentView(R.layout.update_student_layout);

        stdId = (EditText)findViewById(R.id.updateStdId);
        stdName = (EditText)findViewById(R.id.updateStdName);
        stdGender = (EditText)findViewById(R.id.updatesStdIGender);
        stdAge = (EditText)findViewById(R.id.updateStdAge);
        stdYear = (EditText)findViewById(R.id.updateStdInYear);
        stdClass = (EditText)findViewById(R.id.updateStdClass);
        comfirmButton = (Button)findViewById(R.id.updateStdConfirmButton);


        originID = (String)savadInstance.get("stdId");
        originName = (String)savadInstance.get("stdName");
        originGender = (String)savadInstance.get("stdGender");
        originAge = Integer.valueOf((String)savadInstance.get("stdAge"));
        originYear = Integer.valueOf((String)savadInstance.get("stdYear"));
        originClass = (String)savadInstance.get("stdClass");
        stdId.setText(originID);
        stdName.setText(originName);
        stdGender.setText(originGender);
        stdAge.setText(originAge);
        stdYear.setText(originYear);
        stdClass.setText(originClass);
        databasePath = Environment.getExternalStorageDirectory()+"/databaseManagement/"+"data.db";
        db = SQLiteDatabase.openOrCreateDatabase(databasePath,null);
        helper = new DataBaseHelper(db);
        comfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateID(stdId.getText().toString());
                updateName(stdName.getText().toString());
                updateGender(stdGender.getText().toString());
            }
        });
    }
    public void updateID(String value){
        if(!value.equals(originID)){
            List<ChooseCourse> chooseCourses =  helper.getChooseCourseWithStudent(originID);
           for(ChooseCourse chooseCourse:chooseCourses){
               helper.deleteChoose(chooseCourse.getStdId(),chooseCourse.getCourId());
           }
            helper.updateStudent("stdId",value,originID);
        }
    }
    public void updateName(String name){
        if(!name.equals(originName)){
            helper.updateStudent("stdName",name,originID);
        }
    }
    public void updateGender(String gender){
        if(!gender.equals(originGender)){
            helper.updateStudent("stdGender",gender,originID);
        }
    }
    public void updateAge(int age){
        if(age!=originAge){
            helper.updateStudent("stdAge",age,originID);
        }
    }
    public void updateYear(int year){
        if(year!=originYear){
            helper.updateStudent("stdInYear",year,originID);
        }
    }
    public void updateClass(String inClass){
        if(inClass!=originClass){
            helper.updateStudent("stdClass",inClass,originID);
        }
    }
}
