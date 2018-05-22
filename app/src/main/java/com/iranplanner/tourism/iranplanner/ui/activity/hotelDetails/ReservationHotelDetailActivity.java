package com.iranplanner.tourism.iranplanner.ui.activity.hotelDetails;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.coinpany.core.android.widget.CTouchyWebView;
import com.coinpany.core.android.widget.Utils;
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
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMorePresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.attractionDetailActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentContract;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentListActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentPresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary.AddNewDynamicItinerary;
import com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary.DynamicItineraryContract;
import com.iranplanner.tourism.iranplanner.ui.activity.dynamicItinerary.DynamicItineraryPresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.globalSearch.GlobalSearchActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.showRoom.ShowRoomActivity;
import com.iranplanner.tourism.iranplanner.ui.camera.PhotoCropFragment;
import com.iranplanner.tourism.iranplanner.ui.camera.PhotoUtils;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import entity.InterestResult;
import entity.ItineraryLodgingCity;
import entity.MyItineraryAdd;
import entity.MyItineraryList;
import entity.RateParam;
import entity.ResultComment;
import entity.ResultCommentList;
import entity.ResultEditDynamicItinerary;
import entity.ResultImageList;
import entity.ResultLodging;
import entity.ResultLodgingRoomList;
import entity.ResultParamUser;
import entity.ResultPositionAddItinerary;
import entity.ResultRoom;
import entity.ResultWidget;
import entity.ResultWidgetFull;
import entity.SendParamUser;
import entity.SendParamUsetToGetItinerary;
import entity.ShowAtractionDetailMore;
import entity.ShowAttractionListMore;
import ir.adad.client.AdListener;
import ir.adad.client.AdView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import server.Config;
import server.getJsonInterface;
import tools.Constants;
import tools.CustomDialogDate;
import tools.CustomDialogNumberPicker;
import tools.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by h.vahidimehr on 28/02/2017.
 */

