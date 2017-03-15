package com.example.administrator.databasemanagementsystem.Models;

/**
 * Created by Administrator on 2017/3/11.
 */

public class Student {
    public String getStdId() {
        return stdId;
    }

    public void setStdId(String stdId) {
        this.stdId = stdId;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public String getStdGender() {
        return stdGender;
    }

    public void setStdGender(String stdGender) {
        this.stdGender = stdGender;
    }

    public int getStdAge() {
        return stdAge;
    }

    public void setStdAge(int stdAge) {
        this.stdAge = stdAge;
    }

    public int getStdYear() {
        return stdYear;
    }

    public void setStdYear(int stdYear) {
        this.stdYear = stdYear;
    }

    public String getStdClass() {
        return stdClass;
    }

    public void setStdClass(String stdClass) {
        this.stdClass = stdClass;
    }
    public Student(){

    }
    public Student(String stdId,String stdName,String stdGender,int stdAge,int stdYear,String stdClass){
        setStdId(stdId);
        setStdName(stdName);
        this.stdGender = stdGender;
        this.stdAge = stdAge;
        this.stdClass = stdClass;
        this.stdYear = stdYear;
    }
    private String stdId;
    private String stdName;
    private  String stdGender;
    private int stdAge;
    private  int stdYear;
    private String stdClass;

}
