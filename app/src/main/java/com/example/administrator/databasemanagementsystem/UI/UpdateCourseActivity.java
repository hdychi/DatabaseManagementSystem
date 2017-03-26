package com.example.administrator.databasemanagementsystem.UI;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.R;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/3/17.
 */

public class UpdateCourseActivity extends Activity{
    private Button comfirmButton;
    private EditText courId;
    private EditText courName;
    private EditText courTeacherName;
    private EditText courCredit;
    private EditText courMinGrade;
    private EditText courCancelYear;
    private  String originCourId;
    private String originCourName;
    private String originCourTeacherName;
    private int originCourCredit;
    private int originCourMinGrade;
    private int originCourCancelYear;

    private String databasePath;
    private SQLiteDatabase db;
    private DataBaseHelper helper;
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.update_course_layout);

        courId = (EditText)findViewById(R.id.updateCourId);
        courName = (EditText)findViewById(R.id.updateCourName);
        courTeacherName = (EditText)findViewById(R.id.updatesCourTeacher);
        courCredit = (EditText)findViewById(R.id.updateCourCredit);
        courMinGrade = (EditText)findViewById(R.id.updateCourGrade);
        courCancelYear = (EditText)findViewById(R.id.updateCourYear);
        comfirmButton = (Button)findViewById(R.id.updateCourConfirmButton);

        originCourId = (String)savedInstance.get("courId");
        originCourName = (String)savedInstance.get("courName");
        originCourTeacherName = (String)savedInstance.get("courTeacherName");
        originCourCredit = Integer.valueOf((String)savedInstance.get("courCredit"));
        originCourMinGrade =  Integer.valueOf((String)savedInstance.get("courMinGrade"));
        originCourCancelYear = Integer.valueOf((String)savedInstance.get("courCancelYear"));

        courId.setText(originCourId);
        courName.setText(originCourName);
        courTeacherName.setText(originCourTeacherName);
        courCredit.setText(originCourCredit);
        courMinGrade.setText(originCourMinGrade);
        courCancelYear.setText(originCourCancelYear);

        databasePath = Environment.getExternalStorageDirectory()+"/databaseManagement/"+"data.db";
        db = SQLiteDatabase.openOrCreateDatabase(databasePath,null);
        helper = new DataBaseHelper(db);

        comfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        updateId(courId.getText().toString().trim());
                        updateName(courName.getText().toString().trim());
                        updateCredit(Integer.valueOf(courCredit.getText().toString().trim()));
                        updateCancelYear(Integer.valueOf(courCancelYear.getText().toString().replace(" ","")));
                        updateMinGrade(Integer.valueOf(courMinGrade.getText().toString().replace(" ","")));
                        updateTeacher(courTeacherName.getText().toString().trim());
                        subscriber.onNext("更新数据");
                    }
                });
                Subscriber<String> subscriber = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                       finish();
                    }
                };
            }
        });
    }
    public void updateId(String id){
        if(!id.equals(originCourId)){
            helper.updateCourse("courId",id,originCourId);
        }
    }
    public void updateName(String name){
        if(!name.equals(originCourName)){
            helper.updateCourse("courName",name,originCourId);
        }
    }
    public void updateTeacher(String teacher){
        if(!teacher.equals(originCourTeacherName)){
            helper.updateCourse("courTeacherName",teacher,originCourId);
        }
    }
    public void updateCredit(int credit){
        if(credit!=originCourCredit){
           helper.updateCourse("courCredit",credit,originCourId);
        }
    }
    public void updateMinGrade(int grade){
        if(grade!=originCourMinGrade){
            helper.updateCourse("courMinGrade",grade,originCourId);
        }
    }
    public void updateCancelYear(int cancelYear){
        if(cancelYear!=originCourCancelYear){
            helper.updateCourse("courCancelYear",cancelYear,originCourId);
        }
    }
}
