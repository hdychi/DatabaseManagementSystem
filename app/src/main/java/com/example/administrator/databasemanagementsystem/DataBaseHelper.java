package com.example.administrator.databasemanagementsystem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        Cursor cursor = mDatabase.rawQuery("select name from sqlite_master where type='table' order by name", null);
        while(cursor.moveToNext()){
            //遍历出表名
            String name = cursor.getString(0);
            Log.i("表名遍历", name);
        }


    }
    public List<Student> getAllStudent(){
        Cursor cursor = mDatabase.rawQuery("select * from Student",null);
        List<Student> res = new ArrayList<>();
        if(cursor!=null&&cursor.moveToFirst()) {
            //int cnt = cursor.getCount();
          //  Log.i("Cursor长度",cnt+"");
            while (!cursor.isAfterLast()) {

                res.add(new Student(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5)));
                cursor.moveToNext();
            }
        }
        return  res;
    }
    public List<Course> getAllCourse(){
        Cursor cursor = mDatabase.rawQuery("select * from Course",null);
        List<Course> res = new ArrayList<>();
        if(cursor!=null&&cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {

                res.add(new Course(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5)));
                cursor.moveToNext();
            }
        }
        return  res;
    }
    public List<ChooseCourse> getAllChoose(){
        Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse",null);
        List<ChooseCourse> res = new ArrayList<>();
        if(cursor!=null&&cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {


                res.add(new ChooseCourse(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
                cursor.moveToNext();
            }
        }
        return  res;
    }
    public Student getStudentWithName(String name){
        Cursor cursor = mDatabase.rawQuery("select * from Student where stdName=?",new String[]{name});
        if(cursor!=null&&cursor.moveToFirst()) {
            //if (cursor.moveToNext()) {
                Student student = new Student(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5));
                 Log.i("查到了", "数据");
                return student;
            //}
        }
        return null;
    }
    public Student getStudentWithId(String id){
        String sql = "select * from Student where stdId=?";
        Cursor cursor = mDatabase.rawQuery(sql,new String[]{id});
        if(cursor!=null&&cursor.moveToFirst()) {
            Log.i("Cursor","存在");
            //if (cursor.moveToNext()) {
                Student student = new Student(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5));
                Log.i("查到了", "数据");
                return student;
          //  }
        }
        Log.i("没查到","数据"+sql);
        return null;
    }
    public Course getCourseWithName(String name){
        Cursor cursor = mDatabase.rawQuery("select * from Course where courName=?",new String[]{name});
        if(cursor!=null&&cursor.moveToFirst()) {
           // if (cursor.moveToNext()) {
                Course course = new Course(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));
                return course;
           // }
        }
        return null;
    }
    public Course getCourseWithId(String id){
        Cursor cursor = mDatabase.rawQuery("select * from Course where courId=?",new String[]{id});
        if(cursor!=null&&cursor.moveToFirst()) {
           // if (cursor.moveToNext()) {
                Course course = new Course(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));
                return course;
           // }
        }
        return null;
    }
    public ChooseCourse getChooseWithStudentAndCourse(String student,String course){
        if(student.charAt(0)>='0'&&student.charAt(0)<='9'){
            if(course.charAt(0)>='0'&&course.charAt(0)<='9'){
                Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdId=? and courId=?",new String[]{student,course});
                if(cursor!=null&&cursor.moveToFirst()) {
                   // if (cursor.moveToNext()) {
                        ChooseCourse chooseCourse = new ChooseCourse(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
                        return chooseCourse;
                   // }
                }
                return null;
            }
            else{
                Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdId=? and courName=?",new String[]{student,course});
                if(cursor!=null&&cursor.moveToFirst()) {
                   // if (cursor.moveToNext()) {
                        ChooseCourse chooseCourse = new ChooseCourse(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
                        return chooseCourse;
                   // }
                }
                return null;
            }
        }
        else{
            if(course.charAt(0)>='0'&&course.charAt(0)<='9'){
                Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdName=? and courId=?",new String[]{student,course});
                if(cursor!=null&&cursor.moveToFirst()) {
                   // if (cursor.moveToNext()) {
                        ChooseCourse chooseCourse = new ChooseCourse(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
                        return chooseCourse;
                  //  }
                }
                return null;
            }
            else{
                Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdName=? and courName=?",new String[]{student,course});
                if(cursor!=null&&cursor.moveToFirst()) {
                   // if (cursor.moveToNext()) {
                        ChooseCourse chooseCourse = new ChooseCourse(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
                        return chooseCourse;
                   // }
                }
                return null;
            }
        }
    }
    public List<ChooseCourse> getChooseCourseWithCourse(String course){
        List<ChooseCourse> res = new ArrayList<ChooseCourse>();
        if(course.charAt(0)>='0'&&course.charAt(0)<='9'){
            Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where courId=?",new String[]{course});
            if(cursor!=null&&cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    res.add(new ChooseCourse(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
                    cursor.moveToNext();
                }
            }

        }
        else{
            Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where courName="+"'"+course+"'",null);
            if(cursor!=null&&cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    res.add(new ChooseCourse(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
                    cursor.moveToNext();
                }
            }

        }
        return  res;
    }
    public List<ChooseCourse> getChooseCourseWithStudent(String student){
        List<ChooseCourse> res = new ArrayList<ChooseCourse>();
        if(student.charAt(0)>='0'&&student.charAt(0)<='9'){
            Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdId=?",new String[]{student});
            if(cursor!=null&&cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    res.add(new ChooseCourse(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
                    cursor.moveToNext();
                }
            }

        }


        return  res;
    }
    public double getStudentGPA(String stdId){
        List<ChooseCourse> chooseCourses = getChooseCourseWithStudent(stdId);
        double res = 0.0;
       /* int totalCredit = 0;
        for(ChooseCourse chooseCourse:chooseCourses){
            Course course = getCourseWithId(chooseCourse.getCourId());
            res+=course.getCourCredit()*chooseCourse.getGrade();
            totalCredit+=course.getCourCredit();
        }
        if(totalCredit>0) {
            res = res / totalCredit;
        }*/
        Cursor cursor = mDatabase.rawQuery("select avg(grade) from ChooseCourse where stdId=? group by stdId",new String[]{stdId});
        if(cursor!=null&&cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                res = cursor.getDouble(0);
                cursor.moveToNext();
            }
        }
        return res;
    }
    public double getClassGPA(String className){

        double res = 0.0;
        
        Cursor cursor = mDatabase.rawQuery("select avg(ChooseCourse.grade) from Student, " +
                "ChooseCourse where Student.stdClass=? and Student.stdId=ChooseCourse.stdId group by stdClass" ,new String[]{className});
        if(cursor!=null&&cursor.moveToFirst()){
            res = cursor.getDouble(0);
        }
        return res;

    }
    public double getCouseAverage(String courId){
        double res = 0.0;
        Cursor cursor = mDatabase.rawQuery("select avg(grade) from ChooseCourse where courId=? group by courId",new String[]{courId});
        if(cursor!=null&&cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                res = cursor.getDouble(0);
                cursor.moveToNext();
            }
        }
        return res;
    }
    public  int getCountOfRangeGrade(String courId,int lowbound,int upperbound){
        int res = 0;
        Cursor cursor = mDatabase.rawQuery("select count(*) from ChooseCourse  where courId=? and grade between ? and ? group by courId",new String[]{courId,lowbound+"",upperbound+""});
        if(cursor!=null&&cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                res = cursor.getInt(0);
                cursor.moveToNext();
            }
        }
        return  res;
    }
    public void insertStudent(String stdId,String stdName,String stdGender,int stdAge,int stdYear,String stdClass){
        mDatabase.execSQL("insert into Student(stdId,stdName,stdGender,stdAge,stdInYear,stdClass) values (?,?,?,?,?,?);",
                new Object[]{stdId,stdName,stdGender,stdAge,stdYear,stdClass});


    }
    public void insertCourse(String courId,String courName,String courTeacherName,int courCredit,int courMinGrade,int courCancelYear){
      mDatabase.execSQL("insert into Course(courId,courName,courTeacherName,courCredit,courMinGrade,courCancelYear) values (?,?,?,?,?,?)",
              new Object[]{courId,courName,courTeacherName,courCredit,courMinGrade,courCancelYear});
    }
    public void insertChoose(String stdId,String courId,int chooseYear,int grade){
       mDatabase.execSQL("insert into ChooseCourse(stdId,courId,chooseYear,grade) values (?,?,?,?)",new Object[]{stdId,courId,chooseYear,grade});
    }
    public void deleteStudent(String stdId){

     mDatabase.execSQL("delete from Student where stdId=?",new Object[]{stdId});
    }
    public void deleteCourse(String courId){
       mDatabase.execSQL("delete from Course where courId=?",new Object[]{courId});
    }
    public void deleteChoose(String stdId,String courId){
       mDatabase.execSQL("delete from ChooseCourse where stdId=? and courId=?",new Object[]{stdId,courId});
    }
    public void updateStudent(String columnName,String newValues,String stdId){

        if(columnName.equals("stdId")) {
            List<ChooseCourse> chooseCourses = getChooseCourseWithStudent(stdId);
            for (ChooseCourse chooseCourse : chooseCourses) {
                deleteChoose(chooseCourse.getStdId(), chooseCourse.getCourId());
            }
        }

        mDatabase.execSQL("update Student set "+columnName+"=? where stdId=?",new Object[]{newValues,stdId});
    }
    public void updateStudent(String columName,int newValues,String stdId){
        mDatabase.execSQL("update Student set "+columName+"=? where stdId=?",new Object[]{newValues,stdId});
    }
    public void updateCourse(String columnName,String newValues,String Id){
        ContentValues values = new ContentValues();
        values.put(columnName,newValues);
        if(columnName.equals("courId")) {
            List<ChooseCourse> chooseCourses = getChooseCourseWithCourse(Id);
            for (ChooseCourse chooseCourse : chooseCourses) {
                deleteChoose(chooseCourse.getStdId(), chooseCourse.getCourId());
            }
        }
       mDatabase.execSQL("update Course set "+columnName+"=? where courId=?",new Object[]{newValues,Id});
    }
    public void updateCourse(String columnName,int newValues,String Id){
        mDatabase.execSQL("update Course set "+columnName+"=? where courId=?",new Object[]{newValues,Id});
    }
    public void updateChoose(String columnName,String newValues,String stdId,String courId){
       mDatabase.execSQL("update ChooseCourse set "+columnName+"=? where stdId=? and courId=?",new Object[]{newValues,stdId,courId});
    }
    public void updateChoose(String columnName,int newValues,String stdId,String courId){
        mDatabase.execSQL("update ChooseCourse set "+columnName+"=? where stdId=? and courId=?",new Object[]{newValues,stdId,courId});
    }
}
