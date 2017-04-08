package com.example.administrator.databasemanagementsystem.viewModels;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.widget.Toast;

import com.example.administrator.databasemanagementsystem.BR;
import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.Models.Course;
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

public class AllCourseViewModel implements ViewModel {
    private Context mContext;
    public AllCourseViewModel(Context context){
        mContext = context;
    }
    public ObservableList<CourseItemViewModel> items = new ObservableArrayList<>();
    public ItemView itemView = ItemView.of(BR.viewModel, R.layout.all_course_item);
    public void getData(DataBaseHelper helper){
        Subscriber<Course> subscriber = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(mContext,"获取数据错误",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Course course) {
                items.add(new CourseItemViewModel(course));
            }
        };

        Observable.just(helper).map(new Func1<DataBaseHelper, List<Course>>() {

            @Override
            public List<Course> call(DataBaseHelper helper) {
                return helper.getAllCourse();
            }
        }).flatMap(new Func1<List<Course>, Observable<Course>>() {
            @Override
            public Observable<Course> call(List<Course> students) {
                return Observable.from(students);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }
}
