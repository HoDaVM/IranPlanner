package com.iranplanner.tourism.iranplanner.ui.activity.restaurant;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.model.App;
import com.iranplanner.tourism.iranplanner.photoViewer.GridImageActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.MapFullActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.OnCutImageListener;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.ShowAttractionListMoreActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentListActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentPresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.globalSearch.GlobalSearchActivity;
import com.iranplanner.tourism.iranplanner.ui.camera.PhotoCropFragment;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import entity.InterestResult;
import entity.RateParam;
import entity.RestaurantList;
import entity.ResultComment;
import entity.ResultCommentList;
import entity.ResultImageList;
import entity.ResultParamUser;
import entity.ResultRestaurantFull;
import entity.ResultWidgetFull;
import entity.SendParamUser;
import ir.adad.client.AdListener;
import ir.adad.client.AdView;
import tools.Constants;
import tools.Util;

/**
 * Created by h.vahidimehr on 13/03/2018.
 */

public class RestaurantDetailActivity extends StandardActivity implements RestaurantContract.View, CommentContract.View, View.OnClickListener, OnCutImageListener, OnMapReadyCallback {

    @Inject
    CommentPresenter commentPresenter;
    @Inject
    RestaurantPresenter restaurantPresenter;
    private Uri mImageUri;
    ResultRestaurantFull resultRestaurantFull;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.txtAddressCity)
    TextView txtAddressCity;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.img_magnifier_foreground)
    ImageView img_magnifier_foreground;
    @BindView(R.id.likeImg)
    ImageView likeImg;
    @BindView(R.id.aboutCityBtn1)
    TextView aboutCityBtn1;
    @BindView(R.id.commentHolder)
    LinearLayout commentHolder;
    @BindView(R.id.cameraHolder)
    LinearLayout cameraHolder;
    @BindView(R.id.likeHolder)
    LinearLayout likeHolder;
    @BindView(R.id.ratingPeopleHolder)
    RelativeLayout ratingPeopleHolder;
    int LikeValue;
    private GoogleMap mMap;
    Marker marker;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.txtRateType)
    TextView txtRateType;
    private ProgressDialog progressDialog;
    SupportMapFragment mapFragment;
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
    @Override
    protected int getLayoutId() {
        return R.layout.activity_restaurant;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initial();
        findView();
        mapFragment.getMapAsync(this);

        if (savedInstanceState != null) {
//            mImageUri = (Uri) savedInstanceState.getParcelable("IMAGE_URI");
        }
        DaggerRestaurantComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .restaurantModule(new RestaurantModule(this, this))
                .build().inject(this);
        if (resultRestaurantFull.getResultRestaurant().getRate().getRateFinalAvg() != null) {
            ratingBar.setRating(Float.valueOf(resultRestaurantFull.getResultRestaurant().getRate().getRateFinalAvg()));
            txtRateType.setText("تا کنون " + Util.persianNumbers(resultRestaurantFull.getResultRestaurant().getRate().getRateFinalCount()) + "نفر به اینجا امتیاز داده اند ");
        }
        commentPresenter.getWidgetResult("nodeuser", resultRestaurantFull.getResultRestaurant().getRestaurantId(), Util.getUseRIdFromShareprefrence(getApplicationContext()), "restaurant", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
        commentHolder.setOnClickListener(this);
        cameraHolder.setOnClickListener(this);
        likeHolder.setOnClickListener(this);
        ratingPeopleHolder.setOnClickListener(this);
        img.setOnClickListener(this);
        img_magnifier_foreground.setOnClickListener(this);
        ((AdView) findViewById(R.id.banner_ad_view)).setAdListener(mAdListener);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mImageUri != null) {
            outState.putParcelable("IMAGE_URI", mImageUri);
        }
    }
    private void findView() {
        ButterKnife.bind(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    }
    private void initial() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        resultRestaurantFull = (ResultRestaurantFull) bundle.getSerializable("resultRestaurantFull");
        txtAddress.setText(resultRestaurantFull.getResultRestaurant().getAddress());
        txtAddressCity.setText(resultRestaurantFull.getResultRestaurant().getProvinceTitle() + " " + resultRestaurantFull.getResultRestaurant().getCityTitle());
        if (resultRestaurantFull.getResultRestaurant().getRestaurantTypeTitle() != null && !resultRestaurantFull.getResultRestaurant().getRestaurantTypeTitle().equals("")) {
            aboutCityBtn1.setVisibility(View.VISIBLE);
            aboutCityBtn1.setText(resultRestaurantFull.getResultRestaurant().getRestaurantTypeTitle());
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(resultRestaurantFull.getResultRestaurant().getRestaurantTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setCustomView(R.layout.abs_layout);


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile.ttf");

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);
        if (resultRestaurantFull.getResultRestaurant().getImgUrl() != null) {
            Util.setImageView(resultRestaurantFull.getResultRestaurant().getImgUrl(), getApplicationContext(), img, null);
        }
    }

    @Override
    public void showComments(ResultCommentList resultCommentList) {
        List<ResultComment> resultComments = resultCommentList.getResultComment();
        Intent intent = new Intent(RestaurantDetailActivity.this, CommentListActivity.class);
        intent.putExtra("resultComments", (Serializable) resultComments);
        intent.putExtra("restaurantData", (Serializable) resultRestaurantFull);
        intent.putExtra("nextOffset", resultCommentList.getStatistics().getOffsetNext().toString());
        intent.putExtra("fromWhere", "restaurant");
        startActivity(intent);
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
        ratingBar.setRating(Float.valueOf(resultParamUser.getResultRatePost().getRateFinalAvg()));
        txtRateType.setText("تا کنون " + Util.persianNumbers(resultParamUser.getResultRatePost().getRateFinalCount() + "نفر به اینجا امتیاز داده اند "));
    }

    @Override
    public void setRateUser(ResultParamUser resultParamUser) {
        CustomDialogAlert customDialogAlert = new CustomDialogAlert(this, resultParamUser);
        customDialogAlert.show();
    }



    @Override
    public void setImageUri(Uri uri) {

    }

    @Override
    public void setRestaurantFull(ResultRestaurantFull resultRestaurantFull) {

    }

    @Override
    public void setRestaurantList(RestaurantList restaurantList) {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ratingPeopleHolder:
                if (Util.isLogin(getApplicationContext(), this)) {
                    SendParamUser ss = new SendParamUser(Util.getUseRIdFromShareprefrence(getApplicationContext()), Util.getTokenFromSharedPreferences(getApplicationContext()), "restaurant", resultRestaurantFull.getResultRestaurant().getRestaurantId());
                    commentPresenter.getRate("rateinfo", ss, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                }
                break;

            case R.id.likeHolder:
                if (Util.isLogin(getApplicationContext(), this)) {

                    if (LikeValue == 1) {
                        OnClickedIntrestedWidget("like", Constants.intrestDefault, likeImg);
                        likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_off));

                    } else {
                        OnClickedIntrestedWidget("like", Constants.likeImg, likeImg);
                        likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
                    }
                }
                break;

            case R.id.commentHolder:
                if (Util.isLogin(getApplicationContext(), this)) {
                    commentPresenter.getCommentList("pagecomments", resultRestaurantFull.getResultRestaurant().getRestaurantId(), "restaurant", "0");
                }

                break;
            case R.id.img:
                if (Util.isLogin(getApplicationContext(), this)) {
                    commentPresenter.getImages("images", resultRestaurantFull.getResultRestaurant().getRestaurantId(), "restaurant");
                }

                break;

            case R.id.cameraHolder:
                if (Util.isLogin(getApplicationContext(), this)) {
                    App.getInstance().prepareDirectories();

                    if (Build.VERSION.SDK_INT < 23) {
                        commentPresenter.selectImage(RestaurantDetailActivity.this);

                    } else {
                        if (App.checkGroupPermissions(App.STORAGE_PERMISSIONS)) {
                            commentPresenter.selectImage(RestaurantDetailActivity.this);
                        } else {
                            requestPermissions(App.STORAGE_PERMISSIONS, 5);
                        }
                    }
                }
                break;
            case R.id.img_magnifier_foreground:
                Intent intentSearch = new Intent(RestaurantDetailActivity.this, GlobalSearchActivity.class);
                startActivity(intentSearch);
                break;
        }
    }

    private void OnClickedIntrestedWidget(String gType, String gValue, ImageView imageView) {
        if (!Util.getUseRIdFromShareprefrence(getApplicationContext()).isEmpty()) {
            commentPresenter.getInterest("widget", Util.getUseRIdFromShareprefrence(getApplicationContext()), "1", "restaurant", resultRestaurantFull.getResultRestaurant().getRestaurantId(), gType, gValue, Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
        } else {
            Log.e("user is not login", "error");
            Toast.makeText(getApplicationContext(), "شما به حساب کاربری خود وارد نشده اید", Toast.LENGTH_LONG).show();
        }
    }

    private static final int REQUEST_CAMERA = 0;
    private static final int SELECT_FILE = 14;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Bitmap bm = null;
            if (requestCode == REQUEST_CAMERA) {
                bm = commentPresenter.grabImage(RestaurantDetailActivity.this);
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();

                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(RestaurantDetailActivity.this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor;
                cursor = cursorLoader.loadInBackground();
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
                bundleImage.putString("nid", resultRestaurantFull.getResultRestaurant().getRestaurantId());
                bundleImage.putString("uid", Util.getUseRIdFromShareprefrence(getApplicationContext()));
                bundleImage.putString("ntype", "restaurant");
                bundleImage.putSerializable("OnCutImageListener", this);
                photoCropFragment.setArguments(bundleImage);
                loadFragment(this, photoCropFragment, R.id.pe_container, true, 0, 0);
            }
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showProgress() {
        progressDialog = Util.showProgressDialog(getApplicationContext(), "منتظر بمانید", RestaurantDetailActivity.this);
    }

    @Override
    public void dismissProgress() {
        Util.dismissProgress(progressDialog);
    }

    @Override
    public void onCropImage(Bitmap bitmap) {
        img.setImageBitmap(bitmap);
    }

    @Override
    public void showMoreImages(ResultImageList resultImageList) {
        if (resultImageList.getResultImages().size() > 0) {
            Intent intent = new Intent(RestaurantDetailActivity.this, GridImageActivity.class);
            intent.putExtra("ResultImagesList", (Serializable) resultImageList.getResultImages());
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "عکسی برای نمایش وجود ندارد", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        if (resultRestaurantFull.getResultRestaurant().getRestaurantPositionLat() != null && resultRestaurantFull.getResultRestaurant().getRestaurantPositionLon() != null) {
            float lan = Float.valueOf(resultRestaurantFull.getResultRestaurant().getRestaurantPositionLat());
            float lon = Float.valueOf(resultRestaurantFull.getResultRestaurant().getRestaurantPositionLon());
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker));

            marker = mMap.addMarker(markerOptions
                    .position(new LatLng(lan, lon))
                    .title(resultRestaurantFull.getResultRestaurant().getRestaurantTitle())
                    .snippet(":)"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lan, lon), 12.0f));

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Log.e("map is ckicked", "true");
                    Intent intent = new Intent(getApplicationContext(), MapFullActivity.class);
//                    ItineraryLodgingCity i = new ItineraryLodgingCity();
//                    i.setCityPositionLat(resultRestaurantFull.getResultRestaurant().getRestaurantPositionLat());
//                    i.setCityPositionLon(resultRestaurantFull.getResultRestaurant().getRestaurantPositionLon());
//                    List<ItineraryLodgingCity> lodgingCities = new ArrayList<ItineraryLodgingCity>();
//                    lodgingCities.add(i);
//                    intent.putExtra("lodgingCities", (Serializable) lodgingCities);
//                    intent.putExtra("isRoad", true);
                    startActivity(intent);
                }
            });
        }

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
                    SendParamUser ss = new SendParamUser(Util.getUseRIdFromShareprefrence(getApplicationContext()), Util.getTokenFromSharedPreferences(getApplicationContext()), "restaurant", resultRestaurantFull.getResultRestaurant().getRestaurantId(), new RateParam(String.valueOf(ratingBar1.getRating()), String.valueOf(ratingBar2.getRating()), String.valueOf(ratingBar3.getRating()), String.valueOf(ratingBar4.getRating())));
                    commentPresenter.rateSend("rate", ss, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                    break;
            }
            dismiss();
        }
    }

}
