package com.example.administrator.databasemanagementsystem.UI;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
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
import com.example.administrator.databasemanagementsystem.Models.DataBean2;
import com.example.administrator.databasemanagementsystem.Models.RecyclerItem;
import com.example.administrator.databasemanagementsystem.Models.Student;
import com.example.administrator.databasemanagementsystem.R;
import com.example.administrator.databasemanagementsystem.RecyclerDecoration;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.administrator.databasemanagementsystem.R.id.pieChart;
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
    private Button deleteButton;
    private TextView courId;
    private TextView courName;
    private TextView courTeacherName;
    private TextView courCredit;
    private TextView courMinGrade;
    private TextView courCancelYear;
    private EditText editText;

    private SQLiteDatabase db;
    private String databasePath;
    private DataBaseHelper helper;
    private PieChart mChart;

    private boolean isAutoRefresh = true;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savadInstanceState){
        mContext =getActivity();
        layout = inflater.inflate(R.layout.fragment2_layout,null);

        mRecyclerView = (RecyclerView)layout.findViewById(R.id.recyclerView2);

        courId = (TextView)layout.findViewById(R.id.frag2courId);
        courName = (TextView)layout.findViewById(R.id.frag2courName);
        courTeacherName = (TextView)layout.findViewById(R.id.frag2TeacherName);
        courCredit = (TextView)layout.findViewById(R.id.frag2Credit);
        courMinGrade = (TextView)layout.findViewById(R.id.frag2MinGrade);
        courCancelYear = (TextView)layout.findViewById(R.id.frag2CancelYear);

        confirmButton = (Button)layout.findViewById(R.id.confirmButton);
        updateButton = (Button)layout.findViewById(R.id.updateStudentButton);
        deleteButton = (Button)layout.findViewById(R.id.deleteCourseButton);
        editText = (EditText)layout.findViewById(R.id.editText);
        initialChart(layout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        databasePath = Environment.getExternalStorageDirectory()+"/databaseManagement/"+"data.db";
        db = SQLiteDatabase.openOrCreateDatabase(databasePath,null);
        helper = new DataBaseHelper(db);
        mAdapter = new CourseRecyclerAdapter(mContext,new DataBaseHelper(db),this);
        mRecyclerView.setAdapter(mAdapter);


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAutoRefresh = false;
                getData();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext,UpdateCourseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("courId",courId.getText().toString());
                bundle.putString("courName",courName.getText().toString());
                bundle.putString("courTeacherName",courTeacherName.getText().toString());
                bundle.putString("courCredit",courCredit.getText().toString());
                bundle.putString("courMinGrade",courMinGrade.getText().toString());
                bundle.putString("courCancelYear",courCancelYear.getText().toString());
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
                        helper.deleteCourse(courId.getText().toString());
                        subscriber.onNext("删除数据");
                    }
                });
                Subscriber<String> subscriber = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof IOException){
                            Log.i("删除","异常");
                        }
                        courId.setText("");
                        courName.setText("");
                        courCredit.setText("");

                        courCancelYear.setText("");
                        courMinGrade.setText("");
                        courTeacherName.setText("");
                        updateButton.setVisibility(View.GONE);
                        deleteButton.setVisibility(View.GONE);
                        mAdapter.clear();
                        Toast.makeText(mContext,"删除异常",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        courId.setText("");
                        courName.setText("");
                        courCredit.setText("");

                        courCancelYear.setText("");
                        courMinGrade.setText("");
                        courTeacherName.setText("");
                        updateButton.setVisibility(View.GONE);
                        deleteButton.setVisibility(View.GONE);
                        mAdapter.clear();
                    }
                };
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
            }
        });
        return layout;
    }
    @Override
    public void onActivityCreated(Bundle savadInstanceState){
        super.onActivityCreated(savadInstanceState);

    }
    @Override
    public void onResume(){
        super.onResume();
        isAutoRefresh = true;
        getData();
    }
    public void initialChart(View headView){



        mChart = (PieChart) headView.findViewById(R.id.pieChart);
        Description description = new Description();
        description.setText("课程成绩分布");
        mChart.setDescription(description);
        mChart.setCenterText("课程平均分");

       mChart.setDrawSlicesUnderHole(false);

        mChart.setUsePercentValues(true);

// space between slices


    }
    public void getData(){
        Observable<DataBean2> observable = Observable.create(new Observable.OnSubscribe<DataBean2>() {
            @Override
            public void call(Subscriber<? super DataBean2> subscriber) {
                Course course = getCourseInformation(editText.getText().toString());
                List<RecyclerItem> items = getItems(course);
                List<Integer> grades = getAverage(course);
                DataBean2 bean = new DataBean2();
                bean.setCourse(course);
                bean.setItems(items);
                setGradesForBean(grades,bean);
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
                courId.setText("");
                courName.setText("");
                courCredit.setText("");

                courCancelYear.setText("");
                courMinGrade.setText("");
                courTeacherName.setText("");
                mAdapter.clear();
                updateButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.GONE);
                if(!isAutoRefresh) {
                    Toast.makeText(getActivity(), "查询失败", Toast.LENGTH_SHORT).show();
                }
                e.printStackTrace();
            }

            @Override
            public void onNext(DataBean2 dataBean) {
                mAdapter.clear();
                courId.setText(dataBean.getCourse().getCourId()==null?"无":dataBean.getCourse().getCourId());
                courName.setText(dataBean.getCourse().getCourName());
                courTeacherName.setText(dataBean.getCourse().getCourTeacherName());
                courCredit.setText(dataBean.getCourse().getCourCredit()+"");
                courMinGrade.setText(dataBean.getCourse().getCourMinGrade()+"");
                if(dataBean.getCourse().getCourCancelYear()>0) {
                    courCancelYear.setText(dataBean.getCourse().getCourCancelYear() + "");
                }else{
                    courCancelYear.setText("");
                }
                updateChart(dataBean);
                updateButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
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
            Student temp = new DataBaseHelper(db).getStudentWithId(chooseCourse.getStdId());
            RecyclerItem item = new RecyclerItem(temp,course,chooseCourse);
            res.add(item);
        }
        return res;
    }
    public List<Integer> getAverage(Course course){
        List<Integer> res = new ArrayList<>();
        List<ChooseCourse> chooseCourses = new DataBaseHelper(db).getChooseCourseWithCourse(course.getCourId());
        for(ChooseCourse chooseCourse:chooseCourses){


            res.add(chooseCourse.getGrade());
        }
        return res;
    }
    public void setGradesForBean(List<Integer> grades,DataBean2 bean){
        int totalStudent = 0;
        int totalGrades = 0;
        int under60 = 0;
        int up60to69 = 0;
        int up70to79 = 0;
        int up80to89 = 0;
        int up90to9 = 0;
        int fullScore = 0;
        for(Integer score:grades){
            totalStudent++;
            totalGrades+=score;
            if(score<60){
                under60++;
            }
            else if(score<70){
                up60to69++;
            }
            else if(score<80){
                up70to79++;
            }
            else if(score<90){
                up80to89++;
            }
            else if(score<100){
                up90to9++;
            }
            else{
                fullScore++;
            }
        }
        bean.setTotalCount(totalStudent);
        bean.setAverageGrade(((double)totalGrades)/totalStudent);
        bean.setUnder60(under60);
        bean.setUp60to69(up60to69);
        bean.setUp70to79(up70to79);
        bean.setUp80to89(up80to89);
        bean.setUp90to9(up90to9);
        bean.setFullScore(fullScore);
    }
    public void updateChart(DataBean2 bean2){
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(((float) bean2.getUnder60())/bean2.getTotalCount(),"不及格"));
        entries.add(new PieEntry(((float) bean2.getUp60to69())/bean2.getTotalCount(),"60-69"));
        entries.add(new PieEntry(((float) bean2.getUp70to79())/bean2.getTotalCount(),"70-79"));
        entries.add(new PieEntry(((float) bean2.getUp80to89())/bean2.getTotalCount(),"80-89"));
        entries.add(new PieEntry(((float) bean2.getUp90to9())/bean2.getTotalCount(),"90-99"));
        entries.add(new PieEntry(((float) bean2.getFullScore())/bean2.getTotalCount(),"满分"));
        PieDataSet set = new PieDataSet(entries,"分数");
        set.setColors(ColorTemplate.VORDIPLOM_COLORS);
        PieData data = new PieData(set);
        DecimalFormat    df   = new DecimalFormat("######0.00");
        mChart.setData(data);
        mChart.setCenterTextSize(5);
        mChart.setCenterText("课程平均分:"+df.format(bean2.getAverageGrade()));
        mChart.invalidate();
       // yVals.add(new Entry((double)up);

    }
}
