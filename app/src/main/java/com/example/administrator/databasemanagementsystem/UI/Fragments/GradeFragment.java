package com.example.administrator.databasemanagementsystem.UI.Fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.Models.ChooseCourse;
import com.example.administrator.databasemanagementsystem.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/10.
 */

public class GradeFragment extends Fragment {
    private Context mContext;
    private View layout;
    private DataBaseHelper mHelper;
    private EditText student;
    private EditText course;
    private TextView grade;
    private Button comfirmButton;
    private boolean isAutoRefresh = true;
    private SQLiteDatabase db;
    private String databasePath;
    private DataBaseHelper helper;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savadInstanceState){
        layout = inflater.inflate(R.layout.fragment3_layout,null);
        student = (EditText)layout.findViewById(R.id.editText1);
        course = (EditText)layout.findViewById(R.id.editText2);
        grade = (TextView)layout.findViewById(R.id.grade);
        comfirmButton = (Button)layout.findViewById(R.id.confirmButton);
        comfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoRefresh = false;
                getGrade();
            }
        });
        databasePath = Environment.getExternalStorageDirectory() + "/databaseManagement/" + "data.db";
        db = SQLiteDatabase.openOrCreateDatabase(databasePath, null);

        helper = new DataBaseHelper(db);
        return layout;
    }
    @Override
    public void onResume(){
        super.onResume();
        isAutoRefresh = true;
        getGrade();

    }
    public void getGrade(){
        String editText1 = student.getText().toString().trim();
        String editText2 = course.getText().toString().trim();
        Observable<ChooseCourse> observable = Observable.create(new Observable.OnSubscribe<ChooseCourse>() {
            @Override
            public void call(Subscriber<? super ChooseCourse> subscriber) {
                ChooseCourse chooseCourse = helper.getChooseWithStudentAndCourse(editText1,editText2);
                subscriber.onNext(chooseCourse);
            }
        });
        Subscriber<ChooseCourse> subscriber = new Subscriber<ChooseCourse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                if(!isAutoRefresh) {
                    Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                }
                grade.setText("");
               throwable.printStackTrace();
            }

            @Override
            public void onNext(ChooseCourse chooseCourse) {
                     grade.setText(chooseCourse.getGrade()+"");
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }
}
