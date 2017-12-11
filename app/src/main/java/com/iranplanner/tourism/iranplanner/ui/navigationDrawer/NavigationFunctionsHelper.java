package com.iranplanner.tourism.iranplanner.ui.navigationDrawer;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by MrCode on 9/23/17.
 */

public class NavigationFunctionsHelper {

    private Activity context;
    private static NavigationFunctionsHelper instance = null;

    private NavigationFunctionsHelper(Activity context) {
        this.context = context;
    }

    public static NavigationFunctionsHelper getInstance(Activity context) {
        if (instance == null)
            instance = new NavigationFunctionsHelper(context);
        return instance;
    }

    public void sendShareIntent() {
        String shareBody = "\u200F«ایران پلنر» را در بازار اندروید ببین: \n http://cafebazaar.ir/app/?id=com.iranplanner.tourism.iranplanner&ref=share";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Iranplanner");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "iranplanner"));
    }

    public void showAboutUsDialog() {
        new AboutCityDialog(context).show();
    }



}
