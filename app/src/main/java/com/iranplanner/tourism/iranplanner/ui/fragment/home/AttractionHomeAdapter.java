package com.iranplanner.tourism.iranplanner.ui.fragment.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iranplanner.tourism.iranplanner.R;

import java.util.List;

import entity.HomeAttraction;
import tools.Util;


/**
 * Created by HoDA on 7/22/2017.
 */

public class AttractionHomeAdapter extends  RecyclerView.Adapter<AttractionHomeAdapter.MyViewHolder> {

    List<HomeAttraction> homeAttractions;
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewIcon;
        ProgressBar imageLoading;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.txtView);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.image);
            this.imageLoading = (ProgressBar) itemView.findViewById(R.id.imageLoading);
        }
    }

    public AttractionHomeAdapter(List<HomeAttraction> homeAttractions, Context context) {
        this.homeAttractions = homeAttractions;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_attractions_home, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        ImageView imageView = holder.imageViewIcon;
        ProgressBar imageLoading = holder.imageLoading;
        textViewName.setText(homeAttractions.get(listPosition).getAttractionTitle());
        if (homeAttractions.get(listPosition).getImgUrl() != null) {
            Util.setImageView(homeAttractions.get(listPosition).getImgUrl(), context, imageView,imageLoading);
        }
    }

    @Override
    public int getItemCount() {
        return homeAttractions.size();
    }}

