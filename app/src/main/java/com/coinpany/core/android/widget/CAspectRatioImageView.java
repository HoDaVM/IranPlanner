package com.coinpany.core.android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Mahdi Taherian on 11/3/2015.
 */
public class CAspectRatioImageView extends ImageView {

    public CAspectRatioImageView(Context context) {
        super(context);
    }

    public CAspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CAspectRatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private boolean enabled = true;

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!enabled) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        Drawable drawable = getDrawable();
        if (drawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        double ratio = (double) drawable.getIntrinsicHeight() / (double) drawable.getIntrinsicWidth();

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * ratio);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (height > getMaxHeight()) {
                height = getMaxHeight();
                width = (int) (height / ratio);
            }
        }
        setMeasuredDimension(width, height);
    }
}