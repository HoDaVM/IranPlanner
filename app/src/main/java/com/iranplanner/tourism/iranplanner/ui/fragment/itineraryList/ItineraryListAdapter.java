package com.iranplanner.tourism.iranplanner.ui.fragment.itineraryList;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.List;

import entity.ResultItinerary;
import tools.Util;

/**
 * Created by Hoda on 10/01/2017.
 */
// in adapter baraye neshon dadane list az itinerary ha hast.
public class ItineraryListAdapter extends RecyclerView.Adapter<ItineraryListAdapter.ViewHolder> {
    private List<ResultItinerary> itineraries;
    Context context;
    int rowLayout;
    DataTransferInterface dtInterface;

    LayoutInflater inflater;

    public ItineraryListAdapter(Activity a, DataTransferInterface dtInterface, List<ResultItinerary> itinerarys, Context context, int rowLayout) {
        this.itineraries = itinerarys;
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
        if (itineraries.get(i).getItineraryFromCityName().equals(itineraries.get(i).getItineraryToCityName())) {
            viewHolder.itinerary_from_city_name.setText(itineraries.get(i).getItineraryFromCityName() + " (گشت درون شهری) ");
        } else {
            viewHolder.itinerary_from_city_name.setText(itineraries.get(i).getItineraryFromCityName() + " - " + itineraries.get(i).getItineraryToCityName());
        }
        viewHolder.itinerary_duration.setText("مدت زمان سفر: "+Util.persianNumbers(itineraries.get(i).getItineraryDuration()) + " روز");
        float perc = Float.valueOf(itineraries.get(i).getItineraryPercentage().get(0).getItineraryAttractionTypePercentage());
        viewHolder.textTpeTravel.setText(itineraries.get(i).getItineraryPercentage().get(0).getItineraryAttractionType());
        int animationDuration = 2500; // 2500ms = 2,5s

        viewHolder.progressMax.setProgressWithAnimation(perc, animationDuration);
        viewHolder.progressMax.setProgress(perc);
        viewHolder.itinerary_transport_name.setText(itineraries.get(i).getItineraryTransportName());
        viewHolder.itinerary_count_attraction.setText(Util.persianNumbers(itineraries.get(i).getItineraryCountAttraction()) + " مکان دیدنی");
        viewHolder.textPercentage.setText((Util.persianNumbers(String.valueOf((int) perc)) + "%"));

        if (itineraries.get(i).getItineraryTransportId().equals("2830")) {
            viewHolder.travelTypePic.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_air_gret));

        } else if (itineraries.get(i).getItineraryTransportId().equals("2831")) {
            viewHolder.travelTypePic.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_train_grey));

        } else if (itineraries.get(i).getItineraryTransportId().equals("2829")) {
            viewHolder.travelTypePic.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_road_grey));

        }

        if (itineraries.get(i).getItineraryImgUrl() != null) {
            String url = itineraries.get(i).getItineraryImgUrl();
//            Glide.with(context).load(url)   .into(viewHolder.imgItineraryList);

            viewHolder.imageLoading.setVisibility(View.VISIBLE);
            Glide.with(context)
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
        return itineraries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener */ {
        private TextView itinerary_from_city_name;
        private TextView itinerary_duration;
        private TextView itinerary_transport_name;
        private TextView itinerary_count_attraction;
        private TextView textTpeTravel, textPercentage, textPercentage1, textPercentage2, textPercentage3;
        private ImageView imgItineraryList;
        private CircularProgressBar progressMax;
        private ProgressBar imageLoading;
        private ImageView travelTypePic;

        public ViewHolder(View view) {
            super(view);
            itinerary_from_city_name = (TextView) view.findViewById(R.id.itinerary_attraction_name);
            itinerary_duration = (TextView) view.findViewById(R.id.itinerary_duration);
            itinerary_transport_name = (TextView) view.findViewById(R.id.itinerary_transport_name);
            textTpeTravel = (TextView) view.findViewById(R.id.textTpeTravel);
            textPercentage = (TextView) view.findViewById(R.id.textPercentage);
            itinerary_count_attraction = (TextView) view.findViewById(R.id.itinerary_count_attraction);
            imgItineraryList = (ImageView) view.findViewById(R.id.imgItineraryListMore);
            travelTypePic = (ImageView) view.findViewById(R.id.travelTypePic);
            progressMax = (CircularProgressBar) view.findViewById(R.id.progressMax);
            imageLoading = (ProgressBar) view.findViewById(R.id.imageLoading);
            imageLoading.setVisibility(View.VISIBLE);


//            progressMax.getIndeterminateDrawable().setColorFilter(
//                    context.getResources().getColor(pink),
//                    itineraries.graphics.PorterDuff.Mode.SRC_IN);
        }

    }




}


