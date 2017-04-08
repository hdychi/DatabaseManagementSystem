package com.example.administrator.databasemanagementsystem.UI.Fragments;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.R;
import com.example.administrator.databasemanagementsystem.databinding.AllCourseFragementBinding;
import com.example.administrator.databasemanagementsystem.databinding.AllStudentFragmentBinding;
import com.example.administrator.databasemanagementsystem.viewModels.AllCourseViewModel;
import com.example.administrator.databasemanagementsystem.viewModels.AllStudentViewModel;

/**
 * Created by Administrator on 2017/4/8.
 */

public class AllCourseFragment extends Fragment {
    private Context mContext;
    private View layout;
    private DataBaseHelper mHelper;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savadInstanceState){
        mContext = getActivity();
        //layout = inflater.inflate(R.layout.all_course_fragement,null);
        AllCourseFragementBinding binding = DataBindingUtil.inflate(inflater,R.layout.all_course_fragement,null,false);
        AllCourseViewModel model = new AllCourseViewModel(mContext);
        binding.setViewModel(model);
        String databasePath = Environment.getExternalStorageDirectory() + "/databaseManagement/" + "data.db";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(databasePath, null);

        mHelper = new DataBaseHelper(db);
        model.getData(mHelper);
        return binding.getRoot();
    }
}
