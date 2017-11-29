package com.iranplanner.tourism.iranplanner.ui.fragment.showAttractionList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.RecyclerItemOnClickListener;
import com.iranplanner.tourism.iranplanner.ui.activity.MapFullActivity;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;
import com.iranplanner.tourism.iranplanner.standard.StandardFragment;
import com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails.attractionDetailActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entity.ItineraryLodgingCity;
import entity.ResulAttraction;
import entity.ResultAttractionList;
import entity.ResultItineraryAttraction;
import entity.ResultItineraryAttractionDay;
import tools.Util;


public class ShowAttractionListFragment extends StandardFragment implements DataTransferInterface {
    private Context context;
    private RecyclerView attractionRecyclerView;
    private AttractionsListAdapter adapter;
    LinearLayoutManager mLayoutManager;
    List<ResultItineraryAttraction> itineraryActionList;
    List<ResultItineraryAttractionDay> resultItineraryAttractionDays;
    ImageView arrowRight, arrowLeft;
    int dayNumber, allDays;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_attraction_list, container, false);
        Bundle bundle = getArguments();
        attractionRecyclerView = (RecyclerView) view.findViewById(R.id.attractionListRecyclerView);
        arrowLeft = (ImageView) view.findViewById(R.id.arrowLeft);
        arrowRight = (ImageView) view.findViewById(R.id.arrowRight);
        TextView textDayNumber = (TextView) view.findViewById(R.id.textDayNumber);
        itineraryActionList = (List<ResultItineraryAttraction>) bundle.getSerializable("itineraryActionList");
        resultItineraryAttractionDays = (List<ResultItineraryAttractionDay>) bundle.getSerializable("resultItineraryAttractionDays");
        dayNumber = bundle.getInt("dayNumber");
        allDays = bundle.getInt("allDays");
        textDayNumber.setText(" روز " + Util.persianNumbers(String.valueOf(dayNumber)) + " از " + Util.persianNumbers(String.valueOf(allDays)));
        attractionRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        attractionRecyclerView.setLayoutManager(layoutManager);
//        adapter = new AttractionsListAdapter(getActivity(), this, itineraryActionList, getContext(), R.layout.fragment_show_attraction_list_detail);
        AttractionsListAdapterDay adapters = new AttractionsListAdapterDay(getActivity(), this, resultItineraryAttractionDays, getContext(), R.layout.fragment_show_attraction_list_detail);
//        attractionRecyclerView.setAdapter(adapter);
        attractionRecyclerView.setAdapter(adapters);
        mLayoutManager = new LinearLayoutManager(getContext());
        attractionRecyclerView.setLayoutManager(mLayoutManager);
        attractionRecyclerView.addOnItemTouchListener(new RecyclerItemOnClickListener(getContext(), new RecyclerItemOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                final ResulAttraction resulAttraction = resultItineraryAttractionDays.get(position).getResulAttraction();
                final List<ResultAttractionList> resultAttractionList = (List<ResultAttractionList>) resultItineraryAttractionDays.get(position).getResultAttractionList();
                Button navigateBtn = (Button) view.findViewById(R.id.navigateBtn);
                navigateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

                        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            buildAlertMessageNoGps(position);
                        } else {
                            openMapFull(position, resulAttraction);
                        }
                    }
                });
                Button moreInfoHolder = (Button) view.findViewById(R.id.moreInfoHolder);
                moreInfoHolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), attractionDetailActivity.class);
                        intent.putExtra("resulAttraction", (Serializable) resulAttraction);
                        intent.putExtra("resultAttractionList", (Serializable) resultAttractionList);
                        startActivity(intent);
                    }
                });

            }
        }));


        attractionRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        Log.v("...", "Last Item Wow !");

                    }
                }
            }
//            }
        });

        return view;
    }


    private void buildAlertMessageNoGps(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        openMapFull(requestCode, null);
//        if (resultCode == 1) {
//            switch (requestCode) {
//                case 1:
//                    Log.e("resultact","ok");
//                    break;
//            }
//        }
    }

    private void openMapFull(int position, ResulAttraction resulAttraction) {

            Intent intent = new Intent(getContext(), MapFullActivity.class);
            ItineraryLodgingCity i = new ItineraryLodgingCity();
//        i.setCityPositionLat(itineraryActionList.get(position).getAttractionPositionLat());
        if(resulAttraction!=null){
            i.setCityPositionLat(resulAttraction.getAttractionPositionLat());
            i.setCityPositionLon(resulAttraction.getAttractionPositionLon());
        }

//        i.setCityPositionLat(resultItineraryAttractionDays.get(0).getResultAttractionList().get(position).getResulAttraction().getAttractionPositionLat());
//        i.setCityPositionLon(itineraryActionList.get(position).getAttractionPositionOn());
//        i.setCityPositionLat(resultItineraryAttractionDays.get(0).getResultAttractionList().get(position).getResulAttraction().getAttractionPositionLon());
            List<ItineraryLodgingCity> lodgingCities = new ArrayList<ItineraryLodgingCity>();
            lodgingCities.add(i);
            intent.putExtra("lodgingCities", (Serializable) lodgingCities);
            startActivity(intent);


    }

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;


    @Override
    public void setValues(ArrayList<String> al) {
        al.get(0);
    }
}
