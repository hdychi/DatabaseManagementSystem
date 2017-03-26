package com.example.administrator.databasemanagementsystem.UI;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.R;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/13.
 */

public class UpdateChooseFromStudentActivity extends Activity {
    private TextView stdId;
    private EditText courId;
    private EditText chooseYear;
    private EditText grade;

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
        setContentView(R.layout.update_choose_from_student_layout);
        stdId = (TextView)findViewById(R.id.updateChooseStdrId);
        courId = (EditText)findViewById(R.id.updateChooseCourId);
        chooseYear = (EditText)findViewById(R.id.updatesChooseYear);
        grade = (EditText)findViewById(R.id.updateChooseGrade);
        button = (Button)findViewById(R.id.updateChooseConfirmButton);

        originStdId = (String)savadInstance.get("stdId");
        originCourId = (String)savadInstance.get("courId");
        originChooseYear = Integer.valueOf((String)savadInstance.get("chooseYear"));
        originGrade = Integer.valueOf((String)savadInstance.get("grade"));

        stdId.setText(originStdId);
        courId.setText(originCourId);
        chooseYear.setText(originChooseYear);
        grade.setText(originGrade);

        databasePath = Environment.getExternalStorageDirectory()+"/databaseManagement/"+"data.db";
        db = SQLiteDatabase.openOrCreateDatabase(databasePath,null);
        helper = new DataBaseHelper(db);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        updateStdId(stdId.getText().toString().trim());
                        updateCourId(courId.getText().toString().trim());
                        updateChooseYear(Integer.valueOf(chooseYear.getText().toString().replace(" ","")));
                        updateGrade(Integer.valueOf(grade.getText().toString().replace(" ","")));
                        subscriber.onNext("更新数据");
                    }
                });
                Subscriber<String> subscriber = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof IOException){
                            Toast.makeText(getApplicationContext(),"更新异常",Toast.LENGTH_SHORT).show();
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        finish();
                    }
                };
                observable.subscribeOn(Schedulers.io())
                           .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(subscriber);
            }
        });


    }
    public void updateStdId(String id){
        if(!id.equals(originStdId)){
            helper.updateChoose("stdId",id,originStdId,originCourId);
        }
    }
    public void updateCourId(String id){
        if(!id.equals(originCourId)){
            helper.updateChoose("courId",id,originStdId,originCourId);
        }
    }
    public void updateChooseYear(int year){
        if(year!=originChooseYear){
            helper.updateChoose("chooseYear",year,originStdId,originCourId);
        }
    }
    public void updateGrade(int grade){
        if(grade!=originGrade){
            helper.updateChoose("grade",grade,originStdId,originCourId);
        }
    }
}
