package com.example.administrator.databasemanagementsystem.UI;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
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
 * Created by Administrator on 2017/3/26.
 */

public class InsertChooseAcrivity extends Activity {
    private EditText stdId;
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
        stdId = (EditText)findViewById(R.id.updateChooseStdrId);
        courId = (EditText)findViewById(R.id.updateChooseCourId);
        chooseYear = (EditText)findViewById(R.id.updatesChooseYear);
        grade = (EditText)findViewById(R.id.updateChooseGrade);
        button = (Button)findViewById(R.id.updateChooseConfirmButton);



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
                ||grade.getText().toString().trim().length()==0){
            return false;
        }
        helper.insertChoose(stdId.getText().toString().trim(),courId.getText().toString().trim(),Integer.valueOf(chooseYear.getText().toString().replace(" ","")),Integer.valueOf(grade.getText().toString().replace(" ","")));
        return true;
    }
}
