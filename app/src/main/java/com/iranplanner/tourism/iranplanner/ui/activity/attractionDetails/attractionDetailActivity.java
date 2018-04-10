package com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coinpany.core.android.widget.CTouchyWebView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.iranplanner.tourism.iranplanner.photoViewer.GridImageActivity;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.ui.activity.MapFullActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.OnCutImageListener;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentPresenter;
import com.iranplanner.tourism.iranplanner.ui.camera.GetPhoto;
import com.iranplanner.tourism.iranplanner.ui.camera.PhotoCropFragment;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMorePresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentListActivity;
import com.iranplanner.tourism.iranplanner.ui.camera.PhotoUtils;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import entity.InterestResult;
import entity.ItineraryLodgingCity;
import entity.RateParam;
import entity.ResulAttraction;
import entity.ResultAttractionList;
import entity.ResultComment;
import entity.ResultCommentList;
import entity.ResultImageList;
import entity.ResultParamUser;
import entity.ResultWidget;
import entity.ResultWidgetFull;
import entity.SendParamUser;
import entity.ShowAtractionDetailMore;
import entity.ShowAttractionListMore;
import ir.adad.client.AdListener;
import ir.adad.client.AdView;
import ir.adad.client.Adad;
import tools.Constants;
import tools.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.graphics.BitmapFactory.decodeFile;

