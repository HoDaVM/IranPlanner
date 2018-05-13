package com.iranplanner.tourism.iranplanner.ui;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;


/**
 * Created by h.vahidimehr on 11/11/2017.
 */

public class RightToLeftViewPager extends ViewPager {

    public RightToLeftViewPager(Context context) {
        super(context);

    }

    public RightToLeftViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLayoutDirection(int layoutDirection) {
        if (layoutDirection == ViewCompat.LAYOUT_DIRECTION_LTR) {
            setRotationY(0);
        } else if (layoutDirection == ViewCompat.LAYOUT_DIRECTION_RTL) {
            setRotationY(180);
        }
    }

}

