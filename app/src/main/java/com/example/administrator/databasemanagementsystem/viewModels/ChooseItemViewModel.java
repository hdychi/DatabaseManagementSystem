package com.example.administrator.databasemanagementsystem.viewModels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.example.administrator.databasemanagementsystem.Models.ChooseCourse;
import com.kelin.mvvmlight.base.ViewModel;

/**
 * Created by Administrator on 2017/4/7.
 */

public class ChooseItemViewModel implements ViewModel{
    /*private String stdId;
    private String courId;
    private int chooseYear;
    private int grade;*/
    public ObservableField<String> stdId = new ObservableField<>();
    public ObservableField<String> courId = new ObservableField<>();
    public ObservableInt chooseYear = new ObservableInt();
    public ObservableInt grade = new ObservableInt();
    public ChooseItemViewModel(ChooseCourse chooseCourse){
        stdId.set(chooseCourse.getStdId());
        courId.set(chooseCourse.getCourId());
        chooseYear.set(chooseCourse.getChooseYear());
        grade.set(chooseCourse.getGrade());
    }
}
