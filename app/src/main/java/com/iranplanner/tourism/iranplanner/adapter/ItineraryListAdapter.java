package com.iranplanner.tourism.iranplanner.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;

import java.util.List;

import entity.ItineraryPercentage;
import entity.ResultItinerary;
import tools.Util;

/**
 * Created by Hoda on 10/01/2017.
 */
// in adapter baraye neshon dadane list az itinerary ha hast.
public class ItineraryListAdapter extends RecyclerView.Adapter<ItineraryListAdapter.ViewHolder> {
    private List<ResultItinerary> android;
    Context context;
    int rowLayout;
    DataTransferInterface dtInterface;
    //    public ItineraryListAdapter(List<ResultItinerary> android, Context context, int rowLayout) {
//        this.android = android;
//        this.context = context;
//        this.rowLayout = rowLayout;
//
//    }
    LayoutInflater inflater;

    public ItineraryListAdapter(Activity a, DataTransferInterface dtInterface, List<ResultItinerary> android, Context context, int rowLayout) {
        this.android = android;
        this.context = context;
        this.rowLayout = rowLayout;
        Activity activity = a;
        this.dtInterface = dtInterface;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ItineraryListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.from(viewGroup.getContext()).inflate(R.layout.fragment_itinerary_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItineraryListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.itinerary_from_city_name.setText(android.get(i).getItineraryFromCityName() + " - " + android.get(i).getItineraryToCityName());
        viewHolder.itinerary_duration.setText("مدت زمان سفر " + Util.persianNumbers(android.get(i).getItineraryDuration()) + " روز");
        List<ItineraryPercentage> Percentages = android.get(i).getItineraryPercentage();
        float percentage = 0f;
        String type = null;
        for (ItineraryPercentage itineraryPercentage : Percentages) {
            float a = Float.valueOf(itineraryPercentage.getItineraryAttractionTypePercentage());
            if (a > percentage) {
                percentage = a;
                type = itineraryPercentage.getItineraryAttractionType();
            }
        }
        viewHolder.textTpeTravel.setText(type);
        viewHolder.itinerary_transport_name.setText(android.get(i).getItineraryTransportName());
        viewHolder.itinerary_count_attraction.setText(Util.persianNumbers(android.get(i).getItineraryCountAttraction()) + " مکان دیدنی");

        viewHolder.travelTypePic.setTypeface(Util.getFontAwesome(context));
        viewHolder.progressMax.setMax(100);

        if (android.get(i).getItineraryImgUrl() != null) {
            String url = android.get(i).getItineraryImgUrl();
//            Glide.with(context).load(url)   .into(viewHolder.imgItineraryList);

            viewHolder.imageLoading.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(url)
                    .listener(new RequestListener<String, GlideDrawable>() {

                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                           //// TODO: 22/01/2017  get defeult picture
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            viewHolder.imageLoading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(viewHolder.imgItineraryList)
            ;
        } else {
            Glide.clear(viewHolder.imgItineraryList);
            viewHolder.imgItineraryList.setImageDrawable(null);
        }


    }


    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener */ {
        private TextView itinerary_from_city_name;
        private TextView itinerary_duration;
        private TextView itinerary_transport_name;
        private TextView itinerary_count_attraction;
        private TextView textTpeTravel;
        private ImageView imgItineraryList;
        private ProgressBar progressMax;
        private ProgressBar imageLoading;
        private TextView travelTypePic;

        public ViewHolder(View view) {
            super(view);
            itinerary_from_city_name = (TextView) view.findViewById(R.id.itinerary_attraction_name);
            itinerary_duration = (TextView) view.findViewById(R.id.itinerary_duration);
            itinerary_transport_name = (TextView) view.findViewById(R.id.itinerary_transport_name);
            textTpeTravel = (TextView) view.findViewById(R.id.textTpeTravel);
            itinerary_count_attraction = (TextView) view.findViewById(R.id.itinerary_count_attraction);
            imgItineraryList = (ImageView) view.findViewById(R.id.imgItineraryListMore);
            travelTypePic = (TextView) view.findViewById(R.id.travelTypePic);
            progressMax = (ProgressBar) view.findViewById(R.id.progressMax);
            imageLoading = (ProgressBar) view.findViewById(R.id.imageLoading);
            imageLoading.setVisibility(View.VISIBLE);
            progressMax.setProgress(50);
            progressMax.clearAnimation();
//            progressMax.getIndeterminateDrawable().setColorFilter(
//                    context.getResources().getColor(pink),
//                    android.graphics.PorterDuff.Mode.SRC_IN);
        }

    }


}


