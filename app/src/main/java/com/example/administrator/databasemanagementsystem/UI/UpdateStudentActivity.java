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
import com.example.administrator.databasemanagementsystem.Models.ChooseCourse;
import com.example.administrator.databasemanagementsystem.Models.DataBean;
import com.example.administrator.databasemanagementsystem.Models.RecyclerItem;
import com.example.administrator.databasemanagementsystem.Models.Student;
import com.example.administrator.databasemanagementsystem.R;

import java.io.IOException;
import java.util.List;
import java.util.logging.StreamHandler;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/14.
 */

public class UpdateStudentActivity extends AppCompatActivity {
    private Button comfirmButton;
    private EditText stdId;
    private EditText stdName;
    private  EditText stdGender;
    private EditText stdAge;
    private EditText stdYear;
    private EditText stdClass;
    private Toolbar mToolbar;
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
    protected void onCreate(final Bundle savadInstance){
        super.onCreate(savadInstance);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.lightBlue));
        setContentView(R.layout.update_student_layout);

        stdId = (EditText)findViewById(R.id.updateStdId);
        stdName = (EditText)findViewById(R.id.updateStdName);
        stdGender = (EditText)findViewById(R.id.updatesStdIGender);
        stdAge = (EditText)findViewById(R.id.updateStdAge);
        stdYear = (EditText)findViewById(R.id.updateStdInYear);
        stdClass = (EditText)findViewById(R.id.updateStdClass);
        comfirmButton = (Button)findViewById(R.id.updateStdConfirmButton);
        mToolbar =(Toolbar)findViewById(R.id.update_std_toolbar);
        mToolbar.setTitle("更新学生信息");
        setSupportActionBar(mToolbar);
        //mToolbar.setNavigationIcon(R.mipmap.icon_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle bundle = this.getIntent().getExtras();
        originID = (String)bundle.get("stdId");
        originName = (String)bundle.get("stdName");
        originGender = (String)bundle.get("stdGender");
        originAge = Integer.valueOf((String)bundle.get("stdAge"));
        originYear = Integer.valueOf((String)bundle.get("stdYear"));
        originClass = (String)bundle.get("stdClass");
        stdId.setText(originID);
        stdName.setText(originName);
        stdGender.setText(originGender);
        stdAge.setText(originAge+"");
        stdYear.setText(originYear+"");
        stdClass.setText(originClass);



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
                           return;
                       }
                       updateID(stdId.getText().toString().trim());
                       updateName(stdName.getText().toString().trim());
                       updateGender(stdGender.getText().toString().trim());
                       updateAge(Integer.valueOf(stdAge.getText().toString().replace(" ","")));
                       updateYear(Integer.valueOf(stdYear.getText().toString().replace(" ","")));
                       updateClass(stdClass.getText().toString());
                       subscriber.onNext(true);
                   }
               });
                Subscriber<Boolean> subscriber = new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof IOException){
                            Log.i("IOE","EXception");

                        }
                        Log.i("更新","错误");
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
                } ;
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);



            }
        });
    }
    public void updateID(String value){
        if(!value.equals(originID)&&value.length()==10){
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
    public boolean checkLegal(){
        if(TextUtils.isEmpty(stdId.getText())
                ||TextUtils.isEmpty(stdName.getText())
                ||TextUtils.isEmpty(stdAge.getText())
                ||TextUtils.isEmpty(stdClass.getText())
                ||TextUtils.isEmpty(stdYear.getText())
                ||TextUtils.isEmpty(stdGender.getText())){

            return false;
        }
        if(stdId.getText().toString().trim().length()==10
                &&stdName.getText().toString().trim().length()!=0
                &&stdAge.getText().toString().trim().length()!=0
                &&stdClass.getText().toString().trim().length()!=0
                &&stdGender.getText().toString().trim().length()!=0
                &&stdYear.getText().toString().trim().length()!=0){




            return true;
        }
        else{

            return  false;
        }
    }
}
