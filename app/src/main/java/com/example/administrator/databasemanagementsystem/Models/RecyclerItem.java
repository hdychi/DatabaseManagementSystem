package com.example.administrator.databasemanagementsystem.Models;

/**
 * Created by Administrator on 2017/3/12.
 */

public class RecyclerItem {
    private Student student;
    private Course course;
    private ChooseCourse chooseCourse;
    public RecyclerItem(Student student,Course course,ChooseCourse chooseCourse){
        this.student = student;
        this.course = course;
        this.chooseCourse = chooseCourse;
    }
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ChooseCourse getChooseCourse() {
        return chooseCourse;
    }

    public void setChooseCourse(ChooseCourse chooseCourse) {
        this.chooseCourse = chooseCourse;
    }



}
