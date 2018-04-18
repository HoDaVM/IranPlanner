package com.iranplanner.tourism.iranplanner.ui.activity.souvenirFood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coinpany.core.android.widget.CTouchyWebView;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.souvenirHomeAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import entity.GetResultSouvenir;
import entity.HomeSouvenir;
import entity.ResultAttractionList;
import entity.ResultSouvenirFull;
import tools.Util;

/**
 * Created by h.vahidimehr on 18/04/2018.
 */

public class SouvenirFoodActivity extends StandardActivity implements SouvenirFoodContract.View {
    ResultSouvenirFull resultSouvenirFull;
    String myData;

    @BindView(R.id.txtType)
    TextView txtType;

    @BindView(R.id.contentFullDescription)
    CTouchyWebView contentFullDescription;

    @BindView(R.id.txtAddressTitle)
    TextView txtAddressTitle;
    @BindView(R.id.recyclerBestSouvenirFood)
    RecyclerView recyclerBestSouvenirFood;

    @BindView(R.id.txtSouvenirFoodBestTitle)
    TextView txtSouvenirFoodBestTitle;
    @BindView(R.id.txtPhotos)
    TextView txtPhotos;
    @BindView(R.id.img)
    ImageView img;

    @Inject
    SouvenirFoodPresenter souvenirFoodPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.souvenir_food;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getExtra();


        txtType.setText(resultSouvenirFull.getSouvenirTitle());
        txtAddressTitle.setText(resultSouvenirFull.getSouvenirProvinceName());
        myData = resultSouvenirFull.getSouvenirBody();
        if (myData != null) {
            Util.setWebViewJastify(contentFullDescription, myData);
        }
        txtPhotos.setVisibility(View.INVISIBLE);
        Util.setImageView(resultSouvenirFull.getSouvenirImgUrl(), getApplicationContext(), img, null);
        setSouvenir();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(resultSouvenirFull.getSouvenirTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setHomeButtonEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile.ttf");

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);

        DaggerSouvenirFoodComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .souvenirFoodModule(new SouvenirFoodModule(this)).build().inject(this);
    }


    private void getExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        resultSouvenirFull = (ResultSouvenirFull) bundle.getSerializable("ResultSouvenirFull");
    }


    private void setSouvenir() {
        souvenirHomeAdapter attractionHomeAdapter = new souvenirHomeAdapter(null, getApplicationContext(), resultSouvenirFull.getResultSouvenirList());
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerBestSouvenirFood.setLayoutManager(horizontalLayoutManagaer);
        recyclerBestSouvenirFood.setAdapter(attractionHomeAdapter);
        recyclerBestSouvenirFood.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
               souvenirFoodPresenter.getSouvenirFull("full", resultSouvenirFull.getResultSouvenirList().get(position).getResultSouvenir().getSouvenirId());
            }
        }));

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {

        } else {
            progressDialog = Util.showProgressDialog(getApplicationContext(), "لطفا منتظر بمانید", this);
        }

    }

    @Override
    public void dismissProgress() {
        Util.dismissProgress(progressDialog);

    }

    @Override
    public void showFullSouvenir(GetResultSouvenir getResultSouvenir) {
        if (getResultSouvenir.getResultSouvenirFull() != null) {
            Intent intent = new Intent(this, SouvenirFoodActivity.class);
            intent.putExtra("ResultSouvenirFull", getResultSouvenir.getResultSouvenirFull());
            startActivity(intent);
        }

    }
}
