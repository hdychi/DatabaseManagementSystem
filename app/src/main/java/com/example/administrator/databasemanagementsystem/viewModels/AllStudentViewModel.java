package com.example.administrator.databasemanagementsystem.viewModels;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.util.Log;
import android.widget.Toast;


import com.example.administrator.databasemanagementsystem.BR;
import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.Models.Student;
import com.example.administrator.databasemanagementsystem.R;
import com.kelin.mvvmlight.base.ViewModel;

import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/7.
 */

public class AllStudentViewModel implements ViewModel {
    private Context mContext;
    public AllStudentViewModel(Context context){
        mContext = context;
    }
    public ObservableList<StudentItemViewModel> items = new ObservableArrayList<>();
    public ItemView itemView = ItemView.of(BR.viewModel, R.layout.all_student_item);
    public ObservableBoolean isRefresh = new ObservableBoolean();
    public void getData(DataBaseHelper helper){
           isRefresh.set(true);
           Subscriber<Student> subscriber = new Subscriber<Student>() {
               @Override
               public void onCompleted() {

               }

               @Override
               public void onError(Throwable e) {
                   Toast.makeText(mContext,"获取数据错误",Toast.LENGTH_SHORT).show();
                   isRefresh.set(false);
                   e.printStackTrace();
               }

               @Override
               public void onNext(Student student) {
                   Log.i("模块"," 获取的学生添加了");
                   items.add(new StudentItemViewModel(student));
                   isRefresh.set(false);
               }
           };

           Observable.just(helper).map(new Func1<DataBaseHelper, List<Student>>() {

               @Override
               public List<Student> call(DataBaseHelper helper) {
                   return helper.getAllStudent();
               }
           }).flatMap(new Func1<List<Student>, Observable<Student>>() {
               @Override
               public Observable<Student> call(List<Student> students) {
                   return Observable.from(students);
               }
           }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }
}
