package com.iranplanner.tourism.iranplanner.ui.activity.restaurant;

import android.app.Activity;
import android.content.Context;
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
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;

import java.text.DecimalFormat;
import java.util.List;

import entity.RestaurantList;
import entity.ResultLodging;
import entity.ResultRestaurantList;
import tools.Util;

//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;


/**
 * Created by Hoda on 10/01/2017.
 */
public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {

    Context context;
    int rowLayout;
    DataTransferInterface dtInterface;
    LayoutInflater inflater;
    List<String> urls;
    List<ResultRestaurantList> restaurantLists;
    private int price;


    public RestaurantListAdapter(Activity a, DataTransferInterface dtInterface, List<ResultRestaurantList> restaurantLists, Context context, int rowLayout) {
        this.restaurantLists = restaurantLists;
        this.context = context;
        this.rowLayout = rowLayout;
        Activity activity = a;
        this.dtInterface = dtInterface;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public RestaurantListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.from(viewGroup.getContext()).inflate(R.layout.activity_reservation_hotel_items_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RestaurantListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.txtHotelName.setText(restaurantLists.get(i).getResultRestaurant().getRestaurantTitle());
        if (restaurantLists.get(i).getResultRestaurant().getCityTitle() != null) {
            viewHolder.txtType.setText(restaurantLists.get(i).getResultRestaurant().getCityTitle());
        }
        if (restaurantLists.get(i).getResultRestaurant().getRestaurantTypeTitle() != null && !restaurantLists.get(i).getResultRestaurant().getRestaurantTypeTitle().equals("")) {
            viewHolder.txtShowPercent.setText(restaurantLists.get(i).getResultRestaurant().getRestaurantTypeTitle());
            viewHolder.txtShowPercent.setVisibility(View.VISIBLE);
        }


        if (restaurantLists.get(i).getResultRestaurant().getImgUrl() != null) {
            Util.setImageView(restaurantLists.get(i).getResultRestaurant().getImgUrl(), context, viewHolder.imgItineraryListMore, viewHolder.imageLoading);
        }
        viewHolder.imgType.setVisibility(View.INVISIBLE);

    }


    @Override
    public int getItemCount() {
        return restaurantLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgItineraryListMore,imgType;
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
            imgType =  view.findViewById(R.id.imgType);
            imageLoading.setVisibility(View.VISIBLE);
        }
    }

}


