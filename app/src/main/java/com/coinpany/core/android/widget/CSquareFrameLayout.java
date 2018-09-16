package com.coinpany.core.android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author Mahdi.
 *         Created on 2/7/2016.
 */
public class CSquareFrameLayout extends FrameLayout {
    public CSquareFrameLayout(Context context) {
        super(context);
    }

    public CSquareFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CSquareFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CSquareFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        int size = width > height ? height : width;
//        setMeasuredDimension(size, size);
//
//    }
}
