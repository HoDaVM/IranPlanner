package com.iranplanner.tourism.iranplanner.ui.activity.hotelDetails;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.iranplanner.tourism.iranplanner.ui.activity.MapFullActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMorePresenter;
import com.iranplanner.tourism.iranplanner.ui.activity.showRoom.ShowRoomActivity;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import entity.InterestResult;
import entity.ItineraryLodgingCity;
import entity.ResultCommentList;
import entity.ResultData;
import entity.ResultLodging;
import entity.ResultLodgingRoomList;
import entity.ResultRoom;
import entity.ShowAtractionDetailMore;
import entity.ShowAttractionMoreList;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import server.Config;
import server.getJsonInterface;
import tools.CustomDialogDate;
import tools.CustomDialogNumberPicker;
import tools.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by h.vahidimehr on 28/02/2017.
 */

public class ReservationHotelDetailActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, AttractionListMorePresenter.View {

    private GoogleMap mMap;
    //    ResultItineraryAttraction attraction;
    TextView attractionName, attractionPlace, textTimeDuration, textEntranceFee, attractionType, textBody;
    Marker marker;
    protected CTouchyWebView contentFullDescription;
    ImageView imageTypeAttraction;
    ImageView imgHotel;
    SupportMapFragment mapFragment;
    Boolean showMore = true;
    String myData;
    TextView txtDateCheckIn, txtOk, MoreInoText, txtHotelType, txtHotelName, txtAddress, txtDate, txtDurationHotel;
    RelativeLayout TypeDurationHolder, holderDate, ratingHolder, GroupHolder, interestingLayout, VisitedLayout, LikeLayout, changeDateHolder;
    LinearLayout rateHolder, bookmarkHolder, doneHolder, nowVisitedHolder, beftorVisitedHolder, likeHolder, okHolder, dislikeHolder;
    ImageView bookmarkImg, doneImg, dislikeImg, okImg, likeImg, rateImg, beftorVisitedImg, nowVisitedImg, wishImg, triangleShowAttraction;
    boolean ratingHolderFlag = false;
    String rotateImage;
    RotateAnimation rotate;
    ResultLodging resultLodgingHotelDetail;
    Date startOfTravel;
    int durationTravel;
    Button roomReservationBtn;
    private String todayDate;
    private ProgressDialog progressDialog;

    @Inject
    AttractionListMorePresenter attractionListMorePresenter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void findView() {

        Log.e("this tag", ReservationHotelDetailActivity.class.getSimpleName());

//        setContentView(R.layout.activity_reservation_hotel_detail);
        setContentView(R.layout.fragment_reservation);
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
        attractionName = (TextView) findViewById(R.id.attractionName);
        attractionPlace = (TextView) findViewById(R.id.attractionPlace);
        textTimeDuration = (TextView) findViewById(R.id.textTimeDuration);
        textEntranceFee = (TextView) findViewById(R.id.textEntranceFee);
        attractionType = (TextView) findViewById(R.id.attractionType);
        imageTypeAttraction = (ImageView) findViewById(R.id.imageTypeAttraction);
        imgHotel = (ImageView) findViewById(R.id.imgHotel);
        roomReservationBtn = (Button) findViewById(R.id.roomReservationBtn);
        holderDate = (RelativeLayout) findViewById(R.id.holderDate);

        rateHolder = (LinearLayout) findViewById(R.id.rateHolder);
        doneHolder = (LinearLayout) findViewById(R.id.doneHolder);
        nowVisitedHolder = (LinearLayout) findViewById(R.id.nowVisitedHolder);
        beftorVisitedHolder = (LinearLayout) findViewById(R.id.beftorVisitedHolder);
        dislikeHolder = (LinearLayout) findViewById(R.id.dislikeHolder);
        okHolder = (LinearLayout) findViewById(R.id.okHolder);
        likeHolder = (LinearLayout) findViewById(R.id.likeHolder);
        bookmarkHolder = (LinearLayout) findViewById(R.id.bookmarkHolder);
        ratingHolder = (RelativeLayout) findViewById(R.id.ratingHolder);
        GroupHolder = (RelativeLayout) findViewById(R.id.GroupHolder);
        interestingLayout = (RelativeLayout) findViewById(R.id.interestingLayout);
        VisitedLayout = (RelativeLayout) findViewById(R.id.VisitedLayout);
        LikeLayout = (RelativeLayout) findViewById(R.id.LikeLayout);
        changeDateHolder = (RelativeLayout) findViewById(R.id.changeDateHolder);
        txtOk = (TextView) findViewById(R.id.txtOk);
        MoreInoText = (TextView) findViewById(R.id.MoreInoText);
        bookmarkImg = (ImageView) findViewById(R.id.bookmarkImg);
        doneImg = (ImageView) findViewById(R.id.doneImg);
        dislikeImg = (ImageView) findViewById(R.id.dislikeImg);
        okImg = (ImageView) findViewById(R.id.okImg);
        likeImg = (ImageView) findViewById(R.id.likeImg);
        rateImg = (ImageView) findViewById(R.id.rateImg);
        beftorVisitedImg = (ImageView) findViewById(R.id.beftorVisitedImg);
        nowVisitedImg = (ImageView) findViewById(R.id.nowVisitedImg);
        wishImg = (ImageView) findViewById(R.id.wishImg);
        triangleShowAttraction = (ImageView) findViewById(R.id.triangleShowAttraction);
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
                    .into(imgHotel);

        } else {
            Glide.clear(imgHotel);
            imgHotel.setImageDrawable(null);
        }
    }

    //    private void setAttractionTypeImage(){
