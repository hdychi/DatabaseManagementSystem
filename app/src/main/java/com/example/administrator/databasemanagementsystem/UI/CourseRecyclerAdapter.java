package com.example.administrator.databasemanagementsystem.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.databasemanagementsystem.Models.RecyclerItem;
import com.example.administrator.databasemanagementsystem.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2017/3/19.
 */

public class CourseRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.IViewHolder> {
    private List<RecyclerItem> mItems;
    private RecyclerItem nowItem;
    private Context mContext;
    public CourseRecyclerAdapter(Context context){
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
    public StudentRecyclerAdapter.IViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_item,parent,false);
        StudentRecyclerAdapter.IViewHolder holder = new StudentRecyclerAdapter.IViewHolder(layout);
        return holder;
    }
    @Override
    public void onBindViewHolder(StudentRecyclerAdapter.IViewHolder holder, int position){
        View itemLayout = (View)holder.itemView;
        nowItem = mItems.get(position);
        final TextView stdId = (TextView)itemLayout.findViewById(R.id.courFragstdId);
        final TextView stdName = (TextView)itemLayout.findViewById(R.id.courFragstdName);
        final TextView stdChooseYear = (TextView)itemLayout.findViewById(R.id.courFragChooseYear);
        final TextView stdGrade = (TextView)itemLayout.findViewById(R.id.courFragGrade);


        stdId.setText(nowItem.getStudent().getStdId());
        stdName.setText(nowItem.getStudent().getStdName());
        stdChooseYear.setText(nowItem.getChooseCourse().getChooseYear());
        stdGrade.setText(nowItem.getChooseCourse().getGrade());
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setClass(mContext,UpdateChooseFromStudentActivity.class);
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
}
