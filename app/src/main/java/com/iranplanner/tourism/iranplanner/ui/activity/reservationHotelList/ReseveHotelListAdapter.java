package com.iranplanner.tourism.iranplanner.ui.activity.reservationHotelList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import entity.ResultLodging;
import tools.Util;


/**
 * Created by Hoda on 10/01/2017.
 */
public class ReseveHotelListAdapter extends RecyclerView.Adapter<ReseveHotelListAdapter.ViewHolder> {

    Context context;
    int rowLayout;
    DataTransferInterface dtInterface;
    LayoutInflater inflater;
    List<String> urls;
    List<ResultLodging> resultLodgings;
    private int price;


    public ReseveHotelListAdapter(Activity a, DataTransferInterface dtInterface, List<ResultLodging> resultLodgings, Context context, int rowLayout) {
        this.resultLodgings = resultLodgings;
        this.context = context;
        this.rowLayout = rowLayout;
        Activity activity = a;
        this.dtInterface = dtInterface;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public ReseveHotelListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.from(viewGroup.getContext()).inflate(R.layout.activity_reservation_hotel_items_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReseveHotelListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.txtHotelName.setText(resultLodgings.get(i).getLodgingName());
        viewHolder.txtType.setText(resultLodgings.get(i).getLodgingTypeTitle());
        if (resultLodgings.get(i).getLodgingRoomPriceRuleDetail().getLodgingRoomPriceDetailValue() != null && !resultLodgings.get(i).getLodgingRoomPriceRuleDetail().getLodgingRoomPriceDetailValue().equals("")) {
            viewHolder.txtShowPercent.setText(Util.persianNumbers(resultLodgings.get(i).getLodgingRoomPriceRuleDetail().getLodgingRoomPriceDetailValue()) + "تخفیف ");
        }
        if (resultLodgings.get(i).getLodgingRoomPriceDetail().get(0).getLodgingRoomMinPrice() != null) {
            price = resultLodgings.get(i).getLodgingRoomPriceDetail().get(0).getLodgingRoomMinPrice() / 10;

        }

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String s = formatter.format(price);

        viewHolder.txtStartPrice.setText("شروع قیمت از" + " : " + s + " تومان ");

//        viewHolder.txtPrice.setText("شروع قیمت از"+ Utils.persianNumbers(String.valueOf(resultLodgings.get(i).getLodgingRoomPriceDetail().get(0).getLodgingRoomMinPrice()/10))+" تومان");
        if (resultLodgings.get(i).getLodgingRoomPriceRuleDetail().getLodgingRoomPriceDetailType().equals("percent")) {
//            viewHolder.txtShowPercent.setVisibility(View.VISIBLE);
//            viewHolder.txtShowPercent.setText(Utils.persianNumbers( resultLodgings.get(i).getLodgingRoomPriceRuleDetail().getLodgingRoomPriceDetailValue()));
        }
        if (resultLodgings.get(i).getLodgingRateInt().equals("1")) {
            viewHolder.starHolder.setVisibility(View.VISIBLE);
            viewHolder.imgStar1.setVisibility(View.VISIBLE);
        } else if (resultLodgings.get(i).getLodgingRateInt().equals("2")) {
            viewHolder.starHolder.setVisibility(View.VISIBLE);
            viewHolder.imgStar1.setVisibility(View.VISIBLE);
            viewHolder.imgStar2.setVisibility(View.VISIBLE);
        } else if (resultLodgings.get(i).getLodgingRateInt().equals("3")) {
            viewHolder.starHolder.setVisibility(View.VISIBLE);
            viewHolder.imgStar1.setVisibility(View.VISIBLE);
            viewHolder.imgStar2.setVisibility(View.VISIBLE);
            viewHolder.imgStar3.setVisibility(View.VISIBLE);
        } else if (resultLodgings.get(i).getLodgingRateInt().equals("4")) {
            viewHolder.starHolder.setVisibility(View.VISIBLE);
            viewHolder.imgStar1.setVisibility(View.VISIBLE);
            viewHolder.imgStar2.setVisibility(View.VISIBLE);
            viewHolder.imgStar3.setVisibility(View.VISIBLE);
            viewHolder.imgStar4.setVisibility(View.VISIBLE);
        } else if (resultLodgings.get(i).getLodgingRateInt().equals("5")) {
            viewHolder.starHolder.setVisibility(View.VISIBLE);
            viewHolder.imgStar1.setVisibility(View.VISIBLE);
            viewHolder.imgStar2.setVisibility(View.VISIBLE);
            viewHolder.imgStar3.setVisibility(View.VISIBLE);
            viewHolder.imgStar4.setVisibility(View.VISIBLE);
            viewHolder.imgStar5.setVisibility(View.VISIBLE);
        }

        if (resultLodgings.get(i).getLodgingImgUrl() != null) {
//            String url = urls.get(i);

            viewHolder.imageLoading.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(resultLodgings.get(i).getLodgingImgUrl())
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
                    .into(viewHolder.imgItineraryListMore)
            ;
        } else {
            Glide.clear(viewHolder.imgItineraryListMore);
            viewHolder.imgItineraryListMore.setImageDrawable(null);

        }

    }


    @Override
    public int getItemCount() {
        return resultLodgings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgItineraryListMore;
        private ImageView imgStar1, imgStar2, imgStar3, imgStar4, imgStar5;
        RelativeLayout starHolder;
        private TextView txtHotelName, txtType, txtPrice, txtShowPercent, txtStartPrice;
        private ProgressBar imageLoading;


        public ViewHolder(View view) {
            super(view);
            imgItineraryListMore = (ImageView) view.findViewById(R.id.imgItineraryListMore);
            starHolder = (RelativeLayout) view.findViewById(R.id.starHolder);
            imgStar1 = (ImageView) view.findViewById(R.id.imgStar1);
            imgStar2 = (ImageView) view.findViewById(R.id.imgStar2);
            imgStar3 = (ImageView) view.findViewById(R.id.imgStar3);
            imgStar4 = (ImageView) view.findViewById(R.id.imgStar4);
            imgStar5 = (ImageView) view.findViewById(R.id.imgStar5);
            txtHotelName = (TextView) view.findViewById(R.id.txtHotelName);
            txtType = (TextView) view.findViewById(R.id.txtType);
            txtStartPrice = (TextView) view.findViewById(R.id.txtStartPrice);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            txtShowPercent = (TextView) view.findViewById(R.id.txtShowPercent);
            imageLoading = (ProgressBar) view.findViewById(R.id.imageLoading);
            imageLoading.setVisibility(View.VISIBLE);
        }
    }

}


