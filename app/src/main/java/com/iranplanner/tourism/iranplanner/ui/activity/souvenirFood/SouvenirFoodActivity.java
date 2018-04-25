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
import com.iranplanner.tourism.iranplanner.ui.activity.globalSearch.GlobalSearchActivity;
import com.iranplanner.tourism.iranplanner.ui.fragment.home.souvenirHomeAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import entity.GetResultLocalFood;
import entity.GetResultSouvenir;
import entity.HomeSouvenir;
import entity.ResultAttractionList;
import entity.ResultLocalfoodFull;
import entity.ResultSouvenirFull;
import tools.Util;

/**
 * Created by h.vahidimehr on 18/04/2018.
 */

public class SouvenirFoodActivity extends StandardActivity implements SouvenirFoodContract.View {
    ResultSouvenirFull resultSouvenirFull;
    ResultLocalfoodFull resultLocalfoodFull;
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
    ImageView img; @BindView(R.id.img_magnifier_foreground)
    ImageView img_magnifier_foreground;

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


        txtPhotos.setVisibility(View.INVISIBLE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        if (resultSouvenirFull != null) {
            getSupportActionBar().setTitle(resultSouvenirFull.getSouvenirTitle());
            Util.setImageView(resultSouvenirFull.getSouvenirImgUrl(), getApplicationContext(), img, null);
            setSouvenir();
            txtType.setText(resultSouvenirFull.getSouvenirTitle());
            txtAddressTitle.setText(resultSouvenirFull.getSouvenirProvinceName());
            myData = resultSouvenirFull.getSouvenirBody();
            if (myData != null) {
                Util.setWebViewJastify(contentFullDescription, myData);
            }
        } else if (resultLocalfoodFull != null) {
            getSupportActionBar().setTitle(resultLocalfoodFull.getLocalfoodTitle());
            Util.setImageView(resultLocalfoodFull.getLocalfoodImgUrl(), getApplicationContext(), img, null);
            setLocalFood();
            txtType.setText(resultLocalfoodFull.getLocalfoodTitle());
            txtAddressTitle.setText(resultLocalfoodFull.getLocalfoodProvinceName());

            myData = resultLocalfoodFull.getLocalfoodBody();
            if (myData != null) {
                Util.setWebViewJastify(contentFullDescription, myData);
            }
        }
        img_magnifier_foreground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSearch = new Intent(SouvenirFoodActivity.this, GlobalSearchActivity.class);
                startActivity(intentSearch);
            }
        });
    }


    private void getExtra() {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            try {
                resultSouvenirFull = (ResultSouvenirFull) bundle.getSerializable("ResultSouvenirFull");

        }catch (Exception e){

        }
        try {
            resultLocalfoodFull = (ResultLocalfoodFull) bundle.getSerializable("ResultLocalFood");

        }catch (Exception e){

        }

        }


    private void setSouvenir() {
        souvenirHomeAdapter attractionHomeAdapter = new souvenirHomeAdapter(null, getApplicationContext(), resultSouvenirFull.getResultSouvenirList(),null);
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
    private void setLocalFood() {
        souvenirHomeAdapter attractionHomeAdapter = new souvenirHomeAdapter(null, getApplicationContext(),null, resultLocalfoodFull.getResultLocalfoodList());
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerBestSouvenirFood.setLayoutManager(horizontalLayoutManagaer);
        recyclerBestSouvenirFood.setAdapter(attractionHomeAdapter);
        recyclerBestSouvenirFood.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                souvenirFoodPresenter.getLocalFoodFull("full", resultLocalfoodFull.getResultLocalfoodList().get(position).getResultLocalfood().getLocalfoodId());
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
            intent.putExtra("ResultLocalFood",(ResultLocalfoodFull)null);

            startActivity(intent);
        }

    }

    @Override
    public void showFullLocalFood(GetResultLocalFood getResultLocalFood) {
        if (getResultLocalFood.getResultLocalfoodFull() != null) {
            Intent intent = new Intent(this, SouvenirFoodActivity.class);
            intent.putExtra("ResultLocalFood", getResultLocalFood.getResultLocalfoodFull());
            intent.putExtra("ResultSouvenirFull",(ResultLocalfoodFull)null);
            startActivity(intent);

        }
    }
}
