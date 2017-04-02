package com.example.administrator.databasemanagementsystem.UI;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/17.
 */

public class UpdateCourseActivity extends AppCompatActivity{
    private Button comfirmButton;
    private EditText courId;
    private EditText courName;
    private EditText courTeacherName;
    private EditText courCredit;
    private EditText courMinGrade;
    private EditText courCancelYear;
    private Toolbar mToolbar;
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
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.lightBlue));
        setContentView(R.layout.update_course_layout);

        courId = (EditText)findViewById(R.id.updateCourId);
        courName = (EditText)findViewById(R.id.updateCourName);
        courTeacherName = (EditText)findViewById(R.id.updatesCourTeacher);
        courCredit = (EditText)findViewById(R.id.updateCourCredit);
        courMinGrade = (EditText)findViewById(R.id.updateCourGrade);
        courCancelYear = (EditText)findViewById(R.id.updateCourYear);
        comfirmButton = (Button)findViewById(R.id.updateCourConfirmButton);
        mToolbar = (Toolbar)findViewById(R.id.update_cour_toolbar);
        mToolbar.setTitle("更新课程信息");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle bundle  = this.getIntent().getExtras();
        originCourId = (String)bundle.get("courId");
        originCourName = (String)bundle.get("courName");
        originCourTeacherName = (String)bundle.get("courTeacherName");
        originCourCredit = Integer.valueOf((String)bundle.get("courCredit"));
        originCourMinGrade =  Integer.valueOf((String)bundle.get("courMinGrade"));
        if(((String)bundle.get("courCancelYear")).length()>0) {
            originCourCancelYear = Integer.valueOf((String) bundle.get("courCancelYear"));
            Log.i("取消年份",""+originCourCancelYear);
        }
        else{
            originCourCancelYear = -1;
        }
        courId.setText(originCourId);
        courName.setText(originCourName);
        courTeacherName.setText(originCourTeacherName);
        courCredit.setText(originCourCredit+"");
        courMinGrade.setText(originCourMinGrade+"");
        courCancelYear.setText(originCourCancelYear>0?originCourCancelYear+"":"");

        databasePath = Environment.getExternalStorageDirectory()+"/databaseManagement/"+"data.db";
        db = SQLiteDatabase.openOrCreateDatabase(databasePath,null);
        helper = new DataBaseHelper(db);

        comfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber) {
                        if(!checkLegal()){
                            subscriber.onNext(false);
                            Log.i("更新课程","判断数据不合法");
                            return;
                        }
                        updateId(courId.getText().toString().trim());
                        updateName(courName.getText().toString().trim());
                        updateCredit(Integer.valueOf(courCredit.getText().toString().trim()));

                        updateMinGrade(Integer.valueOf(courMinGrade.getText().toString().replace(" ","")));
                        updateTeacher(courTeacherName.getText().toString().trim());
                        if(courCancelYear.getText().toString().replace(" ","").length()>0) {
                            updateCancelYear(Integer.valueOf(courCancelYear.getText().toString().replace(" ", "")));
                        }
                        else{
                            updateCancelYear(-1);
                        }
                        subscriber.onNext(true);
                    }
                });
                Subscriber<Boolean> subscriber = new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(),"数据不合法无法更新" ,Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean s) {
                        if(!s){
                            Toast.makeText(getApplicationContext(),"数据不合法无法更新" ,Toast.LENGTH_SHORT).show();
                        }else {
                            finish();
                        }
                    }
                };
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
            }
        });
    }
    public void updateId(String id){
        if(!id.equals(originCourId)&&id.length()==7){
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
    public boolean checkLegal(){
        if(TextUtils.isEmpty(courId.getText())
                ||TextUtils.isEmpty(courName.getText())
                ||TextUtils.isEmpty(courTeacherName.getText())
                ||TextUtils.isEmpty(courCredit.getText())
                ||TextUtils.isEmpty(courMinGrade.getText())){
            return false;
        }
        if(courId.getText().toString().trim().length()==7
                &&courName.getText().toString().trim().length()!=0
                &&courTeacherName.getText().toString().trim().length()!=0
                &&courCredit.getText().toString().trim().length()!=0
                &&courMinGrade.getText().toString().trim().length()!=0){


            return true;
        }
        return false;
    }

}