//        if (attraction.getAttarctionItineraryTypeId().equals("2930")) {
//            imageTypeAttraction.setImageDrawable(getResources().getDrawable(R.mipmap.ic_religious));
//        } else if (attraction.getAttarctionItineraryTypeId().equals("2931")) {
//            imageTypeAttraction.setImageDrawable(getResources().getDrawable(R.mipmap.ic_nature));
//        } else if (attraction.getAttarctionItineraryTypeId().equals("2932")) {
//            imageTypeAttraction.setImageDrawable(getResources().getDrawable(R.mipmap.ic_history));
//        } else if (attraction.getAttarctionItineraryTypeId().equals("2933")) {
//            imageTypeAttraction.setImageDrawable(getResources().getDrawable(R.mipmap.ic_entertainment));
//        }
//    }
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

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("KILL");
        registerReceiver(receiver, filter);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findView();
        overrideFont();
        getExtras();

        txtHotelName.setText(resultLodgingHotelDetail.getLodgingName());
        txtHotelType.setText("نوع مرکز اقامتی: " + resultLodgingHotelDetail.getLodgingTypeTitle());
        txtAddress.setText(resultLodgingHotelDetail.getLodgingAddress());
//        txtDate.setText(Utils.getSimpleDate(startOfTravel));
//        txtDuration.setText(Utils.persianNumbers(String.valueOf(durationTravel)) + " شب");
        roomReservationBtn.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile.ttf");

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);

        collapsingToolbarLayout.setTitle(resultLodgingHotelDetail.getLodgingName());

        holderDate.setOnClickListener(this);
        TypeDurationHolder.setOnClickListener(this);

        setImageHolder();

//        interestingLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int width = interestingLayout.getWidth();
//                int height = interestingLayout.getHeight();
//                if (width > 0 && height > 0) {
//                    VisitedLayout.setVisibility(View.INVISIBLE);
//                    LikeLayout.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
        mapFragment.getMapAsync(this);
//        MoreInoText.setOnClickListener(this);
//        ratingHolder.setOnClickListener(this);
//        rateHolder.setOnClickListener(this);
//        doneHolder.setOnClickListener(this);
//        likeImg.setOnClickListener(this);
//        okImg.setOnClickListener(this);
//        dislikeImg.setOnClickListener(this);
//        nowVisitedImg.setOnClickListener(this);
//        wishImg.setOnClickListener(this);
//        beftorVisitedImg.setOnClickListener(this);
//        bookmarkHolder.setOnClickListener(this);

    }

