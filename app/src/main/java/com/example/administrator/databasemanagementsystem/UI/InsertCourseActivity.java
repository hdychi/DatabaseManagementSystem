package com.example.administrator.databasemanagementsystem.UI;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.R;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/3/26.
 */

public class InsertCourseActivity extends Activity{
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



        databasePath = Environment.getExternalStorageDirectory()+"/databaseManagement/"+"data.db";
        db = SQLiteDatabase.openOrCreateDatabase(databasePath,null);
        helper = new DataBaseHelper(db);

        comfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber) {
                        insertData();
                        subscriber.onNext(insertData());
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
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean s) {
                        if(s) {
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"数据不完整无法插入",Toast.LENGTH_SHORT).show();
                        }
                    }
                };
            }
        });
    }
    public boolean insertData(){
        if(TextUtils.isEmpty(courId.getText())
        ||TextUtils.isEmpty(courName.getText())
        ||TextUtils.isEmpty(courTeacherName.getText())
                ||TextUtils.isEmpty(courCredit.getText())
                ||TextUtils.isEmpty(courMinGrade.getText())){
            return false;
        }
        if(courId.getText().toString().trim().length()!=0
                &&courName.getText().toString().trim().length()!=0
                &&courTeacherName.getText().toString().trim().length()!=0
                &&courCredit.getText().toString().trim().length()!=0
                &&courMinGrade.getText().toString().trim().length()!=0){
            helper.insertCourse(courId.getText().toString().trim(),courName.getText().toString().trim(),
                    courTeacherName.getText().toString().trim(),Integer.valueOf(courCredit.getText().toString().replace(" ","")),
                    Integer.valueOf(courMinGrade.getText().toString().replace(" ","")),
                    Integer.valueOf(courCancelYear.getText().toString().replace(" ","")));
          return true;
        }
        return false;
    }
}
