package com.iranplanner.tourism.iranplanner.ui.activity.filterMap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;

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
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;
import com.iranplanner.tourism.iranplanner.ui.activity.MySupportMapFragmen;
import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionListMoreContract;

import com.iranplanner.tourism.iranplanner.ui.activity.attractioListMore.AttractionsMoreListAdapter;

import com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList.ReservationHotelListPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import entity.ResultAttractionList;
import entity.ResultCommentList;
import entity.ShowAtractionDetailMore;
import entity.ShowAttractionListMore;
import entity.ShowAttractionMoreList;
import tools.Util;

/**
 * Created by h.vahidimehr on 23/10/2017.
 */

public class FilterMapAttraction extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, DataTransferInterface, GoogleMap.OnMarkerClickListener, View.OnClickListener,AttractionListMoreContract.View
     /*   , AttractionListMoreContract.View*/ {


    //تهران
    List<ResultAttractionList> attractionsList;
//    @Inject
//    AttractionListMorePresenter attractionListMorePresenter;
    private GoogleMap mMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    private List<Marker> markers;
    int position = -1;
    //    private List<ResultLodging> resultLodgings;
//    private Date startOfTravel;
//    private int durationTravel;
    private String nextOffset;
    //    private String todayDate;
//    private String cityName;
    @BindView(R.id.reservationListRecyclerView)
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    private AttractionsMoreListAdapter adapter;
    ArrayList<LatLng> markerPoints;
    ArrayList<String> markerNames;
    MySupportMapFragmen mapFragment;
    @BindView(R.id.btnSelectPolygon)
    Button btnSelectPolygon;
    @Inject
    ReservationHotelListPresenter reservationHotelListPresenter;


    @Override
    public void onConnected(@Nullable Bundle bundle) {
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    //camera zoom to all of points
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        try {
            if (hasFocus == true) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                mMap.animateCamera(cu);
            }
        } catch (Exception e) {

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

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
        showMarkers(markerPoints);
        googleMap.setOnMarkerClickListener(this);


    }


    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }

        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }

    }

    public void showMarkers(List<LatLng> points) {
        mMap.clear();
        markers = new ArrayList<>();
        int i = 0;
        for (LatLng latLng : points) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(markerNames.get(i)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_point_pink)));
            markers.add(marker);
            i++;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_filter);
        ButterKnife.bind(this);
        mapFragment = (MySupportMapFragmen) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        getExtras();



        setUpRecyclerView();
        btnSelectPolygon.setOnClickListener(this);


    }


    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        attractionsList = (List<ResultAttractionList>) extras.getSerializable("attractionsList");
        nextOffset = extras.getString("nextOffset");
        setPointList();
    }

    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        adapter = new AttractionsMoreListAdapter(this, this, attractionsList, getApplicationContext(), R.layout.content_attraction_list);

//        adapter = new ReseveHotelListAdapter(FilterMapAttraction.this, this, resultLodgings, getApplicationContext(), R.layout.fragment_itinerary_item);
        recyclerView.setAdapter(adapter);
        final SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(horizontalLayoutManagaer);
                    int position = horizontalLayoutManagaer.getPosition(centerView);

                    showMarkers(markerPoints);

                    mMap.addMarker(new MarkerOptions().position(markerPoints.get(position))
                            .title(markerNames.get(position)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_blue_pin)));

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(markerPoints.get(position)));
                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                    mMap.animateCamera(zoom);

                }
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getApplicationContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                showMarkers(markerPoints);
                //open
                String offset = "0";
//                reservationHotelListPresenter.getHotelReserve("full", String.valueOf(resultLodgings.get(position).getLodgingId()), "20", offset, Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));
//                attractionListMorePresenter.getAttractionDetailNear("full", attractionsList.get(position).getResulAttraction().getAttractionId(), "fa", "0", Util.getTokenFromSharedPreferences(getApplicationContext()), Util.getAndroidIdFromSharedPreferences(getApplicationContext()));

            }
        }));


    }

    private void setPointList() {

        markerPoints = new ArrayList<>();
        markerNames = new ArrayList<>();

        for (ResultAttractionList resultAttractionList : attractionsList) {
            if (resultAttractionList.getResulAttraction().getAttractionPositionLat() != null &&
                    resultAttractionList.getResulAttraction().getAttractionPositionLon() != null) {
                LatLng latLng = new LatLng(Double.valueOf(resultAttractionList.getResulAttraction().getAttractionPositionLat()), Double.valueOf(resultAttractionList.getResulAttraction().getAttractionPositionLon()));
                markerPoints.add(latLng);
                markerNames.add(resultAttractionList.getResulAttraction().getAttractionTitle());
            }
        }
//        for ( resultLodging : attractionsList) {
//            if (resultLodging.getLodgingPosLat() != null &&
//                    resultLodging.getLodgingPosLong() != null) {
//                LatLng latLng = new LatLng(Double.valueOf(resultLodging.getLodgingPosLat()), Double.valueOf(resultLodging.getLodgingPosLong()));
//                markerPoints.add(latLng);
//                markerNames.add(resultLodging.getLodgingName());
//            }
//        }


    }


    @Override
    public void setValues(ArrayList<String> al) {
        al.get(0);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        int c = 0;
        for (ResultAttractionList resultLodging : attractionsList) {
            if (resultLodging.getResulAttraction().getAttractionTitle().equals(marker.getTitle())) {
                showMarkers(markerPoints);
                mMap.addMarker(new MarkerOptions().position(markerPoints.get(c))
                        .title(markerNames.get(c)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_blue_pin)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(markerPoints.get(c)));
                Log.e("marker", marker.getTitle());
                position = c;
            }
            c++;
        }
        recyclerView.getLayoutManager().scrollToPosition(position);
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSelectPolygon:
                mMap.getUiSettings().setScrollGesturesEnabled(false);
                break;
        }

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

    }

    @Override
    public void dismissProgress() {

    }


    @Override
    public void showAttractionDetail(ShowAtractionDetailMore showAttractionFull) {

    }

    @Override
    public void ShowAttractionLists(ShowAttractionListMore getAttractionList) {
        Log.e("fd","Fd");

    }


//    @Override
//    public void showComments(ResultCommentList resultCommentList) {
//
//    }

//    @Override
//    public void sendCommentMessage(ResultCommentList resultCommentList) {
//
//    }
//
//    @Override
//    public void showError(String message) {
//
//    }
//
//    @Override
//    public void commentResult(String message) {
//
//    }
//
//    @Override
//    public void showComplete() {
//
//    }
//
//    @Override
//    public void dismissProgress() {
//
//    }
//
//    @Override
//    public void ShowAttractionLists(ShowAttractionMoreList showAttractionList) {
//
//    }
//
//    @Override
//    public void showAttractionDetail(ShowAtractionDetailMore showAttractionFull) {
//
//    }
//
//    @Override
//    public void showProgress() {
//
//    }


}
