package com.iranplanner.tourism.iranplanner.ui.activity.globalSearch;

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

import entity.PostBlog;
import entity.PostNode;
import entity.ResultAttractionList;
import entity.ResultGlobalSearch;
import tools.Util;


/**
 * Created by HoDA on 7/22/2017.
 */

public class GlobalSearchAdapter extends RecyclerView.Adapter<GlobalSearchAdapter.Holder> {

    private Context context;
    private List<ResultGlobalSearch> resultGlobalSearch;
    TextView textViewName;
    ImageView imageView;
    ProgressBar imageLoading;


    public GlobalSearchAdapter(List<ResultGlobalSearch> resultGlobalSearch, Context context) {
        this.resultGlobalSearch = resultGlobalSearch;
        this.context = context;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_global_search_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int listPosition) {


        if (resultGlobalSearch.get(listPosition).getType().equals("attraction")) {
            holder.txtType.setText("جاذبه های دیدنی");
            holder.txtAddition.setText(Util.persianNumbers(String.valueOf(resultGlobalSearch.get(listPosition).getProvinceName() + " - " + resultGlobalSearch.get(listPosition).getCityName())));

        } else if (resultGlobalSearch.get(listPosition).getType().equals("blog")) {
            holder.txtType.setText("مجله");
            holder.txtAddition.setText( Util.persianNumbers(resultGlobalSearch.get(listPosition).getAuthorName()));


        } else if (resultGlobalSearch.get(listPosition).getType().equals("restaurant")) {
            holder.txtType.setText("رستوران");
            holder.txtAddition.setText(String.valueOf(resultGlobalSearch.get(listPosition).getProvinceName() + " - " + resultGlobalSearch.get(listPosition).getCityName()));

        } else if (resultGlobalSearch.get(listPosition).getType().equals("lodging")) {
            holder.txtType.setText("اقامتگاه");
            holder.txtAddition.setText(String.valueOf(resultGlobalSearch.get(listPosition).getProvinceName() + " - " + resultGlobalSearch.get(listPosition).getCityName()));

        } else if (resultGlobalSearch.get(listPosition).getType().equals("city")) {
            holder.txtType.setText("شهر");
            holder.txtAddition.setText(String.valueOf( resultGlobalSearch.get(listPosition).getCityName()));

        } else if (resultGlobalSearch.get(listPosition).getType().equals("localfood")) {
            holder.txtType.setText("خوراکی های محلی");
            holder.txtAddition.setText(String.valueOf( resultGlobalSearch.get(listPosition).getProvinceName()));
        } else if (resultGlobalSearch.get(listPosition).getType().equals("souvenir")) {
            holder.txtType.setText("سوغاتی");
            holder.txtAddition.setText(String.valueOf( resultGlobalSearch.get(listPosition).getProvinceName()));
        }
        holder.txtTitle.setText(Util.persianNumbers(String.valueOf(resultGlobalSearch.get(listPosition).getTitle())));
        if(resultGlobalSearch.get(listPosition).getImgUrl()!=null&& !resultGlobalSearch.get(listPosition).getImgUrl().equals("")){
            Util.setImageView(resultGlobalSearch.get(listPosition).getImgUrl(),context,holder.img,null);
        }
    }

    @Override
    public int getItemCount() {
        if (resultGlobalSearch != null && resultGlobalSearch.size() > 0) {
            return resultGlobalSearch.size();
        } else {
            return 0;
        }
    }

    public static class Holder extends RecyclerView.ViewHolder {


        TextView txtType, txtTitle, txtAddition;
        ImageView img;
        ProgressBar imageLoading;

        public Holder(View itemView) {
            super(itemView);
            this.txtType = (TextView) itemView.findViewById(R.id.txtType);
            this.txtAddition = (TextView) itemView.findViewById(R.id.txtAddition);
            this.txtTitle = itemView.findViewById(R.id.txtTitle);
            this.img = itemView.findViewById(R.id.img);
            this.imageLoading = itemView.findViewById(R.id.imageLoading);
        }
    }


}