public class attractionDetailActivity extends StandardActivity implements OnMapReadyCallback, View.OnClickListener, AttractionDetailContract.View, AttractionListMoreContract.View, CommentContract.View
        , OnCutImageListener, PhotoUtils.OnImageUriSelect {
    private static final int REQUEST_CAMERA = 0;
    @Inject
    AttractionDetailPresenter attractionDetailPresenter;
    @Inject
    AttractionListMorePresenter attractionListMorePresenter;
    @Inject
//    static
            CommentPresenter commentPresenter;

    private GoogleMap mMap;
    ResulAttraction resulAttraction;
    Marker marker;
    SupportMapFragment mapFragment;
    Boolean showMore = true;
    String myData;
    boolean ratingHolderFlag = false;
    String rotateImage;
    RotateAnimation rotate;
    List<ResultWidget> resultWidget;
    ProgressDialog progressDialog;
    int BookmarkValue;
    int LikeValue;
    int VisitedValue;
    int WishValue;

    @BindView(R.id.contentFullDescription)
    CTouchyWebView contentFullDescription;
    @BindView(R.id.aboutCityBtn1)
    TextView aboutCityBtn1;
    @BindView(R.id.textTimeDuration)
    TextView textTimeDuration;
    @BindView(R.id.textEntranceFee)
    TextView textEntranceFee;
    @BindView(R.id.txtPhotos)
    TextView txtPhotos;
    @BindView(R.id.attractionType)
    TextView attractionType;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.imageTypeAttraction)
    ImageView imageTypeAttraction;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.commentHolder)
    LinearLayout commentHolder;
    @BindView(R.id.likeHolder)
    LinearLayout likeHolder;
    @BindView(R.id.MoreInoText)
    TextView MoreInoText;
    @BindView(R.id.commentImg)
    ImageView commentImg;
    @BindView(R.id.likeImg)
    ImageView likeImg;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.txtRateType)
    TextView txtRateType;
    @BindView(R.id.recyclerBestAttraction)
    RecyclerView recyclerBestAttraction;
    @BindView(R.id.cameraHolder)
    LinearLayout cameraHolder;
    @BindView(R.id.ratingPeopleHolder)
    RelativeLayout ratingPeopleHolder;
    DaggerAtractionDetailComponent.Builder builder;
    private List<ResultAttractionList> resultAttractionList;
    private ProgressDialog progressBar;
    private Uri mImageUri;
    GetPhoto getPhoto;
    private AdListener mAdListener = new AdListener() {

        @Override
        public void onAdLoaded() {
//            Toast.makeText(getApplicationContext(), "Banner Ad loaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdFailedToLoad() {
//            Toast.makeText(getApplicationContext(), "Banner ad failed to load", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMessageReceive(JSONObject message) {

//            Toast.makeText(getApplicationContext(), "Banner ", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onRemoveAdsRequested() {
//            Toast.makeText(getApplicationContext(), "User requested to remove Banner ads from app", Toast.LENGTH_SHORT).show();
            //Move your user to shopping center of your app
        }

    };

    private void findView() {
        ButterKnife.bind(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }


    private void setAttractionTypeImage() {
        if (resulAttraction.getAttractionItineraryTypeId() != null) {
            if (resulAttraction.getAttractionItineraryTypeId().equals("2930")) {
                imageTypeAttraction.setImageDrawable(getResources().getDrawable(R.mipmap.ic_mazhabi));
            } else if (resulAttraction.getAttractionItineraryTypeId().equals("2931")) {
                imageTypeAttraction.setImageDrawable(getResources().getDrawable(R.mipmap.ic_natural));
            } else if (resulAttraction.getAttractionItineraryTypeId().equals("2932")) {
                imageTypeAttraction.setImageDrawable(getResources().getDrawable(R.mipmap.ic_historical));
            } else if (resulAttraction.getAttractionItineraryTypeId().equals("2933")) {
                imageTypeAttraction.setImageDrawable(getResources().getDrawable(R.mipmap.ic_sport));
            }
        }
    }

    private void setWebViewContent(String myData) {
        Util.setWebViewJastify(contentFullDescription, myData);

    }

    private String getShowMoreString(String myData) {
        int count = 0;
        int position = 0;
        for (count = 0; count < 40; count++) {
            position = myData.indexOf(" ", position + 1);
        }
        return myData.substring(0, position) + "...";
    }

    private void getExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        attraction = (entity.ResultItineraryAttraction) bundle.getSerializable("ResultItineraryAttraction");
        resulAttraction = (ResulAttraction) bundle.getSerializable("resulAttraction");
        resultAttractionList = (List<ResultAttractionList>) bundle.getSerializable("resultAttractionList");
        resultWidget = (List<ResultWidget>) bundle.getSerializable("resultWidget");
        if (resultWidget != null) {
            setInterestResponce(resultWidget);
        }
    }

    private void setNearAttraction(List<ResultAttractionList> resultAttractions) {
        NearAttractionAdapter attractionHomeAdapter = new NearAttractionAdapter(resultAttractions, getApplicationContext(), null, null);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerBestAttraction.setLayoutManager(horizontalLayoutManagaer);
        recyclerBestAttraction.setAdapter(attractionHomeAdapter);
        recyclerBestAttraction.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                attractionListMorePresenter.getAttractionDetailNear("full", resultAttractionList.get(position).getResulAttraction().getAttractionId(), "fa", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
            }
        }));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mImageUri = (Uri) savedInstanceState.getParcelable("IMAGE_URI");
        }

        findView();
        Util.overrideFont();
        getExtra();
        setNearAttraction(resultAttractionList);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(resulAttraction.getAttractionTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setHomeButtonEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile.ttf");

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);


        aboutCityBtn1.setVisibility(View.VISIBLE);
        aboutCityBtn1.setText(resulAttraction.getProvinceTitle() + " - " + resulAttraction.getCityTitle());
        if (resulAttraction.getAttractionEstimatedTime() != null) {
            int totalMinute = Integer.parseInt(resulAttraction.getAttractionEstimatedTime());
            Util.convertMinuteToHour(totalMinute, textTimeDuration);
        }

        if (resulAttraction.getAttractionImgUrl() != null) {
            Util.setImageView(resulAttraction.getAttractionImgUrl(), getApplicationContext(), img, null);
        }
        myData = resulAttraction.getAttractionBody();
        if (myData != null) {
            Util.setWebViewJastify(contentFullDescription, myData);
        }

        if (resulAttraction.getAttractionPrice() == null) {
            textEntranceFee.setText("هزینه ورودی : رایگان");
        } else {
            textEntranceFee.setText("هزینه ورودی : " + Util.persianNumbers(resulAttraction.getAttractionPrice().toString()) + "تومان");
        }
        attractionType.setText(resulAttraction.getAttractionItineraryTypeTitle());
        setAttractionTypeImage();
        txtAddress.setText(resulAttraction.getAttractionAddress());

        mapFragment.getMapAsync(this);


        likeImg.setOnClickListener(this);
        commentImg.setOnClickListener(this);

        commentHolder.setOnClickListener(this);
        ratingPeopleHolder.setOnClickListener(this);
        MoreInoText.setOnClickListener(this);


        img.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                commentPresenter.getImages("images", resulAttraction.getAttractionId(), "attraction");
            }
        });

        builder = DaggerAtractionDetailComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .attractionDetailModule(new AttractionDetailModule(this, this, this));
        builder.build().inject(this);
        rotateImage = "likeImg";
        if (!Util.getUseRIdFromShareprefrence(getApplicationContext()).equals("")) {
            commentPresenter.getWidgetResult("nodeuser", resulAttraction.getAttractionId(), Util.getUseRIdFromShareprefrence(getApplicationContext()), "attraction", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
        }


        getPhoto = new GetPhoto(getApplicationContext(), this);
        cameraHolder.setOnClickListener(this);

        if (resulAttraction.getRate().getRateFinalAvg() != null) {
            ratingBar.setRating(Float.valueOf(resulAttraction.getRate().getRateFinalAvg()));
            txtRateType.setText("تا کنون " + Util.persianNumbers(resulAttraction.getRate().getRateFinalCount()) + "نفر به اینجا امتیاز داده اند ");
        }
        ((AdView) findViewById(R.id.banner_ad_view)).setAdListener(mAdListener);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private Menu menu = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_attraction_detail, menu);
        this.menu = menu;
