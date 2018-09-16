package com.coinpany.core.android.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Mahdi.
 *         Created on 2/4/2016.
 */
public class CDivider extends View {
    public CDivider(Context context) {
        super(context);
    }

//    float density;

//    private int dp(float value) {
//        return (int) Math.ceil(density * value);
//    }


    public CDivider(Context context, AttributeSet attrs) {
        super(context, attrs);
//        if (isInEditMode()) {
//            density = 1;
//        } else {
//            density = getResources().getDisplayMetrics().density;
//        }

        setBackgroundColor(Color.parseColor("#DDDDDD"));
//        getLayoutParams().height = dp(1);
//        getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }


}
