package com.example.administrator.databasemanagementsystem.Models;

import java.util.List;

/**
 * Created by Administrator on 2017/3/19.
 */

public class DataBean2 {
    private Course course;
    private List<RecyclerItem> items;
    public List<RecyclerItem> getItems() {
        return items;
    }

    public void setItems(List<RecyclerItem> items) {
        this.items = items;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }



}
