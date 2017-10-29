package com.iranplanner.tourism.iranplanner.ui.filterManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList.ReservationHotelListActivity;

import tools.Util;

/**
 * Created by MrCode on 9/19/17.
 */

public class FilterManager implements RadioGroup.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private static final String TAG = "Filter Manager Rocks";

    public static final String FILTER_RECEIVED = "filter_received";

    private static final String BASE_URL = "";
    private static String url = "";

    private BroadcastReceiver receiver;

    private View root;
    private Activity activity;

    //primitives used to enable or disable the filter types
    private boolean isSort, isPrice, isType, isRate;

    private CardView cvSort, cvRange, cvType, cvRate;

    //Views corresponding with the Sort Filter
    private RadioGroup rgSort;
    private RadioButton rbSortMostSale, rbSortMinPrice, rbSortMostPrice, rbSortMostRate, rbSortMinRate;

    //Views corresponding with the Price Range Filter
    private SeekBar sbPrice;

    //Views corresponding with the Hotel Type Filter
    private CheckBox cbTypeHotel, cbTypeApartement, cbTypeLocal, cbTypeTraditional;

    //Views corresponding with the Hotel Rate Filter
    private CheckBox cbRateZero, cbRateOne, cbRateTwo, cbRateThree, cbRateFour, cbRateFive;

    private View filterToggle, filterView, filterShade, bottomPanelView;
    private boolean isViewOpen = false;

    public FilterManager(Activity activity, View root) {
        this.root = root;
        this.activity = activity;
        isPrice = false;
        isType = false;
        isSort = false;
        isRate = false;

        init();
    }

    public void onBackPressed(){
        if (isViewOpen)
            closeFilterView();
        else activity.finish();
    }

    private void init() {

        //General Initialization of FilterManager ItSelf
        bottomPanelView = activity.findViewById(R.id.bottomPanelView);
        filterView = activity.findViewById(R.id.filterView);
        filterToggle = activity.findViewById(R.id.filterToggleView);

        filterShade = activity.findViewById(R.id.panelShadeView);

        filterToggle.setOnClickListener(this);
        filterView.setOnClickListener(this);
        filterShade.setOnClickListener(this);
        bottomPanelView.setOnClickListener(this);

        filterShade.setAlpha(0);
        filterShade.setVisibility(View.GONE);

        filterView.setY(Util.dpToPx(activity, (int) activity.getResources().getDimension(R.dimen.filter_view_height)));

        //Initialization Of FilterManager Child Filters
        cvSort = (CardView) root.findViewById(R.id.filterCvSort);
        cvRange = (CardView) root.findViewById(R.id.filterCvRange);
        cvType = (CardView) root.findViewById(R.id.filterCvType);
        cvRate = (CardView) root.findViewById(R.id.filterCvRate);

        rgSort = (RadioGroup) root.findViewById(R.id.filterSortRg);

        rbSortMinPrice = (RadioButton) root.findViewById(R.id.filterSortMinPriceRb);
        rbSortMostSale = (RadioButton) root.findViewById(R.id.filterSortMostSaleRb);
        rbSortMostPrice = (RadioButton) root.findViewById(R.id.filterSortMostPriceRb);
        rbSortMostRate = (RadioButton) root.findViewById(R.id.filterSortRateMostRb);
        rbSortMinRate = (RadioButton) root.findViewById(R.id.filterSortMinRateRb);

        sbPrice = (SeekBar) root.findViewById(R.id.filterPriceSeekBar);

        cbTypeApartement = (CheckBox) root.findViewById(R.id.filterTypeApartementCb);
        cbTypeHotel = (CheckBox) root.findViewById(R.id.filterTypeHotelCb);
        cbTypeLocal = (CheckBox) root.findViewById(R.id.filterTypeLocalCb);
        cbTypeTraditional = (CheckBox) root.findViewById(R.id.filterTypeTraditionalCb);

        cbRateZero = (CheckBox) root.findViewById(R.id.filterRate0StarCb);
        cbRateOne = (CheckBox) root.findViewById(R.id.filterRate1StarCb);
        cbRateTwo = (CheckBox) root.findViewById(R.id.filterRate2StarCb);
        cbRateThree = (CheckBox) root.findViewById(R.id.filterRate3StarCb);
        cbRateFour = (CheckBox) root.findViewById(R.id.filterRate4StarCb);
        cbRateFive = (CheckBox) root.findViewById(R.id.filterRate5StarCb);
    }

    private void updateUi() {
        if (!isPrice)
            cvRange.setVisibility(View.GONE);
        else
            cvRange.setVisibility(View.VISIBLE);

        if (!isRate)
            cvRate.setVisibility(View.GONE);
        else
            cvRate.setVisibility(View.VISIBLE);

        if (!isSort)
            cvSort.setVisibility(View.GONE);
        else
            cvSort.setVisibility(View.VISIBLE);

        if (!isType)
            cvType.setVisibility(View.GONE);
        else
            cvType.setVisibility(View.VISIBLE);

    }

    private void updateDataSet() {
        //use this on filter changes
        //call update url here
        //show a progress dialog over the activity
        //make a request and get the fresh data set from api
        //disable the progress dialog
        //update the adapter used in activity with fresh data
    }

    private void initSort() {
        rgSort.setOnCheckedChangeListener(this);
    }

    //// TODO: 9/19/17 call the update adapter method here
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        RadioButton radioButton = (RadioButton) root.findViewById(radioGroup.getCheckedRadioButtonId());
        Log.e(TAG, String.valueOf(radioButton.getText()));
    }

    private void initPriceRange() {
        sbPrice.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    //// TODO: 9/19/17 call the update adapter method here
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.e(TAG, String.valueOf(seekBar.getProgress()));
    }

    private void initHotelType() {

        cbTypeHotel.setOnCheckedChangeListener(this);
        cbTypeLocal.setOnCheckedChangeListener(this);
        cbTypeApartement.setOnCheckedChangeListener(this);
        cbTypeTraditional.setOnCheckedChangeListener(this);
    }

    //// TODO: 9/19/17 call the update adapter method here
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        // boolean b can be used to indicate if button is checked or not USEFUL crap
        Log.e(TAG, String.valueOf(compoundButton.getText()) + b);
    }

    private void initHotelRate() {

        cbRateZero.setOnCheckedChangeListener(checkedChangeListener);
        cbRateOne.setOnCheckedChangeListener(checkedChangeListener);
        cbRateTwo.setOnCheckedChangeListener(checkedChangeListener);
        cbRateThree.setOnCheckedChangeListener(checkedChangeListener);
        cbRateFour.setOnCheckedChangeListener(checkedChangeListener);
        cbRateFive.setOnCheckedChangeListener(checkedChangeListener);
    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        //// TODO: 9/19/17 call the update adapter method here
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            // boolean b can be used to indicate if button is checked or not USEFUL crap
            Log.e(TAG, String.valueOf(compoundButton.getText()) + b);
        }
    };

    public void registerReceiver(Activity activity) {

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG, "receiver is working probably ");
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(FILTER_RECEIVED);
        activity.registerReceiver(receiver, filter);
    }

    public void unregisterReceiver(Activity activity) {
        activity.unregisterReceiver(receiver);

        Log.e(TAG, "receiver unregistered");
    }

    public String getBaseUrl() {
        return BASE_URL + url;
    }

    public void enableAll() {
        isType = true;
        isSort = true;
        isPrice = true;
        isRate = true;

        //handle loading of all relative views
        initHotelType();
        initSort();
        initPriceRange();
        initHotelRate();

        //updateUi
        updateUi();
    }

    public void enableSort() {
        isSort = true;

        //handle the view loadings here
        initSort();

        //updateUi
        updateUi();
    }

    public void enablePriceRange() {
        isPrice = true;

        //handle the view loadings here
        initPriceRange();

        //updateUi
        updateUi();
    }

    public void enablePlaceType() {
        isType = true;

        //handle the view loadings here
        initHotelType();

        //updateUi
        updateUi();
    }

    public void enablePlaceRate() {
        isRate = true;

        //handle the view Loadings here
        initHotelRate();

        //updateUi
        updateUi();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filterToggleView:
            case R.id.panelShadeView:
                togglePanel();
                break;
            case R.id.filterView:

                break;
        }
    }

    private void togglePanel() {
        if (isViewOpen) {
            closeFilterView();
            return;
        }
        openFilterView();
    }

    private void openFilterView() {
        Interpolator interpolator = new AccelerateInterpolator();

        filterToggle.setOnClickListener(null);

        filterView.animate().setInterpolator(interpolator).translationYBy(-Util.dpToPx(activity, (int) activity.getResources().getDimension(R.dimen.filter_view_height))).setDuration(300).start();
        bottomPanelView.animate().setInterpolator(interpolator).translationYBy(-Util.dpToPx(activity, 350)).setDuration(300).start();
        filterShade.setVisibility(View.VISIBLE);
        filterShade.animate().alpha(0.7f).setDuration(300).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isViewOpen = true;
                filterToggle.setOnClickListener(FilterManager.this);
            }
        }, 300);
    }

    private void closeFilterView() {

        Interpolator interpolator = new AccelerateInterpolator();

        filterToggle.setOnClickListener(null);
        isViewOpen = false;

        filterView.animate().setInterpolator(interpolator).translationYBy(Util.dpToPx(activity, (int) activity.getResources().getDimension(R.dimen.filter_view_height))).setDuration(300).start();
        bottomPanelView.animate().setInterpolator(interpolator).translationYBy(Util.dpToPx(activity, 350)).setDuration(300).start();
        filterShade.animate().alpha(0).setDuration(300).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                filterToggle.setOnClickListener(FilterManager.this);
                filterShade.setVisibility(View.GONE);
            }
        }, 300);
    }

}
