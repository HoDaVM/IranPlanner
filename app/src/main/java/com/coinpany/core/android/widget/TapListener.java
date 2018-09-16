package com.coinpany.core.android.widget;

import android.view.View;

/**
 * Created by Mahdi Taherian on 11/10/2015.
 */
public abstract class TapListener implements View.OnClickListener {

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds

    long lastClickTime = 0;
    boolean doubleTapped = false;


    @Override
    public void onClick(final View v) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            if (!doubleTapped) {
                onDoubleTap(v);
            }
            doubleTapped = true;
        } else {
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!doubleTapped) {
                        onSingleTap(v);
                    }
                }
            }, DOUBLE_CLICK_TIME_DELTA);
            doubleTapped = false;
        }
        lastClickTime = clickTime;
    }


    public abstract void onDoubleTap(View v);

    public abstract void onSingleTap(View v);


}
