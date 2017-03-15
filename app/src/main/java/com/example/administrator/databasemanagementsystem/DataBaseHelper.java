package com.example.administrator.databasemanagementsystem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.databasemanagementsystem.Models.ChooseCourse;
import com.example.administrator.databasemanagementsystem.Models.Course;
import com.example.administrator.databasemanagementsystem.Models.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/11.
 */

public class DataBaseHelper {
    private SQLiteDatabase mDatabase;
    public DataBaseHelper(SQLiteDatabase database){
        mDatabase = database;
    }
    public Student getStudentWithName(String name){
        Cursor cursor = mDatabase.rawQuery("select * from Student where stdName="+"'"+name+"'",null);
        if(cursor.moveToNext()){
            Student student = new Student(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5));
            return student;
        }
        return null;
    }
    public Student getStudentWithId(String id){
        Cursor cursor = mDatabase.rawQuery("select * from Student where stdName="+"'"+id+"'",null);
        if(cursor.moveToNext()){
            Student student = new Student(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5));
            return student;
        }
        return null;
    }
    public Course getCourseWithName(String name){
        Cursor cursor = mDatabase.rawQuery("select * from Course where courName="+"'"+name+"'",null);
        if(cursor.moveToNext()){
            Course course = new Course(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5));
            return course;
        }
        return null;
    }
    public Course getCourseWithId(String id){
        Cursor cursor = mDatabase.rawQuery("select * from Course where courId="+"'"+id+"'",null);
        if(cursor.moveToNext()){
            Course course = new Course(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5));
            return course;
        }
        return null;
    }
    public ChooseCourse getChooseWithStudentAndCourse(String student,String course){
        if(student.charAt(0)>='0'&&student.charAt(0)<='9'){
            if(course.charAt(0)>='0'&&course.charAt(0)<='9'){
                Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdId="+"'"+student+"'"+" and courId="+"'"+course+"'",null);
                if(cursor.moveToNext()){
                    ChooseCourse chooseCourse = new ChooseCourse(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
                    return chooseCourse;
                }
                return null;
            }
            else{
                Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdId="+"'"+student+"'"+" and courName="+"'"+course+"'",null);
                if(cursor.moveToNext()){
                    ChooseCourse chooseCourse = new ChooseCourse(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
                    return chooseCourse;
                }
                return null;
            }
        }
        else{
            if(course.charAt(0)>='0'&&course.charAt(0)<='9'){
                Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdName="+"'"+student+"'"+" and courId="+"'"+course+"'",null);
                if(cursor.moveToNext()){
                    ChooseCourse chooseCourse = new ChooseCourse(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
                    return chooseCourse;
                }
                return null;
            }
            else{
                Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdName="+"'"+student+"'"+" and courName="+"'"+course+"'",null);
                if(cursor.moveToNext()){
                    ChooseCourse chooseCourse = new ChooseCourse(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
                    return chooseCourse;
                }
                return null;
            }
        }
    }
    public List<ChooseCourse> getChooseCourseWithCourse(String course){
        List<ChooseCourse> res = new ArrayList<ChooseCourse>();
        if(course.charAt(0)>='0'&&course.charAt(0)<='9'){
            Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where courId="+"'"+course+"'",null);
            for(int i = 0;i < cursor.getCount();i++){
                cursor.move(i);
                res.add(new ChooseCourse(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            }

        }
        else{
            Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where courName="+"'"+course+"'",null);
            for(int i = 0;i < cursor.getCount();i++){
                cursor.move(i);
                res.add(new ChooseCourse(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            }

        }
        return  res;
    }
    public List<ChooseCourse> getChooseCourseWithStudent(String student){
        List<ChooseCourse> res = new ArrayList<ChooseCourse>();
        if(student.charAt(0)>='0'&&student.charAt(0)<='9'){
            Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdId= "+"'"+student+"'",null);
            for(int i = 0;i < cursor.getCount();i++){
                cursor.move(i);
                res.add(new ChooseCourse(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            }

        }
        else{
            Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdName= "+"'"+student+"'",null);
            for(int i = 0;i < cursor.getCount();i++){
                cursor.move(i);
                res.add(new ChooseCourse(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            }

        }
        return  res;
    }
    public double getStudentGPA(String stdId){
        List<ChooseCourse> chooseCourses = getChooseCourseWithStudent(stdId);
        double res = 0.0;
        int totalCredit = 0;
        for(ChooseCourse chooseCourse:chooseCourses){
            Course course = getCourseWithId(chooseCourse.getCourId());
            res+=course.getCourCredit()*chooseCourse.getGrade();
            totalCredit+=course.getCourCredit();
        }
        res = res/totalCredit;
        return res;
    }
    public double getClassGPA(String className){
       Cursor cursor = mDatabase.rawQuery("select * from Student where stdClass="+"'"+className+"'",null);
        double res = 0.0;
        if(cursor.getCount()>0) {
            for (int i = 0; i<cursor.getCount();i++){
                cursor.move(i);
                Student temp =  new Student(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5));
                res+=getStudentGPA(temp.getStdId());
            }
            res = res/cursor.getCount();
            return res;
        }
        else {
            return 0;
        }
    }
    public void insertStudent(String stdId,String stdName,String stdGender,int stdAge,int stdYear,String stdClass){
        ContentValues values = new ContentValues();
        values.put("stdId",stdId);
        values.put("stdName",stdName);
        values.put("stdGender",stdGender);
        values.put("stdAge",stdAge);
        values.put("stdYear",stdYear);
        values.put("stdClass",stdClass);
        mDatabase.insert("Student",null,values);
    }
    public void insertCourse(String courId,String courName,String courTeacherName,int courCredit,int courMinGrade,int courCancelYear){
        ContentValues values = new ContentValues();
        values.put("courId",courId);
        values.put("courName",courName);
        values.put("courTeacherName",courTeacherName);
        values.put("courCredit",courCredit);
        values.put("courMinGrade",courMinGrade);
        values.put("courCancelYear",courCancelYear);
        mDatabase.insert("Course",null,values);
    }
    public void insertChoose(String stdId,String courId,int chooseYear,int grade){
        ContentValues values = new ContentValues();
        values.put("stdId",stdId);
        values.put("courId",courId);
        values.put("chooseYea",chooseYear);
        values.put("grade",grade);

        mDatabase.insert("ChooseCourse",null,values);
    }
    public void deleteStudent(String stdId){
        String sql = "delete from Student S where S.stdId="+stdId;
        mDatabase.execSQL(sql);
    }
    public void deleteCourse(String courId){
        String sql = "delete from Course S where S.courId="+courId;
        mDatabase.execSQL(sql);
    }
    public void deleteChoose(String stdId,String courId){
        String sql = "delete from ChooseCourse S where S.stdId="+stdId+ " and S.courId="+courId;
        mDatabase.execSQL(sql);
    }
    public void updateStudent(String columnName,String newValues,String stdId){
         String sql = "update Student set "+columnName+"="+newValues+" where stdId="+stdId;
         mDatabase.execSQL(sql);
    }
    public void updateStudent(String columName,int newValues,String stdId){
        String sql = "update Student set "+columName+"="+newValues+" where stdId="+stdId;
        mDatabase.execSQL(sql);
    }
    public void updateCourse(String columnName,String newValues,String Id){
        String sql = "update Course set "+columnName+"="+newValues+" where courId="+Id;
        mDatabase.execSQL(sql);
    }
    public void updateCourse(String columnName,int newValues,String Id){
        String sql = "update Course set "+columnName+"="+newValues+" where courId="+Id;
        mDatabase.execSQL(sql);
    }
    public void updateChoose(String columnName,String newValues,String stdId,String courId){
        String sql = "update ChooseCourse set "+columnName+"="+newValues+" where stdId="+stdId+" and "+"courId"+courId;
        mDatabase.execSQL(sql);
    }
    public void updateChoose(String columnName,int newValues,String stdId,String courId){
        String sql = "update ChooseCourse set "+columnName+"="+newValues+" where stdId="+stdId+" and "+"courId"+courId;
        mDatabase.execSQL(sql);
    }
}
