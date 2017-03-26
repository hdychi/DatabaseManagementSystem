package com.example.administrator.databasemanagementsystem.UI;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.Models.ChooseCourse;
import com.example.administrator.databasemanagementsystem.Models.Course;
import com.example.administrator.databasemanagementsystem.Models.DataBean;
import com.example.administrator.databasemanagementsystem.Models.DataBean2;
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

import static com.example.administrator.databasemanagementsystem.R.id.stdGPA;

/**
 * Created by Administrator on 2017/3/19.
 */

public class CourseFragment extends Fragment {
    private Context mContext;
    private View layout;
    private RecyclerView mRecyclerView;
    private CourseRecyclerAdapter mAdapter;
    private Button updateButton;
    private Button confirmButton;
    private TextView courId;
    private TextView courName;
    private TextView courTeacherName;
    private TextView courCredit;
    private TextView courMinGrade;
    private TextView courCancelYear;
    private EditText editText;

    private SQLiteDatabase db;
    private String databasePath;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savadInstanceState){
        mContext =getActivity();
        layout = inflater.inflate(R.layout.fragment2_layout,null);

        mRecyclerView = (RecyclerView)layout.findViewById(R.id.recyclerView);

        courId = (TextView)layout.findViewById(R.id.frag2courId);
        courName = (TextView)layout.findViewById(R.id.frag2courName);
        courTeacherName = (TextView)layout.findViewById(R.id.frag2TeacherName);
        courCredit = (TextView)layout.findViewById(R.id.frag2Credit);
        courMinGrade = (TextView)layout.findViewById(R.id.frag2MinGrade);
        courCancelYear = (TextView)layout.findViewById(R.id.frag2CancelYear);

        confirmButton = (Button)layout.findViewById(R.id.confirmButton);
        updateButton = (Button)layout.findViewById(R.id.updateStudentButton);
        editText = (EditText)layout.findViewById(R.id.editText);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CourseRecyclerAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,UpdateCourseActivity.class);
                intent.putExtra("courId",courId.getText());
                intent.putExtra("courName",courName.getText());
                intent.putExtra("courTeacherName",courTeacherName.getText());
                intent.putExtra("courCredit",courCredit.getText());
                intent.putExtra("courMinGrade",courMinGrade.getText());
                intent.putExtra("courCancelYear",courCancelYear.getText());
                startActivity(intent);
            }
        });
        return layout;
    }
    @Override
    public void onActivityCreated(Bundle savadInstanceState){
        databasePath = Environment.getExternalStorageDirectory()+"/databaseManagement/"+"data.db";
        db = SQLiteDatabase.openOrCreateDatabase(databasePath,null);
        getData();
    }
    public void getData(){
        Observable<DataBean2> observable = Observable.create(new Observable.OnSubscribe<DataBean2>() {
            @Override
            public void call(Subscriber<? super DataBean2> subscriber) {
                Course course = getCourseInformation(editText.getText().toString());
                List<RecyclerItem> items = getItems(course);

                DataBean2 bean = new DataBean2();
                bean.setCourse(course);
                bean.setItems(items);

                subscriber.onNext(bean);
            }
        });
        Subscriber<DataBean2> subscriber = new Subscriber<DataBean2>() {
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
            public void onNext(DataBean2 dataBean) {
                courId.setText(dataBean.getCourse().getCourId()==null?"无":dataBean.getCourse().getCourId());
                courName.setText(dataBean.getCourse().getCourName());
                courTeacherName.setText(dataBean.getCourse().getCourTeacherName());
                courCredit.setText(dataBean.getCourse().getCourCredit());
                courMinGrade.setText(dataBean.getCourse().getCourMinGrade());
                courCancelYear.setText(dataBean.getCourse().getCourCancelYear());
                //TODO:更新其他view控件
                mAdapter.addAll(dataBean.getItems());
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    public Course getCourseInformation(String idOrName){
        if(idOrName.charAt(0)<='9'&&idOrName.charAt(0)>='0'){
            return new DataBaseHelper(db).getCourseWithId(idOrName);
        }
        else{
            return new DataBaseHelper(db).getCourseWithName(idOrName);
        }

    }
    public List<RecyclerItem> getItems(Course course){
        List<RecyclerItem> res = new ArrayList<>();
        List<ChooseCourse> chooseCourses = new DataBaseHelper(db).getChooseCourseWithCourse(course.getCourId());
        for(ChooseCourse chooseCourse:chooseCourses){
            Student temp = new DataBaseHelper(db).getStudentWithId(chooseCourse.getCourId());
            RecyclerItem item = new RecyclerItem(temp,course,chooseCourse);
            res.add(item);
        }
        return res;
    }
}
