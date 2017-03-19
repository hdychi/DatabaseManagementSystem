package com.example.administrator.databasemanagementsystem.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.databasemanagementsystem.Models.RecyclerItem;
import com.example.administrator.databasemanagementsystem.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.IViewHolder>{
    private List<RecyclerItem> mItems;
    private RecyclerItem nowItem;
    private Context mContext;
    public RecyclerAdapter(Context context){
        mItems = new ArrayList<>();
        mContext = context;
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
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        IViewHolder holder = new IViewHolder(layout);
        return holder;
    }
    @Override
    public void onBindViewHolder(IViewHolder holder,int position){
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

        courId.setText(nowItem.getCourse().getCourId());
        courName.setText(nowItem.getCourse().getCourName());
        teacher.setText(nowItem.getCourse().getCourTeacherName());
        courCredit.setText(nowItem.getCourse().getCourCredit());
        minGrade.setText(nowItem.getCourse().getCourMinGrade());
        cancelYear.setText(nowItem.getCourse().getCourCancelYear());
        chooseYear.setText(nowItem.getChooseCourse().getChooseYear());
        grade.setText(nowItem.getChooseCourse().getGrade());
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setClass(mContext,UpdateChooseActivity.class);
                intent.putExtra("stdId",nowItem.getStudent().getStdId());
                intent.putExtra("courId",nowItem.getCourse().getCourId());
                mContext.startActivity(intent);
            }
        });*/
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
