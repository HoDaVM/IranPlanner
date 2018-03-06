package com.iranplanner.tourism.iranplanner.ui.navigationDrawer;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by MrCode on 9/23/17.
 */

public class NavigationFunctionsHelper {

    private Activity context;
    private String body;
    private String subject;
    private String title;
    private static NavigationFunctionsHelper instance = null;

    private NavigationFunctionsHelper(Activity context, String body,String subject,String title) {
        this.context = context;
        this.body=body;
        this.subject=subject;
    }

    public static NavigationFunctionsHelper getInstance(Activity context,String body,String subject,String title) {
        if (instance == null)
            instance = new NavigationFunctionsHelper(context,body,subject,title);
        return instance;
    }

    public void sendShareIntent() {
//        String shareBody = "\u200F«ایران پلنر» را دانلود کنید: \n http://iranplanner.com/app";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(sharingIntent, title));
    }

    public void showAboutUsDialog() {
        new AboutCityDialog(context).show();
    }



}
