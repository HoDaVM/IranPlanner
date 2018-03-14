package com.iranplanner.tourism.iranplanner.ui.fragment.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iranplanner.tourism.iranplanner.R;

import java.util.List;

import entity.HomeSouvenir;
import tools.Util;


/**
 * Created by HoDA on 7/22/2017.
 */

public class souvenirHomeAdapter extends RecyclerView.Adapter<souvenirHomeAdapter.MyViewHolder> {

    List<HomeSouvenir> homeSouvenirs;
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewIcon;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.txtView);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public souvenirHomeAdapter(List<HomeSouvenir> homeSouvenirs, Context context) {
        this.homeSouvenirs = homeSouvenirs;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_home_small, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView textViewName = holder.textViewName;
        ImageView imageView = holder.imageViewIcon;
        textViewName.setText(homeSouvenirs.get(listPosition).getSouvenirsTitle());
        if (homeSouvenirs.get(listPosition).getImgUrl() != null) {
            Util.setImageView(String.valueOf(homeSouvenirs.get(listPosition).getImgUrl()), context, imageView,null);
    }
    }

    @Override
    public int getItemCount() {
        return homeSouvenirs.size();
    }
}

