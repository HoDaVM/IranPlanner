package com.iranplanner.tourism.iranplanner.ui.activity.attractionDetails;

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
import tools.Util;


/**
 * Created by HoDA on 7/22/2017.
 */

public class NearAttractionAdapter extends RecyclerView.Adapter<NearAttractionAdapter.Holder> {

    private Context context;
    private List<ResultAttractionList> resultAttractionList;
    private List<PostNode> postNodes;
    List<PostBlog> PostBlog;

    public NearAttractionAdapter(List<ResultAttractionList> resultAttractionList, Context context, List<PostNode> postNodes, List<PostBlog> PostBlog) {
        this.resultAttractionList = resultAttractionList;
        this.context = context;
        this.postNodes = postNodes;
        this.PostBlog = PostBlog;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_home_small, parent, false);
        if (PostBlog != null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_attractions_home, parent, false);
        }
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int listPosition) {
        // use content_attraction_home layout
        if (PostBlog != null) {
            TextView textViewName = holder.txtView;
            ImageView image = holder.image;
            ProgressBar imageLoading = holder.imageLoading;
            textViewName.setLines(2);
            textViewName.setText(PostBlog.get(listPosition).getTitle());
            if (PostBlog.get(listPosition).getImgUrl() != null)
                Util.setImageView(String.valueOf(PostBlog.get(listPosition).getImgUrl()), context, image, imageLoading);
        }

//use content_home_small
        else {
            TextView textViewName = holder.textViewName;
            TextView txtDistance = holder.txtDistance;
            ImageView imageView = holder.imageViewIcon;
            txtDistance.setVisibility(View.VISIBLE);
            if (postNodes != null) {
                textViewName.setText(postNodes.get(listPosition).getTitle());
                txtDistance.setText(postNodes.get(listPosition).getSubTitle());

                if (postNodes.get(listPosition).getImgUrl() != null)
                    Util.setImageView(String.valueOf(postNodes.get(listPosition).getImgUrl()), context, imageView, null);
            } else if (resultAttractionList != null) {
                textViewName.setText(resultAttractionList.get(listPosition).getResulAttraction().getAttractionTitle());
                txtDistance.setText(Util.persianNumbers(distanceConvert(resultAttractionList.get(listPosition).getResulAttraction().getAttractionDistance())));

                if (resultAttractionList.get(listPosition).getResulAttraction().getAttractionImgUrl() != null)
                    Util.setImageView(String.valueOf(resultAttractionList.get(listPosition).getResulAttraction().getAttractionImgUrl()), context, imageView, null);
            }
        }


    }

    @Override
    public int getItemCount() {
        if (PostBlog!=null && postNodes != null && PostBlog.size() > 0) {
            return postNodes.size();
        } else if (resultAttractionList != null && resultAttractionList.size() > 0) {
            return resultAttractionList.size();
        } else {
            return 0;
        }
    }

    public static class Holder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView txtDistance, txtView;
        ImageView imageViewIcon, image;
        ProgressBar imageLoading;

        public Holder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.txtView);
            this.txtDistance = (TextView) itemView.findViewById(R.id.txtDistance);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.image);
            this.txtView = itemView.findViewById(R.id.txtView);
            this.image = itemView.findViewById(R.id.image);
            this.imageLoading = itemView.findViewById(R.id.imageLoading);
        }
    }

    private String distanceConvert(String distance) {
        String dis;
        if (Float.valueOf(distance) < 1) {
            dis = (int) Math.round(Float.valueOf(distance) * 1000) + "متر";
        } else {
            dis = distance + "کیلومتر";
        }
        return dis;

    }
}

