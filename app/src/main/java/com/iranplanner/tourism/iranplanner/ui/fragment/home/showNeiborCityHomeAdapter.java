package com.iranplanner.tourism.iranplanner.ui.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iranplanner.tourism.iranplanner.R;
import com.iranplanner.tourism.iranplanner.standard.DataTransferInterface;

import java.util.List;

import entity.HomeNeighborCity;
import tools.Util;


/**
 * Created by HoDA on 7/22/2017.
 */

public class showNeiborCityHomeAdapter extends RecyclerView.Adapter<showNeiborCityHomeAdapter.ViewHolder> {

    Context context;
    int rowLayout;
    DataTransferInterface dtInterface;

    LayoutInflater inflater;

    //    List<HomeLodging> homeLodgings;
    List<HomeNeighborCity> homeNeighborCities;

    public showNeiborCityHomeAdapter(Activity a, DataTransferInterface dtInterface, List<HomeNeighborCity> homeNeighborCities, Context context, int rowLayout) {
        this.homeNeighborCities = homeNeighborCities;
        this.context = context;
        this.rowLayout = rowLayout;
        Activity activity = a;
        this.dtInterface = dtInterface;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public showNeiborCityHomeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.from(viewGroup.getContext()).inflate(R.layout.content_home_small, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView textViewName = holder.textViewName;
        ImageView imageView = holder.imageViewIcon;
        textViewName.setText(homeNeighborCities.get(position).getName());
        if (homeNeighborCities.get(position).getImageUrl() != null) {
            Util.setImageView(String.valueOf(homeNeighborCities.get(position).getImageUrl()), context, imageView, null);
        }
    }


    @Override
    public int getItemCount() {
        return homeNeighborCities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        private ImageView imageViewIcon;


        public ViewHolder(View view) {
            super(view);

            textViewName = (TextView) itemView.findViewById(R.id.txtView);
            imageViewIcon = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}

