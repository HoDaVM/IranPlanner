package com.iranplanner.tourism.iranplanner.showcaseview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;

import com.github.amlcurran.showcaseview.ShowcaseDrawer;
import com.iranplanner.tourism.iranplanner.R;

/**
 * @author Hoda
 */
public class CustomShowcaseView implements ShowcaseDrawer {
    private  float width;
    private  float height;
    private final Paint eraserPaint;
    private final Paint basicPaint;
    private final int eraseColour;
    private final RectF renderRect;

    public CustomShowcaseView(Resources resources) {

        eraserPaint = new Paint();
        eraserPaint.setColor(0xFFFFFF);
        eraserPaint.setAlpha(0);
        eraserPaint.setAntiAlias(true);
        eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        eraseColour = resources.getColor(R.color.bg_showcase);
        basicPaint = new Paint();
        renderRect = new RectF();
    }
    public void customShowcaseSize(float width,float height){
        this.width=width;
        this.height=height;
    }
    @Override
    public void setShowcaseColour(int color) {

    }

    @Override
    public void drawShowcase(Bitmap buffer, float x, float y, float scaleMultiplier) {
        Canvas bufferCanvas = new Canvas(buffer);
        renderRect.left = x - width / 2f;
        renderRect.right = x + width / 2f;
        renderRect.top = y - height / 2f;
        renderRect.bottom = y + height / 2f;
        bufferCanvas.drawRoundRect(renderRect,50,50, eraserPaint);
    }

    @Override
    public int getShowcaseWidth() {
        return (int) width;
    }

    @Override
    public int getShowcaseHeight() {
        return (int) height;
    }

    @Override
    public float getBlockedRadius() {
        return width;
    }

    @Override
    public void setBackgroundColour(int backgroundColor) {
        // No-op, remove this from the API?
    }

    @Override
    public void erase(Bitmap bitmapBuffer) {
        bitmapBuffer.eraseColor(eraseColour);
    }

    @Override
    public void drawToCanvas(Canvas canvas, Bitmap bitmapBuffer) {
        canvas.drawBitmap(bitmapBuffer, 0, 0, basicPaint);
    }

}


