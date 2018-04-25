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
import entity.ResultLocalfoodList;
import entity.ResultSouvenirList;
import tools.Util;


/**
 * Created by HoDA on 7/22/2017.
 */

public class souvenirHomeAdapter extends RecyclerView.Adapter<souvenirHomeAdapter.MyViewHolder> {

    List<HomeSouvenir> homeSouvenirs;
    List<ResultSouvenirList> resultSouvenirLists;
    List<ResultLocalfoodList> resultLocalfoodLists;
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

    public souvenirHomeAdapter(List<HomeSouvenir> homeSouvenirs, Context context, List<ResultSouvenirList> resultSouvenirLists, List<ResultLocalfoodList> resultLocalfoodLists) {
        this.homeSouvenirs = homeSouvenirs;
        this.context = context;
        this.resultSouvenirLists=resultSouvenirLists;
        this.resultLocalfoodLists=resultLocalfoodLists;
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
        if (homeSouvenirs != null) {
            textViewName.setText(homeSouvenirs.get(listPosition).getSouvenirsTitle());
            if (homeSouvenirs.get(listPosition).getImgUrl() != null) {
                Util.setImageView(String.valueOf(homeSouvenirs.get(listPosition).getImgUrl()), context, imageView, null);
            }
        }else  if (resultSouvenirLists != null) {
            textViewName.setText(resultSouvenirLists.get(listPosition).getResultSouvenir().getSouvenirName());
            if (resultSouvenirLists.get(listPosition).getResultSouvenir().getSouvenirImgUrl() != null) {
                Util.setImageView(String.valueOf(resultSouvenirLists.get(listPosition).getResultSouvenir().getSouvenirImgUrl()), context, imageView, null);
            }
        }
        else  if (resultLocalfoodLists != null) {
            textViewName.setText(resultLocalfoodLists.get(listPosition).getResultLocalfood().getLocalfoodTitle());
            if (resultLocalfoodLists.get(listPosition).getResultLocalfood().getLocalfoodImgUrl() != null) {
                Util.setImageView(String.valueOf(resultLocalfoodLists.get(listPosition).getResultLocalfood().getLocalfoodImgUrl()), context, imageView, null);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (resultSouvenirLists != null) {
            return resultSouvenirLists.size();
        } else if (homeSouvenirs != null) {
            return homeSouvenirs.size();
        } else if (resultLocalfoodLists != null) {
            return resultLocalfoodLists.size();
        }else return 0;

    }
}

