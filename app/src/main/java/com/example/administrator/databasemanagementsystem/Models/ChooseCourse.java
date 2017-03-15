package com.example.administrator.databasemanagementsystem.Models;

/**
 * Created by Administrator on 2017/3/11.
 */

public class ChooseCourse {
    private String stdId;
    private String courId;
    private int chooseYear;
    private int grade;
    public ChooseCourse(String stdId,String courId,int chooseYear,int grade){
        setStdId(stdId);
        setCourId(courId);
        setChooseYear(chooseYear);
        setGrade(grade);
    }
    public String getCourId() {
        return courId;
    }

    public void setCourId(String courId) {
        this.courId = courId;
    }

    public String getStdId() {
        return stdId;
    }

    public void setStdId(String stdId) {
        this.stdId = stdId;
    }

    public int getChooseYear() {
        return chooseYear;
    }

    public void setChooseYear(int chooseYear) {
        this.chooseYear = chooseYear;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }



}