//    private void setInterestResponce(List<ResultWidget> resultWidget) {
//        if (resultWidget.get(0).getWidgetBookmarkValue() != null && resultWidget.get(0).getWidgetBookmarkValue() == 1) {
//            bookmarkImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_bookmark_pink));
//        }
//        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 1) {
//            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_air));
//            rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_air));
//            okImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_grey_));
//            dislikeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_grey_));
//        }
//        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 2) {
//            okImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_pink));
//            rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_pink));
//            dislikeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_grey_));
//            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_grey));
//        }
//        if (resultWidget.get(0).getWidgetLikeValue() != null && resultWidget.get(0).getWidgetLikeValue() == 3) {
//            dislikeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_pink));
//            rateImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_dislike_pink));
//            likeImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_grey));
//            okImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_grey_));
//        }
//        if (resultWidget.get(0).getWidgetVisitedValue() != null && resultWidget.get(0).getWidgetVisitedValue() == 1) {
//            nowVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_done_pink));
//            doneImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_done_pink));
//            beftorVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_done_grey));
//        }
//        if (resultWidget.get(0).getWidgetVisitedValue() != null && resultWidget.get(0).getWidgetVisitedValue() == 2) {
//            beftorVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_done_pink));
//            doneImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_done_pink));
//            nowVisitedImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_done_grey));
//        }
//        if (resultWidget.get(0).getWidgetWishValue() != null && resultWidget.get(0).getWidgetWishValue() == 1) {
//            wishImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_wish_pink));
//        }
//
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Log.e("taggg", "on support back pressed");
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);

        MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker));
        Double lan = resultLodgingHotelDetail.getLodgingPosLat();
        Double lon = resultLodgingHotelDetail.getLodgingPosLong();

        marker = mMap.addMarker(markerOptions
                .position(new LatLng(lan, lon))
                .title(resultLodgingHotelDetail.getLodgingName())
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lan, lon), 15.0f));

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
                startActivity(intent);
            }
        });
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
            case R.id.ratingHolder:
                if (ratingHolderFlag) {
                    translateUp();
                }
                break;
            case R.id.holderDate:

                showDialogDate();

                break;
            case R.id.rateHolder:
                if (!ratingHolderFlag) {
                    VisitedLayout.setVisibility(View.INVISIBLE);
                    LikeLayout.setVisibility(View.VISIBLE);
                    rotateImage = "rateImg";
                    translateDown();
                    break;
                } else {
                    translateUp();
                    break;
                }

            case R.id.doneHolder:
                if (!ratingHolderFlag) {
                    LikeLayout.setVisibility(View.INVISIBLE);
                    VisitedLayout.setVisibility(View.VISIBLE);
                    rotateImage = "doneImg";
                    translateDown();
                    break;
                } else {
                    translateUp();
                    break;
                }

            case R.id.likeImg:
                rotateImage = "likeImg";
                if (!Util.getUseRIdFromShareprefrence(getApplicationContext()).isEmpty()) {
                    animWaiting(likeImg);
                    String uid = Util.getUseRIdFromShareprefrence(getApplicationContext());

                } else {
                    Log.e("user is not login", "error");
                    Toast.makeText(getApplicationContext(), "شما به حساب کاربری خود وارد نشده اید", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.okImg:
                rotateImage = "okImg";
                if (!Util.getUseRIdFromShareprefrence(getApplicationContext()).isEmpty()) {
                    animWaiting(okImg);
                    String uid = Util.getUseRIdFromShareprefrence(getApplicationContext());

                } else {
                    Log.e("user is not login", "error");
                    Toast.makeText(getApplicationContext(), "شما به حساب کاربری خود وارد نشده اید", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.dislikeImg:
                rotateImage = "dislikeImg";
                if (!Util.getUseRIdFromShareprefrence(getApplicationContext()).isEmpty()) {
                    animWaiting(dislikeImg);
                    String uid = Util.getUseRIdFromShareprefrence(getApplicationContext());

                } else {
                    Log.e("user is not login", "error");
                    Toast.makeText(getApplicationContext(), "شما به حساب کاربری خود وارد نشده اید", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.wishImg:
                rotateImage = "wishImg";
                if (!Util.getUseRIdFromShareprefrence(getApplicationContext()).isEmpty()) {
                    animWaiting(wishImg);
                    String uid = Util.getUseRIdFromShareprefrence(getApplicationContext());
                } else {
                    Log.e("user is not login", "error");
                    Toast.makeText(getApplicationContext(), "شما به حساب کاربری خود وارد نشده اید", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.beftorVisitedImg:
                rotateImage = "beftorVisitedImg";
                if (!Util.getUseRIdFromShareprefrence(getApplicationContext()).isEmpty()) {
                    animWaiting(beftorVisitedImg);
                    String uid = Util.getUseRIdFromShareprefrence(getApplicationContext());
                } else {
                    Log.e("user is not login", "error");
                    Toast.makeText(getApplicationContext(), "شما به حساب کاربری خود وارد نشده اید", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bookmarkHolder:
                rotateImage = "bookmarkImg";
                if (!Util.getUseRIdFromShareprefrence(getApplicationContext()).isEmpty()) {
                    animWaiting(bookmarkImg);
                    String uid = Util.getUseRIdFromShareprefrence(getApplicationContext());

                } else {
                    Log.e("user is not login", "error");
                    Toast.makeText(getApplicationContext(), "شما به حساب کاربری خود وارد نشده اید", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.roomReservationBtn:
                showProgress();
                getLodgingResevationRoom(String.valueOf(resultLodgingHotelDetail.getLodgingId()));
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

    public void getLodgingResevationRoom(String hotelID) {
//        getResultLodgingRoomList
        Retrofit retrofit = new Retrofit.Builder()
                .client(setHttpClient())
                .baseUrl(Config.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getJsonInterface getJsonInterface = retrofit.create(server.getJsonInterface.class);
        Call<ResultLodgingRoomList> callc = getJsonInterface.getResultLodgingRoomList("room", hotelID, "", "");
        callc.enqueue(new Callback<ResultLodgingRoomList>() {
            @Override
            public void onResponse(Call<ResultLodgingRoomList> call, Response<ResultLodgingRoomList> response) {
                Log.e("result of ResultRooms", "true");
                if (response.body() != null) {
                    dismissProgress();
                    ResultLodgingRoomList res = response.body();
                    List<ResultRoom> ResultRooms = res.getResultRoom();
                    Intent intent = new Intent(getApplicationContext(), ShowRoomActivity.class);

                    intent.putExtra("ResultRooms", (Serializable) ResultRooms);
                    intent.putExtra("resultLodgingHotelDetail", (Serializable) resultLodgingHotelDetail);
                    intent.putExtra("startOfTravel", startOfTravel);
                    intent.putExtra("durationTravel", durationTravel);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResultLodgingRoomList> call, Throwable t) {
                Log.e("result of intresting", "false");
            }
        });
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
                    ResultData resultData = jsonResponse.getResultData();
                    //// TODO: 14/02/2017
                    rotate.setRepeatCount(0);
                    checkWhichImageIntrested(rotateImage);
//                    bookmarkImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_bookmark_pink));
                } else {
                    Log.e("Responce body", "null");
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

    public void showProgress() {
        progressDialog = Util.showProgressDialog(getApplicationContext(), "لطفا منتظر بمانید", ReservationHotelDetailActivity.this);
    }

    public void dismissProgress() {
        Util.dismissProgress(progressDialog);
    }

    @Override
    public void ShowAttractionLists(ShowAttractionMoreList showAttractionList) {

    }

    @Override
    public void showAttractionDetail(ShowAtractionDetailMore showAttractionFull) {

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
//            case "okImg":
//                okImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_ok_pink));
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

}
