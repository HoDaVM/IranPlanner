package com.coinpany.core.android.widget;

/**
 * Created by Mahdi Taherian on 3/16/2016
 */


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CSquareImageView extends ImageView {

    public CSquareImageView(Context context) {
        super(context);
    }

    public CSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CSquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}
