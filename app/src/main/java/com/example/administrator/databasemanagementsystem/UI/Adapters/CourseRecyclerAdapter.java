package com.example.administrator.databasemanagementsystem.UI.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.Models.RecyclerItem;
import com.example.administrator.databasemanagementsystem.R;
import com.example.administrator.databasemanagementsystem.UI.Activities.UpdateChooseFromCourseActivity;
import com.example.administrator.databasemanagementsystem.UI.Fragments.CourseFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/19.
 */

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.mViewHolder> {
    private List<RecyclerItem> mItems;
    private RecyclerItem nowItem;
    private Context mContext;
    private DataBaseHelper mHelper;
    private CourseFragment mFragment;
    public CourseRecyclerAdapter(Context context){
        mItems = new ArrayList<>();
        mContext = context;
    }
    public CourseRecyclerAdapter(Context context,DataBaseHelper helper){
        mItems = new ArrayList<>();
        mContext = context;
        mHelper = helper;
    }
    public CourseRecyclerAdapter(Context context,DataBaseHelper helper,CourseFragment fragment){
        mItems = new ArrayList<>();
        mContext = context;
        mHelper = helper;
        mFragment = fragment;
    }
    public void  addAll(Collection<RecyclerItem> list){
        mItems.addAll(list);

    }
    public void remove(int position){
        mItems.remove(position);
        notifyDataSetChanged();
    }
    public void clear(){
        mItems.clear();
        notifyDataSetChanged();
    }
    public void add(RecyclerItem item){
        mItems.add(item);
    }
    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView layout = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item,parent,false);
        mViewHolder holder = new mViewHolder(layout) ;
        return holder;
    }
    @Override
    public void onBindViewHolder(mViewHolder holder,final int position){
        View itemLayout = (View)holder.itemView;
        nowItem = mItems.get(position);
        final TextView stdId = (TextView)itemLayout.findViewById(R.id.courFragstdId);
        final TextView stdName = (TextView)itemLayout.findViewById(R.id.courFragstdName);
        final TextView stdChooseYear = (TextView)itemLayout.findViewById(R.id.courFragChooseYear);
        final TextView stdGrade = (TextView)itemLayout.findViewById(R.id.courFragGrade);
        final Button updateButton = (Button) itemLayout.findViewById(R.id.course_list_item_update_button);
        final Button deleteButton = (Button)itemLayout.findViewById(R.id.course_list_item_delete_button);

        stdId.setText(nowItem.getStudent().getStdId());
        stdName.setText(nowItem.getStudent().getStdName());
        stdChooseYear.setText(nowItem.getChooseCourse().getChooseYear()+"");
        stdGrade.setText(nowItem.getChooseCourse().getGrade()+"");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("stdId",mItems.get(position).getStudent().getStdId());
                intent.putExtra("courId",mItems.get(position).getCourse().getCourId());
                intent.putExtra("chooseYear",mItems.get(position).getChooseCourse().getChooseYear());
                intent.putExtra("grade",mItems.get(position).getChooseCourse().getGrade());
                intent.setClass(mContext,UpdateChooseFromCourseActivity.class);
                Log.i("适配器位置",position+"");
                mContext.startActivity(intent);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("适配器位置",position+"");
                Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        mHelper.deleteChoose(mItems.get(position).getStudent().getStdId(),mItems.get(position).getCourse().getCourId());
                        subscriber.onNext("删除数据");
                    }
                });
                Subscriber<String> subscriber = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof IOException){
                            Log.i("删除","异常");
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        mItems.remove(position);
                        notifyDataSetChanged();
                        mFragment.getData();
                    }
                };
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mItems.size();
    }
    public static class mViewHolder extends RecyclerView.ViewHolder {

        public mViewHolder(View itemView) {
            super(itemView);
        }
    }
}