public class ReservationHotelDetailActivity extends StandardActivity implements OnMapReadyCallback, CommentContract.View,
        View.OnClickListener, AttractionListMorePresenter.View, HotelDetailContract.View, PhotoUtils.OnImageUriSelect, OnCutImageListener, DynamicItineraryContract.View {
    private Uri mImageUri;

    private GoogleMap mMap;
    //    ResultItineraryAttraction attraction;
    TextView attractionName, attractionPlace, textTimeDuration, textEntranceFee, attractionType, textBody;
    Marker marker;
    protected CTouchyWebView contentFullDescription;
    ImageView imageTypeAttraction;
    ImageView img;
    SupportMapFragment mapFragment;
    Boolean showMore = true;
    String myData;
    TextView txtDateCheckIn, txtOk, MoreInoText, txtHotelType, txtHotelName, txtAddress, txtDate, txtDurationHotel;
    RelativeLayout TypeDurationHolder, holderDate, ratingHolder, GroupHolder, interestingLayout, VisitedLayout, LikeLayout, changeDateHolder;
    LinearLayout rateHolder, bookmarkHolder, doneHolder, nowVisitedHolder, beftorVisitedHolder, likeHolder, okHolder, dislikeHolder, commentHolder;
    ImageView bookmarkImg, doneImg, dislikeImg, okImg, likeImg, rateImg, beftorVisitedImg, nowVisitedImg, wishImg, triangleShowAttraction, img_magnifier_foreground;
    boolean ratingHolderFlag = false;
    String rotateImage;
    RotateAnimation rotate;
    ResultLodging resultLodgingHotelDetail;
    Date startOfTravel;
    int durationTravel;
    Button roomReservationBtn;
    private String todayDate;
    private ProgressDialog progressDialog;
    ImageView imgStar1, imgStar2, imgStar3, imgStar4, imgStar5;
    RelativeLayout starHolder;
    int LikeValue;
    DaggerHotelDetailComponent.Builder builder;
    LinearLayout cameraHolder;
    @Inject
    CommentPresenter commentPresenter;
    @Inject
    HotelDetailPresenter hotelDetailPresenter;
    @Inject
    DynamicItineraryPresenter dynamicItineraryPresenter;
    RelativeLayout ratingPeopleHolder;
    RatingBar ratingBar;
    TextView txtRateType, txtPhotos;
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
    private LinearLayout addHolder;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.raiting_layout;
    }

    private void findView() {

        Log.e("this tag", ReservationHotelDetailActivity.class.getSimpleName());


        setContentView(R.layout.fragment_reservation);
        starHolder = findViewById(R.id.starShowHolder);
        imgStar1 = findViewById(R.id.imgStarH1);
        imgStar2 = findViewById(R.id.imgStarH2);
        imgStar3 = findViewById(R.id.imgStarH3);
        imgStar4 = findViewById(R.id.imgStarH4);
        imgStar5 = findViewById(R.id.imgStarH5);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        contentFullDescription = (CTouchyWebView) findViewById(R.id.contentFullDescription);
        MoreInoText = (TextView) findViewById(R.id.MoreInoText);
        txtDateCheckIn = (TextView) findViewById(R.id.txtDateCheckIn);
        txtHotelName = (TextView) findViewById(R.id.txtHotelName);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDurationHotel = (TextView) findViewById(R.id.txtDurationHotel);
        TypeDurationHolder = (RelativeLayout) findViewById(R.id.TypeDurationHolder);
        txtHotelType = (TextView) findViewById(R.id.txtHotelType);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
//        attractionName = (TextView) findViewById(R.id.attractionName);
//        attractionPlace = (TextView) findViewById(R.id.attractionPlace);
        textTimeDuration = (TextView) findViewById(R.id.textTimeDuration);
        textEntranceFee = (TextView) findViewById(R.id.textEntranceFee);
        attractionType = (TextView) findViewById(R.id.attractionType);
        imageTypeAttraction = (ImageView) findViewById(R.id.imageTypeAttraction);
        img = (ImageView) findViewById(R.id.img);
        img_magnifier_foreground = (ImageView) findViewById(R.id.img_magnifier_foreground);
        roomReservationBtn = (Button) findViewById(R.id.roomReservationBtn);
        holderDate = (RelativeLayout) findViewById(R.id.holderDate);

//        rateHolder = (LinearLayout) findViewById(R.id.rateHolder);
//        doneHolder = (LinearLayout) findViewById(R.id.doneHolder);
//        nowVisitedHolder = (LinearLayout) findViewById(R.id.nowVisitedHolder);
//        beftorVisitedHolder = (LinearLayout) findViewById(R.id.beftorVisitedHolder);
//        dislikeHolder = (LinearLayout) findViewById(R.id.dislikeHolder);
//        okHolder = (LinearLayout) findViewById(R.id.okHolder);
        likeHolder = (LinearLayout) findViewById(R.id.likeHolder);
//        bookmarkHolder = (LinearLayout) findViewById(R.id.bookmarkHolder);
        ratingHolder = (RelativeLayout) findViewById(R.id.ratingHolder);
        GroupHolder = (RelativeLayout) findViewById(R.id.GroupHolder);
//        interestingLayout = (RelativeLayout) findViewById(R.id.interestingLayout);
//        VisitedLayout = (RelativeLayout) findViewById(R.id.VisitedLayout);
        LikeLayout = (RelativeLayout) findViewById(R.id.LikeLayout);
        changeDateHolder = (RelativeLayout) findViewById(R.id.changeDateHolder);
        txtOk = (TextView) findViewById(R.id.txtOk);
        MoreInoText = (TextView) findViewById(R.id.MoreInoText);
//        bookmarkImg = (ImageView) findViewById(R.id.bookmarkImg);
//        doneImg = (ImageView) findViewById(R.id.doneImg);
//        dislikeImg = (ImageView) findViewById(R.id.dislikeImg);
        okImg = (ImageView) findViewById(R.id.commentImg);
        likeImg = (ImageView) findViewById(R.id.likeImg);
//        rateImg = (ImageView) findViewById(R.id.rateImg);
//        beftorVisitedImg = (ImageView) findViewById(R.id.beftorVisitedImg);
//        nowVisitedImg = (ImageView) findViewById(R.id.nowVisitedImg);
//        wishImg = (ImageView) findViewById(R.id.wishImg);
        triangleShowAttraction = (ImageView) findViewById(R.id.triangleShowAttraction);
        commentHolder = findViewById(R.id.commentHolder);
        ratingPeopleHolder = findViewById(R.id.ratingPeopleHolder);
        ratingBar = findViewById(R.id.ratingBar);
        txtRateType = findViewById(R.id.txtRateType);
        txtPhotos = findViewById(R.id.txtPhotos);
        cameraHolder = findViewById(R.id.cameraHolder);
        addHolder = findViewById(R.id.addHolder);
        cameraHolder.setOnClickListener(this);
        addHolder.setOnClickListener(this);

//        setupTablayout();
    }

    private void overrideFont() {
        // for Override font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMobile.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private void setImageHolder() {
        if (resultLodgingHotelDetail.getLodgingImgUrl() != null) {
            String url = resultLodgingHotelDetail.getLodgingImgUrl();
            Glide.with(getApplicationContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .listener(new RequestListener<String, GlideDrawable>() {

                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            //// TODO: 22/01/2017  get defeult picture
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(img);

        } else {
            Glide.clear(img);
            img.setImageDrawable(null);
        }
    }

    private void setWebViewContent(String myData) {
        contentFullDescription.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        contentFullDescription.setLongClickable(false);
        contentFullDescription.setHapticFeedbackEnabled(false);

        String pish = "<html><head><style type=\"text/css\">@font-face {color:#737373;font-family: MyFont;src: url(\"file:///android_asset/fonts/IRANSansMobile.ttf\")}body {font-family: MyFont;font-size: small;text-align: justify;direction:rtl}</style></head><body>";
        String pas = "</body></html>";
        String myHtmlString = pish + myData + pas;
        contentFullDescription.loadDataWithBaseURL(null, myHtmlString, "text/html", "UTF-8", null);
    }

    private String getShowMoreString(String myData) {
        int count = 0;
        int position = 0;
        for (count = 0; count < 40; count++) {
            position = myData.indexOf(" ", position + 1);
        }
        return myData.substring(0, position) + "...";
    }

    //todo use broadcast for after get reservation hotel  open my reservation on setting
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("KILL");
            registerReceiver(receiver, filter);
        } catch (Exception e) {

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //todo hoda anjam bede injaro haji
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_share:
//
//                break;
//            case R.id.action_like:
//
//                break;
        }
        return true;
    }

    private void getExtras() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        resultLodgingHotelDetail = (ResultLodging) bundle.getSerializable("resultLodgingHotelDetail");
        startOfTravel = (Date) bundle.getSerializable("startOfTravel");
        durationTravel = (int) bundle.getSerializable("durationTravel");
        todayDate = bundle.getString("todayDate");
        String s = (startOfTravel != null) ? Util.persianNumbers(Utils.getSimpleDate(startOfTravel)) : Util.persianNumbers(Utils.getSimpleDateMilli(Long.valueOf(todayDate)));
        txtDateCheckIn.setText(s);
        txtDurationHotel.setText("به مدت " + Util.persianNumbers(String.valueOf(durationTravel)) + " شب");
    }

    private void showDialogNumber() {
        CustomDialogNumberPicker cdd = new CustomDialogNumberPicker(this, 10, 1, "مدت زمان اقامت", null);
        cdd.show();
        cdd.setDialogResult(new CustomDialogNumberPicker.OnDialogNumberPick() {
            @Override
            public void finish(int result) {
                durationTravel = result;
                txtDurationHotel.setText(Util.persianNumbers(result + "شب"));
            }
        });
    }


    private void setStar() {
        if (resultLodgingHotelDetail.getLodgingRateInt().equals("1")) {
            starHolder.setVisibility(View.VISIBLE);
            imgStar1.setVisibility(View.VISIBLE);
        } else if (resultLodgingHotelDetail.getLodgingRateInt().equals("2")) {
            starHolder.setVisibility(View.VISIBLE);
            imgStar1.setVisibility(View.VISIBLE);
            imgStar2.setVisibility(View.VISIBLE);
        } else if (resultLodgingHotelDetail.getLodgingRateInt().equals("3")) {
            starHolder.setVisibility(View.VISIBLE);
            imgStar1.setVisibility(View.VISIBLE);
            imgStar2.setVisibility(View.VISIBLE);
            imgStar3.setVisibility(View.VISIBLE);
        } else if (resultLodgingHotelDetail.getLodgingRateInt().equals("4")) {
            starHolder.setVisibility(View.VISIBLE);
            imgStar1.setVisibility(View.VISIBLE);
            imgStar2.setVisibility(View.VISIBLE);
            imgStar3.setVisibility(View.VISIBLE);
            imgStar4.setVisibility(View.VISIBLE);
        } else if (resultLodgingHotelDetail.getLodgingRateInt().equals("5")) {
            starHolder.setVisibility(View.VISIBLE);
            imgStar1.setVisibility(View.VISIBLE);
            imgStar2.setVisibility(View.VISIBLE);
            imgStar3.setVisibility(View.VISIBLE);
            imgStar4.setVisibility(View.VISIBLE);
            imgStar5.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findView();
        overrideFont();
        getExtras();
        if (savedInstanceState != null) {
            mImageUri = (Uri) savedInstanceState.getParcelable("IMAGE_URI");
        }
        txtHotelName.setText(resultLodgingHotelDetail.getLodgingName());
        txtHotelType.setText("نوع مرکز اقامتی: " + resultLodgingHotelDetail.getLodgingTypeTitle());
        txtAddress.setText(resultLodgingHotelDetail.getLodgingAddress());
        roomReservationBtn.setOnClickListener(this);
        setStar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(resultLodgingHotelDetail.getLodgingName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setCustomView(R.layout.abs_layout);


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile.ttf");

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);


        if (resultLodgingHotelDetail.getRate().getRateFinalAvg() != null) {
            ratingBar.setRating(Float.valueOf(resultLodgingHotelDetail.getRate().getRateFinalAvg()));
            txtRateType.setText("تا کنون " + Util.persianNumbers(resultLodgingHotelDetail.getRate().getRateFinalCount()) + "نفر به اینجا امتیاز داده اند ");
        }

        holderDate.setOnClickListener(this);
        TypeDurationHolder.setOnClickListener(this);
        commentHolder.setOnClickListener(this);
        likeHolder.setOnClickListener(this);
        ratingPeopleHolder.setOnClickListener(this);
        img.setOnClickListener(this);
        img_magnifier_foreground.setOnClickListener(this);



        setImageHolder();
        DaggerHotelDetailComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .hotelDetailModule(new HotelDetailModule(this, this, this))
                .build().inject(this);
        commentPresenter.getWidgetResult("nodeuser", resultLodgingHotelDetail.getLodgingId(), Util.getUseRIdFromShareprefrence(getApplicationContext()), "lodging", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
        mapFragment.getMapAsync(this);
        ((AdView) findViewById(R.id.banner_ad_view)).setAdListener(mAdListener);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mImageUri != null) {
            outState.putParcelable("IMAGE_URI", mImageUri);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);

        MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker));
        if (resultLodgingHotelDetail.getLodgingPosLat() != null &&
                resultLodgingHotelDetail.getLodgingPosLong() != null && !resultLodgingHotelDetail.getLodgingPosLat().equals("") && !resultLodgingHotelDetail.getLodgingPosLong().equals("")) {
            Double lan = Double.valueOf(resultLodgingHotelDetail.getLodgingPosLat());
            Double lon = Double.valueOf(resultLodgingHotelDetail.getLodgingPosLong());
            marker = mMap.addMarker(markerOptions
                    .position(new LatLng(lan, lon))
                    .title(resultLodgingHotelDetail.getLodgingName())
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lan, lon), 15.0f));
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.e("map is ckicked", "true");
//
//                Log.e("map is ckicked", "true");
//                Intent intent = new Intent(getApplicationContext(), MapFullActivity.class);
//                ItineraryLodgingCity i = new ItineraryLodgingCity();
//                i.setCityPositionLat(attraction.getAttractionPositionLat());
//                i.setCityPositionLon(attraction.getAttractionPositionOn());
//                List<ItineraryLodgingCity> lodgingCities = new ArrayList<ItineraryLodgingCity>();
//                lodgingCities.add(i);
//                intent.putExtra("lodgingCities", (Serializable) lodgingCities);
//                intent.putExtra("attraction", (Serializable) attraction);
//                startActivity(intent);
                //===
                Intent intent = new Intent(getApplicationContext(), MapFullActivity.class);
                ItineraryLodgingCity i = new ItineraryLodgingCity();
                i.setCityPositionLat(String.valueOf(resultLodgingHotelDetail.getLodgingPosLat()));
                i.setCityPositionLon(String.valueOf(resultLodgingHotelDetail.getLodgingPosLong()));
                List<ItineraryLodgingCity> lodgingCities = new ArrayList<ItineraryLodgingCity>();
                lodgingCities.add(i);
                intent.putExtra("lodgingCities", (Serializable) lodgingCities);
                intent.putExtra("isRoad", true);
                startActivity(intent);
            }
        });
    }

    private static final int REQUEST_CAMERA = 0;
    private static final int SELECT_FILE = 14;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap bm = null;
            if (requestCode == REQUEST_CAMERA) {
                bm = commentPresenter.grabImage(ReservationHotelDetailActivity.this);
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();

                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(ReservationHotelDetailActivity.this, selectedImageUri, projection, null, null,
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
                bundleImage.putString("nid", resultLodgingHotelDetail.getLodgingId());
                bundleImage.putString("uid", Util.getUseRIdFromShareprefrence(getApplicationContext()));
                bundleImage.putString("ntype", "Lodging");
                bundleImage.putSerializable("OnCutImageListener", this);
                photoCropFragment.setArguments(bundleImage);
                loadFragment(this, photoCropFragment, R.id.pe_container, true, 0, 0);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.MoreInoText:
                if (showMore) {
                    setWebViewContent(myData);
                    MoreInoText.setText("مطلب کوتاه");
                    showMore = false;
                } else {
//                    setWebViewContent(getShowMoreString(myData));
                    MoreInoText.setText("بیشتر بخوانید");
                    showMore = true;
                }

                break;
            case R.id.TypeDurationHolder:

                showDialogNumber();


                break;

            case R.id.holderDate:
                showDialogDate();
                break;


            case R.id.ratingPeopleHolder:
                if (Util.isLogin(getApplicationContext(), this)) {
                    DaggerHotelDetailComponent.builder()
                            .netComponent(((App) getApplicationContext()).getNetComponent())
                            .hotelDetailModule(new HotelDetailModule(this, this, this))
                            .build().inject(this);
                    SendParamUser ss = new SendParamUser(Util.getUseRIdFromShareprefrence(getApplicationContext()), Util.getTokenFromSharedPreferences(getApplicationContext()), "lodging", resultLodgingHotelDetail.getLodgingId());
                    commentPresenter.getRate("rateinfo", ss, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                }
                break;

            case R.id.commentHolder:
                if (Util.isLogin(getApplicationContext(), this)) {
                    DaggerHotelDetailComponent.builder()
                            .netComponent(((App) getApplicationContext()).getNetComponent())
                            .hotelDetailModule(new HotelDetailModule(this, this, this))
                            .build().inject(this);
                    commentPresenter.getCommentList("pagecomments", resultLodgingHotelDetail.getLodgingId(), "lodging", "0");
                }

                break;
            case R.id.cameraHolder:
                if (Util.isLogin(getApplicationContext(), this)) {

                    App.getInstance().prepareDirectories();

                    if (Build.VERSION.SDK_INT < 23) {
                        commentPresenter.selectImage(ReservationHotelDetailActivity.this);

                    } else {
                        if (App.checkGroupPermissions(App.STORAGE_PERMISSIONS)) {
                            commentPresenter.selectImage(ReservationHotelDetailActivity.this);
                        } else {
                            requestPermissions(App.STORAGE_PERMISSIONS, 5);
                        }
                    }
                }
                break;
            case R.id.img:

                commentPresenter.getImages("images", resultLodgingHotelDetail.getLodgingId(), "lodging");


                break;
            case R.id.img_magnifier_foreground:
                Intent intentSearch = new Intent(ReservationHotelDetailActivity.this, GlobalSearchActivity.class);
                startActivity(intentSearch);
                break;

            case R.id.roomReservationBtn:
//                showProgress();
                hotelDetailPresenter.getResultLodgingRoomList("room", String.valueOf(resultLodgingHotelDetail.getLodgingId()), "", "");
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
            case R.id.addHolder:
                if (Util.isLogin(getApplicationContext(), this)) {
                    dynamicItineraryPresenter.getDynamicItineraryList(new SendParamUsetToGetItinerary(Util.getUseRIdFromShareprefrence(getApplicationContext()), Util.getTokenFromSharedPreferences(getApplicationContext()), "lodging", resultLodgingHotelDetail.getLodgingId(), ""), Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                }
                break;
        }
    }

    private void showDialogDate() {
        CustomDialogDate customDialogDate = new CustomDialogDate(this);
        customDialogDate.show();
        customDialogDate.setDialogDateResult(new CustomDialogDate.OnDialogDatePick() {
            @Override
            public void finish(Date result) {
                startOfTravel = result;
                txtDateCheckIn.setText(Util.persianNumbers(Utils.getSimpleDate(result)));
            }
        });
    }

    private void OnClickedIntrestedWidget(String gType, String gValue, ImageView imageView) {
        if (!Util.getUseRIdFromShareprefrence(getApplicationContext()).isEmpty()) {
            commentPresenter.getInterest("widget", Util.getUseRIdFromShareprefrence(getApplicationContext()), "1", "lodging", resultLodgingHotelDetail.getLodgingId(), gType, gValue, Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
        } else {
            Log.e("user is not login", "error");
            Toast.makeText(getApplicationContext(), "شما به حساب کاربری خود وارد نشده اید", Toast.LENGTH_LONG).show();
        }
    }

    private void translateDown() {

        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(ratingHolder, "translationY", interestingLayout.getHeight()),
                ObjectAnimator.ofFloat(GroupHolder, "translationY", interestingLayout.getHeight()),
                ObjectAnimator.ofFloat(triangleShowAttraction, "translationY", -55));
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
        ratingHolderFlag = true;

    }

    private void translateUp() {
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(ratingHolder, "translationY", 0),
                ObjectAnimator.ofFloat(GroupHolder, "translationY", 0),
                ObjectAnimator.ofFloat(triangleShowAttraction, "translationY", 0));
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
        ratingHolderFlag = false;
    }

    private void animWaiting(ImageView image) {
        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setRepeatCount(5);
        rotate.setDuration(5000);
        rotate.setInterpolator(new LinearInterpolator());
        image.startAnimation(rotate);
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                translateUp();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void getInterestResult(String uid, String nid, String gvalue, String gtype) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASEURL)
                .client(setHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getJsonInterface getJsonInterface = retrofit.create(server.getJsonInterface.class);
//        "api-com.iranplanner.tourism.iranplanner.di.data.php?action=widget&uid=792147600796866&cid=1&ntype=itinerary&nid=21905&gtype=bookmark&gvalue=1"
        Call<InterestResult> call = getJsonInterface.getInterest("widget", uid, Util.getTokenFromSharedPreferences(getApplicationContext()), "attraction", nid, gtype, gvalue);
        call.enqueue(new Callback<InterestResult>() {
            @Override

            public void onResponse(Call<InterestResult> call, Response<InterestResult> response) {

                if (response.body() != null) {
                    InterestResult jsonResponse = response.body();
//                    ResultData resultData = jsonResponse.getResultData();
                    //// TODO: 14/02/2017
                    rotate.setRepeatCount(0);
                    checkWhichImageIntrested(rotateImage);
//                    bookmarkImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_bookmark_pink));
                } else {
                    Log.e("Responce body", "null");
                    dismissProgress();
                }
            }

            @Override
            public void onFailure(Call<InterestResult> call, Throwable t) {
                dismissProgress();
            }
        });

    }

    @Override
    public void showComments(ResultCommentList resultCommentList) {
        List<ResultComment> resultComments = resultCommentList.getResultComment();
        Intent intent = new Intent(ReservationHotelDetailActivity.this, CommentListActivity.class);
        intent.putExtra("resultComments", (Serializable) resultComments);
        intent.putExtra("lodgingData", (Serializable) resultLodgingHotelDetail);
        intent.putExtra("nextOffset", resultCommentList.getStatistics().getOffsetNext().toString());
        intent.putExtra("fromWhere", "Lodging");
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
        List<ResultWidget> resultWidget = resultWidgetFull.getResultWidget();
        setWidgetValue(resultWidget);
    }

    private void setWidgetValue(List<ResultWidget> resultWidget) {

        if (resultWidget.get(0).getWidgetLikeValue() != null) {
            LikeValue = resultWidget.get(0).getWidgetLikeValue();
        }
        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 1) {
            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
            rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));
        } else if (resultWidget.get(0).getWidgetLikeValue() == null || resultWidget.get(0).getWidgetLikeValue() == 0) {
            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_off));
        }

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

    public void showProgress() {
        progressDialog = Util.showProgressDialog(getApplicationContext(), "لطفا منتظر بمانید", ReservationHotelDetailActivity.this);
    }

    public void dismissProgress() {
        Util.dismissProgress(progressDialog);
    }

    @Override
    public void showDynamicItineraryList(MyItineraryList myItineraryList) {
        Intent intentAdd = new Intent(ReservationHotelDetailActivity.this, AddNewDynamicItinerary.class);
        if (myItineraryList != null && myItineraryList.getResultItnListUser().size() > 0) {
            intentAdd.putExtra("ResultItnListUser", (Serializable) myItineraryList.getResultItnListUser());
            intentAdd.putExtra("nid", resultLodgingHotelDetail.getLodgingId());
        }
        startActivity(intentAdd);
    }

    @Override
    public void confirmationAddDynamicItinerary(MyItineraryAdd myItineraryAdd) {

    }

    @Override
    public void confirmationAddDynamicPosition(ResultPositionAddItinerary resultPositionAddItinerary) {

    }

    @Override
    public void showResultEditDynamicItinerary(ResultEditDynamicItinerary resultEditDynamicItinerary) {

    }

    @Override
    public void showMoreImages(ResultImageList resultImageList) {
        if (resultImageList.getResultImages().size() > 0) {
            Intent intent = new Intent(ReservationHotelDetailActivity.this, GridImageActivity.class);
            intent.putExtra("ResultImagesList", (Serializable) resultImageList.getResultImages());
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "عکسی برای نمایش وجود ندارد", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setImageUri(Uri uri) {
        mImageUri = uri;
    }

    @Override
    public void setLodgingRoomList(ResultLodgingRoomList resultLodgingRoomList) {
        if (resultLodgingRoomList != null && resultLodgingRoomList.getResultRoom().size() > 0) {
            List<ResultRoom> ResultRooms = resultLodgingRoomList.getResultRoom();
            Intent intent = new Intent(getApplicationContext(), ShowRoomActivity.class);
            intent.putExtra("ResultRooms", (Serializable) ResultRooms);
            intent.putExtra("resultLodgingHotelDetail", (Serializable) resultLodgingHotelDetail);
            intent.putExtra("startOfTravel", startOfTravel);
            intent.putExtra("durationTravel", durationTravel);
            intent.putExtra("hotelName", resultLodgingHotelDetail.getLodgingName());
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "اتاق ناموجود", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void showAttractionDetail(ShowAtractionDetailMore showAttractionFull) {

    }

    @Override
    public void ShowAttractionLists(ShowAttractionListMore getAttractionList) {

    }

    private OkHttpClient setHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    private void checkWhichImageIntrested(String imageView) {

//        String im = imageView;
//        switch (im) {
//            case "bookmarkImg":
//                bookmarkImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_bookmark_pink));
//                break;
//            case "nowVisitedImg":
//                nowVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_done_pink));
//                doneImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_done_pink));
//                break;
//            case "beftorVisitedImg":
//                beftorVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_done_pink));
//                doneImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_done_pink));
//                break;
//            case "dislikeImg":
//                dislikeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_pink));
//                rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_pink));
//                break;
//            case "commentImg":
//                commentImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_pink));
//                rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_pink));
//                break;
//            case "likeImg":
//                likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_air));
//                rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_air));
//                break;
//            case "wishImg":
//                wishImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_wish_pink));
//                break;
//            default:
//                break;
//        }
    }

    @Override
    public void onSelectImage(Uri uri) {
        mImageUri = uri;
    }

    @Override
    public void onCropImage(Bitmap bitmap) {
        img.setImageBitmap(bitmap);
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
                    SendParamUser ss = new SendParamUser(Util.getUseRIdFromShareprefrence(getApplicationContext()), Util.getTokenFromSharedPreferences(getApplicationContext()), "lodging", resultLodgingHotelDetail.getLodgingId(), new RateParam(String.valueOf(ratingBar1.getRating()), String.valueOf(ratingBar2.getRating()), String.valueOf(ratingBar3.getRating()), String.valueOf(ratingBar4.getRating())));
                    commentPresenter.rateSend("rate", ss, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                    break;
            }
            dismiss();
        }
    }
}
