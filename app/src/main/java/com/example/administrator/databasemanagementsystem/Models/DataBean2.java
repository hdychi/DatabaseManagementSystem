package com.example.administrator.databasemanagementsystem.Models;

import java.util.List;

/**
 * Created by Administrator on 2017/3/19.
 */

public class DataBean2 {
    private Course course;
    private List<RecyclerItem> items;
    private double averageGrade;
    private int under60;
    private int up60to69;
    private int up70to79;
    private int up80to89;
    private int up90to9;
    private int fullScore;
    private int totalCount;
    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }





    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public int getUnder60() {
        return under60;
    }

    public void setUnder60(int under60) {
        this.under60 = under60;
    }

    public int getUp60to69() {
        return up60to69;
    }

    public void setUp60to69(int up60to69) {
        this.up60to69 = up60to69;
    }

    public int getUp70to79() {
        return up70to79;
    }

    public void setUp70to79(int up70to79) {
        this.up70to79 = up70to79;
    }

    public int getUp80to89() {
        return up80to89;
    }

    public void setUp80to89(int up80to89) {
        this.up80to89 = up80to89;
    }

    public int getUp90to9() {
        return up90to9;
    }

    public void setUp90to9(int up90to9) {
        this.up90to9 = up90to9;
    }

    public int getFullScore() {
        return fullScore;
    }

    public void setFullScore(int fullScore) {
        this.fullScore = fullScore;
    }


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
