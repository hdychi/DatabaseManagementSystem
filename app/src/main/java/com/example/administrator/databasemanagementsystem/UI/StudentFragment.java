package com.example.administrator.databasemanagementsystem.UI;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.util.AsyncListUtil;
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
    private RecyclerAdapter mAdapter;
    private Button updateButton;
    private Button confirmButton;
    private TextView stdId;
    private TextView stdName;
    private TextView stdGender;
    private TextView stdAge;
    private TextView stdYear;
    private TextView stdClass;
    private EditText editText;
    private TextView stdGPA;
    private TextView classGPA;
    private SQLiteDatabase db;
    private String databasePath;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savadInstanceState){
        mContext =getActivity();
        layout = inflater.inflate(R.layout.fragment1_layout,null);

        mRecyclerView = (RecyclerView)layout.findViewById(R.id.recyclerView);

        stdId = (TextView)layout.findViewById(R.id.stdId);
        stdName = (TextView)layout.findViewById(R.id.stdName);
        stdGender = (TextView)layout.findViewById(R.id.stdIGender);
        stdAge = (TextView)layout.findViewById(R.id.stdAge);
        stdYear = (TextView)layout.findViewById(R.id.stdInYear);
        stdClass = (TextView)layout.findViewById(R.id.stdClass);
        stdGPA = (TextView)layout.findViewById(R.id.stdGPA);
        classGPA = (TextView)layout.findViewById(R.id.stdClassGPA);
        confirmButton = (Button)layout.findViewById(R.id.confirmButton);
        updateButton = (Button)layout.findViewById(R.id.updateStudentButton);
        editText = (EditText)layout.findViewById(R.id.editText);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new RecyclerAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,UpdateStudentActivity.class);
                intent.putExtra("stdId",stdId.getText());
                intent.putExtra("stdName",stdName.getText());
                intent.putExtra("stdGender",stdGender.getText());
                intent.putExtra("stdClass",stdClass.getText());
                intent.putExtra("stdYear",stdYear.getText());
                intent.putExtra("stdAge",stdAge.getText());
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
      Observable<DataBean> observable = Observable.create(new Observable.OnSubscribe<DataBean>() {
          @Override
          public void call(Subscriber<? super DataBean> subscriber) {
              Student student = getStudentInformation(editText.getText().toString());
              List<RecyclerItem> items = getItems(student);
              double stdGPA = new DataBaseHelper(db).getStudentGPA(stdId.getText().toString());
              double classGPA = new DataBaseHelper(db).getClassGPA(stdClass.getText().toString());
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
             if(e instanceof IOException){
                 Log.i("IOE","EXception");

             }
             e.printStackTrace();
         }

         @Override
         public void onNext(DataBean dataBean) {
           stdId.setText(dataBean.getStudent().getStdId());
           stdName.setText(dataBean.getStudent().getStdName());
           stdAge.setText(dataBean.getStudent().getStdAge());
           stdClass.setText(dataBean.getStudent().getStdClass());
           stdGender.setText(dataBean.getStudent().getStdGender());
           stdYear.setText(dataBean.getStudent().getStdYear());
           stdGPA.setText(dataBean.getStdGPA()+"");
           classGPA.setText(dataBean.getStdClassGPA()+"");
           mAdapter.addAll(dataBean.getItems());
         }
     };
     observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
   }
   public Student getStudentInformation(String idOrName){
       if(idOrName.charAt(0)<='9'&&idOrName.charAt(0)>='0'){
           return new DataBaseHelper(db).getStudentWithId(idOrName);
       }
       else{
           return new DataBaseHelper(db).getStudentWithName(idOrName);
       }

   }
   public List<RecyclerItem> getItems(Student student){
     List<RecyclerItem> res = new ArrayList<>();
     List<ChooseCourse> chooseCourses = new DataBaseHelper(db).getChooseCourseWithStudent(student.getStdId());
     for(ChooseCourse chooseCourse:chooseCourses){
         Course temp = new DataBaseHelper(db).getCourseWithId(chooseCourse.getCourId());
         RecyclerItem item = new RecyclerItem(student,temp,chooseCourse);
         res.add(item);
     }
       return res;
   }
}
