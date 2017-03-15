package com.example.administrator.databasemanagementsystem.Models;

/**
 * Created by Administrator on 2017/3/11.
 */

public class Course {
    private  String courId;
    private String courName;
    private String courTeacherName;
    private int courCredit;
    private int courMinGrade;
    private int courCancelYear;
    public Course(){

    }
    public Course(String courId,String courName,String courTeacherName,int courCredit,int courMinGrade,int courCancelYear){
        this.courId = courId;
        this.courName = courName;
        this.courTeacherName = courTeacherName;
        setCourCredit(courCredit);
        setCourMinGrade(courMinGrade);
        setCourCancelYear(courCancelYear);
    }
    public int getCourCredit() {
        return courCredit;
    }

    public void setCourCredit(int courCredit) {
        this.courCredit = courCredit;
    }

    public String getCourId() {
        return courId;
    }

    public void setCourId(String courId) {
        this.courId = courId;
    }

    public String getCourName() {
        return courName;
    }

    public void setCourName(String courName) {
        this.courName = courName;
    }

    public String getCourTeacherName() {
        return courTeacherName;
    }

    public void setCourTeacherName(String courTeacherName) {
        this.courTeacherName = courTeacherName;
    }

    public int getCourMinGrade() {
        return courMinGrade;
    }

    public void setCourMinGrade(int courMinGrade) {
        this.courMinGrade = courMinGrade;
    }

    public int getCourCancelYear() {
        return courCancelYear;
    }

    public void setCourCancelYear(int courCancelYear) {
        this.courCancelYear = courCancelYear;
    }



}
