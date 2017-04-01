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
                Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdId="+"'"+student+"'"+" and courName="+"'"+course+"'",null);
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
                Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdName="+"'"+student+"'"+" and courId="+"'"+course+"'",null);
                if(cursor!=null&&cursor.moveToFirst()) {
                   // if (cursor.moveToNext()) {
                        ChooseCourse chooseCourse = new ChooseCourse(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
                        return chooseCourse;
                  //  }
                }
                return null;
            }
            else{
                Cursor cursor = mDatabase.rawQuery("select * from ChooseCourse where stdName="+"'"+student+"'"+" and courName="+"'"+course+"'",null);
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
        int totalCredit = 0;
        for(ChooseCourse chooseCourse:chooseCourses){
            Course course = getCourseWithId(chooseCourse.getCourId());
            res+=course.getCourCredit()*chooseCourse.getGrade();
            totalCredit+=course.getCourCredit();
        }
        if(totalCredit>0) {
            res = res / totalCredit;
        }
        return res;
    }
    public double getClassGPA(String className){
       Cursor cursor = mDatabase.rawQuery("select * from Student where stdClass=?",new String[]{className});
        double res = 0.0;
        int cnt = 0;
        if(cursor!=null&&cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {


                    Student temp = new Student(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5));
                    if(getStudentGPA(temp.getStdId())!=0){
                        res += getStudentGPA(temp.getStdId());
                        cnt++;
                    }

                    cursor.moveToNext();


            }
            if(cnt!=0) {
                res = res / cnt;
            }
            return  res;

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
        values.put("stdInYear",stdYear);
        values.put("stdClass",stdClass);
        for(String key:values.keySet()){
            Log.i("检查values",key+values.get(key));
        }
        if(mDatabase.insert("Student",null,values)<0){
            Log.i("插入","失败");
        }
        else{
            Log.i("插入","成功");
        }

    }
    public void insertCourse(String courId,String courName,String courTeacherName,int courCredit,int courMinGrade,int courCancelYear){
        ContentValues values = new ContentValues();
        values.put("courId",courId);
        values.put("courName",courName);
        values.put("courTeacherName",courTeacherName);
        values.put("courCredit",courCredit);
        values.put("courMinGrade",courMinGrade);

        values.put("courCancelYear", courCancelYear);

        mDatabase.insert("Course",null,values);
    }
    public void insertChoose(String stdId,String courId,int chooseYear,int grade){
        ContentValues values = new ContentValues();
        values.put("stdId",stdId);
        values.put("courId",courId);
        values.put("chooseYear",chooseYear);
        values.put("grade",grade);

        mDatabase.insert("ChooseCourse",null,values);
    }
    public void deleteStudent(String stdId){
        mDatabase.delete("Student","stdId=?",new String[]{stdId});
    }
    public void deleteCourse(String courId){
        mDatabase.delete("Course","courId=?",new String[]{courId});
    }
    public void deleteChoose(String stdId,String courId){
        mDatabase.delete("ChooseCourse","stdId=? and courId=?",new String[]{stdId,courId});
    }
    public void updateStudent(String columnName,String newValues,String stdId){
         ContentValues values = new ContentValues();
         values.put(columnName,newValues);

          mDatabase.update("Student",values,"stdId=?",new String[]{stdId});
    }
    public void updateStudent(String columName,int newValues,String stdId){
        ContentValues values = new ContentValues();
        values.put(columName,newValues);

        mDatabase.update("Student",values,"stdId=?",new String[]{stdId});
    }
    public void updateCourse(String columnName,String newValues,String Id){
        ContentValues values = new ContentValues();
        values.put(columnName,newValues);

        mDatabase.update("Course",values,"courId=?",new String[]{Id});
    }
    public void updateCourse(String columnName,int newValues,String Id){
        ContentValues values = new ContentValues();
        values.put(columnName,newValues);

        mDatabase.update("Course",values,"courId=?",new String[]{Id});
    }
    public void updateChoose(String columnName,String newValues,String stdId,String courId){
        ContentValues values = new ContentValues();
        values.put(columnName,newValues);

        mDatabase.update("ChooseCourse",values,"stdId=? and courId=?",new String[]{stdId,courId});
    }
    public void updateChoose(String columnName,int newValues,String stdId,String courId){
        ContentValues values = new ContentValues();
        values.put(columnName,newValues);

        mDatabase.update("ChooseCourse",values,"stdId=? and courId=?",new String[]{stdId,courId});
    }
}
