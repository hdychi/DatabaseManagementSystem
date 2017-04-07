package com.example.administrator.databasemanagementsystem.viewModels;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.example.administrator.databasemanagementsystem.BR;
import com.example.administrator.databasemanagementsystem.R;
import com.kelin.mvvmlight.base.ViewModel;

import me.tatarka.bindingcollectionadapter.ItemView;

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
}
