package com.example.administrator.databasemanagementsystem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.databasemanagementsystem.Models.RecyclerItem;

/**
 * Created by Administrator on 2017/4/2.
 */

public class RecyclerDecoration extends RecyclerView.ItemDecoration {
    private int tagWidth;
    private Paint leftPaint;
    private Paint rightPaint;
    private int space = 25;
    public RecyclerDecoration(Context context) {
        leftPaint = new Paint();
        leftPaint.setColor(context.getResources().getColor(R.color.colorAccent));
        rightPaint = new Paint();
        rightPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
        tagWidth = 25;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(child);
            boolean isLeft = pos % 2 == 0;
            if (isLeft) {
                float left = child.getLeft();
                float right = left + tagWidth;
                float top = child.getTop();
                float bottom = child.getBottom();
                c.drawRect(left, top, right, bottom, leftPaint);
            } else {
                float right = child.getRight();
                float left = right - tagWidth;
                float top = child.getTop();
                float bottom = child.getBottom();
                c.drawRect(left, top, right, bottom, rightPaint);

            }
        }
    }
   /* @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        outRect.bottom = space;
        outRect.left = space/2;
        outRect.right = space/2;
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0)
            outRect.top = space;
    }*/

}
