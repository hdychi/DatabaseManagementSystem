package com.example.administrator.databasemanagementsystem.Models;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public class DataBean {
    private Student student;
    private List<RecyclerItem> items;
    private double stdGPA;
    private double stdClassGPA;
    public double getStdClassGPA() {
        return stdClassGPA;
    }

    public void setStdClassGPA(double stdClassGPA) {
        this.stdClassGPA = stdClassGPA;
    }

    public double getStdGPA() {
        return stdGPA;
    }

    public void setStdGPA(double stdGPA) {
        this.stdGPA = stdGPA;
    }



    public List<RecyclerItem> getItems() {
        return items;
    }

    public void setItems(List<RecyclerItem> items) {
        this.items = items;
    }



    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


}
