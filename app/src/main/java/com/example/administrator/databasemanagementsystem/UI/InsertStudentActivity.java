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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/22.
 */

public class InsertStudentActivity extends Activity {
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



        stdId.setText("");
        stdName.setText("");
        stdGender.setText("");
        stdAge.setText("");
        stdYear.setText("");
        stdClass.setText("");
        databasePath = Environment.getExternalStorageDirectory()+"/databaseManagement/"+"data.db";
        db = SQLiteDatabase.openOrCreateDatabase(databasePath,null);
        helper = new DataBaseHelper(db);
        comfirmButton.setOnClickListener(new View.OnClickListener() {
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
                        if(e instanceof IOException){
                            Log.i("IOE","EXception");

                        }
                        Toast.makeText(getApplicationContext(),"数据非法无法加入",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean s) {
                        if(s) {
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"数据非法无法加入",Toast.LENGTH_SHORT).show();
                        }
                    }
                } ;
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);



            }
        });
    }

    public boolean insertData(){
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

               helper.insertStudent(stdId.getText().toString(),stdName.getText().toString(),stdGender.getText().toString(),
                       Integer.valueOf(stdAge.getText().toString().replace(" ","")),
                       Integer.valueOf(stdYear.getText().toString().replace(" ","")),stdClass.getText().toString());


           return true;
        }
        else{

            return  false;
        }
    }
}
