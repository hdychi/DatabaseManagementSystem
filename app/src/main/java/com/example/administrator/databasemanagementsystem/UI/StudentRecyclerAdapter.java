package com.example.administrator.databasemanagementsystem.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.databasemanagementsystem.DataBaseHelper;
import com.example.administrator.databasemanagementsystem.Models.RecyclerItem;
import com.example.administrator.databasemanagementsystem.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/13.
 */

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.IViewHolder>{
    private List<RecyclerItem> mItems;
    private RecyclerItem nowItem;
    private Context mContext;
    private DataBaseHelper mHelper;
    public StudentRecyclerAdapter(Context context){
        mItems = new ArrayList<>();
        mContext = context;
    }
    public StudentRecyclerAdapter(Context context,DataBaseHelper helper){
        mContext = context;
        mItems = new ArrayList<>();
        mHelper = helper;
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
    public IViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        CardView layout = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        IViewHolder holder = new IViewHolder(layout);
        return holder;
    }
    @Override
    public void onBindViewHolder(IViewHolder holder, final int position){
        View itemLayout = (View)holder.itemView;
        nowItem = mItems.get(position);
        final TextView courId = (TextView)itemLayout.findViewById(R.id.chooseCourId);
        final TextView courName = (TextView)itemLayout.findViewById(R.id.chooseCourName);
        final TextView teacher = (TextView)itemLayout.findViewById(R.id.courTeacherName);
        final TextView courCredit = (TextView)itemLayout.findViewById(R.id.courCredit);
        final TextView minGrade = (TextView)itemLayout.findViewById(R.id.courMinGrade);
        final TextView cancelYear = (TextView)itemLayout.findViewById(R.id.courCancelYear);
        final TextView chooseYear = (TextView)itemLayout.findViewById(R.id.chooseYear);
        final TextView grade = (TextView)itemLayout.findViewById(R.id.grade);
        final Button updateButton = (Button)itemLayout.findViewById(R.id.list_item_update_button);
        final Button deleteButton = (Button)itemLayout.findViewById(R.id.list_item_delete_button);

        courId.setText(nowItem.getCourse().getCourId());
        courName.setText(nowItem.getCourse().getCourName());
        teacher.setText(nowItem.getCourse().getCourTeacherName());
        courCredit.setText(nowItem.getCourse().getCourCredit()+"");
        minGrade.setText(nowItem.getCourse().getCourMinGrade()+"");
        if(nowItem.getCourse().getCourCancelYear()>0) {
            cancelYear.setText(nowItem.getCourse().getCourCancelYear() + "");
        }
        else{
            cancelYear.setText("");
        }
        chooseYear.setText(nowItem.getChooseCourse().getChooseYear()+"");
        grade.setText(nowItem.getChooseCourse().getGrade()+"");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("stdId",mItems.get(position).getStudent().getStdId());
                intent.putExtra("courId",mItems.get(position).getCourse().getCourId());
                intent.putExtra("chooseYear",mItems.get(position).getChooseCourse().getChooseYear());
                intent.putExtra("grade",mItems.get(position).getChooseCourse().getGrade());
                intent.setClass(mContext,UpdateChooseFromStudentActivity.class);
                mContext.startActivity(intent);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    public static class IViewHolder extends RecyclerView.ViewHolder {

        public IViewHolder(View itemView) {
            super(itemView);
        }
    }
}
