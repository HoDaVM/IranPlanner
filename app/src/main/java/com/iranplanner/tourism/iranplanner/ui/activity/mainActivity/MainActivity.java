package com.iranplanner.tourism.iranplanner.ui.activity.mainActivity;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.coinpany.core.android.widget.Utils;
import com.crashlytics.android.Crashlytics;
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
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.OnVisibleShowCaseViewListener;
import com.iranplanner.tourism.iranplanner.ui.fragment.blog.BlogContract;
import com.iranplanner.tourism.iranplanner.ui.fragment.blog.BlogDetailActivity;
import com.iranplanner.tourism.iranplanner.ui.fragment.blog.BlogModule;
import com.iranplanner.tourism.iranplanner.ui.fragment.blog.BlogPresenter;
import com.iranplanner.tourism.iranplanner.ui.fragment.blog.DaggerBlogComponent;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.HomeFragment;
import com.iranplanner.tourism.iranplanner.ui.tutorial.TutorialActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import entity.GetHomeResult;
import entity.InterestResult;
import entity.ResultBlogFull;
import entity.ResultBlogList;
import entity.ResultCommentList;
import entity.ResultImageList;
import entity.ResultParamUser;
import entity.ResultPostList;
import entity.ResultWidgetFull;
import entity.ShowAtractionDetailMore;
import entity.ShowAttractionListMore;
import server.Config;
import server.NotificationUtils;
import tools.Constants;
import tools.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends StandardActivity implements ForceUpdateChecker.OnUpdateNeededListener, OnVisibleShowCaseViewListener, BlogPresenter.View, AttractionListMoreContract.View, CommentContract.View {
    GetHomeResult homeResult;
    private ResultBlogList resultBlogList;
    List<ResultPostList> resultPostList;
    CustomShowcaseView customShowcaseView;
    private int counter = 0;
    ShowcaseView showcaseView, showcaseViewPanda;
    private static final String TOPIC_MAIN = "main";

    boolean doubleBackToExitPressedOnce = false;
    private NonSwipeableViewPager viewPager;
    private TabPagerAdapter pagerAdapter;
    TabLayout mainTabLayout;
    @Inject
    BlogPresenter blogPresenter;
    //runtime permission field vars
    private static final int ACCESS_FINE_LOCATION_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    private SharedPreferences permissionStatus;
    Typeface tf;
    boolean isFromNOtification = true;
    String idNotification, nTypeNotification;

    //


    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        init();
        checkPermission();
        ((App) getApplication()).getLastLocation();

        Log.e("TOKEN", FirebaseInstanceId.getInstance().getToken() + ".");

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_MAIN);
        tf = Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile.ttf");

        DaggerBlogComponent.builder().netComponent(((App) getApplicationContext()).getNetComponent())
                .blogModule(new BlogModule(this, this, this))
                .build().inject(this);
        showNotification();
        displayFirebaseRegId();

    }

    private void showNotification() {
        // show and open app  notification when app is killed
        if (nTypeNotification != null) {
            blogPresenter.getBlogFull("full", idNotification, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
        }


        // show and open notification when app is open
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                android.os.Debug.waitForDebugger();
//                android.os.Debug.isDebuggerConnected();
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String idNotification = intent.getStringExtra("id");
                    blogPresenter.getBlogFull("full", idNotification, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                }
            }
        };
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regid", null);
        Log.e(TAG, "Firebase reg id: " + regId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));
        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));
        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("توجه");
                builder.setMessage("برای کارکرد بهتر اپلیکیشن نیاز به دسترسی موقعیت مکانی دارد");
                builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION_PERMISSION_CONSTANT);
                    }
                });
                builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
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
                builder.setTitle("توجه");
                builder.setMessage("برای کارکرد بهتر اپلیکیشن نیاز به دسترسی موقعیت مکانی دارد");
                builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
