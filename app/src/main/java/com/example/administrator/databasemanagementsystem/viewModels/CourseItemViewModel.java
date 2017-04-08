package com.example.administrator.databasemanagementsystem.viewModels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.example.administrator.databasemanagementsystem.Models.Course;
import com.kelin.mvvmlight.base.ViewModel;

/**
 * Created by Administrator on 2017/4/7.
 */

public class CourseItemViewModel implements ViewModel{
   /* private String courId;
    private String courName;
    private String courTeacherName;
    private int courCredit;
    private int courMinGrade;
    private int courCancelYear;*/
    public ObservableField<String> courId = new ObservableField<>();
    public ObservableField<String> courName = new ObservableField<>();
    public ObservableField<String> courTeacherName = new ObservableField<>();
    public ObservableInt courCredit = new ObservableInt();
    public ObservableInt courMinGrade = new ObservableInt();
    public ObservableField<String> courCancelYear = new ObservableField<>();
    public CourseItemViewModel(Course course){
        courId.set(course.getCourId());
        courName.set(course.getCourName());
        courTeacherName.set(course.getCourTeacherName());
        courCredit.set(course.getCourCredit());
        courMinGrade.set(course.getCourMinGrade());
        courCancelYear.set(course.getCourCancelYear()==-1?"":course.getCourCancelYear()+"");
    }


}
