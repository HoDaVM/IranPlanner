package com.iranplanner.tourism.iranplanner.ui.activity.moreItemItinerary;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
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
import com.coinpany.core.android.widget.calendar.dateutil.PersianCalendar;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.di.model.App;

import com.iranplanner.tourism.iranplanner.showcaseview.CustomShowcaseView;
import com.iranplanner.tourism.iranplanner.ui.activity.MapFullActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.hotelReservationListOfCity.ReservationListActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.showAttraction.ShowAttractionActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.StandardActivity;
import com.iranplanner.tourism.iranplanner.ui.activity.comment.CommentListActivity;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import entity.InterestResult;
import entity.ItineraryLodgingCity;
import entity.ItineraryPercentage;
import entity.ResultComment;
import entity.ResultCommentList;
import entity.ResultData;
import entity.ResultItinerary;
import entity.ResultItineraryAttraction;
import entity.ResultItineraryAttractionDay;
import entity.ResultItineraryAttractionList;
import entity.ResultWidget;

import entity.ResultWidgetFull;


import entity.ShowAttractionListItinerary;
import tools.Constants;

import tools.Util;

import tools.widget.PersianDatePicker;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MoreItemItineraryActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        View.OnClickListener,
        ItineraryContract.View {

    @Inject
    ItineraryPresenter itineraryPresenter;

    private CollapsingToolbarLayout collapsingToolbar;
    private GoogleMap mMap;
    private ArrayList<LatLng> MarkerPoints;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private ResultItinerary itineraryData;
    private String itineraryId;
    private List<ResultItineraryAttraction> itineraryActionList;
    private List<ResultItineraryAttractionDay> resultItineraryAttractionDays;
    private CircularProgressBar progress1;
    private CircularProgressBar progress2;
    private CircularProgressBar progress3;
    private TextView textTpeTravel1, textTpeTravel2, textTpeTravel3;
    private List<Marker> markers;
    private TextView textPercentage1, textPercentage2, textPercentage3;
    private TextView txtItinerary_attraction_type, txtDate;
    private ProgressDialog progressDialog;
    private ImageView itinerary_attraction_type_more;
    protected CTouchyWebView contentFullDescription;
    private TextView txtItinerary_attraction_Difficulty;
    private TextView txtItinerary_count_attraction;
    private ImageView imgItineraryListMore;
    private TextView itineraryDuration;
    private TextView fromCityName, toCityName;
    private TextView showItinerys;
    private TextView txtOk, MoreInoText;
    private LinearLayout changeDateHolder;
    private String myData;
    private Boolean showMore = true;
    private ViewPager toolsPager;
    private Date startOfTravel;
    private Button showReservation, showItinerary;
    int BookmarkValue;
    int LikeValue;
    int VisitedValue;
    int WishValue;
    DaggerItineraryComponent.Builder builder;

    private void findView() {

        txtItinerary_attraction_Difficulty = (TextView) findViewById(R.id.txtItinerary_attraction_Difficulty);
        toolsPager = (ViewPager) findViewById(R.id.toolsPager);
        txtItinerary_attraction_type = (TextView) findViewById(R.id.txtItinerary_attraction_type);
        txtItinerary_count_attraction = (TextView) findViewById(R.id.txtItinerary_count_attraction);
        showReservation = (Button) findViewById(R.id.showReservation);
        itinerary_attraction_type_more = (ImageView) findViewById(R.id.itinerary_attraction_type_more);
        showItinerary = (Button) findViewById(R.id.showItinerary1);
        textTpeTravel1 = (TextView) findViewById(R.id.textTpeTravel1);
        textTpeTravel2 = (TextView) findViewById(R.id.textTpeTravel2);
        textTpeTravel3 = (TextView) findViewById(R.id.textTpeTravel3);
        progress1 = (CircularProgressBar) findViewById(R.id.progress1);
        progress2 = (CircularProgressBar) findViewById(R.id.progress2);
        progress3 = (CircularProgressBar) findViewById(R.id.progress3);
        textPercentage1 = (TextView) findViewById(R.id.textPercentage1);
        textPercentage2 = (TextView) findViewById(R.id.textPercentage2);
        textPercentage3 = (TextView) findViewById(R.id.textPercentage3);
        txtDate = (TextView) findViewById(R.id.txtDate);
        changeDateHolder = (LinearLayout) findViewById(R.id.changeDateHolder);
        imgItineraryListMore = (ImageView) findViewById(R.id.imgItineraryListMore);
        contentFullDescription = (CTouchyWebView) findViewById(R.id.contentFullDescription);
        txtOk = (TextView) findViewById(R.id.txtOk);
        MoreInoText = (TextView) findViewById(R.id.MoreInoText);
        AppBarLayout appBar = (AppBarLayout) findViewById(R.id.app_bar);
        ViewCompat.setElevation(appBar, Util.dpToPx(this, 28));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_itinerary_item_more);
        findView();
        BookmarkValue = 0;
        LikeValue = 0;
        VisitedValue = 0;
        WishValue = 0;
        showToolViewPager();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itineraryData = (ResultItinerary) bundle.getSerializable("itineraryData");
        String duration = bundle.getString("duration");
        List<ResultWidget> resultWidget = (List<ResultWidget>) bundle.getSerializable("resultWidget");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        String title = "برنامه سفر " + itineraryData.getItineraryFromCityName() + " به " + itineraryData.getItineraryToCityName();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        collapsingToolbarLayout.setTitle(title);
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white_));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white_));

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile.ttf");

        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);

        setTypeOfTravel();
        myData = itineraryData.getItineraryBody();
        setWebViewContent(getShowMoreString(myData));
        SetPercentage();
        setImageView();

        //set current date
        long time = System.currentTimeMillis();
        txtDate.setText(Utils.getSimpleDateMilli(time));
        startOfTravel = new Date(time);
        txtItinerary_attraction_Difficulty.setText(itineraryData.getItineraryDifficulty().getItineraryDifficultyGroup());
        txtItinerary_count_attraction.setText(Util.persianNumbers(itineraryData.getItineraryCountAttraction()) + " مکان دیدنی");

        changeDateHolder.setOnClickListener(this);
        itineraryId = itineraryData.getItineraryId();
        showItinerary.setOnClickListener(this);
        MoreInoText.setOnClickListener(this);
        showReservation.setOnClickListener(this);

        //-------------------map
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Initializing
        MarkerPoints = new ArrayList<>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        toolsPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("position", position + "");

            }

            @Override
            public void onPageSelected(int position) {
//                if (position == 2) {
//                    Intent intent = new Intent(MoreItemItineraryActivity.this, GridActivity.class);
//                    startActivity(intent);
//                }
//                if (position == 0) {
//                    Intent intent = new Intent(MoreItemItineraryActivity.this, GridActivity.class);
//                    startActivity(intent);
//                }
//                if (position == 1) {
//                    Intent intent = new Intent(MoreItemItineraryActivity.this, GridActivity.class);
//                    startActivity(intent);
//                }
                Log.e("position", position + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("position", state + "");
            }

        });

        builder = DaggerItineraryComponent.builder()
                .netComponent(((App) getApplicationContext()).getNetComponent())
                .itineraryModule(new ItineraryModule(this));
        builder.build().inject(this);
        itineraryPresenter.getWidgetResult("nodeuser", itineraryData.getItineraryId(), Util.getUseRIdFromShareprefrence(getApplicationContext()), "itinerary", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
//        if (!Boolean.parseBoolean(Util.getFromPreferences(Constants.PREF_SHOWCASE_PASSED_MOREITEMITINERARY, "false", false, getApplicationContext()))) {
        setShowCase();
//        }
    }

    private Menu menu = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_itinerary_more, menu);
        this.menu = menu;
        MenuItem menuItem = menu.getItem(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItineraryComment:
//            here comes the comment code section
                showProgressDialog();
                builder.build().inject(this);
                itineraryPresenter.getItineraryCommentList("pagecomments", itineraryId, "itinerary", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                break;
        }
        return true;
    }

    private boolean isFav = false;

    private boolean toggleFav() {
        //here comes the api code
        return isFav = !isFav;
    }

    private void showToolViewPager() {
//        ShowTavelToolsAdapter showTavelToolsAdapter = new ShowTavelToolsAdapter(getSupportFragmentManager());
        ShowTavelToolsAdapter showTavelToolsAdapter = new ShowTavelToolsAdapter(getApplicationContext(), MoreItemItineraryActivity.this, itineraryId);
        toolsPager.setAdapter(showTavelToolsAdapter);
        toolsPager.setCurrentItem(2);
        toolsPager.setClipToPadding(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.commentHolder:
                showProgressDialog();
                builder.build().inject(this);
                itineraryPresenter.getItineraryCommentList("pagecomments", itineraryId, "itinerary", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                break;

            case R.id.showReservation:
                Intent intent = new Intent(this, ReservationListActivity.class);
                intent.putExtra("itineraryData", (Serializable) itineraryData);
                intent.putExtra("startOfTravel", startOfTravel);
                startActivity(intent);
                break;

            case R.id.changeDateHolder:
                CustomDialogTravel cdd = new CustomDialogTravel(this);
                cdd.show();
                break;

            case R.id.MoreInoText:
                if (showMore) {
                    setWebViewContent(myData);
                    MoreInoText.setText("مطلب کوتاه");
                    showMore = false;
                } else {
                    setWebViewContent(getShowMoreString(myData));
                    MoreInoText.setText("بیشتر بخوانید");
                    showMore = true;
                }

                break;
            case R.id.showItinerary1:
                showProgressDialog();
                builder.build().inject(this);
//                itineraryPresenter.getItineraryAttractionList("attraction", "fa", itineraryId,Util.getTokenFromSharedPreferences(getApplicationContext()),Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
                itineraryPresenter.getItineraryAttractionListDay("attractionday", "fa", itineraryId, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));

                break;
        }
    }

    private void OnClickedIntrestedWidget(String gType, String gValue, ImageView imageView) {
        if (!Util.getUseRIdFromShareprefrence(getApplicationContext()).isEmpty()) {
//            itineraryPresenter.doWaitingAnimation(imageView);
            String user = Util.getUseRIdFromShareprefrence(getApplicationContext());
            String ss = Util.getAndroidIdFromSharedPreferences(getApplicationContext());
            itineraryPresenter.getInterest("widget", user, "1", "itinerary", itineraryId, gType, gValue, ss);
        } else {
            Log.e("user is not login", "error");
            Toast.makeText(getApplicationContext(), "شما به حساب کاربری خود وارد نشده اید", Toast.LENGTH_LONG).show();
        }
    }

    private String getShowMoreString(String myData) {
        int count = 0;
        int position = 0;
        for (count = 0; count < 40; count++) {
            position = myData.indexOf(" ", position + 1);
        }
        return myData.substring(0, position) + "...";
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

    private void setTypeOfTravel() {
        if (itineraryData.getItineraryTransportId().equals("2830")) {
            itinerary_attraction_type_more.setImageDrawable(getResources().getDrawable(R.mipmap.ic_air_gret));
            txtItinerary_attraction_type.setText("هوایی");
        } else if (itineraryData.getItineraryTransportId().equals("2831")) {
            itinerary_attraction_type_more.setImageDrawable(getResources().getDrawable(R.mipmap.ic_train_grey));
            txtItinerary_attraction_type.setText("ترن");
        } else if (itineraryData.getItineraryTransportId().equals("2829")) {
            itinerary_attraction_type_more.setImageDrawable(getResources().getDrawable(R.mipmap.ic_road_grey));
            txtItinerary_attraction_type.setText("جاده ای");
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(MoreItemItineraryActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("لطفا منتظر بمانید");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void setImageView() {
        if (itineraryData.getItineraryImgUrl() != null) {
            String url = itineraryData.getItineraryImgUrl();
            Glide.with(this).load(url).into(imgItineraryListMore);
        }
    }

    private void SetPercentage() {
        ArrayList<String> addtypes = new ArrayList<>();
        ArrayList<String> addtypesper = new ArrayList<>();
        ArrayList<String> deleteType = new ArrayList<>();
        ArrayList<String> deleteTypeper = new ArrayList<>();
        deleteType.add("مذهبی");
        deleteType.add("تاریخی و فرهنگی");
        deleteType.add("طبیعی");
        deleteType.add("تفریحی و ورزشی");
        deleteTypeper.add("0");
        deleteTypeper.add("0");
        deleteTypeper.add("0");
        deleteTypeper.add("0");
        for (ItineraryPercentage p : itineraryData.getItineraryPercentage()) {
            addtypes.add(p.getItineraryAttractionType());
            addtypesper.add(p.getItineraryAttractionTypePercentage());
            if (deleteType.contains(p.getItineraryAttractionType()))
                deleteType.remove(p.getItineraryAttractionType());
        }
        if (addtypes.size() <= 3)
            for (int i = addtypes.size(); i < 3; i++) {
                addtypes.add(deleteType.get(i - 1));
                addtypesper.add(deleteTypeper.get(i - 1));
            }


        textTpeTravel1.setText(addtypes.get(0));
        textTpeTravel2.setText(addtypes.get(1));
        textTpeTravel3.setText(addtypes.get(2));
        progress1.setProgress(Float.valueOf(addtypesper.get(0)));
        progress2.setProgress(Float.valueOf(addtypesper.get(1)));
        progress3.setProgress(Float.valueOf(addtypesper.get(2)));
        float perc1 = Float.valueOf(addtypesper.get(0));
        float perc2 = Float.valueOf(addtypesper.get(1));
        float perc3 = Float.valueOf(addtypesper.get(2));

        textPercentage1.setText((Util.persianNumbers(String.valueOf((int) perc1)) + "%"));
        textPercentage2.setText((Util.persianNumbers(String.valueOf((int) perc2)) + "%"));
        textPercentage3.setText((Util.persianNumbers(String.valueOf((int) perc3)) + "%"));
    }


    //camera zoom to all of points
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        try {
            if (hasFocus == true) {
                RelativeLayout myLinearLayout = (RelativeLayout) findViewById(R.id.mapHolder);
                int width = myLinearLayout.getWidth();
                int height = myLinearLayout.getHeight();
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                if (markers != null) {
                    for (Marker marker : markers) {
                        builder.include(marker.getPosition());
                    }
                }

                LatLngBounds bounds = builder.build();
//        int width = getResources().getDisplayMetrics().widthPixels;
//        int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                mMap.animateCamera(cu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "اجازه دسترسی داده نشد", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }


    @Override
    public void showAttraction(ResultItineraryAttractionList resultItineraryAttractionList) {
        itineraryActionList = resultItineraryAttractionList.getResultItineraryAttraction();
        progressDialog.dismiss();
        Intent intent = new Intent(getApplicationContext(), ShowAttractionActivity.class);
        intent.putExtra("ResultItineraryAttraction", (Serializable) itineraryActionList);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
//        menu.findItem(R.id.menuItineraryFav).setIcon(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_off));

        Log.e("error", " in get attraction list" + message);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        if (message.contains("Unable to resolve host ") || message.contains("Software caused connection abort")) {
            Toast.makeText(getApplicationContext(), "عدم دسترسی به اینترنت", Toast.LENGTH_LONG).show();
        }
//        if (message.contains("HTTP 400 BAD REQUEST")) {
//            Toast.makeText(getApplicationContext(), "در این مسیر برنامه سفری یافت نشد", Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    public void showComplete() {
        Log.e("complete", "get attraction list");
        progressDialog.dismiss();
    }

    @Override
    public void showItineraryComment(ResultCommentList resultCommentList, String commentType) {
        List<ResultComment> resultComments = resultCommentList.getResultComment();
        Intent intent = new Intent(MoreItemItineraryActivity.this, CommentListActivity.class);
        intent.putExtra("resultComments", (Serializable) resultComments);
        intent.putExtra("itineraryData", (Serializable) itineraryData);
        intent.putExtra("nextOffset", resultCommentList.getStatistics().getOffsetNext().toString());
        intent.putExtra("fromWhere", commentType);
        startActivity(intent);
        progressDialog.dismiss();
    }

    @Override
    public void setLoadWidget(ResultWidgetFull resultWidgetFull) {
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void setIntrestedWidget(InterestResult InterestResult) {
        if (toggleFav()) {

//            menu.findItem(R.id.menuItineraryFav).setIcon(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_off));
        } else {
//            menu.findItem(R.id.menuItineraryFav).setIcon(getApplicationContext().getResources().getDrawable(R.mipmap.ic_like_on));

        }

        ResultData resultData = InterestResult.getResultData();
        //// TODO: 14/02/2017
//        rotate.setRepeatCount(0);
//        checkWhichImageIntrested(rotateImage);
//                    bookmarkImg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.mipmap.ic_bookmark_pink));

    }

    @Override
    public void showAnimationWhenWaiting() {
//        ratingHolderFlag = itineraryPresenter.doTranslateAnimationUp(ratingHolder, GroupHolder, triangleShowAttraction);
    }

    @Override
    public void showAttractionDay(ShowAttractionListItinerary showAttractionListItinerary) {
        resultItineraryAttractionDays = showAttractionListItinerary.getResultItineraryAttractionDay();
        progressDialog.dismiss();
        Intent intent = new Intent(getApplicationContext(), ShowAttractionActivity.class);
        intent.putExtra("resultItineraryAttractionDays", (Serializable) resultItineraryAttractionDays);
        startActivity(intent);
    }

    @Override
    public void setIntrestValue(InterestResult InterestResult) {

    }

    @Override
    public void removeMarkers() {

    }

    @Override
    public void showMarkerAt(float latitude, float longitude) {

    }

    @Override
    public void showDirectionOnMap(PolylineOptions rectLine) {
        mMap.addPolyline(rectLine).setColor(getApplicationContext().getResources().getColor(R.color.pink));
    }

    public class CustomDialogTravel extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        public TextView yes, no;
        PersianDatePicker persianDatePickr;

        public CustomDialogTravel(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //        bara inke keybord bala nayad
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_date_travel);
            persianDatePickr = (PersianDatePicker) findViewById(R.id.travelDate);
            yes = (TextView) findViewById(R.id.txtOk);
            no = (TextView) findViewById(R.id.txtNo);
            yes.setOnClickListener(this);
            no.setOnClickListener(this);
            startOfTravel = persianDatePickr.getDisplayDate();
            persianDatePickr.setOnDateChangedListener(new PersianDatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(int newYear, int newMonth, int newDay) {
                    txtDate.setText(Utils.getSimpleDate(persianDatePickr.getDisplayDate()));
                    startOfTravel = persianDatePickr.getDisplayDate();
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txtOk:
                    persianDatePickr.getDisplayDate();
                    dismiss();
                    break;
                case R.id.txtNo:
                    dismiss();
                    break;
            }
            dismiss();
        }
    }

    //-------------------------------
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //------------- no zoom
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        DaggerItineraryComponent.builder()
                .netComponent(((App) getApplicationContext()).getGoogleNetComponent())
                .itineraryModule(new ItineraryModule(this))
                .build().inject(this);

        //-----------------
        List<ItineraryLodgingCity> lodgingCities = itineraryData.getItineraryLodgingCity();
        if (MarkerPoints.size() > 1) {
            MarkerPoints.clear();
            mMap.clear();
        }

        markers = new ArrayList<>();
        MarkerOptions options = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker));

        for (ItineraryLodgingCity lodgingCity : lodgingCities) {
            LatLng point = new LatLng(Float.valueOf(lodgingCity.getCityPositionLat()), Float.valueOf(lodgingCity.getCityPositionLon()));
            MarkerPoints.add(point);
            options.position(point);
            markers.add(mMap.addMarker(options));
        }
        if (MarkerPoints.size() >= 2) {
            for (int j = 0; j < MarkerPoints.size() - 1; j++) {
                String origins = MarkerPoints.get(j).latitude + "," + MarkerPoints.get(j).longitude;
                String destination = MarkerPoints.get(j + 1).latitude + "," + MarkerPoints.get(j + 1).longitude;
                itineraryPresenter.getDirection(origins, destination);
            }
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.e("map is ckicked", "true");
                Intent intent = new Intent(getApplicationContext(), MapFullActivity.class);
                List<ItineraryLodgingCity> lodgingCities = itineraryData.getItineraryLodgingCity();
                intent.putExtra("lodgingCities", (Serializable) lodgingCities);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    ShowcaseView showcaseView;
    int counter = 0;

    private void setShowCase() {
        Button customButton = (Button) this.getLayoutInflater().inflate(R.layout.showcase_custom_button, null);
        CustomShowcaseView showcaseDrawer = new CustomShowcaseView(getResources());
        float width = getResources().getDimension(R.dimen.custom_showcase_moreItem_width);
        float height = getResources().getDimension(R.dimen.custom_showcase_moreItem_height);
        showcaseDrawer.customShowcaseSize(width, height);

        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(changeDateHolder))
                .setShowcaseDrawer(showcaseDrawer)
                .blockAllTouches()
                .replaceEndButton(customButton)
                .build();
        Util.saveInPreferences(Constants.PREF_SHOWCASE_PASSED_MOREITEMITINERARY, String.valueOf(true), false, getApplicationContext());
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
                        showcaseView.setShowcase(new ViewTarget(showItinerary), true);
                        showcaseView.setContentText("روی این دکمه کلیک کن تا برنامه بازدیدهای هر روز رو به تفکیک ببینی");
                        showcaseView.setContentTitle("دیدن برنامه روز به روز سفر");
                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                        break;
                    }

                    case 1: {
                        showcaseView.setShowcase(new ViewTarget(showReservation), true);
                        showcaseView.setContentTitle("رزرو اقامتگاه");
                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                        showcaseView.setContentText("بر اساس برنامه سفری که انتخاب کردی، ما بهت پیشنهاد میکنیم تا توی چه شهر هایی شب رو اقامت داشته باشی تا سفر پرخاطره تری داشته باشی");
                        break;
                    }

//                    case 2: {
//                        showcaseView.setShowcase(new ViewTarget(commentHolder), true);
//                        showcaseView.setContentTitle(getString(R.string.tutorialAttractionTitle));
//                        showcaseView.setContentText(getString(R.string.tutorialAttractionText));
//                        showcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
//                        showcaseView.setButtonText("بستن");
//                        break;
//                    }
                    case 2: {
                        showcaseView.setTarget(com.github.amlcurran.showcaseview.targets.Target.NONE);
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
        showcaseView.setContentText("تاریخ شروع سفر");
        showcaseView.setContentTitle("تاریخ آغاز سفرت رو وارد کن");
        showcaseView.forceTextPosition(ShowcaseView.BELOW_SHOWCASE);
        showcaseView.setButtonPosition(lps);

    }

//    changeDateHolder
//            showItinerary1
//            commenttoolbar
}
