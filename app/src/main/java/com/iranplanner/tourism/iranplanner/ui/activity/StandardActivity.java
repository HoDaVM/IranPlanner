package com.iranplanner.tourism.iranplanner.ui.activity;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.model.App;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Hoda on 10/01/2017.
 */

public abstract class StandardActivity extends AppCompatActivity {
    LocaleUtils localeUtils = new LocaleUtils();

    public static final String TAG = "DEBUG::::";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        FirebaseCrash.logcat(Log.ERROR, "firebaseCrash", "NPE caught");
        FirebaseCrash.report(new Exception("opss"));
        FirebaseCrash.logcat(Log.DEBUG, "TAG", "MainActivity started");

        int[] attrs = {R.attr.fontPath};
        TypedArray a = obtainStyledAttributes(R.style.StandardText, attrs);
        String fontPath = a.getString(0);
        a.recycle();

        localeUtils.setLocale(new Locale("fa"));
        localeUtils.updateConfig((Application) getApplicationContext(), getBaseContext().getResources().getConfiguration());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localeUtils.updateConfig((Application) getApplicationContext(), newConfig);
    }

    /**
     * Attach CalligraphyContextWrapper to context because of new fonts.
     *
     * @param newBase
     */

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void loadFragment(FragmentActivity callingActivity, Fragment fragment, int containerId, boolean addToBackStack, int outTransition, int inTransition) {
        FragmentManager supportFragmentManager = callingActivity.getSupportFragmentManager();
        if (!addToBackStack) {
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.setCustomAnimations(inTransition, outTransition, inTransition, outTransition);
            transaction.replace(containerId, fragment).commitAllowingStateLoss();
        } else {
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.setCustomAnimations(inTransition, outTransition, inTransition, outTransition);
            transaction.replace(containerId, fragment).addToBackStack(fragment.getClass().getName()).commit();
        }
    }

    public void loadFragment(FragmentActivity callingActivity, Fragment fragment, int containerId, boolean addToBackStack, int outTransition, int inTransition, int outPopTransition, int inPopTransition) {
        FragmentManager supportFragmentManager = callingActivity.getSupportFragmentManager();
        if (!addToBackStack) {
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.setCustomAnimations(inTransition, outTransition, inPopTransition, outPopTransition);
            transaction.replace(containerId, fragment).commit();
        } else {
            FragmentTransaction transaction = supportFragmentManager.beginTransaction();
            transaction.setCustomAnimations(inTransition, outTransition, inPopTransition, outPopTransition);
            transaction.replace(containerId, fragment).addToBackStack(fragment.getClass().getName()).commit();
        }
    }

    private boolean encrypted = false;

    @Override
    protected void onResume() {
        FirebaseAnalytics firebaseAnalytics = ((App) getApplication()).getmFirebaseAnalytics();
        Log.d("FAnalytics", "setCurrentScreen: " + getClass().getSimpleName());
        super.onResume();
    }

    public int getActionBarSize() {
        return (int) getResources().getDimension(R.dimen.actionBarSize);
    }

    public class LocaleUtils {
        private Locale sLocale;

        public void setLocale(Locale locale) {
            sLocale = locale;
            if (sLocale != null) {
                Locale.setDefault(sLocale);
            }
        }

        public void updateConfig(ContextThemeWrapper wrapper) {
            if (sLocale != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Configuration configuration = new Configuration();
                configuration.setLocale(sLocale);
                wrapper.applyOverrideConfiguration(configuration);
            }
        }

        public void updateConfig(Application app, Configuration configuration) {
            if (sLocale != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                //Wrapping the configuration to avoid Activity endless loop
                Configuration config = new Configuration(configuration);
                // We must use the now-deprecated config.locale and res.updateConfiguration here,
                // because the replacements aren't available till API level 24 and 17 respectively.
                config.locale = sLocale;
                Resources res = app.getBaseContext().getResources();
                res.updateConfiguration(config, res.getDisplayMetrics());
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Configuration configurationw = getResources().getConfiguration();
                if (/*isSlectedLanguageRTL*/true) {
                    configuration.setLayoutDirection(new Locale("fa"));
                } else {
                    configuration.setLayoutDirection(Locale.ENGLISH);
                }
                getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
            }
        }
    }

    protected abstract int getLayoutId();

    public void setCollapseFont(CollapsingToolbarLayout toolbar) {

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile.ttf");

        toolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);
        toolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        toolbar.setCollapsedTitleTypeface(tf);
        toolbar.setExpandedTitleTypeface(tf);

    }

}
