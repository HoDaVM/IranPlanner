package com.iranplanner.tourism.iranplanner.ui.fragment.pandaMap;

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

import entity.ResultLodging;
import entity.ResultPandaMap;
import tools.Util;

//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;


/**
 * Created by Hoda on 10/01/2017.
 */
public class PandaAdapter extends RecyclerView.Adapter<PandaAdapter.ViewHolder> {

    Context context;
    int rowLayout;
    DataTransferInterface dtInterface;
    LayoutInflater inflater;
    List<String> urls;
    private int price;
    List<ResultPandaMap> resultPandaMapList;


    public PandaAdapter(Activity a, List<ResultPandaMap> resultPandaMapList, Context context) {
        this.resultPandaMapList = resultPandaMapList;
        this.context = context;

        Activity activity = a;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public PandaAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.from(viewGroup.getContext()).inflate(R.layout.fragment_panda_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PandaAdapter.ViewHolder viewHolder, int i) {

        viewHolder.txtTitle.setText(Util.persianNumbers(String.valueOf(i + 1)) + "-" + resultPandaMapList.get(i).getPoint().getTitle());
//        viewHolder.txtType.setText(resultPandaMapList.get(i).getPoint().getType());
        viewHolder.txtAddress.setText(resultPandaMapList.get(i).getPoint().getCityTitle());
        if (resultPandaMapList.get(i).getPoint().getImgUrl() != null) {
            viewHolder.imageLoading.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(resultPandaMapList.get(i).getPoint().getImgUrl())
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
                    .into(viewHolder.imgItineraryListMore);
        } else {
            Glide.clear(viewHolder.imgItineraryListMore);
            viewHolder.imgItineraryListMore.setImageDrawable(null);

        }

    }


    @Override
    public int getItemCount() {
        return resultPandaMapList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgItineraryListMore;


        private TextView txtTitle, txtType, txtAddress;
        private ProgressBar imageLoading;


        public ViewHolder(View view) {
            super(view);
            imgItineraryListMore = (ImageView) view.findViewById(R.id.imgItineraryListMore);

            txtTitle = view.findViewById(R.id.txtTitle);
//            txtType = view.findViewById(R.id.txtType);
            txtAddress = view.findViewById(R.id.txtAddress);
            imageLoading = view.findViewById(R.id.imageLoading);

        }
    }

}