//        menu.findItem(R.id.menuAttractionShare).setVisible(true);
        return true;
    }

    private void setInterestResponce(List<ResultWidget> resultWidget) {

        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 1) {
            LikeValue = 1;
            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        if (resulAttraction.getAttractionPositionLat() != null && resulAttraction.getAttractionPositionLon() != null) {
            float lan = Float.valueOf(resulAttraction.getAttractionPositionLat());
            float lon = Float.valueOf(resulAttraction.getAttractionPositionLon());
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker));

            marker = mMap.addMarker(markerOptions
                    .position(new LatLng(lan, lon))
                    .title(resulAttraction.getCityTitle())
                    .snippet(":)"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lan, lon), 12.0f));

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Log.e("map is ckicked", "true");
                    Intent intent = new Intent(getApplicationContext(), MapFullActivity.class);
                    ItineraryLodgingCity i = new ItineraryLodgingCity();
                    i.setCityPositionLat(resulAttraction.getAttractionPositionLat());
                    i.setCityPositionLon(resulAttraction.getAttractionPositionLon());
                    List<ItineraryLodgingCity> lodgingCities = new ArrayList<ItineraryLodgingCity>();
                    lodgingCities.add(i);
                    intent.putExtra("lodgingCities", (Serializable) lodgingCities);
                    intent.putExtra("resulAttraction", (Serializable) resulAttraction);
                    intent.putExtra("isRoad", true);
                    startActivity(intent);
                }
            });
        }

    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(attractionDetailActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("لطفا منتظر بمانید");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commentHolder:
                builder.build().inject(this);
                if (Util.isLogin(getApplicationContext(), this)) {
                    commentPresenter.getCommentList("pagecomments", resulAttraction.getAttractionId(), "attraction", "0");
                }
                break;
            case R.id.commentImg:
                builder.build().inject(this);
                if (Util.isLogin(getApplicationContext(), this)) {
                    commentPresenter.getCommentList("pagecomments", resulAttraction.getAttractionId(), "attraction", "0");
                }
                break;
            case R.id.MoreInoText:
                if (showMore) {
                    Util.setWebViewJastify(contentFullDescription, myData);
                    MoreInoText.setText("مطلب کوتاه");
                    showMore = false;
                } else {
                    if (resulAttraction.getAttractionBody() != null) {
                        Util.setWebViewJastify(contentFullDescription, myData);
                        MoreInoText.setText("بیشتر بخوانید");
                        showMore = true;
                    }
                }

                break;

            case R.id.likeImg:
                if (Util.isLogin(getApplicationContext(), this)) {
                    rotateImage = "likeImg";
                    if (LikeValue == 1) {
                        OnClickedIntrestedWidget("like", Constants.intrestDefault, likeImg);
                        likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_off));


                    } else {
                        OnClickedIntrestedWidget("like", Constants.likeImg, likeImg);
                        likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));

                    }
                }

                break;

            case R.id.cameraHolder:


                if (Util.isLogin(getApplicationContext(), this)) {
                    App.getInstance().prepareDirectories();

                    if (Build.VERSION.SDK_INT < 23) {
                        commentPresenter.selectImage(attractionDetailActivity.this);

                    } else {
                        if (App.checkGroupPermissions(App.STORAGE_PERMISSIONS)) {
                            commentPresenter.selectImage(attractionDetailActivity.this);
                        } else {
                            requestPermissions(App.STORAGE_PERMISSIONS, 5);
                        }
                    }
                }
                break;


            case R.id.ratingPeopleHolder:
                //todo

                if (Util.isLogin(getApplicationContext(), this)) {
                    SendParamUser ss = new SendParamUser(Util.getUseRIdFromShareprefrence(getApplicationContext()), Util.getTokenFromSharedPreferences(getApplicationContext()), "attraction", resulAttraction.getAttractionId());
                    commentPresenter.getRate("rateinfo", ss, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                }
                break;


        }
    }

    private void OnClickedIntrestedWidget(String gType, String gValue, ImageView imageView) {
        commentPresenter.getInterest("widget", Util.getUseRIdFromShareprefrence(getApplicationContext()), Util.getTokenFromSharedPreferences(getApplicationContext()), "attraction", resulAttraction.getAttractionId(), gType, gValue, Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
    }


    @Override
    public void showComments(ResultCommentList resultCommentList) {
        List<ResultComment> resultComments = resultCommentList.getResultComment();
        Intent intent = new Intent(attractionDetailActivity.this, CommentListActivity.class);
        intent.putExtra("resultComments", (Serializable) resultComments);
        intent.putExtra("attraction", (Serializable) resulAttraction);
        intent.putExtra("nextOffset", resultCommentList.getStatistics().getOffsetNext().toString());
        intent.putExtra("fromWhere", "Attraction");
        startActivity(intent);
//        progressDialog.dismiss();
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

        progressBar = Util.showProgressDialog(getApplicationContext(), "لطفا منتظر بمانید", attractionDetailActivity.this);
    }

    @Override
    public void dismissProgress() {
        progressBar.dismiss();
    }

    @Override
    public void showMoreImages(ResultImageList resultImageList) {
        if (resultImageList.getResultImages().size() > 0) {
            Intent intent = new Intent(attractionDetailActivity.this, GridImageActivity.class);
            intent.putExtra("ResultImagesList", (Serializable) resultImageList.getResultImages());
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "عکسی برای نمایش وجود ندارد", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setRate(ResultParamUser resultParamUser) {
        if (resultParamUser.getResultRatePost().getRateFinalAvg() != null) {
            ratingBar.setRating(Float.valueOf(resultParamUser.getResultRatePost().getRateFinalAvg()));
            txtRateType.setText("تا کنون " + Util.persianNumbers(resultParamUser.getResultRatePost().getRateFinalCount() + "نفر به اینجا امتیاز داده اند "));
        }
    }

    @Override
    public void setRateUser(ResultParamUser resultParamUser) {
        CustomDialogAlert customDialogAlert = new CustomDialogAlert(this, resultParamUser);
        customDialogAlert.show();
    }

    @Override
    public void setImageUri(Uri uri) {
        mImageUri = uri;
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

    @Override
    public void ShowAttractionLists(ShowAttractionListMore getAttractionList) {

    }


    @Override
    public void setLoadWidget(ResultWidgetFull resultWidgetFull) {
        List<ResultWidget> resultWidget = resultWidgetFull.getResultWidget();
        setWidgetValue(resultWidget);
    }

    @Override
    public void setIntrestedWidget(InterestResult InterestResult) {
        setWidgetValue(InterestResult.getResultWidget());
    }

    @Override
    public void showAnimationWhenWaiting() {

    }


    @Override
    public void showDirectionOnMap(PolylineOptions rectLine) {
        mMap.addPolyline(rectLine);

    }

    private void setWidgetValue(List<ResultWidget> resultWidget) {

        if (resultWidget.get(0).getWidgetLikeValue() != null) {
            LikeValue = resultWidget.get(0).getWidgetLikeValue();
        }

        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 1) {
            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
        } else if (resultWidget.get(0).getWidgetLikeValue() == null || resultWidget.get(0).getWidgetLikeValue() == 0) {
            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_off));
        }
//
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_attraction_details;
    }

    @Override
    public void onCropImage(Bitmap bitmap) {
        img.setImageBitmap(bitmap);
    }

    @Override
    public void onSelectImage(Uri uri) {
        mImageUri = uri;
    }


       public class CustomDialogAlert extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        public ResultParamUser resultParamUser;
        public TextView txtNo, txtOk,
                txtRateName1,
                txtRateName2,
                txtRateName3,
                txtRateName4;
        public RatingBar ratingBar1,
                ratingBar2,
                ratingBar3,
                ratingBar4,
                alertDescription;


        public CustomDialogAlert(Activity a, ResultParamUser resultParamUser) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.resultParamUser = resultParamUser;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.raiting_layout);
            txtNo = findViewById(R.id.txtNo);
            txtOk = findViewById(R.id.txtOk);
            txtRateName1 = findViewById(R.id.txtRateName1);
            txtRateName2 = findViewById(R.id.txtRateName2);
            txtRateName3 = findViewById(R.id.txtRateName3);
            txtRateName4 = findViewById(R.id.txtRateName4);

            ratingBar1 = findViewById(R.id.ratingBar1);
            ratingBar2 = findViewById(R.id.ratingBar2);
            ratingBar3 = findViewById(R.id.ratingBar3);
            ratingBar4 = findViewById(R.id.ratingBar4);
            try {
                txtRateName1.setText(resultParamUser.getResultRatePost().getRateFinalParam().get(0).getValueTitle());
                txtRateName2.setText(resultParamUser.getResultRatePost().getRateFinalParam().get(1).getValueTitle());
                txtRateName3.setText(resultParamUser.getResultRatePost().getRateFinalParam().get(2).getValueTitle());
                txtRateName4.setText(resultParamUser.getResultRatePost().getRateFinalParam().get(3).getValueTitle());
                ratingBar1.setRating(Float.valueOf(resultParamUser.getResultRatePost().getRateFinalParam().get(0).getValueAvg()));
                ratingBar2.setRating(Float.valueOf(resultParamUser.getResultRatePost().getRateFinalParam().get(1).getValueAvg()));
                ratingBar3.setRating(Float.valueOf(resultParamUser.getResultRatePost().getRateFinalParam().get(2).getValueAvg()));
                ratingBar4.setRating(Float.valueOf(resultParamUser.getResultRatePost().getRateFinalParam().get(3).getValueAvg()));
            } catch (Exception e) {

            }

            txtNo.setOnClickListener(this);
            txtOk.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txtNo:
                    dismiss();
                    break;
                case R.id.txtOk:
