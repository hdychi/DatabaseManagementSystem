package com.example.administrator.databasemanagementsystem.UI;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.Models.ChooseCourse;
import com.example.administrator.databasemanagementsystem.Models.Course;
import com.example.administrator.databasemanagementsystem.Models.DataBean;
import com.example.administrator.databasemanagementsystem.Models.RecyclerItem;
import com.example.administrator.databasemanagementsystem.Models.Student;
import com.example.administrator.databasemanagementsystem.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/13.
 */

public class StudentFragment extends Fragment {
    private Context mContext;
    private View layout;
    private RecyclerView mRecyclerView;
    private RecyclerView allStudentsRecycler;
    private StudentRecyclerAdapter mAdapter;
    private Button updateButton;
    private Button confirmButton;
    private Button deleteButton;
    private TextView stdId;
    private TextView stdName;
    private TextView stdGender;
    private TextView stdAge;
    private TextView stdYear;
    private TextView stdClass;
    private EditText editText;
    private TextView stdGPA;
    private TextView classGPA;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SQLiteDatabase db;
    private String databasePath;
    private DataBaseHelper helper;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savadInstanceState) {
        mContext = getActivity();
        layout = inflater.inflate(R.layout.fragment1_layout, null);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);

        stdId = (TextView) layout.findViewById(R.id.stdId);
        stdName = (TextView) layout.findViewById(R.id.stdName);
        stdGender = (TextView) layout.findViewById(R.id.stdIGender);
        stdAge = (TextView) layout.findViewById(R.id.stdAge);
        stdYear = (TextView) layout.findViewById(R.id.stdInYear);
        stdClass = (TextView) layout.findViewById(R.id.stdClass);
        stdGPA = (TextView) layout.findViewById(R.id.stdGPA);
        classGPA = (TextView) layout.findViewById(R.id.stdClassGPA);
        confirmButton = (Button) layout.findViewById(R.id.confirmButton);
        updateButton = (Button) layout.findViewById(R.id.updateStudentButton);
        deleteButton = (Button) layout.findViewById(R.id.deleteStudentButton);
        editText = (EditText) layout.findViewById(R.id.editText);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        databasePath = Environment.getExternalStorageDirectory() + "/databaseManagement/" + "data.db";
        db = SQLiteDatabase.openOrCreateDatabase(databasePath, null);

        helper = new DataBaseHelper(db);
        mAdapter = new StudentRecyclerAdapter(mContext, helper);
        mRecyclerView.setAdapter(mAdapter);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, UpdateStudentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stdId", stdId.getText().toString());
                bundle.putString("stdName", stdName.getText().toString());
                bundle.putString("stdGender", stdGender.getText().toString());
                bundle.putString("stdClass", stdClass.getText().toString());
                bundle.putString("stdYear", stdYear.getText().toString());
                bundle.putString("stdAge", stdAge.getText().toString());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        helper.deleteStudent(stdId.getText().toString());
                        subscriber.onNext("删除数据");
                    }
                });
                Subscriber<String> subscriber = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof IOException) {
                            Log.i("删除", "异常");
                        }
                        stdId.setText("");
                        stdName.setText("");
                        stdAge.setText("");
                        stdClass.setText("");
                        stdGender.setText("");
                        stdYear.setText("");
                        stdGPA.setText("");
                        classGPA.setText("");
                        mAdapter.clear();
                        updateButton.setVisibility(View.GONE);
                        deleteButton.setVisibility(View.GONE);
                        Toast.makeText(mContext,"删除异常",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        stdId.setText("");
                        stdName.setText("");
                        stdAge.setText("");
                        stdClass.setText("");
                        stdGender.setText("");
                        stdYear.setText("");
                        stdGPA.setText("");
                        classGPA.setText("");
                        mAdapter.clear();
                        updateButton.setVisibility(View.GONE);
                        deleteButton.setVisibility(View.GONE);
                    }
                };
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
            }

        });
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savadInstanceState) {
        super.onActivityCreated(savadInstanceState);
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public void getData() {
        Observable<DataBean> observable = Observable.create(new Observable.OnSubscribe<DataBean>() {
            @Override
            public void call(Subscriber<? super DataBean> subscriber) {
                List<Student> check = helper.getAllStudent();
                Log.i("查询学生的总数", "为" + check.size());
                Student student = getStudentInformation(editText.getText().toString());
                if (student != null) {
                    System.out.println("stdName" + student.getStdName() + "stdGender" + student.getStdGender() + "stdClass" + student.getStdClass() + "stdAGE" + student.getStdAge());
                }
                List<RecyclerItem> items = getItems(student);
                double stdGPA = new DataBaseHelper(db).getStudentGPA(student.getStdId());
                double classGPA = new DataBaseHelper(db).getClassGPA(student.getStdClass());
                DataBean bean = new DataBean();
                bean.setStudent(student);
                bean.setItems(items);
                bean.setStdGPA(stdGPA);
                bean.setStdClassGPA(classGPA);
                subscriber.onNext(bean);
            }
        });
        Subscriber<DataBean> subscriber = new Subscriber<DataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof IOException) {
                    Log.i("IOE", "EXception");

                }
                stdId.setText("");
                stdName.setText("");
                stdAge.setText("");
                stdClass.setText("");
                stdGender.setText("");
                stdYear.setText("");
                stdGPA.setText("");
                classGPA.setText("");
                mAdapter.clear();
                Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                updateButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.GONE);
                Log.i("查询", "错误");
                e.printStackTrace();
            }

            @Override
            public void onNext(DataBean dataBean) {
                mAdapter.clear();
                Student student = dataBean.getStudent();
                System.out.println("2stdGender" + student.getStdGender() + "stdClass" + student.getStdClass() + "stdAGE" + student.getStdAge() + "stdYear" + student.getStdYear());

                stdId.setText(dataBean.getStudent().getStdId() == null ? "" : dataBean.getStudent().getStdId());
                stdName.setText(dataBean.getStudent().getStdName());
                stdAge.setText(dataBean.getStudent().getStdAge() + "");
                stdClass.setText(dataBean.getStudent().getStdClass());
                stdGender.setText(dataBean.getStudent().getStdGender());
                stdYear.setText(dataBean.getStudent().getStdYear() + "");
                stdGPA.setText(dataBean.getStdGPA() + "");
                classGPA.setText(dataBean.getStdClassGPA() + "");
                mAdapter.addAll(dataBean.getItems());
                updateButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
                Log.i("查询", "成功");
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Student getStudentInformation(String idOrName) {
        idOrName = idOrName.trim();
        if (idOrName.charAt(0) <= '9' && idOrName.charAt(0) >= '0') {
            return new DataBaseHelper(db).getStudentWithId(idOrName);
        } else {
            return new DataBaseHelper(db).getStudentWithName(idOrName);
        }

    }

    public List<RecyclerItem> getItems(Student student) {
        List<RecyclerItem> res = new ArrayList<>();
        List<ChooseCourse> chooseCourses = new DataBaseHelper(db).getChooseCourseWithStudent(student.getStdId());
        if (chooseCourses.size() > 0) {
            Log.i("查询结果的item数", "不为0");
        } else {
            Log.i("查询结果的item数", "为0");
        }
        for (ChooseCourse chooseCourse : chooseCourses) {
            Course temp = new DataBaseHelper(db).getCourseWithId(chooseCourse.getCourId());
            RecyclerItem item = new RecyclerItem(student, temp, chooseCourse);
            res.add(item);
        }

        return res;
    }
}
