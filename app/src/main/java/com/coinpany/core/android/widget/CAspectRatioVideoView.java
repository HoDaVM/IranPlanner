package com.coinpany.core.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by Mahdi Taherian on 4/13/2016
 */
public class CAspectRatioVideoView extends SurfaceView {

    private int mVideoWidth;
    private int mVideoHeight;
    private float videoAspectRatio;

    public CAspectRatioVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CAspectRatioVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CAspectRatioVideoView(Context context) {
        super(context);
    }

    public void setVideoSize(int width, int height) {
        mVideoWidth = width;
        mVideoHeight = height;
        float ratio = (float) width / height;
        setVideoAspectRatio(ratio);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
        if (videoAspectRatio > 0) {
            float ratio = (float) width / (float) height;
            if (videoAspectRatio > ratio) {
                height = (int) (width / videoAspectRatio);

            } else if (videoAspectRatio < ratio) {
                width = (int) (height * videoAspectRatio);
            }
        }
        setMeasuredDimension(width, height);
    }

    public void setVideoAspectRatio(float videoAspectRatio) {
        this.videoAspectRatio = videoAspectRatio;
    }
}