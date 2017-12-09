package com.iranplanner.tourism.iranplanner.ui.activity.mainActivity;


import android.Manifest;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

//import com.appsee.Appsee;
import com.coinpany.core.android.widget.Utils;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.iranplanner.tourism.iranplanner.NonSwipeableViewPager;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.di.model.ForceUpdateChecker;
import com.iranplanner.tourism.iranplanner.showcaseview.CustomShowcaseView;
import com.iranplanner.tourism.iranplanner.ui.activity.SplashActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;

import com.iranplanner.tourism.iranplanner.ui.fragment.itineraryList.ItineraryListFragment;
import com.iranplanner.tourism.iranplanner.ui.fragment.itinerarySearch.MainSearchFragment;
import com.iranplanner.tourism.iranplanner.ui.tutorial.TutorialActivity;

import entity.GetHomeResult;
import rx.Observable;
import server.Config;
import server.NotificationUtils;
import tools.Constants;
import tools.Util;

public class MainActivity extends StandardActivity implements ForceUpdateChecker.OnUpdateNeededListener {
    GetHomeResult homeResult;
    CustomShowcaseView customShowcaseView;
    private int counter = 0;
    ShowcaseView showcaseView;
    private static final String TOPIC_MAIN = "main";

    boolean doubleBackToExitPressedOnce = false;
    private NonSwipeableViewPager viewPager;
    private TabPagerAdapter pagerAdapter;
    TabLayout mainTabLayout;

    //runtime permission field vars
    private static final int ACCESS_FINE_LOCATION_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        init();
        checkPermission();
        ((App) getApplication()).getLastLocation();

        Log.e("TOKEN", FirebaseInstanceId.getInstance().getToken() + ".");

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_MAIN);

        initTutorial();
        boolean responseBoolean = Boolean.parseBoolean(Util.getFromPreferences(Constants.PREF_SHOWCASE_PASSED_HOMEfRAGMENT, "false", false,getApplicationContext()));
        if (!responseBoolean) {
            setShowCase();
        }
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Location Permission");
                builder.setMessage("This app needs Location permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(Manifest.permission.ACCESS_FINE_LOCATION, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_PERMISSION_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.ACCESS_FINE_LOCATION, true);
            editor.apply();

        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }

    private void init() {

        Bundle extras = getIntent().getExtras();
        homeResult = (GetHomeResult) extras.getSerializable("HomeResult");
        viewPager = (NonSwipeableViewPager) findViewById(R.id.main_view_pager);
        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), this, homeResult);
        if (viewPager != null)
            viewPager.setAdapter(pagerAdapter);

        mainTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mainTabLayout != null) {
            mainTabLayout.setupWithViewPager(viewPager);
            for (int i = 0; i < mainTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = mainTabLayout.getTabAt(i);
                if (tab != null) {
                    tab.setCustomView(pagerAdapter.getTabView(i));
                }
            }
        }



        ///
        int position =0;
        mainTabLayout.getTabAt(position).getCustomView().setSelected(true);
        viewPager.setCurrentItem(position);

        Util.displayFirebaseRegId(this);

        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();
        viewPager.setOffscreenPageLimit(4);

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //Wrote this legend to Handle all those weird back presses
    @Override
    public void onBackPressed() {

        if (viewPager.getCurrentItem() == 0) {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "برای خروج مجددا کلیک کنید", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else viewPager.setCurrentItem(0, true);
    }

    @Override
    public void onUpdateNeeded(final String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("نسخه جدید برنامه قابل دانلود است")
                .setMessage("لطفا برنامه را به روز رسانی کنید ")
                .setCancelable(false)
                .setPositiveButton("به روز رسانی",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(updateUrl);
                            }
                        })
                .create();
        dialog.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    private void proceedAfterPermission() {
        //We've got the permission, now we can proceed further
        ((App) getApplication()).enableLocationCheck();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACCESS_FINE_LOCATION_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                proceedAfterPermission();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ACCESS_FINE_LOCATION_PERMISSION_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(), "Unable to get Permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    private void initTutorial() {

        String BOOL_FIRST_TIME = "first_time";

        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF, 0);

        if (preferences.getBoolean(BOOL_FIRST_TIME, true)) {
            startActivity(new Intent(this, TutorialActivity.class));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(BOOL_FIRST_TIME, false);
            editor.apply();
        }

        Log.e(TAG, String.valueOf(preferences.getBoolean(BOOL_FIRST_TIME, true)));
    }

    Runnable showSubscribeRunnable = null;


