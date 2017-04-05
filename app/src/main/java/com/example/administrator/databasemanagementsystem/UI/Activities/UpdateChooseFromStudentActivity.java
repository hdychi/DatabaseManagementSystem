package com.example.administrator.databasemanagementsystem.UI.Activities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.Models.Course;
import com.example.administrator.databasemanagementsystem.Models.Student;
import com.example.administrator.databasemanagementsystem.R;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/13.
 */

public class UpdateChooseFromStudentActivity extends AppCompatActivity {
    private TextView stdId;
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
    protected void onCreate(Bundle savadInstance) {
        super.onCreate(savadInstance);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.lightBlue));
        setContentView(R.layout.update_choose_from_student_layout);
        stdId = (TextView) findViewById(R.id.updateChooseStdrId);
        courId = (EditText) findViewById(R.id.updateChooseCourId);
        chooseYear = (EditText) findViewById(R.id.updatesChooseYear);
        grade = (EditText) findViewById(R.id.updateChooseGrade);
        button = (Button) findViewById(R.id.updateChooseConfirmButton);
        mToolbar = (Toolbar)findViewById(R.id.update_choose_toolbar) ;
        mToolbar.setTitle("更新选课信息");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle bundle = this.getIntent().getExtras();

        originStdId = (String) bundle.get("stdId");
        originCourId = (String) bundle.get("courId");
        originChooseYear = (Integer) bundle.get("chooseYear");
        originGrade = (Integer) bundle.get("grade");

        stdId.setText(originStdId);
        courId.setText(originCourId);
        chooseYear.setText(originChooseYear + "");
        grade.setText(originGrade + "");

        databasePath = Environment.getExternalStorageDirectory() + "/databaseManagement/" + "data.db";
        db = SQLiteDatabase.openOrCreateDatabase(databasePath, null);
        helper = new DataBaseHelper(db);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Boolean> observable = Observable.create(new Observable.OnSubscribe<Boolean>() {
                    @Override
                    public void call(Subscriber<? super Boolean> subscriber) {
                        if (!checkLegal()) {
                            subscriber.onNext(false);
                            return;
                        }
                        updateStdId(stdId.getText().toString().trim());
                        updateCourId(courId.getText().toString().trim());
                        updateChooseYear(Integer.valueOf(chooseYear.getText().toString().replace(" ", "")));
                        updateGrade(Integer.valueOf(grade.getText().toString().replace(" ", "")));
                        subscriber.onNext(true);
                    }
                });
                Subscriber<Boolean> subscriber = new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof IOException) {

                        }
                        Toast.makeText(getApplicationContext(), "数据不合法无法更新", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Boolean s) {
                        if (!s) {
                            Toast.makeText(getApplicationContext(), "数据不合法无法更新", Toast.LENGTH_SHORT).show();
                        } else {
                            finish();
                        }
                    }
                };
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(subscriber);
            }
        });


    }

    public void updateStdId(String id) {
        if (!id.equals(originStdId)) {
            helper.updateChoose("stdId", id, originStdId, originCourId);
        }
    }

    public void updateCourId(String id) {
        if (!id.equals(originCourId)) {
            helper.updateChoose("courId", id, originStdId, originCourId);
        }
    }

    public void updateChooseYear(int year) {
        if (year != originChooseYear) {
            helper.updateChoose("chooseYear", year, originStdId, originCourId);
        }
    }

    public void updateGrade(int grade) {
        if (grade != originGrade) {
            helper.updateChoose("grade", grade, originStdId, originCourId);
        }
    }

    public boolean checkLegal() {
        if (TextUtils.isEmpty(stdId.getText()) || TextUtils.isEmpty(courId.getText()) || TextUtils.isEmpty(chooseYear.getText()) || TextUtils.isEmpty(grade.getText())) {
            return false;
        }
        if (courId.getText().toString().trim().length() != 7
                || stdId.getText().toString().trim().length() != 10
                || chooseYear.getText().toString().trim().length() == 0
                || grade.getText().toString().trim().length() == 0) {
            return false;
        }
        Student student = helper.getStudentWithId(stdId.getText().toString());
        Course course = helper.getCourseWithId(courId.getText().toString());
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(System.currentTimeMillis());
        if ((Integer.valueOf(chooseYear.getText().toString().replace(" ", "")) > course.getCourCancelYear() && course.getCourCancelYear() > 0) || Integer.valueOf(chooseYear.getText().toString().replace(" ", "")) - student.getStdYear() + (calendar.get(java.util.Calendar.MONTH) > 9 ? 1 : 0) < course.getCourMinGrade()) {


            System.out.println("选课年份:" + Integer.valueOf(chooseYear.getText().toString().replace(" ", "")) + "取消年份" + course.getCourCancelYear() + "年级" + (Integer.valueOf(chooseYear.getText().toString().replace(" ", "")) - student.getStdYear() + (calendar.get(java.util.Calendar.MONTH) > 9 ? 1 : 0)) + "适合年级" + course.getCourMinGrade());
            return false;
        }
        return true;
    }
}