//                {"uid":"101","cid":"20","ntype":"attraction","nid":"30","rate_param":{"param1":"1","param2":"2","param3":"5"}}

                    SendParamUser ss = new SendParamUser(Util.getUseRIdFromShareprefrence(getApplicationContext()), Util.getTokenFromSharedPreferences(getApplicationContext()), "attraction", resulAttraction.getAttractionId(), new RateParam(String.valueOf(ratingBar1.getRating()), String.valueOf(ratingBar2.getRating()), String.valueOf(ratingBar3.getRating()), String.valueOf(ratingBar4.getRating())));
//                    attractionDetailPresenter.rateSend(ss, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                    commentPresenter.rateSend("rate", ss, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                    break;
            }
            dismiss();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mImageUri != null) {
            outState.putParcelable("IMAGE_URI", mImageUri);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED /*&& grantResults[1] == PackageManager.PERMISSION_GRANTED*/) {
                App.getInstance().prepareDirectories();
                commentPresenter.selectImage(attractionDetailActivity.this);
                return;
            }
        }
    }


    private static final int SELECT_FILE = 14;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Bitmap bm = null;
            if (requestCode == REQUEST_CAMERA) {
                bm = commentPresenter.grabImage(attractionDetailActivity.this);
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();

                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(attractionDetailActivity.this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);

                bm = BitmapFactory.decodeFile(selectedImagePath/*, options*/);

                int orientation = 0;
                Matrix matrix = new Matrix();
                try {
                    ExifInterface ei = new ExifInterface(selectedImagePath);
                    int exif = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    switch (exif) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            orientation = 90;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            orientation = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            orientation = 270;
                            break;
                    }
                    matrix.preRotate(orientation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Rotate image bitmap to correct orientation.
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);

            }
            if (bm != null) {
                final PhotoCropFragment photoCropFragment = new PhotoCropFragment();
                Bundle bundleImage = new Bundle();
                bundleImage.putParcelable("IMAGE_TO_CROP", bm);
                bundleImage.putString("nid", resulAttraction.getAttractionId());
                bundleImage.putString("uid", Util.getUseRIdFromShareprefrence(getApplicationContext()));
                bundleImage.putString("ntype", "attraction");
                bundleImage.putSerializable("OnCutImageListener", this);
                photoCropFragment.setArguments(bundleImage);
                loadFragment(this, photoCropFragment, R.id.pe_container, true, 0, 0);
            }
        }

    }


}
