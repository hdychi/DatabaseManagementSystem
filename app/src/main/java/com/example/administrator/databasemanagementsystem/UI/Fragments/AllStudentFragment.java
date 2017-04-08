package com.example.administrator.databasemanagementsystem.UI.Fragments;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.databinding.tool.DataBinder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.R;
import com.example.administrator.databasemanagementsystem.UI.Activities.UpdateCourseActivity;
import com.example.administrator.databasemanagementsystem.UI.Adapters.CourseRecyclerAdapter;
import com.example.administrator.databasemanagementsystem.viewModels.AllStudentViewModel;
import com.example.administrator.databasemanagementsystem.viewModels.StudentItemViewModel;
import com.kelin.mvvmlight.base.ViewModel;
import com.example.administrator.databasemanagementsystem.databinding.AllStudentFragmentBinding;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/7.
 */

public class AllStudentFragment extends Fragment {
    private Context mContext;
    private View layout;
    private DataBaseHelper mHelper;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savadInstanceState){
        mContext = getActivity();
        //layout = inflater.inflate(R.layout.all_student_fragment,null);
        AllStudentFragmentBinding binding = DataBindingUtil.inflate(inflater,R.layout.all_student_fragment,null,false);
        AllStudentViewModel model = new AllStudentViewModel(mContext);
        binding.setViewModel(model);
        String databasePath = Environment.getExternalStorageDirectory() + "/databaseManagement/" + "data.db";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(databasePath, null);

        mHelper = new DataBaseHelper(db);
        model.getData(mHelper);
        return binding.getRoot();
    }
}