//                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
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

    @Override
    public void onNewIntent(Intent newIntent) {
        this.setIntent(newIntent);
//notification when app run in background
        // Now getIntent() returns the updated Intent
        try {
            nTypeNotification = getIntent().getStringExtra("nTypeNotification");
            idNotification = getIntent().getStringExtra("idNotification");
            if (nTypeNotification.equals("attraction")) {
                viewPager.setCurrentItem(0, true);
            } else if (nTypeNotification.equals("blog")) {
                blogPresenter.getBlogFull("full", idNotification, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
            }
        } catch (Exception e) {

        }


    }


    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        homeResult = (GetHomeResult) extras.getSerializable("HomeResult");
        resultBlogList = (ResultBlogList) extras.getSerializable("ResultBlogList");
        try {
            nTypeNotification = extras.getString("nTypeNotification");
            idNotification = extras.getString("idNotification");
        } catch (Exception e) {

        }
    }

    private void init() {

        getExtras();
        resultPostList = resultBlogList.getResultPostList();
        viewPager = (NonSwipeableViewPager) findViewById(R.id.main_view_pager);
        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), this, homeResult, resultPostList, this);
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

        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();
        viewPager.setOffscreenPageLimit(5);

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        mainTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            /*    if (tab.getPosition() == 1) {
//                    Crashlytics.getInstance().crash(); // Force a crash
                    if (!Boolean.parseBoolean(Util.getFromPreferences(Constants.PREF_SHOWCASE_PASSED_SETTINGFRAGMENT, "false", false, getApplicationContext()))) {
                        mainTabLayout.setVisibility(View.INVISIBLE);
                    }
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (settingView != null && !Boolean.parseBoolean(Util.getFromPreferences(Constants.PREF_SHOWCASE_PASSED_SETTINGFRAGMENT, "false", false, getApplicationContext()))) {
                                counter=0;
                                setShowCaseSetting(settingView.get(0));
                                mainTabLayout.setVisibility(View.VISIBLE);
                            }
                        }
                    }, 250);
                } else */
                if (tab.getPosition() == 3) {
                    if (!Boolean.parseBoolean(Util.getFromPreferences(Constants.PREF_SHOWCASE_PASSED_PANDAFRAGMENT, "false", false, getApplicationContext()))) {
                        mainTabLayout.setVisibility(View.INVISIBLE);
                    }
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (pandaView != null && !Boolean.parseBoolean(Util.getFromPreferences(Constants.PREF_SHOWCASE_PASSED_PANDAFRAGMENT, "false", false, getApplicationContext()))) {
                                counter = 0;
                                setShowCasePanda(pandaView);
                                mainTabLayout.setVisibility(View.VISIBLE);
                            }
                        }
                    }, 250);
                } else if (tab.getPosition() == 2) {
                    if (!Boolean.parseBoolean(Util.getFromPreferences(Constants.PREF_SHOWCASE_PASSED_SEARCHINERARY, "false", false, getApplicationContext()))) {
                        mainTabLayout.setVisibility(View.INVISIBLE);
                    }

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (itineraryView != null && !Boolean.parseBoolean(Util.getFromPreferences(Constants.PREF_SHOWCASE_PASSED_SEARCHINERARY, "false", false, getApplicationContext()))) {
                                counter = 0;
                                setShowCaseItinerary(itineraryView);
                                mainTabLayout.setVisibility(View.VISIBLE);
                            }
                        }
                    }, 250);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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

    List<View> settingView;
    List<View> pandaView;
    List<View> itineraryView;

    @Override
    public void onVisibleShowCase(String fragmentName, List<View> views) {
        if (fragmentName.equals("homeFragment")) {
            if (!Boolean.parseBoolean(Util.getFromPreferences(Constants.PREF_SHOWCASE_PASSED_HOMEfRAGMENT, "false", false, getApplicationContext()))) {
                setShowCase(views);
            }

        } else if (fragmentName.equals("settingFragment")) {
            settingView = new ArrayList<View>();
            settingView = views;
        } else if (fragmentName.equals("pandaFragment")) {
            pandaView = new ArrayList<View>();
            pandaView = views;
        } else if (fragmentName.equals("ItineraryFragment")) {
            itineraryView = new ArrayList<View>();
            itineraryView = views;
        }

    }

    private void setShowCase(final List<View> views) {
        Button customButton = (Button) this.getLayoutInflater().inflate(R.layout.showcase_custom_button, null);
        CustomShowcaseView showcaseDrawer = new CustomShowcaseView(getResources());
        float width = getResources().getDimension(R.dimen.custom_showcase_moreItem_width);
        float height = getResources().getDimension(R.dimen.custom_showcase_moreItem_height_home);
        showcaseDrawer.customShowcaseSize(width, height);

        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(views.get(0)))
                .setShowcaseDrawer(showcaseDrawer)
                .blockAllTouches()
                .replaceEndButton(customButton)
                .build();
        Util.saveInPreferences(Constants.PREF_SHOWCASE_PASSED_HOMEfRAGMENT, String.valueOf(true), false, getApplicationContext());
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = Utils.dp(getApplicationContext(), 16);
        lps.setMargins(0, 0, 0, margin);
        showcaseView.setButtonPosition(lps);
        final int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        showcaseView.overrideButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (counter) {
                    case 0: {
                        showcaseView.setShowcase(new ViewTarget(views.get(1)), true);
                        showcaseView.setContentText(getString(R.string.tutorialHotelext));
                        showcaseView.setContentTitle(getString(R.string.tutorialHotelTitle));
                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                        showcaseView.setStyle(R.style.MyShowcaseView);
                        showcaseView.setButtonText("بعدی");
                        break;
                    }

                    case 1: {
                        showcaseView.setShowcase(new ViewTarget(views.get(2)), true);
                        showcaseView.setContentTitle(getString(R.string.tutorialItineraryText));
                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                        showcaseView.setContentText(getResources().getString(R.string.tutorialItinerary));
                        showcaseView.setStyle(R.style.MyShowcaseView);
                        showcaseView.setButtonText("بعدی");
                        break;
                    }

                    case 2: {
                        showcaseView.setShowcase(new ViewTarget(views.get(3)), true);
                        showcaseView.setContentTitle(getString(R.string.tutorialAttractionTitle));
                        showcaseView.setContentText(getString(R.string.tutorialAttractionText));
                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                        showcaseView.setButtonText("بعدی");
                        showcaseView.setStyle(R.style.MyShowcaseView);
                        break;
                    }
                    case 3: {
                        showcaseView.setShowcase(new ViewTarget(views.get(4)), true);
                        showcaseView.setContentTitle(getString(R.string.tutorialEventTitle));
                        showcaseView.setContentText(getString(R.string.tutorialEventText));
                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                        showcaseView.setButtonText("بستن");
                        break;
                    }
                    case 4: {
                        showcaseView.setTarget(Target.NONE);
                        showcaseView.setContentTitle("");
                        showcaseView.hide();
//                showcaseView.setButtonText("بستن");
                        //setAlpha(0.4f, v0,v1, v2,v3);
                        break;
                    }
//            case 4: {
//                showcaseView.hide();
//                //  setAlpha(1.0f, v0,v1, v2,v3);
//                break;
//            }
                }
                counter++;
            }
        });
        showcaseView.setButtonText(getString(R.string.tutorialNext));
        showcaseView.setContentText(getString(R.string.tutorialWhereToText));
        showcaseView.setContentTitle(getString(R.string.tutorialWhereToTitle));
        showcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
    }

    private void setShowCaseSetting(View view) {
        Button customButton = (Button) this.getLayoutInflater().inflate(R.layout.showcase_custom_button, null);
        CustomShowcaseView showcaseDrawer = new CustomShowcaseView(getResources());
        float width = getResources().getDimension(R.dimen.custom_showcase_moreItem_width);
        float height = getResources().getDimension(R.dimen.custom_showcase_moreItem_height);
        showcaseDrawer.customShowcaseSize(width, height);

        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(view))
                .setShowcaseDrawer(showcaseDrawer)
                .blockAllTouches()
                .replaceEndButton(customButton)
                .build();
        Util.saveInPreferences(Constants.PREF_SHOWCASE_PASSED_SETTINGFRAGMENT, String.valueOf(true), false, getApplicationContext());
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = Utils.dp(getApplicationContext(), 16);
        lps.setMargins(0, 0, 0, margin);
        showcaseView.setButtonPosition(lps);
        showcaseView.setButtonText("بستن");
        showcaseView.setContentText(getString(R.string.tutorialReservation));
        showcaseView.setContentTitle(getString(R.string.tutorialReservationTitle));
        showcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
    }

    private void setShowCasePanda(final List<View> views) {
        Button customButtonPanda = (Button) this.getLayoutInflater().inflate(R.layout.showcase_custom_button, null);
        CustomShowcaseView showcaseDrawer = new CustomShowcaseView(getResources());
        float width = getResources().getDimension(R.dimen.custom_showcase_moreItem_width);
        float height = getResources().getDimension(R.dimen.custom_showcase_moreItem_height);
        showcaseDrawer.customShowcaseSize(width, height);

        showcaseViewPanda = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(views.get(0)))
                .setShowcaseDrawer(showcaseDrawer)
                .blockAllTouches()
                .replaceEndButton(customButtonPanda)
                .build();
        Util.saveInPreferences(Constants.PREF_SHOWCASE_PASSED_PANDAFRAGMENT, String.valueOf(true), false, getApplicationContext());
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = Utils.dp(getApplicationContext(), 16);
        lps.setMargins(0, 0, 0, margin);
        showcaseViewPanda.setButtonPosition(lps);
        final int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        showcaseViewPanda.overrideButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (counter) {


                    case 0: {
                        showcaseViewPanda.setContentText("به کمک این گزینه میتونی نوع اطلاعاتی که روی نقشه نمایش داده می شه رو فیلتر کنی");
                        showcaseViewPanda.setContentTitle("فیلتر کردن ");
                        showcaseViewPanda.setShowcase(new ViewTarget(views.get(1)), true);
                        showcaseViewPanda.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
                        showcaseViewPanda.setButtonText("بستن");
                        break;
                    }

                    case 1: {
                        showcaseViewPanda.setTarget(Target.NONE);
                        showcaseViewPanda.setContentTitle("");
                        showcaseViewPanda.hide();

                        break;
                    }

                }
                counter++;
            }
        });


        showcaseViewPanda.setContentTitle("کشیدن محدوده با دست روی نقشه");
        showcaseViewPanda.setContentText("یه ویژگی خاص و منحصر به فرد! کافیه محدوده مورد نظرت رو با دست روی نقشه ترسیم کنی تا همه اطلاعات در اون منطقه رو یه جا بدست بیاری");
        showcaseViewPanda.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setShowCaseItinerary(final List<View> views) {
        Button customButton = (Button) this.getLayoutInflater().inflate(R.layout.showcase_custom_button, null);
        CustomShowcaseView showcaseDrawer = new CustomShowcaseView(getResources());
        float width = getResources().getDimension(R.dimen.custom_showcase_moreItem_width);
        float height = getResources().getDimension(R.dimen.custom_showcase_moreItem_height);
        showcaseDrawer.customShowcaseSize(width, height);

        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(views.get(0)))
                .setShowcaseDrawer(showcaseDrawer)
                .blockAllTouches()
                .replaceEndButton(customButton)
                .build();
        Util.saveInPreferences(Constants.PREF_SHOWCASE_PASSED_SEARCHINERARY, String.valueOf(true), false, getApplicationContext());
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = Utils.dp(getApplicationContext(), 40);
        lps.setMargins(0, 0, 0, margin);
        showcaseView.setButtonPosition(lps);
        final int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        showcaseView.overrideButtonClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (counter) {
                    case 0: {
                        showcaseView.setShowcase(new ViewTarget(views.get(1)), true);
                        showcaseView.setContentText(getString(R.string.tutorialItineraryCityText));
                        showcaseView.setContentTitle(getString(R.string.tutorialItineraryCityTitle));
                        showcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
                        showcaseView.setButtonText("بستن");
                        break;
                    }

//                    case 1: {
//                        showcaseView.setShowcase(new ViewTarget(views.get(2)), true);
//                        showcaseView.setContentTitle(getString(R.string.tutorialItineraryProvinceTitle));
//                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
//                        showcaseView.setContentText(getResources().getString(R.string.tutorialItineraryProvinceText));
//                        break;
//                    }

//                    case 2: {
//                        showcaseView.setShowcase(new ViewTarget(views.get(3)), true);
//                        showcaseView.setContentTitle(getString(R.string.tutorialItineraryAttractionTitle));
//                        showcaseView.setContentText(getString(R.string.tutorialItineraryAttractionText));
//                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
//                        showcaseView.setButtonText("بستن");
//                        break;
//                    }
                    case 1: {
                        showcaseView.setTarget(Target.NONE);
                        showcaseView.setContentTitle("");
                        showcaseView.hide();
//                showcaseView.setButtonText("بستن");
                        //setAlpha(0.4f, v0,v1, v2,v3);
                        break;
                    }
//            case 4: {
//                showcaseView.hide();
//                //  setAlpha(1.0f, v0,v1, v2,v3);
//                break;
//            }
                }
                counter++;
            }
        });
        showcaseView.setButtonText(getString(R.string.tutorialNext));
        showcaseView.setContentText(getString(R.string.tutorialItineraryIranText));
        showcaseView.setContentTitle(getString(R.string.tutorialItineraryIranTitle));
        showcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
    }

    @Override
    public void showComments(ResultCommentList resultCommentList) {

    }

    @Override
    public void sendCommentMessage(ResultCommentList resultCommentList) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void commentResult(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void setIntrestedWidget(InterestResult interestResult) {

    }

    @Override
    public void setLoadWidget(ResultWidgetFull resultWidgetFull) {

    }

    @Override
    public void setRate(ResultParamUser resultParamUser) {

    }

    @Override
    public void setRateUser(ResultParamUser resultParamUser) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void showMoreImages(ResultImageList resultImageList) {

    }

    @Override
    public void setImageUri(Uri uri) {

    }

    @Override
    public void showAttractionDetail(ShowAtractionDetailMore showAttractionFull) {

    }

    @Override
    public void ShowAttractionLists(ShowAttractionListMore getAttractionList) {

    }

    @Override
    public void showBlogList(ResultBlogList resultBlogList) {

    }

    @Override
    public void showBlogFull(ResultBlogFull resultBlogFull) {
        if (resultBlogFull.getResultPostFull() != null) {
            Intent intent = new Intent(MainActivity.this, BlogDetailActivity.class);
            intent.putExtra("ResultPostFull", (Serializable) resultBlogFull.getResultPostFull());
            startActivity(intent);
        }
    }
}
