package com.example.administrator.databasemanagementsystem.viewModels;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.widget.Toast;

import com.example.administrator.databasemanagementsystem.BR;
import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.Models.ChooseCourse;
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

public class AllChooseViewModel implements ViewModel {
    private Context mContext;
    public AllChooseViewModel(Context context){
        mContext = context;
    }
    public ObservableList<ChooseItemViewModel> items = new ObservableArrayList<>();
    public ItemView itemView = ItemView.of(BR.viewModel, R.layout.all_choose_item);
    public ObservableBoolean isRefresh = new ObservableBoolean();
    public void getData(DataBaseHelper helper){
        Subscriber<ChooseItemViewModel> subcriber = new Subscriber<ChooseItemViewModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(mContext,"获取数据错误",Toast.LENGTH_SHORT).show();
                isRefresh.set(false);
            }

            @Override
            public void onNext(ChooseItemViewModel chooseItemViewModel) {
                items.add(chooseItemViewModel);
                isRefresh.set(false);
            }
        };
        Observable.just(helper).map(new Func1<DataBaseHelper, List<ChooseCourse>>() {
            @Override
            public List<ChooseCourse> call(DataBaseHelper helper) {
                return helper.getAllChoose();
            }
        }).flatMap(new Func1<List<ChooseCourse>, Observable<ChooseCourse>>() {
            @Override
            public Observable<ChooseCourse> call(List<ChooseCourse> chooseCourses) {
                return rx.Observable.from(chooseCourses);
            }
        }).map(new Func1<ChooseCourse, ChooseItemViewModel>() {
            @Override
            public ChooseItemViewModel call(ChooseCourse chooseCourse) {
                return new ChooseItemViewModel((chooseCourse));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subcriber);
    }
}
