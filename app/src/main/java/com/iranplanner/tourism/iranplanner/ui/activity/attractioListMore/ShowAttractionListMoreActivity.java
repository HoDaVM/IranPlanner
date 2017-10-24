package com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.attractionDetailActivity;
import com.iranplanner.tourism.iranplanner.ui.filterManager.FilterManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import entity.ResulAttraction;
import entity.ResultAttractionList;
import entity.ResultCommentList;
import entity.ShowAtractionDetailMore;
import entity.ShowAttractionMoreList;
import tools.Util;


public class ShowAttractionListMoreActivity extends StandardActivity implements DataTransferInterface, AttractionListMoreContract.View, View.OnClickListener {
    private Context context;
    @Inject
    AttractionListMorePresenter attractionListMorePresenter;
    //    private RecyclerView attractionRecyclerView;
    private AttractionsMoreListAdapter adapter;
    LinearLayoutManager mLayoutManager;
    List<ResultAttractionList> attractionsList;
    String nextOffset;
    Toolbar toolbar;

    @InjectView(R.id.attractionListRecyclerView)
    RecyclerView attractionRecyclerView;

    //Added by Amin
    private View filterToggle, mapToggle, filterView, filterShade, bottomPanelView;
    private boolean isViewOpen = false;
    private ProgressDialog progressDialog;

    private void getExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        attractionsList = (List<ResultAttractionList>) bundle.getSerializable("attractionsList");
        nextOffset = bundle.getString("nextOffset");
    }

    private void setToolbar() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("جاذبه های گردشگری");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_attraction_list_more);
        ButterKnife.inject(this);

        DaggerAttractionListMoreComponent.builder().netComponent(((App) getApplicationContext()).getNetComponent())
                .attractionListMoreModule(new AttractionListMoreModule(this))
                .build().inject(this);

        getExtra();
        attractionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (attractionsList != null && attractionsList.size() > 0) {
            adapter = new AttractionsMoreListAdapter(this, this, attractionsList, getApplicationContext(), R.layout.content_attraction_list);
            attractionRecyclerView.addItemDecoration(new AttractionListMoreItemDecoration(this));
            attractionRecyclerView.setAdapter(adapter);

        }

        attractionRecyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                attractionListMorePresenter.getAttractionDetailNear("full", attractionsList.get(position).getResulAttraction().getAttractionId(), "fa", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
            }
        }));
        attractionRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
//                    visibleItemCount = mLayoutManager.getChildCount();
//                    totalItemCount = mLayoutManager.getItemCount();
//                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
//
//                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
////                        lklk
////                        reservationPresenter.getLodgingList("list", String.valueOf(resultLodgings.get(0).getLodgingCityId()), "20", nextOffset, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
//                    }
                }
            }
//            }
        });

        init();
    }

    private void init() {
        mapToggle = findViewById(R.id.attractionMapToggleView);

        bottomPanelView = findViewById(R.id.attractionBottomPanelView);
        filterView = findViewById(R.id.attractionFilterView);
        filterToggle = findViewById(R.id.attractionFilterToggleView);
        filterShade = findViewById(R.id.attractionPanelShadeView);

        mapToggle.setOnClickListener(this);
        filterToggle.setOnClickListener(this);
        filterView.setOnClickListener(this);
        filterShade.setOnClickListener(this);
        bottomPanelView.setOnClickListener(this);

        filterShade.setAlpha(0);
        filterShade.setVisibility(View.GONE);

        filterView.setY(Util.dpToPx(this, 300));
        setToolbar();

        FilterManager filterManager = new FilterManager(findViewById(R.id.attractionFilterView));
//        filterManager.enableAll();
        filterManager.enablePlaceRate();

    }

    private void togglePanel() {
        if (isViewOpen) {
            closeFilterView();
            return;
        }
        openFilterView();
    }

    private void openFilterView() {
        filterToggle.setOnClickListener(null);

        filterView.animate().translationYBy(-Util.dpToPx(this, 300)).setDuration(300).start();
        bottomPanelView.animate().translationYBy(-Util.dpToPx(this, 300)).setDuration(300).start();
        filterShade.setVisibility(View.VISIBLE);
        filterShade.animate().alpha(0.7f).setDuration(300).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isViewOpen = true;
                filterToggle.setOnClickListener(ShowAttractionListMoreActivity.this);
            }
        }, 300);
    }

    private void closeFilterView() {
        filterToggle.setOnClickListener(null);
        isViewOpen = false;

        filterView.animate().translationYBy(Util.dpToPx(this, 300)).setDuration(300).start();
        bottomPanelView.animate().translationYBy(Util.dpToPx(this, 300)).setDuration(300).start();
        filterShade.animate().alpha(0).setDuration(300).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                filterToggle.setOnClickListener(ShowAttractionListMoreActivity.this);
                filterShade.setVisibility(View.GONE);
            }
        }, 300);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.attractionMapToggleView:
                break;
            case R.id.attractionPanelShadeView:
            case R.id.attractionFilterToggleView:
                togglePanel();
                break;
            case R.id.attractionFilterView:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isViewOpen)
            closeFilterView();
        else super.onBackPressed();
    }

    private void buildAlertMessageNoGps(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("مسیریاب شما فعال نیست، آیا تمایل به روشن کردن آن دارید؟")
                .setCancelable(false)
//                // TODO: 06/02/2017  below
                // toye startActivityForResult be jaye code request posotion ro ferestam . ye joor kalak .
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), position);
                    }
                })
                .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("map is ckicked", "true");
        openMapFull(requestCode);
//        if (resultCode == 1) {
//            switch (requestCode) {
//                case 1:
//                    Log.e("resultact","ok");
//                    break;
//            }
//        }
    }

    private void openMapFull(int position) {
//        Intent intent = new Intent(getApplicationContext(), MapFullActivity.class);
//        ItineraryLodgingCity i = new ItineraryLodgingCity();
//        i.setCityPositionLat(attractionsList.get(position).getAttractionL));
//        i.setCityPositionLon(attractionsList.get(position).getAttractionLang());
//        List<ItineraryLodgingCity> lodgingCities = new ArrayList<ItineraryLodgingCity>();
//        lodgingCities.add(i);
//        intent.putExtra("lodgingCities" , (Serializable) lodgingCities);
//        startActivity(intent);
    }

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;


    @Override
    public void setValues(ArrayList<String> al) {
        al.get(0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_attraction_list_more;
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
    public void showProgress() {
        progressDialog = Util.showProgressDialog(getApplicationContext(), "منتظر بمانید", ShowAttractionListMoreActivity.this);
    }

    @Override
    public void dismissProgress() {
        Util.dismissProgress(progressDialog);
    }

    @Override
    public void ShowAttractionLists(ShowAttractionMoreList showAttractionList) {

    }

    @Override
    public void showAttractionDetail(ShowAtractionDetailMore showAttractionFull) {
        ResulAttraction resulAttraction = showAttractionFull.getResultAttractionFull().getResulAttraction();
        List<ResultAttractionList> resultAttractions = (List<ResultAttractionList>) showAttractionFull.getResultAttractionFull().getResultAttractionList();
        Intent intent = new Intent(this, attractionDetailActivity.class);
        intent.putExtra("resulAttraction", (Serializable) resulAttraction);
        intent.putExtra("resultAttractionList", (Serializable) resultAttractions);
        startActivity(intent);
    }
}
