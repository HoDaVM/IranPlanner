package com.coinpany.core.android.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author Mahdi.
 *         Created on 2/4/2016.
 */
public class CBanner extends RelativeLayout {
    public ImageView logo;


    public CBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rootView = inflater.inflate(R.layout.cbanner_layout, null, false);
//        addView(rootView.findViewById(R.id.banner_layout));


//        setBackgroundResource(R.drawable.bannerbg);

        RelativeLayout layout = new RelativeLayout(context);

        LayoutParams layoutParams;
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(layoutParams);
//        layout.setBackgroundColor(Color.parseColor("#AA0000"));


        layout.setPadding(
                Utils.dp(context, 2),
                Utils.dp(context, 2),
                Utils.dp(context, 2),
                Utils.dp(context, 2)
        );

//        ImageView bg = new ImageView(context);
//        bg.setImageResource(R.drawable.bannerbg);
//        bg.setScaleType(ImageView.ScaleType.FIT_XY);
//        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        bg.setLayoutParams(layoutParams); //causes layout update
//        layout.addView(bg);


        logo = new ImageView(context);
//        logo.setImageResource(R.drawable.smlogo);
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.width = Utils.dp(context, 200);
        layoutParams.height = Utils.dp(context, 60);
        logo.setLayoutParams(layoutParams); //causes layout update
        logo.setScaleType(ImageView.ScaleType.FIT_XY);

        layout.addView(logo);

        addView(layout);
    }

    public void setLogo(int resId) {
        logo.setImageResource(resId);
    }

    public void setLogo(Bitmap bitmap) {
        logo.setImageBitmap(bitmap);
    }

    public void setLogo(Drawable drawable) {
        logo.setImageDrawable(drawable);
    }


}
