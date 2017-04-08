package com.example.administrator.databasemanagementsystem.viewModels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.example.administrator.databasemanagementsystem.Models.Student;
import com.kelin.mvvmlight.base.ViewModel;

/**
 * Created by Administrator on 2017/4/7.
 */

public class StudentItemViewModel implements ViewModel {
   /* private String stdId;
    private String stdName;
    private String stdGender;
    private int stdAge;
    private  int stdYear;
    private String stdClass;*/
    public ObservableField<String> stdId= new ObservableField<>();
    public ObservableField<String> stdName = new ObservableField<>();
    public ObservableField<String> stdGender = new ObservableField<>();
    public ObservableInt stdAge = new ObservableInt();
    public ObservableInt stdYear = new ObservableInt();
    public ObservableField<String> stdClass = new ObservableField<>();
    public StudentItemViewModel(Student student){
        stdId.set(student.getStdId());
        stdName.set(student.getStdName());
        stdGender.set(student.getStdGender());
        stdAge.set(student.getStdAge());
        stdClass.set(student.getStdClass());
        stdYear.set(student.getStdYear());
    }
}
