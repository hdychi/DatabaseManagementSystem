package com.example.administrator.databasemanagementsystem.UI;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;


import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.Models.Course;
import com.example.administrator.databasemanagementsystem.Models.Student;
import com.example.administrator.databasemanagementsystem.R;


import java.util.Calendar;
import java.util.TimeZone;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/26.
 */

public class InsertChooseAcrivity extends AppCompatActivity {
    private EditText stdId;
    private EditText courId;
    private EditText chooseYear;
    private EditText grade;
    private Toolbar mToolbar;

    private Button button;
    private String originStdId;
    private String originCourId;
    private int originChooseYear;
    private int originGrade;

    private String databasePath;
    private SQLiteDatabase db;
    private DataBaseHelper helper;
    @Override
    protected void onCreate(Bundle savadInstance){
        super.onCreate(savadInstance);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.lightBlue));
        setContentView(R.layout.insert_choose_layout);
        stdId = (EditText)findViewById(R.id.updateChooseStdrId);
        courId = (EditText)findViewById(R.id.updateChooseCourId);
        chooseYear = (EditText)findViewById(R.id.updatesChooseYear);
        grade = (EditText)findViewById(R.id.updateChooseGrade);
        button = (Button)findViewById(R.id.updateChooseConfirmButton);
        mToolbar = (Toolbar)findViewById(R.id.update_choose_toolbar) ;
        mToolbar.setTitle("插入选课信息");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        databasePath = Environment.getExternalStorageDirectory()+"/databaseManagement/"+"data.db";
        db = SQLiteDatabase.openOrCreateDatabase(databasePath,null);
        helper = new DataBaseHelper(db);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber) {

                        subscriber.onNext(insertData());
                    }
                });
                Subscriber<Boolean> subscriber = new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(),"数据非法无法插入",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean s) {
                        if(s) {
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"数据不合法无法插入",Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);
            }
        });


    }
    public boolean insertData(){
        if(TextUtils.isEmpty(stdId.getText())||TextUtils.isEmpty(courId.getText())||TextUtils.isEmpty(chooseYear.getText())||TextUtils.isEmpty(grade.getText())){
            return false;
        }
        if(courId.getText().toString().trim().length()==0
                ||stdId.getText().toString().trim().length()==0
                ||chooseYear.getText().toString().trim().length()==0
                ||grade.getText().toString().trim().length()==0) {
            return false;
        }
        Student student =helper.getStudentWithId(stdId.getText().toString());
        Course course   = helper.getCourseWithId(courId.getText().toString()) ;
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(System.currentTimeMillis());
        if((Integer.valueOf(chooseYear.getText().toString().replace(" ",""))>course.getCourCancelYear()&&course.getCourCancelYear()>0)||Integer.valueOf(chooseYear.getText().toString().replace(" ", ""))-student.getStdYear()+(calendar.get(java.util.Calendar.MONTH)>9?1:0)<course.getCourMinGrade()){


            System.out.println("选课年份:"+Integer.valueOf(chooseYear.getText().toString().replace(" ",""))+"取消年份"+course.getCourCancelYear()+"年级"+(Integer.valueOf(chooseYear.getText().toString().replace(" ", ""))-student.getStdYear()+(calendar.get(java.util.Calendar.MONTH)>9?1:0))+"适合年级"+course.getCourMinGrade());
            return false;
        }
        helper.insertChoose(stdId.getText().toString().trim(),courId.getText().toString().trim(),Integer.valueOf(chooseYear.getText().toString().replace(" ","")),Integer.valueOf(grade.getText().toString().replace(" ","")));
        return true;
    }
}