//    @Override
//    public void callback(int key, Object... params) {
//        super.callback(key, params);
//        if (key == CallbackCenter.emptyContentList) {
//            mViewPager.setCurrentItem(mSectionsPagerAdapter.getCount() - 2);
//        }
//        if (key == CallbackCenter.subscribeTutorial) {
//            final View followbtnView = (View) params[0];
//            showSubscribeRunnable = new Runnable() {
//                @Override
//                public void run() {
//
//                    float width = getResources().getDimension(R.dimen.custom_showcase_width_small);
//                    float height = getResources().getDimension(R.dimen.custom_showcase_height_small);
//                    customShowcaseView.customShowcaseSize(width, height);
//
//                    showcaseView.setShowcase(new ViewTarget(followbtnView), true);
//                    showcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
//                    showcaseView.setContentTitle(getString(R.string.TutorialSubscribeTitle));
//                    showcaseView.setContentText(getString(R.string.TutorialSubscribeText));
//                }
//            };
//        }
//    }
    private void setShowCase() {
        TextPaint paintTitle = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paintTitle.setTextSize(getResources().getDimension(R.dimen.text_margin));
        paintTitle.setColor(getResources().getColor(R.color.white));
//        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
//        paint.setTextSize(getResources().getDimension(R.dimen.tutorialtext));
//        paint.setColor(Color.WHITE);
        Button customButton = (Button) getLayoutInflater().inflate(R.layout.showcase_custom_button, null);
        viewPager.setCurrentItem(0);
        customShowcaseView = new CustomShowcaseView(getResources());
        float width = getResources().getDimension(R.dimen.custom_showcase_width);
        float height = getResources().getDimension(R.dimen.custom_showcase_height);
        customShowcaseView.customShowcaseSize(width, height);
        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(((ViewGroup) mainTabLayout.getChildAt(0)).getChildAt(0)))
//                .setOnClickListener(ContentActivity.this)

                .setShowcaseDrawer(customShowcaseView)
                .blockAllTouches()
//                .setContentTitlePaint(paintTitle)
                .replaceEndButton(customButton)
//                .setContentTextPaint(paint)
                .build();
        Util.saveInPreferences(Constants.PREF_SHOWCASE_PASSED_MAINACTIVITY, String.valueOf(true), false,getApplicationContext());


        showcaseView.setContentText("متن هوم");
        showcaseView.setContentTitle("متن");
        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);

        showcaseView.overrideButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (counter) {
                    case 0: {
                        if (showSubscribeRunnable != null) {
                            showSubscribeRunnable.run();

                        } else {
                            counter++;
                        }
                        break;
                    }
                    case 1: {
                        float width = getResources().getDimension(R.dimen.custom_showcase_width);
                        float height = getResources().getDimension(R.dimen.custom_showcase_height);
                        customShowcaseView.customShowcaseSize(width, height);
                        viewPager.setCurrentItem(1);
                        showcaseView.setTitleTextAlignment(Layout.Alignment.ALIGN_NORMAL);
                        showcaseView.setContentTitle("متن 2");
                        showcaseView.setContentText("متن2");
                        showcaseView.setShowcase(new ViewTarget(((ViewGroup) mainTabLayout.getChildAt(0)).getChildAt(1)), true);
                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                        break;
                    }
//
                    case 2: {
                        viewPager.setCurrentItem(2);
                        showcaseView.setShowcase(new ViewTarget(((ViewGroup) mainTabLayout.getChildAt(0)).getChildAt(2)), true);
                        showcaseView.setContentText("متن3");
                        showcaseView.setContentTitle("متن3");
                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                        break;
                    }
                    case 3: {
                        viewPager.setCurrentItem(3);
                        showcaseView.setShowcase(new ViewTarget(((ViewGroup) mainTabLayout.getChildAt(0)).getChildAt(3)), true);
                        showcaseView.setContentText("ljk");
                        showcaseView.setContentTitle("juhg");
                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                        break;
                    }
//                    case 4: {
//                        mViewPager.setCurrentItem(0);
//                        showcaseView.setShowcase(new ViewTarget(((ViewGroup) tabs.getChildAt(0)).getChildAt(0)), true);
//                        showcaseView.setContentTitle(getResources().getString(R.string.tutorialSetting));
//                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
//                        showcaseView.setContentText(getString(R.string.tutorialSettingText));
//
//                        break;
//                    }
//                    case 5: {
//                        showcaseView.setShowcase(new ViewTarget(search), true);
//                        showcaseView.setContentTitle(getResources().getString(R.string.tutorialSearch));
//                        showcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
//                        showcaseView.setContentText(getString(R.string.tutorialSearchText));
//                        showcaseView.setButtonText(getString(R.string.tutorialClose));
//                        break;
//                    }
                    case 4: {
                        showcaseView.setTarget(Target.NONE);
                        showcaseView.setContentTitle("");
                        showcaseView.setContentText("");
                        showcaseView.hide();
                        break;
                    }

                }
                counter++;
            }
        });
        showcaseView.setButtonText(getString(R.string.tutorialNext));
        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = getResources().getDimensionPixelSize(R.dimen.actionBarSize) + Utils.dp(getApplicationContext(),16);
        lps.setMargins(0, 0, 0, margin);
        showcaseView.setButtonPosition(lps);

    }
}
