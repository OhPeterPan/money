package com.zrdb.app.decorate;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

public class MainGridDecorate extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        int space = SizeUtils.dp2px(2);
        if (position > 0) {
            if (position % 2 == 0) {
                outRect.set(space, 0, space, 0);
            } else {
                outRect.set(space, 0, space, 0);
            }
        }
    }
}
